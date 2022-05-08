/*
 * @Author: Linhao Yu
 * @Date: 2022-05-08 16:08:01
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-08 16:26:52
 */
import { Button, Form, Input, message, Select, Tag } from 'antd'
import React from 'react'

const options = [
    { value: 'gold' },
    { value: 'lime' },
    { value: 'green' },
    { value: 'cyan' },
]

function tagRender(props: any) {
    const { label, value, closable, onClose } = props
    const onPreventMouseDown = (event: any) => {
        event.preventDefault()
        event.stopPropagation()
    }
    return (
        <Tag
            color={value}
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

    const onFinish = (values: any) => {
        message.error('还没有实现')
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
                name='roleId'
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
                name='roleName'
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
                    mode='multiple'
                    showArrow
                    tagRender={tagRender}
                    defaultValue={['gold', 'cyan']}
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
