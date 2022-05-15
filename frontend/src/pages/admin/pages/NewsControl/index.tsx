/*
 * @Author: Linhao Yu
 * @Date: 2022-04-24 17:19:29
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-15 02:24:38
 */
import ProTable from '@ant-design/pro-table'
import { Button } from 'antd'
import React from 'react'
import { reqGetAllUser } from '../../api'
import { TableListItem } from '../RoleControl'
import './ant-pro-card.scss'
import styles from './index.module.scss'
import { useNavigate } from 'react-router-dom'
export default function NewsControl() {
    const navigate = useNavigate()
    const handleAddNews = () => {
        navigate('/admin/news-edit')
    }
    return (
        <>
            <ProTable<TableListItem>
                // actionRef={ref}
                // columns={columns}
                request={async (params, sorter, filter) => {
                    // 表单搜索项会从 params 传入，传递给后端接口。
                    if ('userId' in params) {
                        params.userId = parseInt(params.userId)
                    }
                    // setParams(params)
                    console.log('UseControl: ', params, sorter, filter)
                    const msg = await reqGetAllUser({
                        ...params,
                        ...sorter,
                    })
                    return Promise.resolve({
                        // data: tableListDataSource,
                        success: true,
                    })
                    // if (msg.code === 200) {
                    //     if (msg.data.data) {
                    //         getdata(msg.data.data)
                    //     } else {
                    //         settableListDataSource([])
                    //     }
                    //     return {
                    //         data: tableListDataSource,
                    //         // success 请返回 true，
                    //         // 不然 table 会停止解析数据，即使有数据
                    //         success: true,
                    //         // 不传会使用 data 的长度，如果是分页一定要传
                    //         total: tableListDataSource.length,
                    //     }
                    // } else {
                    //     handleErr(msg)
                    //     tableListDataSource.length = 0
                    //     return {
                    //         data: tableListDataSource,
                    //         // success 请返回 true，
                    //         // 不然 table 会停止解析数据，即使有数据
                    //         success: true,
                    //         // 不传会使用 data 的长度，如果是分页一定要传
                    //         total: tableListDataSource.length,
                    //     }
                    // }
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
                // dataSource={tableListDataSource}
                // rowKey={(record) => {
                //     return record.userId + Date.now().toString() //在这里加上一个时间戳就可以了
                // }}
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
