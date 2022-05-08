import { DownOutlined } from '@ant-design/icons'
import type { ActionType, ProColumns } from '@ant-design/pro-table'
import ProTable from '@ant-design/pro-table'
import '@ant-design/pro-table/dist/table.css'
import { Button, message } from 'antd'
import React, { useEffect, useRef, useState } from 'react'
import { reqGetRoleList } from '../../api'
import './ant-pro-card.scss'
import styles from './index.module.scss'

export type TableListItem = {
    key: number
    roelName: string
    roleId: string
    createTime: number
    orderText: string
}

export default function UserControl() {
    const [searchCollapsed, setsearchCollapsed] = useState(false)
    const [roleList, setroleList] = useState({})
    const [tableListDataSource, settableListDataSource] = useState<
        TableListItem[]
    >([]) // 记录操作行的数据

    const ref = useRef<ActionType>()

    useEffect(() => {
        let tempRoleList = Object()
        async function getrolelist() {
            const res = await reqGetRoleList()
            // console.log(res)
            if (res.code === 200) {
                res.data.data.forEach((item: any) => {
                    const _key = item.roleId
                    tempRoleList[_key] = item.roleName
                })
                setroleList(tempRoleList)
            } else {
                message.error('请求用户列表出错：' + res.msg)
            }
        }
        console.log(tempRoleList)
        getrolelist()
    }, [])

    const onCollapse = () => {
        setsearchCollapsed(!searchCollapsed)
    }
    const getData = (data: any) => {
        let temp: TableListItem[]
        temp = []
        data.forEach((item: any) => {
            let newItem: TableListItem
            newItem = { ...item }
            newItem.key = Date.now()
            newItem.createTime = Date.now() - 1000000000
            temp.push(newItem)
        })
        console.log('TableListItem', temp)
        settableListDataSource(temp)
    }

    const columns: ProColumns<TableListItem>[] = [
        {
            title: '用户ID',
            width: 100,
            dataIndex: 'roleId',
            align: 'center',
            render: (_, record) => <a>{_}</a>,
        },
        {
            title: '角色名',
            width: 150,
            dataIndex: 'roleName',
            align: 'center',
            // search: false,
            filters: true,
            onFilter: true,
            valueEnum: roleList,
        },
        {
            title: '创建时间',
            align: 'center',
            width: 190,
            key: 'since',
            dataIndex: 'createTime',
            valueType: 'date',
        },
        {
            title: '排序方式',
            width: 140,
            key: 'orderText',
            dataIndex: 'orderText',
            hideInTable: true,
            valueEnum: {
                'ur.USER_ID asc': '按用户ID升序',
                'ur.USER_ID des': '按用户ID降序',
                'ur.CREATE_TIME asc': '按创建时间升序',
                'ur.CREATE_TIME desc': '按创建时间降序',
            },
        },
        {
            title: '操作',
            width: 50,
            key: 'option',
            valueType: 'option',
            render: (text, record, index) => [<a key='edit'>编辑</a>],
        },
    ]

    return (
        <>
            <ProTable<TableListItem>
                // scroll={{ y: 240 }}
                actionRef={ref}
                columns={columns}
                request={async (params, sorter, filter) => {
                    const msg = await reqGetRoleList({
                        ...params,
                        ...sorter,
                        ...filter,
                    })
                    if (msg.code === 200) {
                        if (msg.data.data) {
                            getData(msg.data.data)
                        } else {
                            settableListDataSource([])
                        }
                        return {
                            data: tableListDataSource,
                            // success 请返回 true，
                            // 不然 table 会停止解析数据，即使有数据
                            success: true,
                            // 不传会使用 data 的长度，如果是分页一定要传
                            total: tableListDataSource.length,
                        }
                    } else {
                        message.error(msg.errormsg)
                        tableListDataSource.length = 0
                        return {
                            data: tableListDataSource,
                            // success 请返回 true，
                            // 不然 table 会停止解析数据，即使有数据
                            success: true,
                            // 不传会使用 data 的长度，如果是分页一定要传
                            total: tableListDataSource.length,
                        }
                    }
                }}
                // dataSource={tableListDataSource}
                rowKey={(record) => {
                    return record.roleId + Date.now().toString() //在这里加上一个时间戳就可以了
                }}
                pagination={{
                    showQuickJumper: true,
                }}
                search={{
                    // optionRender: false,
                    collapsed: searchCollapsed,
                    onCollapse: onCollapse,
                }}
                dateFormatter='string'
                headerTitle='所有角色'
                toolBarRender={() => [
                    <Button key='show'>查看日志</Button>,
                    <Button key='out'>
                        导出数据
                        <DownOutlined />
                    </Button>,
                    <Button type='primary' key='primary'>
                        添加用户
                    </Button>,
                ]}
                className={styles.protable}
            />
        </>
    )
}
