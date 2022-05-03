/*
 * @Author: Linhao Yu
 * @Date: 2022-04-24 17:17:45
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-03 15:42:40
 */
import {
    DownOutlined,
    EditOutlined,
    ExclamationCircleOutlined,
    QuestionCircleOutlined
} from '@ant-design/icons'
import type { ActionType, ProColumns } from '@ant-design/pro-table'
import ProTable from '@ant-design/pro-table'
import '@ant-design/pro-table/dist/table.css'
import {
    Button,
    Dropdown,
    Menu,
    message,
    Modal,
    Space,
    Switch,
    Tooltip
} from 'antd'
import React, { useRef, useState } from 'react'
import { reqGetAllUser } from '../../api'
import ChangePwd from '../../components/ChangePwd'
import EditForm from '../../components/EditForm'
import './ant-pro-card.scss'
import styles from './index.module.scss'

export type TableListItem = {
    key: number
    userId: string
    name: string
    roleName: string
    status: string
    country: string
    city: string
    createTime: number
}
const tableListDataSource: TableListItem[] = []

// !删除用户

const handleErr = (msg: any) => {
    message.error(msg.errormsg)
}

export default function UserControl() {
    const [params, setParams] = useState({})
    const [searchCollapsed, setsearchCollapsed] = useState(false)
    const [visiable, setvisiable] = useState(false)
    const [EditVisiable, setEditVisiable] = useState(false)
    const [ChangePwdVisible, setchangePwdVisible] = useState(false)
    const [Mutevisible, setMutevisible] = useState(false)
    const [confirmLoading, setConfirmLoading] = useState(false)
    const [modalText, setModalText] = useState('初始文字')
    const [selectUserName, setselectUserName] = useState('')
    const [selectUserId, setselectUserId] = useState(0)
    const [record, setRecord] = useState({}) // 记录操作行的数据
    const ref = useRef<ActionType>()


    const getdata = (data: any) => {
        tableListDataSource.length = 0
        console.log('tableListDataSource', tableListDataSource)
        data.forEach((item: any) => {
            let newitem: TableListItem
            newitem = { ...item }
            newitem.key = item.userId
            newitem.name = '等待实名'
            console.log(newitem)
            item.createAt = new Date(item.createAt).getTime()
            tableListDataSource.push(newitem)
        })
        // ref.current.reload()
    }

    const onCollapse = () => {
        setsearchCollapsed(!searchCollapsed)
    }
    const onChange = (record: any) => {
        setRecord(record)
        let text = record.status == 'on' ? '封禁' : '解封'
        setModalText('确定' + text + '该用户吗？')
        setMutevisible(true)
    }

    // 封禁用户
    const handleMuteOk = (record: any) => {
        message.warning(
            '还没实现呢~ReactDOM.render is no longer supported in React 18.'
        )
        record.status = record.status == 'on' ? 'off' : 'on'
        setMutevisible(false)
    }

    //取消封禁用户
    const handleMuteCancel = () => {
        setMutevisible(false)
    }

    // 删除用户
    const deleteUser = (text: any, record: any, index: any) => {
        setvisiable(true)
        setModalText('确定要删除该用户吗？')
        //! params 用于更新的时候传参
        // console.log('user', user)
        // console.log('record', record)
        // console.log('index', index)
        // console.log('params', params)
    }

    // 确认删除用户
    const handleDelOk = () => {
        message.warning(
            '还没实现呢~ReactDOM.render is no longer supported in React 18.'
        )
        setvisiable(false)
    }

    // 取消删除用户
    const handleDelCancel = () => {
        setvisiable(false)
    }

    // 编辑用户
    const EditUser = (text: any, record: any, index: any) => {
        setEditVisiable(true)
        //! params 用于更新的时候传参
        // console.log('params', params)
    }

    // 确认编辑用户
    const handleEditOk = () => {
        message.warning(
            '还没实现呢~ReactDOM.render is no longer supported in React 18.'
        )
        setEditVisiable(false)
    }

    //取消编辑用户
    const handleEditCancel = () => {
        setEditVisiable(false)
    }

    // 重置密码
    const showChangePwd = (record: any) => {
        console.log('1111111111', record)
        setselectUserId(record.userId)
        setselectUserName(record.name)
        setchangePwdVisible(true)
    }

    // 确认重置密码
    const handleChangePwdOk = () => {
        message.warning(
            '还没实现呢~ReactDOM.render is no longer supported in React 18.'
        )
        setchangePwdVisible(false)
    }

    // 取消重置密码
    const handleChangePwdCancel = () => {
        setchangePwdVisible(false)
    }

    const menu = (
        <Menu>
            <Menu.Item key='1' onClick={() => showChangePwd(record)}>
                重置密码
            </Menu.Item>
            <Menu.Divider />
            <Menu.Item key='2'>分配权限</Menu.Item>
        </Menu>
    )

    const columns: ProColumns<TableListItem>[] = [
        {
            title: '用户ID',
            width: 50,
            dataIndex: 'userId',
            align: 'center',
            render: (_) => <a>{_}</a>,
        },
        {
            title: (
                <>
                    用户名
                    <Tooltip placement='top' title='用户的真实姓名'>
                        <QuestionCircleOutlined style={{ marginLeft: 4 }} />
                    </Tooltip>
                </>
            ),
            width: 120,
            dataIndex: 'name',
            align: 'center',
        },
        {
            title: '角色',
            width: 80,
            dataIndex: 'roleName',
            align: 'center',
            // search: false,
            filters: true,
            onFilter: true,
            // hideInTable: true,
            valueEnum: {
                NORMAL_USER: '普通用户',
                Refugee: 'Refugee',
                Editor: 'Editor',
            },
        },
        {
            title: '状态',
            width: 100,
            dataIndex: 'status',
            search: false,
            filters: true,
            onFilter: true,
            // hideInTable: true,
            align: 'center',
            // valueEnum: {
            //     on: { text: '正常', status: 'Success' },
            //     off: { text: '封禁', status: 'Error' },
            // },
            render: (text, record, index) => (
                <Switch
                    checked={record.status == 'on' ? true : false}
                    onChange={() => onChange(record)}
                />
            ),
        },
        {
            title: '国家',
            width: 80,
            dataIndex: 'country',
            align: 'center',
        },
        {
            title: '城市',
            width: 80,
            dataIndex: 'city',
            align: 'center',
        },
        {
            title: '创建时间',
            width: 140,
            key: 'since',
            dataIndex: 'createTime',
            valueType: 'date',
            sorter: (a, b) => a.createTime - b.createTime,
        },
        {
            title: '操作',
            width: 180,
            key: 'option',
            valueType: 'option',
            render: (text, record, index) => [
                <a key='delete' onClick={() => deleteUser(text, record, index)}>
                    删除
                </a>,
                <a key='edit' onClick={() => EditUser(text, record, index)}>
                    编辑
                </a>,
                <Dropdown key={1} overlay={menu}>
                    <a
                        onClick={(e) => {
                            e.preventDefault()
                            setRecord(record)
                            console.log('点击了')
                        }}
                        onMouseEnter={() => {
                            setRecord(record)
                            console.log('hover了')
                        }}>
                        <Space>
                            更多
                            <DownOutlined />
                        </Space>
                    </a>
                </Dropdown>,
            ],
        },
    ]

    return (
        <>
            <ProTable<TableListItem>
                actionRef={ref}
                columns={columns}
                request={async (params, sorter, filter) => {
                    // 表单搜索项会从 params 传入，传递给后端接口。
                    if ('userId' in params) {
                        params.userId = parseInt(params.userId)
                    }
                    setParams(params)
                    console.log('UseControl: ', params, sorter, filter)
                    const msg = await reqGetAllUser({
                        ...params,
                        ...sorter,
                    })
                    if (msg.code === 200) {
                        console.log(msg.data.data)
                        getdata(msg.data.data)
                        console.log('执行完：', tableListDataSource)
                        return {
                            data: tableListDataSource,
                            // success 请返回 true，
                            // 不然 table 会停止解析数据，即使有数据
                            success: true,
                            // 不传会使用 data 的长度，如果是分页一定要传
                            total: tableListDataSource.length,
                        }
                    } else {
                        handleErr(msg)
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
                rowKey='key'
                pagination={{
                    showQuickJumper: true,
                }}
                search={{
                    // optionRender: false,
                    collapsed: searchCollapsed,
                    onCollapse: onCollapse,
                }}
                dateFormatter='string'
                headerTitle='所有用户'
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
            {/* 封禁用户 */}
            <Modal
                title={
                    <>
                        <ExclamationCircleOutlined
                            style={{
                                fontSize: 20,
                                color: '#eb2f96',
                                marginRight: 10,
                            }}
                        />
                        系统提示
                    </>
                }
                visible={Mutevisible}
                onOk={() => handleMuteOk(record)}
                confirmLoading={confirmLoading}
                onCancel={handleMuteCancel}>
                <p>{modalText}</p>
            </Modal>

            {/* 删除用户 */}
            <Modal
                title={
                    <>
                        <ExclamationCircleOutlined
                            style={{
                                fontSize: 20,
                                color: '#eb2f96',
                                marginRight: 10,
                            }}
                        />
                        系统提示
                    </>
                }
                visible={visiable}
                onOk={() => handleDelOk()}
                confirmLoading={confirmLoading}
                onCancel={handleDelCancel}>
                <p>{modalText}</p>
            </Modal>

            {/* 编辑用户信息 */}
            <Modal
                title={
                    <>
                        <EditOutlined
                            style={{
                                fontSize: 20,
                                color: '#000',
                                marginRight: 10,
                            }}
                        />
                        编辑用户信息
                    </>
                }
                visible={EditVisiable}
                onOk={() => handleEditOk()}
                confirmLoading={confirmLoading}
                onCancel={handleEditCancel}
                footer={null}>
                <EditForm />
            </Modal>

            {/* 重置密码 */}
            <Modal
                title={
                    <>
                        <EditOutlined
                            style={{
                                fontSize: 20,
                                color: '#000',
                                marginRight: 10,
                            }}
                        />
                        编辑用户信息
                    </>
                }
                visible={ChangePwdVisible}
                onOk={handleChangePwdOk}
                confirmLoading={confirmLoading}
                onCancel={handleChangePwdCancel}
                footer={null}>
                <ChangePwd userName={selectUserName} userId={selectUserId} />
            </Modal>
        </>
    )
}
