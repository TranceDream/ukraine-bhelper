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
    userRoleId: number
}
const onFinish = (values: any) => {
    // console.log('Success:', values)
}

const onFinishFailed = (errorInfo: any) => {
    // console.log('Failed:', errorInfo)
}

const roles: string[] = []
const roledata: any[] = []

export default function MuteForm(props: Props) {
    const [hadRoleData, setHadRoleData] = useState<any[]>([])
    const [nonAddRoleData, setNonAddRoleData] = useState<any[]>([])
    const [Index, setIndex] = useState(0)
    const [record, setRecord] = useState<any>({})
    const [visible, setVisible] = React.useState(false)
    const [addRolevisible, setaddRolevisible] = React.useState(false)
    const [confirmLoading, setConfirmLoading] = React.useState(false)
    const [modalText, setModalText] = React.useState('Content of the modal')
    const [hadRoleLoading, setHadRoleLoading] = React.useState(true)
    const [nonHadRoleLoading, setNonHadRoleLoading] = React.useState(true)

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
            render: (_: any, record: any, index: number) => {
                // console.table(record)
                return (
                    <Switch
                        defaultChecked={
                            record.lockedMark === 'NO' ? true : false
                        }
                        onClick={() => onChange(record, index)}
                        checked={
                            record.lockedMark === 'NO' ? true : false
                        }></Switch>
                )
            },
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
            render: (_: any, record: any, index: number) => {
                // console.table(record)
                return (
                    <Switch
                        defaultChecked={
                            record.lockedMark === 'NO' ? true : false
                        }
                        onClick={() => addRole(record, index)}
                        checked={
                            record.lockedMark === 'NO' ? true : false
                        }></Switch>
                )
            },
        },
    ]
    // 点击冻结/解冻用户角色按钮
    const onChange = async (record: any, index: number) => {
        console.log('record', record)
        setVisible(true)
        setRecord(record)
        setIndex(index)
        // setlockedMark(record.lockedMark)
        if (record.lockedMark === 'YES') {
            setModalText('确定解冻用户该角色吗？')
        } else {
            setModalText('确定冻结用户该角色吗？')
        }
    }

    // 确认解冻/冻结用户已有角色
    const handleOk = async () => {
        console.log('1111111111')
        setConfirmLoading(true)
        const res = await reqLockUser({
            userId: record.userId,
            userRoleId: record.userRoleId,
            lockedMark: record.lockedMark === 'YES' ? 'NO' : 'YES',
        })
        if (res.code === 200) {
            message.success('成功')
            let tempHadRoleData = hadRoleData.slice()
            tempHadRoleData[Index].lockedMark =
                tempHadRoleData[Index].lockedMark === 'YES' ? 'NO' : 'YES'
            setHadRoleData(tempHadRoleData)
        } else {
            message.error(res.msg)
        }
        setVisible(false)
        setConfirmLoading(false)
    }

    // 取消冻结/解冻已有角色
    const handleCancel = () => {
        setVisible(false)
    }

    // 点击没有的角色赋权
    const addRole = async (record: any, index: number) => {
        setRecord(record)
        setaddRolevisible(true)
        setIndex(index)
        setModalText('确定给予ID为' + record.userId + '的用户该权限吗？')
    }

    // 确认增加角色
    const handleAddRoleOk = async () => {
        console.log('eww', record)
        setConfirmLoading(true)
        const res = await reqAddRole({
            userId: record.userId,
            roleId: record.roleId,
        })
        if (res.code === 200) {
            message.success('赋权成功')
            let tempNonHadRoleData = nonAddRoleData.slice()
            tempNonHadRoleData[Index].lockedMark =
                tempNonHadRoleData[Index].lockedMark === 'YES' ? 'NO' : 'YES'
            setNonAddRoleData(tempNonHadRoleData)
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
                // console.table(res)
                initializeColomns(res.data.data)
                setNonHadRoleLoading(false)
                setHadRoleLoading(false)
            }
        }

        roledata.length = 0
        console.log('prosp', props.roleList)
        for (let Key in props.roleList) {
            roledata.push({
                key: Key.toString() + Date.now().toString(),
                name: props.roleList[Key],
                roleId: +Key,
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
            // console.log('item', item)
            const index = roles.indexOf(item.roleName)
            let newobj = Object.assign(roledata[index], {
                lockedMark: item.lockedMark,
                userId: item.userId,
                userRoleId: item.id,
            })
            roledatatemp.push(newobj)
            mentionedIndex.push(index)
        })
        for (let i: number = 0; i < roledata.length; i++) {
            if (mentionedIndex.indexOf(i) === -1) {
                let newobj = Object.assign(roledata[i], {
                    lockedMark: 'YES',
                    userId: Data.userId,
                })
                datatemp.push(newobj)
            }
        }
        console.log('roledatatemp', roledatatemp)
        console.log('datatemp', datatemp)
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
