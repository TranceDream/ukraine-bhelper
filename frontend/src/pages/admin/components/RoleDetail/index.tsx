/*
 * @Author: Linhao Yu
 * @Date: 2022-05-08 16:30:09
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-08 16:42:05
 */

import { Divider, Form, Input, Switch, Table } from 'antd'
import React, { useEffect, useState } from 'react'
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

interface DataType {}
const roles: string[] = []
const roledata: any[] = []

export default function RoleDetail() {
    const [hadRoleData, setHadRoleData] = useState<DataType[]>([])
    const [hadRoleLoading, setHadRoleLoading] = React.useState(true)

    const role_columns = [
        {
            title: '权限名',
            dataIndex: 'permissionName',
            width: 200,
        },
        {
            title: '权限ID',
            dataIndex: 'permissionId',
            width: 200,
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
            render: (_: any, record: any) => <Switch></Switch>,
        },
    ]

    useEffect(() => {}, [])

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
                    label='权限名'
                    name='permissionName'
                    preserve={false}>
                    <Input disabled={true} style={{ width: 300 }} />
                </Form.Item>

                <Form.Item label='权限ID' name='permissionId' preserve={false}>
                    <Input disabled={true} style={{ width: 300 }} />
                </Form.Item>

                <Form.Item label='创建日期' name='createTime' preserve={false}>
                    <Input disabled={true} style={{ width: 300 }} />
                </Form.Item>

                <Form.Item label='地址' name='url' preserve={false}>
                    <Input disabled={true} style={{ width: 300 }} />
                </Form.Item>
            </Form>
            <Divider />
            权限管理
            <div>
                <Table
                    loading={hadRoleLoading}
                    columns={role_columns}
                    dataSource={hadRoleData}
                    pagination={false}
                />
            </div>
        </>
    )
}
