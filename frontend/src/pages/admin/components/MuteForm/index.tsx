/*
 * @Author: Linhao Yu
 * @Date: 2022-05-07 09:52:01
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-07 12:09:45
 */
import { Button, Col, Divider, Form, Input, Table } from 'antd'
import React, { useEffect, useState } from 'react'
interface Props {
    userName: string
    userId: number
    data: []
}
const onFinish = (values: any) => {
    console.log('Success:', values)
}

const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo)
}

const columns = [
    {
        title: '角色名',
        dataIndex: 'name',
    },
    {
        title: '角色ID',
        dataIndex: 'roleID',
    },
    {
        title: '权限字符',
        dataIndex: 'authority',
    },
]

interface DataType {
    key: React.Key
    roleID: number
    name: string
    authority: string
}

const data: DataType[] = [
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
        name: '难民',
        roleID: 10001,
        authority: 'NORMAL_USER',
    },
]

export default function MuteForm(props: Props) {
    const [selectedRowKeys, setselectedRowKeys] = useState(['1', '2'])
    const [selectedRows, setselectedRows] = useState<DataType[]>([])

    useEffect(() => {
        async function initialize() {
            console.log('1111')
            // const res = await reqUserDetail(props.userId)
            // if (res.code === 500) {
            //     message.error(res.msg)
            // } else {
            //     res.roleList.forEach((item: any) => {
            //         if (item.roleName === 'ADMIN') {
            //             selectedRowKeys.push('1')
            //         }
            //         if (item.roleName === 'EDITOR') {
            //             selectedRowKeys.push('2')
            //         }
            //         if (item.roleName === 'NORMAL_USER') {
            //             selectedRowKeys.push('3')
            //         }
            //     })
            // }
        }
        initialize()
    })

    // rowSelection object indicates the need for row selection
    const rowSelection = {
        onChange: (selectedRowKeys: React.Key[], selectedRows: DataType[]) => {
            console.log(
                `selectedRowKeys: ${selectedRowKeys}`,
                'selectedRows: ',
                setselectedRows(selectedRows)
            )
        },
        getCheckboxProps: (record: DataType) => ({
            disabled: record.name === 'Disabled User', // Column configuration not to be checked
            name: record.name,
        }),
    }
    const changeAuthority = () => {
        // ! todo
    }
    return (
        <>
            基本信息
            <Divider />
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
            </Form>
            角色分配
            <Divider />
            <div>
                <Table
                    rowSelection={{
                        type: 'checkbox',
                        defaultSelectedRowKeys: selectedRowKeys,
                        ...rowSelection,
                    }}
                    columns={columns}
                    dataSource={data}
                    pagination={false}
                />
            </div>
            <Col offset={10} span={24}>
                <Button
                    type='primary'
                    style={{ marginTop: 10 }}
                    onClick={changeAuthority}>
                    提交
                </Button>
            </Col>
        </>
    )
}
