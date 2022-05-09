import { DownOutlined, EditOutlined } from '@ant-design/icons'
import type { ActionType, ProColumns } from '@ant-design/pro-table'
import ProTable from '@ant-design/pro-table'
import '@ant-design/pro-table/dist/table.css'
import { Button, message, Modal } from 'antd'
import React, { useEffect, useRef, useState } from 'react'
import { reqGetRoleList } from '../../api'
import AddRole from '../../components/AddRole'
import ChangeRoleName from '../../components/ChangeRoleName'
import RoleDetail from '../../components/RoleDetail'
import PubSub from '../../Utils/pubsub'
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
    const [confirmLoading, setConfirmLoading] = useState(false)
    const [searchCollapsed, setsearchCollapsed] = useState(false)
    const [selectRoleId, setselectRoleId] = useState(0)
    const [roleList, setroleList] = useState({})
    const [EditVisible, setEditVisible] = useState(false)
    const [AddRoleVisible, setAddRoleVisible] = useState(false)
    const [RoleDetailVisible, setRoleDetailVisible] = useState(false)
    const [record, setRecord] = useState<any>({})
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
        // console.log(tempRoleList)
        getrolelist()

        var token = PubSub.subscribe(
            'updateRole',
            (msg: string, data: string) => {
                if (data === 'fail') {
                    message.success('修改失败')
                } else {
                    message.success('修改成功')
                }
                setEditVisible(false)
                ref.current?.reload()
            }
        )

        return () => {
            PubSub.unsubscribe(token)
        }
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
        // console.log('TableListItem', temp)
        settableListDataSource(temp)
    }

    // 编辑名字
    const handleEdit = (record: any) => {
        // console.log('record111', record)
        setRecord(record)
        setEditVisible(true)
    }

    //取消编辑名字
    const handleEditCancel = () => {
        setEditVisible(false)
    }

    // 添加角色
    const handleAddRole = () => {
        setAddRoleVisible(true)
        // todo 获取角色
    }

    // 取消添加角色
    const handleAddRoleCancel = () => {
        setAddRoleVisible(false)
    }

    // 编辑用户详情
    const handleRoleDetail = (record: any) => {
        setselectRoleId(record.roleId)
        setRoleDetailVisible(true)
    }

    //取消编辑用户详情
    const handleRoleDetailCancel = () => {
        setRoleDetailVisible(false)
    }

    const columns: ProColumns<TableListItem>[] = [
        {
            title: '角色ID',
            width: 100,
            dataIndex: 'roleId',
            align: 'center',
            render: (_, record) => (
                <a onClick={() => handleRoleDetail(record)}>{_}</a>
            ),
        },
        {
            title: '角色名',
            width: 150,
            dataIndex: 'roleName',
            align: 'center',
            search: false,
            // filters: true,
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
                'ROLE_ID asc': '按用户ID升序',
                'ROLE_ID desc': '按角色ID降序',
                'CREATE_TIME asc': '按创建时间升序',
                'CREATE_TIME desc': '按创建时间降序',
            },
        },
        {
            title: '操作',
            width: 50,
            key: 'option',
            valueType: 'option',
            render: (text, record, index) => [
                <a key='edit' onClick={() => handleEdit(record)}>
                    编辑
                </a>,
            ],
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
                toolbar={{
                    multipleLine: false,
                    actions: [
                        <Button
                            key='add'
                            type='primary'
                            onClick={() => {
                                handleAddRole()
                            }}>
                            添加角色
                        </Button>,
                    ],
                }}
                dataSource={tableListDataSource}
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

            {/* 编辑角色名称 */}
            <Modal
                destroyOnClose={true}
                title={
                    <>
                        <EditOutlined
                            style={{
                                fontSize: 20,
                                color: '#000',
                                marginRight: 10,
                            }}
                        />
                        编辑
                    </>
                }
                visible={EditVisible}
                confirmLoading={confirmLoading}
                onCancel={handleEditCancel}
                footer={null}>
                <ChangeRoleName
                    roleName={record.roleName}
                    roleId={record.roleId}
                    createTime={record.createTime}
                />
            </Modal>

            {/* 增加角色 */}
            <Modal
                destroyOnClose={true}
                title={
                    <>
                        <EditOutlined
                            style={{
                                fontSize: 20,
                                color: '#000',
                                marginRight: 10,
                            }}
                        />
                        增加角色
                    </>
                }
                visible={AddRoleVisible}
                confirmLoading={confirmLoading}
                onCancel={handleAddRoleCancel}
                footer={null}>
                <AddRole />
            </Modal>

            {/* 详情权限查看 */}
            <Modal
                width={800}
                destroyOnClose={true}
                title={
                    <>
                        <EditOutlined
                            style={{
                                fontSize: 20,
                                color: '#000',
                                marginRight: 10,
                            }}
                        />
                        角色详情
                    </>
                }
                visible={RoleDetailVisible}
                confirmLoading={confirmLoading}
                onCancel={handleRoleDetailCancel}
                footer={null}>
                <RoleDetail roleId={selectRoleId} />
            </Modal>
        </>
    )
}
