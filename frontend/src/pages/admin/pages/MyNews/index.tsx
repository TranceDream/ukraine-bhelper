/*
 * @Author: Linhao Yu
 * @Date: 2022-04-24 17:19:29
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-15 23:15:10
 */
import ProTable, { ActionType, ProColumns } from '@ant-design/pro-table'
import { Button, message } from 'antd'
import React, { useEffect, useRef, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import { reqNewsGroups, reqselectYourArticle } from '../../api'
import './ant-pro-card.scss'
import styles from './index.module.scss'
export type TableListItem = {
    key: number
    articleId: number
    deleteMark: string
    author: number
    title: string
    content: string
    status: number
    groupId: number
}

export default function MyNews() {
    const loca = useLocation()
    const [newsGroup, setNewsGroup] = useState<any>({})
    const navigate = useNavigate()
    const [tableListDataSource, settableListDataSource] = useState<
        TableListItem[]
    >([]) // 记录操作行的数据
    const handleAddNews = () => {
        navigate('/admin/news-edit')
    }
    const ref = useRef<ActionType>()

    useEffect(() => {
        async function getNewsList() {
            const res = await reqNewsGroups()
            if (res.code === 200) {
                setNewsGroup(res.data)
            } else {
                message.error(res.msg)
            }
        }
        getNewsList()
    }, [])

    const columns: ProColumns<TableListItem>[] = [
        {
            title: '文章编号',
            width: 50,
            dataIndex: 'articleId',
            align: 'center',
            key: 'articleId',
            // render: (_, record) => (
            //     <a onClick={() => handleMute(record)}>{_}</a>
            // ),
        },
        {
            title: '作者编号',
            width: 50,
            dataIndex: 'author',
            align: 'center',
            key: 'author',
        },
        {
            title: '文章标题',
            width: 50,
            dataIndex: 'title',
            align: 'center',
            key: 'title',
        },
        {
            title: '状态',
            width: 50,
            dataIndex: 'status',
            align: 'center',
            key: 'status',
            valueEnum: {
                1: '待审核',
                2: '审核通过',
                3: '被驳回',
            },
        },
        {
            title: '组编号',
            width: 50,
            dataIndex: 'groupId',
            align: 'center',
            key: 'groupId',
            valueEnum: newsGroup,
        },
        {
            title: '操作',
            width: 180,
            key: 'option',
            valueType: 'option',
            render: (text, record, index) => [
                <a
                    key='edit'
                    onClick={
                        record.status === 1
                            ? pause
                            : () => editNews(record, index)
                    }>
                    {record.status === 1 ? '-----' : '编辑'}
                </a>,
            ],
        },
    ]

    const pause = () => {}

    // 点击编辑
    const editNews = (record: any, index: number) => {
        //  console.log(loca.pathname + '?edit=' + record.articleId)
        navigate('/admin/news-edit' + '?id=' + record.articleId)
    }

    const getdata = (data: any) => {
        // console.log('data', data)
        let temp: TableListItem[] = []
        data.forEach((item: any) => {
            let newitem: TableListItem
            newitem = { ...item }
            newitem.key = item.articleId
            // console.log('newitem', newitem)
            temp.push(newitem)
        })
        // console.table(temp)
        settableListDataSource(data)
    }
    return (
        <>
            <ProTable<TableListItem>
                actionRef={ref}
                columns={columns}
                request={async (params, sorter, filter) => {
                    // 表单搜索项会从 params 传入，传递给后端接口。

                    if ('articleId' in params) {
                        params.articleId = parseInt(params.articleId)
                    }
                    if ('author' in params) {
                        params.author = parseInt(params.author)
                    }
                    if ('groupId' in params) {
                        params.groupId = parseInt(params.groupId)
                    }
                    if ('status' in params) {
                        params.status = parseInt(params.status)
                    }
                    // setParams(params)
                    // console.log('UseControl: ', params, sorter, filter)
                    const msg = await reqselectYourArticle({
                        ...params,
                        ...sorter,
                    })
                    if (msg.code === 200) {
                        if (msg.data.articles) {
                            getdata(msg.data.articles)
                        } else {
                            // settableListDataSource([])
                        }
                        return {
                            data: tableListDataSource,
                            // success 请返回 true，
                            // 不然 table 会停止解析数据，即使有数据
                            success: true,
                            // 不传会使用 data 的长度，如果是分页一定要传
                            total: msg.data.count,
                        }
                    } else {
                        // handleErr(msg)
                        // tableListDataSource.length = 0
                        return {
                            data: tableListDataSource,
                            // success 请返回 true，
                            // 不然 table 会停止解析数据，即使有数据
                            success: true,
                            // 不传会使用 data 的长度，如果是分页一定要传
                            total: msg.data.count,
                        }
                    }
                }}
                toolbar={{
                    multipleLine: false,
                    actions: [
                        <Button
                            key='add'
                            type='primary'
                            onClick={() => {
                                handleAddNews()
                            }}>
                            添加新闻
                        </Button>,
                    ],
                }}
                dataSource={tableListDataSource}
                rowKey={(record) => {
                    return record.articleId + Date.now().toString() //在这里加上一个时间戳就可以了
                }}
                pagination={{
                    showQuickJumper: true,
                }}
                search={
                    {
                        // optionRender: false,
                        // collapsed: searchCollapsed,
                        // onCollapse: onCollapse,
                    }
                }
                dateFormatter='string'
                headerTitle='所有新闻'
                className={styles.protable}
            />
        </>
    )
}
