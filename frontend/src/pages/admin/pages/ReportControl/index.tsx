/* eslint-disable jsx-a11y/anchor-is-valid */
/*
 * @Author: Linhao Yu
 * @Date: 2022-04-24 17:18:13
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-11 00:15:59
 */
import ProTable, { ActionType, ProColumns } from '@ant-design/pro-table'
import { message } from 'antd'
import React, { useEffect, useRef, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { reqObjtypeList, reqReportList } from '../../api'
import './ant-pro-card.scss'
import styles from './index.module.scss'
export type TableListItem = {
    key: number
    reportId: number
    objtypeId: number
    defense: number // 被举报人
    reason: string
    prosecution: string // 举报人
    auditStatus: string
    count: number
    createTime: number
}

export default function ReportControl() {
    const navigate = useNavigate()
    const [record, setRecord] = useState<any>({})
    const [tableListDataSource, settableListDataSource] = useState<
        TableListItem[]
    >([]) // 记录操作行的数据
    const [reportType, setReportType] = useState<any>({})
    const [objType, setObjType] = useState<number>(0)
    const [initialValue, setinitialValue] = useState('10006')
    const ref = useRef<ActionType>()

    useEffect(() => {
        async function getReportTypes() {
            const res = await reqObjtypeList()
            if (res.code === 200) {
                console.log(res.data)
                setReportType(res.data)
                setinitialValue(res.data['10006'])
            } else {
                message.error(res.msg)
            }
        }
        getReportTypes()
        //   return () => {
        //     second;
        //   };
    }, [])

    const getdata = (data: any) => {
        // console.log(data)
        let temp: TableListItem[] = []
        data.forEach((item: any) => {
            let newitem: TableListItem
            newitem = { ...item }
            console.log('newitem', newitem)
            newitem.key = item.reportId
            temp.push(newitem)
        })
        // console.table(temp)
        settableListDataSource(data)
    }

    const handleErr = (msg: any) => {
        message.error(msg.errormsg)
    }
    const columns: ProColumns<TableListItem>[] = [
        {
            title: '被举报对象',
            width: 140,
            dataIndex: 'defense',
            align: 'center',
            hideInSearch: true,
            render: (_, record: any) => <a>{_}</a>,
        },
        {
            title: '举报条数',
            align: 'center',
            width: 140,
            hideInSearch: true,
            key: 'count',
            dataIndex: 'count',
        },
        {
            title: '举报模块',
            align: 'center',
            width: 140,
            dataIndex: 'objtypeId',
            initialValue: initialValue,
            hideInTable: true,
            valueEnum: reportType,
        },
        {
            title: '操作',
            width: 180,
            key: 'option',
            valueType: 'option',
            align: 'center',
            render: (_: any, record: any, index: number) => [
                <a
                    key='edit'
                    onClick={() => {
                        navigate(
                            '/admin/report?defense=' +
                                record.defense +
                                '&objtypeId=' +
                                objType,
                            { replace: false }
                        )
                    }}>
                    查看详情
                </a>,
            ],
        },
    ]
    return (
        <ProTable<TableListItem>
            actionRef={ref}
            columns={columns}
            request={async (params, sorter, filter) => {
                params = Object.assign(params, { pageNo: params.current })
                // 表单搜索项会从 params 传入，传递给后端接口。
                // if ('reportId' in params) {
                //     params.reportId = parseInt(params.reportId)
                // }
                // params.objtypeId = 10006
                setObjType(params.objtypeId)
                const msg = await reqReportList({
                    ...params,
                    ...sorter,
                })
                if (msg.code === 200) {
                    if (msg.data.data) {
                        // settableListDataSource([])
                        getdata(msg.data.data)
                    } else {
                        settableListDataSource([])
                    }
                    return {
                        // data: tableListDataSource,
                        // success 请返回 true,
                        // 不然 table 会停止解析数据，即使有数据
                        success: true,
                        // 不传会使用 data 的长度，如果是分页一定要传
                        total: msg.total,
                    }
                } else {
                    handleErr(msg)
                    tableListDataSource.length = 0
                    return {
                        // data: tableListDataSource,
                        // success 请返回 true，
                        // 不然 table 会停止解析数据，即使有数据
                        success: true,
                        // 不传会使用 data 的长度，如果是分页一定要传
                        total: msg.total,
                    }
                }
            }}
            toolbar={{
                multipleLine: false,
                actions: [],
            }}
            dataSource={tableListDataSource}
            rowKey={(record) => {
                return record.defense + Date.now().toString() //在这里加上一个时间戳就可以了
            }}
            pagination={{
                showQuickJumper: true,
            }}
            // search={{
            //     // optionRender: false,
            //     collapsed: searchCollapsed,
            //     onCollapse: onCollapse,
            // }}
            dateFormatter='string'
            headerTitle='未举报信息'
            className={styles.protable}
        />
    )
}
