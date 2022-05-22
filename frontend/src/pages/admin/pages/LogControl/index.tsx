import ProTable, { ProColumns } from '@ant-design/pro-table'
import { message } from 'antd'
import React, { useEffect, useState } from 'react'
import { reqAdminObjtypeList, reqOperationList, reqSyslogList } from '../../api'
import './ant-pro-card.scss'
import styles from './index.module.scss'
export type TableListItem = {
    key: number
    logId: number //!
    createTime: number //!
    deleteMark: string
    objtypeId: number //模块Id
    objId: number // !对象ID
    operator: number //!操作者
    message: string // !
    objtype: string
    operation: string //!
}

const objTypeMap: any = {}
// const operationList: any = {}
export default function LogControl() {
    const [objTypeList, setObjTypeList] = useState<any>({})
    const [operationList, setoperationList] = useState<any>({})
    const [tableListDataSource, settableListDataSource] = useState<
        TableListItem[]
    >([]) // 记录操作行的数据

    useEffect(() => {
        async function prepare() {
            const res1 = await reqAdminObjtypeList()
            if (res1.code === 200) {
                for (let key in res1.data) {
                    objTypeMap[res1.data[key]] = parseInt(key)
                }
                console.table(objTypeMap)
                setObjTypeList(res1.data)
            } else {
                message.error(res1.msg)
            }

            const res2 = await reqOperationList()
            if (res2.code === 200) {
                let temp: any = {}
                res2.data.forEach((item: any) => {
                    temp[item.operId] = item.oper
                })

                setoperationList(temp)
            } else {
                message.error(res2.msg)
            }
        }
        prepare()
    }, [])
    const columns: ProColumns<TableListItem>[] = [
        {
            title: '日志ID',
            width: 50,
            dataIndex: 'logId',
            align: 'center',
            hideInSearch: true,
            render: (_, record) => <a>{_}</a>,
        },
        {
            title: '操作者ID',
            width: 80,
            hideInSearch: true,
            dataIndex: 'operator',
            align: 'center',
        },
        {
            title: '被操作对象ID',
            width: 80,
            hideInSearch: true,
            dataIndex: 'objId',
            align: 'center',
        },

        {
            title: '模块ID',
            width: 80,
            hideInTable: true,
            dataIndex: 'objtypeId',
            align: 'center',
            initialValue: 'USER',
            formItemProps: {
                rules: [
                    {
                        required: true,
                        message: '此项为必填项',
                    },
                ],
            },
            valueEnum: objTypeList,
        },
        {
            title: '创建时间',
            align: 'center',
            width: 140,
            key: 'since',
            hideInSearch: true,
            dataIndex: 'createTime',
            valueType: 'date',
        },
        {
            title: '操作',
            width: 100,
            key: 'operation',
            dataIndex: 'operation',
            valueEnum: operationList,
        },
        {
            title: '内容',
            width: 140,
            hideInSearch: true,
            dataIndex: 'message',
            align: 'left',
        },
    ]

    const getdata = (data: any) => {
        settableListDataSource([])
        let temp: TableListItem[]
        temp = []
        data.forEach((item: any) => {
            let newitem: TableListItem
            newitem = { ...item }
            newitem.key = Date.now()
            // newitem.name = '等待实名'
            item.createAt = new Date(item.createAt).getTime()
            temp.push(newitem)
        })
        settableListDataSource(temp)
    }

    return (
        <>
            <ProTable<TableListItem>
                // actionRef={ref}
                columns={columns}
                request={async (params, sorter, filter) => {
                    if (params.objtypeId === 'USER') {
                        params.objtypeId = objTypeMap[params.objtypeId]
                    } else {
                        params.objtypeId = parseInt(params.objtypeId)
                    }
                    params.operation = parseInt(params.operation)
                    const msg = await reqSyslogList({
                        ...params,
                    })
                    if (msg.code === 200) {
                        if (msg.data.data) {
                            getdata(msg.data.data)
                        } else {
                            settableListDataSource([])
                        }
                        return {
                            data: tableListDataSource,
                            // success 请返回 true，
                            // 不然 table 会停止解析数据，即使有数据
                            success: true,
                            // 不传会使用 data 的长度，如果是分页一定要传
                            total: msg.total,
                        }
                    } else {
                        // handleErr(msg)
                        // tableListDataSource.length = 0
                        settableListDataSource([])
                        return {
                            data: tableListDataSource,
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
