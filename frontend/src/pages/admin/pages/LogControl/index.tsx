import ProTable, { ProColumns } from '@ant-design/pro-table'
import { message } from 'antd'
import React, { useEffect, useState } from 'react'
import { reqAdminObjtypeList } from '../../api'
import './ant-pro-card.scss'
import styles from './index.module.scss'
export type TableListItem = {
    key: number
    LogId: number //!
    createTime: number //!
    deleteMark: string
    objtypeId: number //模块Id
    objId: number // !对象ID
    operator: number //!操作者
    message: string // !
    objtype: string
    operation: string //!
}

export default function LogControl() {
    const [objTypeList, setObjTypeList] = useState<any>({})

    useEffect(() => {
        async function prepare() {
            const res1 = await reqAdminObjtypeList()
            if (res1.code === 200) {
                setObjTypeList(res1.data)
            } else {
                message.error(res1.msg)
            }
        }
        prepare()
    })
    const columns: ProColumns<TableListItem>[] = [
        {
            title: '日志ID',
            width: 50,
            dataIndex: 'LogId',
            align: 'center',
            render: (_, record) => <a>{_}</a>,
        },
        {
            title: '操作者ID',
            width: 80,
            dataIndex: 'operator',
            align: 'center',
        },
        {
            title: '被操作对象ID',
            width: 80,
            dataIndex: 'objId',
            align: 'center',
        },

        {
            title: '模块ID',
            width: 80,
            hideInTable: true,
            dataIndex: 'objtypeId',
            align: 'center',
            valueEnum: objTypeList,
        },
        // {
        //     title: '模块ID',
        //     width: 80,
        //     dataIndex: 'objtype',
        //     align: 'center',
        // },

        {
            title: '创建时间',
            align: 'center',
            width: 140,
            key: 'since',
            dataIndex: 'createTime',
            valueType: 'date',
        },

        // {
        //     title: '排序方式',
        //     width: 140,
        //     key: 'orderText',
        //     dataIndex: 'orderText',
        //     hideInTable: true,
        //     valueEnum: {
        //         'ur.USER_ID asc': '按用户ID升序',
        //         'ur.USER_ID des': '按用户ID降序',
        //         'ur.CREATE_TIME asc': '按创建时间升序',
        //         'ur.CREATE_TIME desc': '按创建时间降序',
        //     },
        // },
        {
            title: '操作',
            width: 100,
            key: 'option',
            valueType: 'option',
            render: (text, record, index) => [
                // <a key='delete' onClick={() => deleteUser(text, record, index)}>
                //     删除
                // </a>,
                // <a key='edit' onClick={() => EditUser(text, record, index)}>
                //     编辑
                // </a>,
                // <a key='change' onClick={() => showChangePwd(record)}>
                //     重置密码
                // </a>,
            ],
        },
        {
            title: '内容',
            width: 140,
            dataIndex: 'message',
            align: 'left',
        },
    ]
    return (
        <>
            <ProTable<TableListItem>
                // actionRef={ref}
                columns={columns}
                // request={async (params, sorter, filter) => {
                //     params = Object.assign(params, { pageNo: params.current })
                //     // 表单搜索项会从 params 传入，传递给后端接口。
                //     if ('userId' in params) {
                //         params.userId = parseInt(params.userId)
                //     }
                //     // setParams(params)
                //     console.log('UseControl: ', params, sorter, filter)
                //     const msg = await reqGetAllUser({
                //         ...params,
                //         ...sorter,
                //     })
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
                //             total: msg.data.total,
                //         }
                //     }
                // }}
                toolbar={{
                    multipleLine: false,
                    actions: [],
                }}
                // dataSource={tableListDataSource}
                // rowKey={(record) => {
                //     return record.userId + Date.now().toString() //在这里加上一个时间戳就可以了
                // }}
                pagination={{
                    showQuickJumper: true,
                }}
                // search={{
                //     // optionRender: false,
                //     collapsed: searchCollapsed,
                //     onCollapse: onCollapse,
                // }}
                dateFormatter='string'
                headerTitle='所有用户'
                className={styles.protable}
            />
        </>
    )
}
