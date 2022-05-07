/*
 * @Author: Linhao Yu
 * @Date: 2022-05-07 09:52:01
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-07 23:40:55
 */
import { ExclamationCircleOutlined } from '@ant-design/icons'
import { Divider, Form, Input, message, Modal, Switch, Table } from 'antd'
import React, { useEffect, useState } from 'react'
import { reqAddRole, reqLockUser, reqUserDetail } from '../../api'
interface Props {
    userName: string
    userId: number
    country: string
    city: string
    createTime: string
    roleList: any
}
const onFinish = (values: any) => {
    // console.log('Success:', values)
}

const onFinishFailed = (errorInfo: any) => {
    // console.log('Failed:', errorInfo)
}

interface DataType {
    key: React.Key
    roleID: number
    name: string
    authority: string
}
const roles: string[] = []
const roledata: any[] = []

export default function MuteForm(props: Props) {
    const [hadRoleData, setHadRoleData] = useState<DataType[]>([])
    const [nonAddRoleData, setNonAddRoleData] = useState<DataType[]>([])
    const [selectUserId, setselectUserId] = useState(0)
    const [selectUserRoleId, setselectUserRoleId] = useState(0)
    const [selectLockedMark, setlockedMark] = useState(false)
    const [checked, setChecked] = useState(false)
    const [addRolechecked, setaddRolechecked] = useState(false)
    const [visible, setVisible] = React.useState(false)
    const [addRolevisible, setaddRolevisible] = React.useState(false)
    const [confirmLoading, setConfirmLoading] = React.useState(false)
    const [modalText, setModalText] = React.useState('Content of the modal')
    const [hadRoleLoading, setHadRoleLoading] = React.useState(true)
    const [nonHadRoleLoading, setNonHadRoleLoading] = React.useState(true)

    const onChange = async (record: any) => {
        setVisible(true)
        setselectUserId(record.userId)
        setselectUserRoleId(record.roleID)
        setlockedMark(record.lockedMark)
        if (record.lockedMark) {
            setModalText('确定解冻该用户吗？')
        } else {
            setModalText('确定冻结该用户吗？')
        }
    }

    const addRole = async (record: any) => {
        setaddRolevisible(true)
        setselectUserId(record.userId)
        setselectUserRoleId(record.roleID)
        setModalText('确定给予ID为' + record.userId + '的用户该权限吗？')
    }

    const role_columns = [
        {
            title: '角色名',
            dataIndex: 'name',
            width: 200,
        },
        {
            title: '角色ID',
            dataIndex: 'roleID',
            width: 200,
        },
        {
            title: '权限字符',
            dataIndex: 'authority',
            width: 200,
        },
        {
            title: '操作',
            render: (_: any, record: any) => (
                <Switch
                    defaultChecked={record.lockedMark ? true : false}
                    onClick={() => onChange(record)}
                    checked={checked}></Switch>
            ),
        },
    ]

    const columns = [
        {
            title: '角色名',
            dataIndex: 'name',
            width: 200,
        },
        {
            title: '角色ID',
            dataIndex: 'roleID',
            width: 200,
        },
        {
            title: '权限字符',
            dataIndex: 'authority',
            width: 200,
        },
        {
            title: '操作',
            render: (_: any, record: any) => (
                <Switch
                    defaultChecked={record.lockedMark ? true : false}
                    onClick={() => addRole(record)}
                    checked={addRolechecked}></Switch>
            ),
        },
    ]
    const handleOk = async () => {
        setConfirmLoading(true)
        const res = await reqLockUser({
            userId: selectUserId,
            userRoleId: selectUserRoleId,
            lockedMark: selectLockedMark ? 'NO' : 'YES',
        })
        if (res.code === 200) {
            message.success('成功')
            setChecked(!checked)
        } else {
            message.error(res.msg)
        }
        setVisible(false)
        setConfirmLoading(false)
    }

    const handleCancel = () => {
        // console.log('Clicked cancel button')
        setVisible(false)
    }

    const handleAddRoleOk = async () => {
        setConfirmLoading(true)
        const res = await reqAddRole({
            userId: selectUserId,
            roleId: selectUserRoleId,
        })
        if (res.code == 200) {
            message.success('成功')
            setaddRolechecked(!addRolechecked)
        } else {
            message.error(res.msg)
        }
        setConfirmLoading(false)
        setaddRolevisible(false)
    }

    const handleAddRoleCancel = () => {
        setaddRolevisible(false)
    }
    useEffect(() => {
        async function initialize() {
            const res = await reqUserDetail({ userId: props.userId })
            if (res.code === 500) {
                message.error(res.msg)
            } else {
                initializeColomns(res.data.data)
                setNonHadRoleLoading(false)
                setHadRoleLoading(false)
            }
        }

        roledata.length = 0
        for (let Key in props.roleList) {
            roledata.push({
                // 111: 222,
                key: Key.toString() + Date.now().toString(),
                name: props.roleList[Key],
                roleId: Key,
                authority: props.roleList[Key],
            })
            roles.push(props.roleList[Key])
        }

        initialize()

    }, [])

    const initializeColomns = (Data: any) => {
        // console.log(data)
        let datatemp: any = []
        let roledatatemp: any = []
        let mentionedIndex: number[] = []
        Data.roleList.forEach((item: any) => {
            // console.log("roeldata",roledata)
            const index = roles.indexOf(item.roleName)
            let newobj = Object.assign(roledata[index], {
                lockedMark: item.lockedMark === 'NO' ? false : true,
                userId: item.userId,
            })
            roledatatemp.push(newobj)
            mentionedIndex.push(index)
        })
        for (let i: number = 0; i < roledata.length; i++) {
            if (mentionedIndex.indexOf(i) === -1) {
                let newobj = Object.assign(roledata[i], {
                    userId: Data.userId,
                })
                datatemp.push(newobj)
            }
        }
        setHadRoleData(roledatatemp)
        setNonAddRoleData(datatemp)
    }


    return (
        <>
            <Divider />
            基本信息
            <Form
                name='basic'
                labelCol={{ span: 8 }}
                wrapperCol={{ span: 16 }}
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
                autoComplete='off'>
                <Form.Item
                    label='用户名'
                    name='userName'
                    preserve={false}
                    initialValue={props.userName}>
                    <Input disabled={true} style={{ width: 300 }} />
                </Form.Item>

                <Form.Item
                    label='用户ID'
                    name='userId'
                    preserve={false}
                    initialValue={props.userId}>
                    <Input disabled={true} style={{ width: 300 }} />
                </Form.Item>

                <Form.Item
                    label='国家'
                    name='country'
                    preserve={false}
                    initialValue={props.country}>
                    <Input disabled={true} style={{ width: 300 }} />
                </Form.Item>

                <Form.Item
                    label='城市'
                    name='city'
                    preserve={false}
                    initialValue={props.city}>
                    <Input disabled={true} style={{ width: 300 }} />
                </Form.Item>

                <Form.Item
                    label='创建日期'
                    name='createAt'
                    preserve={false}
                    initialValue={props.createTime}>
                    <Input disabled={true} style={{ width: 300 }} />
                </Form.Item>
            </Form>
            <Divider />
            已有角色
            <div>
                <Table
                    loading={hadRoleLoading}
                    columns={role_columns}
                    dataSource={hadRoleData}
                    pagination={false}
                />
            </div>
            <Divider />
            角色分配
            <div>
                <Table
                    loading={nonHadRoleLoading}
                    columns={columns}
                    dataSource={nonAddRoleData}
                    pagination={false}
                />
            </div>
            {/* 冻结/解冻 */}
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
                visible={visible}
                onOk={handleOk}
                confirmLoading={confirmLoading}
                onCancel={handleCancel}>
                <p>{modalText}</p>
            </Modal>
            {/* 赋权 */}
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
                visible={addRolevisible}
                onOk={handleAddRoleOk}
                confirmLoading={confirmLoading}
                onCancel={handleAddRoleCancel}>
                <p>{modalText}</p>
            </Modal>
        </>
    )
}
