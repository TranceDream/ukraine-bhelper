/*
 * @Author: Linhao Yu
 * @Date: 2022-05-07 09:52:01
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-07 20:39:44
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
}
const onFinish = (values: any) => {
    console.log('Success:', values)
}

const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo)
}

interface DataType {
    key: React.Key
    roleID: number
    name: string
    authority: string
}
const roles = ['ADMIN', 'EDITOR', 'NORMAL_USER']
const roledetails = [
    {
        key: '1',
        name: '管理员',
        roleID: 10000,
        authority: 'ADMIN',
    },
    {
        key: '2',
        name: '编辑者',
        roleID: 10002,
        authority: 'EDITOR',
    },
    {
        key: '3',
        name: '普通用户',
        roleID: 10001,
        authority: 'NORMAL_USER',
    },
]

// const data: DataType[] = []

// const role_data: DataType[] = []

export default function MuteForm(props: Props) {
    const [selectedRowKeys, setselectedRowKeys] = useState<React.Key[]>([])
    const [data, setData] = useState<DataType[]>([])
    const [roleData, setroleData] = useState<DataType[]>([])
    // const [selectedRows, setselectedRows] = useState<DataType[]>([])
    // const [resData, setResData] = useState<any>()
    const [selectUserId, setselectUserId] = useState(0)
    const [selectUserRoleId, setselectUserRoleId] = useState(0)
    const [selectLockedMark, setlockedMark] = useState(false)
    const [checked, setChecked] = useState(false)
    const [addRolechecked, setaddRolechecked] = useState(false)
    const [visible, setVisible] = React.useState(false)
    const [addRolevisible, setaddRolevisible] = React.useState(false)
    const [confirmLoading, setConfirmLoading] = React.useState(false)
    const [modalText, setModalText] = React.useState('Content of the modal')
    // let selectedRowKeys: string[] = []

    const onChange = async (record: any) => {
        console.log('record', record)
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
        console.log("record", record)
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
        // {
        //     title: 'deletemark',
        //     width: 200,
        //     dataIndex: 'delete',
        //     className: 'notshow',
        // },
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
        // {
        //     title: 'deletemark',
        //     width: 200,
        //     dataIndex: 'delete',
        //     className: 'notshow',
        // },
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

    const handleAddRoleCancel =  () => {
        
        setaddRolevisible(false)
    }
    useEffect(() => {
        let temp: string[] = []
        async function initialize() {
            const res = await reqUserDetail({ userId: props.userId })
            if (res.code === 500) {
                message.error(res.msg)
            } else {
                // setResData(res.data.data)
                initializeColomns(res.data.data)
                res.data.data.roleList.forEach((item: any) => {
                    if (item.roleName === 'ADMIN') {
                        temp.push('1')
                    }
                    if (item.roleName === 'EDITOR') {
                        temp.push('2')
                    }
                    if (item.roleName === 'NORMAL_USER') {
                        temp.push('3')
                    }
                })
                setselectedRowKeys(temp)
            }
        }
        initialize()
    }, [])

    const initializeColomns = (Data: any) => {
        // console.log(data)
        let datatemp: any = []
        let roledatatemp: any = []
        let mentionedIndex: number[] = []
        Data.roleList.forEach((item: any) => {
            const index = roles.indexOf(item.roleName)
            let newobj = Object.assign(roledetails[index], {
                lockedMark: item.lockedMark === 'NO' ? false : true,
                userId: item.userId,
            })
            roledatatemp.push(newobj)
            mentionedIndex.push(index)
        })
        for (let i: number = 0; i <= 2; i++) {
            if (mentionedIndex.indexOf(i) === -1) {
                let newobj = Object.assign(roledetails[i], {
                    userId: Data.userId,
                })
                datatemp.push(newobj)
            }
        }
        setData(datatemp)
        setroleData(roledatatemp)
    }

    // // rowSelection object indicates the need for row selection
    // const rowSelection = {
    //     onChange: (selectedRowKeys: React.Key[], selectedRows: DataType[]) => {
    //         console.log(
    //             `selectedRowKeys: ${selectedRowKeys}`,
    //             'selectedRows: ',
    //             selectedRows
    //         )
    //         setselectedRowKeys(selectedRowKeys)
    //     },
    //     getCheckboxProps: (record: DataType) => ({
    //         disabled: record.name === 'Disabled User', // Column configuration not to be checked
    //         name: record.name,
    //     }),
    // }
    // const changeAuthority = () => {
    //     // ! todo
    // }
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
                    columns={role_columns}
                    dataSource={roleData}
                    pagination={false}
                />
            </div>
            <Divider />
            角色分配
            <div>
                <Table columns={columns} dataSource={data} pagination={false} />
            </div>
            {/* <Col offset={10} span={24}>
                <Button
                    type='primary'
                    style={{ marginTop: 10 }}
                    onClick={changeAuthority}>
                    提交
                </Button>
            </Col> */}
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
