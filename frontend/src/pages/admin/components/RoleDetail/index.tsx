/*
 * @Author: Linhao Yu
 * @Date: 2022-05-08 16:30:09
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-08 16:42:05
 */

import { ExclamationCircleOutlined } from '@ant-design/icons'
import { Divider, Form, Input, message, Modal, Switch, Table } from 'antd'
import React, { useEffect, useState } from 'react'
import { reqPermissionManage, reqRoleDetail } from '../../api'
interface Props {
    roleId: number
}
const onFinish = (values: any) => {
    // console.log('Success:', values)
}

const onFinishFailed = (errorInfo: any) => {
    // console.log('Failed:', errorInfo)
}

interface DataType {}
const roles: string[] = []
const roledata: any[] = []

export default function RoleDetail(props: Props) {
    const [form] = Form.useForm()
    const [baseData, setBaseData] = useState<any>({})
    const [record, serRecord] = useState<any>({})
    const [loading, setLoading] = useState(true)
    const [confirmLoading, setConfirmLoading] = useState(false)
    const [checked, setChecked] = useState<true | false>()
    const [changeStatusVisible, setChangeStatusVisible] = useState(false)
    const [modalText, setmodalText] = useState('')
    const [permissionData, setPermissionData] = useState<any[]>([])
    const role_columns = [
        {
            title: '权限名',
            dataIndex: 'permissionName',
            width: 120,
        },
        {
            title: '权限ID',
            dataIndex: 'permissionId',
            width: 120,
        },
        {
            title: '创建时间',
            dataIndex: 'createTime',
            width: 200,
        },
        {
            title: '地址',
            dataIndex: 'url',
            width: 200,
        },
        {
            title: '操作',
            render: (_: any, record: any) => (
                <Switch
                    defaultChecked={record.lockedMark === 'NO' ? true : false}
                    onClick={() => changeLockdown(record)}
                    checked={checked}></Switch>
            ),
        },
    ]

    // 点击解冻按钮
    const changeLockdown = (record: any) => {
        setChecked(record.lockedMark)
        // console.log('reeee', record)
        setChangeStatusVisible(true)
        serRecord(record)
        setmodalText('确定删除该角色的' + record.roleName + '权限吗？')
        // if (record.lockedMark == 'NO') {
        // }
    }

    // 取消解冻/冻结
    const handleChangeStatusCancel = () => {
        setChangeStatusVisible(false)
    }

    // 确定解冻按钮
    const handleChangeStatusOk = async () => {
        setConfirmLoading(true)
        const res = await reqPermissionManage({
            rolePermissionId: record.rolePermissionId,
            lockedMark: !record.lockedMark,
        })
        if (res.code === 200) {
            message.success('成功')
            setChecked(!checked)
        } else {
            message.error(res.msg)
        }
        setConfirmLoading(false)
        setChangeStatusVisible(false)
    }
    const preparePermissionData = (data: any) => {
        let tempPermissionData: any[] = []
        data.forEach((item: any) => {
            let newItem = { ...item }
            newItem.key = newItem.rolePermissionId
            tempPermissionData.push(newItem)
        })
        setPermissionData(tempPermissionData)
    }

    useEffect(() => {
        async function getPermission() {
            const res = await reqRoleDetail({ roleId: props.roleId })
            console.log('res', res)
            let tempBaseData = Object()
            // tempBaseData = {}
            if (res.code == 200) {
                tempBaseData.roleId = res.data.data.roleId
                tempBaseData.createTime = res.data.data.createTime
                tempBaseData.roleName = res.data.data.roleName
                setBaseData(tempBaseData)
                preparePermissionData(res.data.data.permissions)
                setLoading(false)
            } else {
                message.error(res.msg)
            }
        }
        getPermission()
    }, [])

    useEffect(() => {
        form?.resetFields()
    }, [baseData])

    return (
        <>
            基本信息
            <Form
                form={form}
                name='basic'
                labelCol={{ span: 8 }}
                wrapperCol={{ span: 16 }}
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
                autoComplete='off'>
                <Form.Item
                    label='角色名'
                    name='roleName'
                    initialValue={baseData.roleName}
                    preserve={false}>
                    <Input disabled={true} style={{ width: 300 }} />
                </Form.Item>

                <Form.Item
                    label='角色ID'
                    name='roleId'
                    initialValue={baseData.roleId}
                    preserve={false}>
                    <Input disabled={true} style={{ width: 300 }} />
                </Form.Item>

                <Form.Item
                    label='创建日期'
                    name='createTime'
                    initialValue={baseData.createTime}
                    preserve={false}>
                    <Input disabled={true} style={{ width: 300 }} />
                </Form.Item>
            </Form>
            <Divider />
            权限管理
            <div>
                <Table
                    scroll={{ y: 300 }}
                    loading={loading}
                    columns={role_columns}
                    dataSource={permissionData}
                    pagination={false}
                />
            </div>
            {/* 解冻/冻结 */}
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
                visible={changeStatusVisible}
                onOk={handleChangeStatusOk}
                confirmLoading={confirmLoading}
                onCancel={handleChangeStatusCancel}>
                <p>{modalText}</p>
            </Modal>
        </>
    )
}
