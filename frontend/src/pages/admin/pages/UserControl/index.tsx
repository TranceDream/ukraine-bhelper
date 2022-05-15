/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable jsx-a11y/anchor-is-valid */
/*
 * @Author: Linhao Yu
 * @Date: 2022-04-24 17:17:45
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-11 02:25:11
 */
import {
    EditOutlined,
    ExclamationCircleOutlined,
    QuestionCircleOutlined
} from '@ant-design/icons'
import type { ActionType, ProColumns } from '@ant-design/pro-table'
import ProTable from '@ant-design/pro-table'
import '@ant-design/pro-table/dist/table.css'
import { message, Modal, Tooltip } from 'antd'
import React, { useEffect, useRef, useState } from 'react'
import { reqDelUser, reqGetAllUser, reqGetRoleList } from '../../api'
import ChangePwd from '../../components/ChangePwd'
import EditForm from '../../components/EditForm'
import MuteForm from '../../components/MuteForm'
import PubSub from '../../Utils/pubsub'
import './ant-pro-card.scss'
import styles from './index.module.scss'

export type TableListItem = {
    key: number
    userId: number
    name: string
    roleId: string
    status: string
    country: string
    city: string
    createTime: number
    orderText: string
}
// const tableListDataSource: TableListItem[] = []

// !删除用户

const handleErr = (msg: any) => {
    message.error(msg.errormsg)
}

export default function UserControl() {
    const [searchCollapsed, setsearchCollapsed] = useState(false)
    const [visiable, setvisiable] = useState(false)
    const [EditVisiable, setEditVisiable] = useState(false)
    const [ChangePwdVisible, setchangePwdVisible] = useState(false)
    const [Mutevisible, setMutevisible] = useState(false)
    const [confirmLoading, setConfirmLoading] = useState(false)
    const [modalText, setModalText] = useState('初始文字')
    // const [selectUserName, setselectUserName] = useState('')
    // const [selectUserId, setselectUserId] = useState(0)
    // const [selectCountry, setselectCountry] = useState('')
    // const [selectCity, setselectCity] = useState('')
    // const [selectCreateTime, setselectCreateTime] = useState('')
    const [record, setRecord] = useState<any>({}) // 记录操作行的数据
    const [roleList, setroleList] = useState({})
    const [tableListDataSource, settableListDataSource] = useState<
        TableListItem[]
    >([]) // 记录操作行的数据

    const ref = useRef<ActionType>()

    useEffect(() => {
        let temp = Object()
        async function getrolelist() {
            const res = await reqGetRoleList()
            // console.log(res)
            if (res.code === 200) {
                res.data.data.forEach((item: any) => {
                    const _key = item.roleId
                    temp[_key] = item.roleName
                })
                setroleList(temp)
            } else {
                message.error('请求用户列表出错：' + res.msg)
            }
        }
        getrolelist()
        var token = PubSub.subscribe(
            'updateUser',
            (msg: string, data: string) => {
                if (data === 'success') {
                    message.success('修改成功')
                } else {
                    message.error('修改失败')
                }
                setEditVisiable(false)
                ref.current?.reload()
            }
        )

        return () => {
            PubSub.unsubscribe(token)
        }
    }, [])

    const getdata = (data: any) => {
        let temp: TableListItem[]
        temp = []
        data.forEach((item: any) => {
            let newitem: TableListItem
            newitem = { ...item }
            newitem.key = Date.now()
            newitem.name = '等待实名'
            item.createAt = new Date(item.createAt).getTime()
            temp.push(newitem)
        })

        settableListDataSource(temp)
    }

    const onCollapse = () => {
        setsearchCollapsed(!searchCollapsed)
    }

    //封禁用户
    const handleMute = (record: any) => {
        // console.log('record', record)
        setRecord(record)
        setMutevisible(true)
        // setselectUserName(record.name)
        // setselectUserId(record.userId)
        // setselectCreateTime(record.createTime)
        // setselectCity(record.city)
        // setselectCountry(record.country)
    }

    // 封禁用户
    const handleMuteOk = (record: any) => {
        setMutevisible(false)
    }

    //取消封禁用户
    const handleMuteCancel = () => {
        setMutevisible(false)
    }

    // 删除用户
    const deleteUser = (text: any, record: any, index: any) => {
        setRecord(record)
        setvisiable(true)
        setModalText('确定要删除该用户吗？')
    }

    // 确认删除用户
    const handleDelOk = async () => {
        console.table(record)
        const res = await reqDelUser({ userId: record.userId })
        if (res.code === 200) {
            message.success('删除用户成功')
            ref.current?.reload()
        } else {
            message.error(res.msg)
        }
        setvisiable(false)
    }

    // 取消删除用户
    const handleDelCancel = () => {
        setvisiable(false)
    }

    // 编辑用户
    const EditUser = (text: any, record: any, index: any) => {
        // console.log('编辑用户', record)
        setEditVisiable(true)
        setRecord(record)
        // setselectCity(record.city)
        // setselectCountry(record.country)
        // setselectUserId(record.userId)
        // setselectUserName(record.name)
    }

    //取消编辑用户
    const handleEditCancel = () => {
        setEditVisiable(false)
    }

    // 重置密码
    const showChangePwd = (record: any) => {
        setRecord(record)
        // setselectUserId(record.userId)
        // setselectUserName(record.name)
        setchangePwdVisible(true)
    }

    // 取消重置密码
    const handleChangePwdCancel = () => {
        setchangePwdVisible(false)
    }

    const columns: ProColumns<TableListItem>[] = [
        {
            title: '用户ID',
            width: 50,
            dataIndex: 'userId',
            align: 'center',
            render: (_, record) => (
                <a onClick={() => handleMute(record)}>{_}</a>
            ),
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
            dataIndex: 'roleId',
            align: 'center',
            // search: false,
            filters: true,
            onFilter: true,
            hideInTable: true,
            valueEnum: roleList,
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
            align: 'center',
            width: 140,
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
                <a key='change' onClick={() => showChangePwd(record)}>
                    重置密码
                </a>,
            ],
        },
    ]

    return (
        <>
            <ProTable<TableListItem>
                actionRef={ref}
                columns={columns}
                request={async (params, sorter, filter) => {
                    params = Object.assign(params, { pageNo: params.current })
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
                            total: msg.data.total,
                        }
                    }
                }}
                toolbar={{
                    multipleLine: false,
                    actions: [],
                }}
                dataSource={tableListDataSource}
                rowKey={(record) => {
                    return record.userId + Date.now().toString() //在这里加上一个时间戳就可以了
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
                headerTitle='所有用户'
                className={styles.protable}
            />
            {/* 封禁用户 */}
            <Modal
                width={800}
                destroyOnClose={true}
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
                footer={null}
                onCancel={handleMuteCancel}>
                <MuteForm
                    userName={record.name}
                    userId={record.userId}
                    country={record.country}
                    city={record.city}
                    createTime={record.createTime}
                    roleList={roleList}
                    userRoleId={record.id}
                />
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
                        编辑用户信息
                    </>
                }
                visible={EditVisiable}
                confirmLoading={confirmLoading}
                onCancel={handleEditCancel}
                footer={null}>
                <EditForm
                    userName={record.name}
                    userId={record.userId}
                    country={record.country}
                    city={record.city}
                />
            </Modal>

            {/* 重置密码 */}
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
                        重置密码
                    </>
                }
                visible={ChangePwdVisible}
                confirmLoading={confirmLoading}
                onCancel={handleChangePwdCancel}
                footer={null}>
                <ChangePwd userName={record.name} userId={record.userId} />
            </Modal>
        </>
    )
}
