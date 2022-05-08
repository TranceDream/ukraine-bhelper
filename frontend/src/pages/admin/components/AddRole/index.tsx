/*
 * @Author: Linhao Yu
 * @Date: 2022-05-08 16:08:01
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-08 16:26:52
 */
import { Button, Form, Input, message, Select, Tag } from 'antd'
import React, { useEffect, useState } from 'react'
import { reqAddNewRole, reqPermissionList } from '../../api'

// const Options = [
//     { value: 'gold' },
//     { value: 'lime' },
//     { value: 'green' },
//     { value: 'cyan' },
// ]

let permissionMapId: any = {}

function tagRender(props: any) {
    const colors = ['gold', 'lime', 'green', 'cyan']
    const { label, value, closable, onClose } = props
    const onPreventMouseDown = (event: any) => {
        event.preventDefault()
        event.stopPropagation()
    }
    return (
        <Tag
            color={colors[Math.floor(Math.random() * 4)]}
            onMouseDown={onPreventMouseDown}
            closable={closable}
            onClose={onClose}
            style={{ marginRight: 3 }}>
            {label}
        </Tag>
    )
}

const formItemLayout = {
    labelCol: {
        xs: { span: 24 },
        sm: { span: 8 },
    },
    wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
    },
}
const tailFormItemLayout = {
    wrapperCol: {
        xs: {
            span: 24,
            offset: 6,
        },
        sm: {
            span: 16,
            offset: 10,
        },
    },
}

export default function AddRole() {
    const [form] = Form.useForm()
    const [options, setOptions] = useState<any[]>([])
    const [selectedPermission, setSelectedPermission] = useState<any[]>([])
    const [loading, setLoading] = useState(false)

    const onFinish = async (values: any) => {
        setLoading(true)
        const res = await reqAddNewRole({
            roleName: values.roleName,
            permissions: selectedPermission,
        })
        if (res.code === 200) {
            message.success('添加成功')
        } else {
            message.error(res.msg)
        }
        // console.log('vvvvv', values)

        setLoading(false)
    }

    useEffect(() => {
        for (let key in permissionMapId) {
            delete permissionMapId[key]
        }
        async function getPermissions() {
            const res = await reqPermissionList()
            console.log('res', res)
            if (res.code === 200) {
                prepareOptions(res.data.data)
            } else {
                message.error(res.msg)
            }
        }
        getPermissions()
    }, [])
    const prepareOptions = (data: any) => {
        let tempOptions: any[] = []
        data.forEach((item: any) => {
            let tempOption = Object()
            tempOption = {}
            tempOption.value = item.permissionName
            tempOptions.push(tempOption)
            permissionMapId[item.permissionName] = item.permissionId
        })
        setOptions(tempOptions)
        console.log('permissionMapId', permissionMapId)
    }

    // 权限选择改变
    const handleChange = (value: any) => {
        console.log('changed', value)
        const tempselectedPermission: any = []
        value.forEach((item: any) => {
            console.log('item', item)
            tempselectedPermission.push(permissionMapId[item])
        })
        setSelectedPermission(tempselectedPermission)
        // console.log('1111111111', selectedPermission)
    }
    return (
        <Form
            {...formItemLayout}
            form={form}
            name='changePassword'
            onFinish={onFinish}
            preserve={false}
            scrollToFirstError>
            <Form.Item
                name='roleName'
                label='角色名称'
                rules={[
                    {
                        required: true,
                        message: '请输入角色名称',
                    },
                ]}
                // initialValue={props.roleId}
            >
                <Input />
            </Form.Item>

            <Form.Item
                name='rolePermissions'
                label='角色权限'
                preserve={false}
                rules={[
                    {
                        required: true,
                        message: '请选择角色权限',
                    },
                ]}
                // initialValue={props.roleName}
            >
                <Select
                    onChange={handleChange}
                    mode='multiple'
                    showArrow
                    tagRender={tagRender}
                    // defaultValue={['gold', 'cyan']}
                    style={{ width: '100%' }}
                    options={options}
                />
            </Form.Item>

            <Form.Item {...tailFormItemLayout}>
                <Button type='primary' htmlType='submit'>
                    修改
                </Button>
            </Form.Item>
        </Form>
    )
}
