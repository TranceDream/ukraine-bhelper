/*
 * @Author: Linhao Yu
 * @Date: 2022-04-24 17:18:13
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-11 00:15:59
 */
import ProTable, { ActionType, ProColumns } from '@ant-design/pro-table'
import React, { useRef } from 'react'
import './ant-pro-card.scss'
import styles from './index.module.scss'
export type TableListItem = {
    key: number
    reportId: number
    objtypeId: number
    defense: number
    reason: string
    prosecution: string // 举报人
    auditStatus: string // 被举报人
    count: number
    createTime: number
}

export default function ReportControl() {
    const ref = useRef<ActionType>()

    const viewReport = (_: any, record: any, index: number) => {}

    const columns: ProColumns<TableListItem>[] = [
        {
            title: '举报信息ID',
            width: 80,
            dataIndex: 'reportId',
            align: 'center',
            render: (_, record: any) => <a>{_}</a>,
        },
        {
            title: '举报者',
            align: 'center',
            width: 140,
            key: 'prosecution',
            dataIndex: 'prosecution',
        },
        {
            title: '举报时间',
            align: 'center',
            width: 140,
            key: 'since',
            dataIndex: 'createTime',
            valueType: 'date',
        },
        {
            title: '举报理由',
            align: 'center',
            width: 140,
            dataIndex: 'reason',
        },
        {
            title: '操作',
            width: 180,
            key: 'option',
            valueType: 'option',
            render: (_: any, record: any, index: number) => [
                <a key='edit' onClick={() => viewReport(_, record, index)}>
                    审核
                </a>,
            ],
        },
    ]
    return (
        <ProTable<TableListItem>
            actionRef={ref}
            columns={columns}
            request={async (params, sorter, filter) => {
                // 表单搜索项会从 params 传入，传递给后端接口。
                if ('reportId' in params) {
                    params.reportId = parseInt(params.reportId)
                }
                return Promise.resolve({
                    //   data: tableListDataSource,
                    success: true,
                })
                // const msg = await reqGetAllUser({
                //     ...params,
                //     ...sorter,
                // })
                //     if (msg.code === 200) {
                //         if (msg.data.data) {
                //             getdata(msg.data.data)
                //         } else {
                //             settableListDataSource([])
                //         }
                //         return {
                //             data: tableListDataSource,
                //             // success 请返回 true，
                //             // 不然 table 会停止解析数据，即使有数据
                //             success: true,
                //             // 不传会使用 data 的长度，如果是分页一定要传
                //             total: tableListDataSource.length,
                //         }
                //     } else {
                //         handleErr(msg)
                //         tableListDataSource.length = 0
                //         return {
                //             data: tableListDataSource,
                //             // success 请返回 true，
                //             // 不然 table 会停止解析数据，即使有数据
                //             success: true,
                //             // 不传会使用 data 的长度，如果是分页一定要传
                //             total: tableListDataSource.length,
                //         }
                //     }
            }}
            toolbar={{
                multipleLine: false,
                actions: [],
            }}
            // dataSource={tableListDataSource}
            rowKey={(record) => {
                return record.reportId + Date.now().toString() //在这里加上一个时间戳就可以了
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
