/*
 * @Author: Linhao Yu
 * @Date: 2022-05-08 15:09:36
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-08 15:20:09
 */
import { Button, Form, Input, message } from 'antd'
import React from 'react'
import PubSub from '../../Utils/pubsub'

interface Props {
    roleName: string
    roleId: number
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

export default function ChangeRoleName(props: Props) {
    const [form] = Form.useForm()

    const onFinish = (values: any) => {
        message.error('还没有实现')
        const res = { code: 500 }
        // console.log('Received values of form: ', values)
        if (res.code === 200) {
            PubSub.publish('changeRoleName', 'success')
        }
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
                label='角色ID'
                rules={[
                    {
                        required: true,
                        message: 'Please input your E-mail!',
                    },
                ]}
                initialValue={props.roleId}>
                <Input disabled={true} />
            </Form.Item>

            <Form.Item
                name='roleName'
                label='角色名'
                preserve={false}
                rules={[
                    {
                        required: true,
                        message: '请输入更改的名称',
                    },
                ]}
                initialValue={props.roleName}>
                <Input />
            </Form.Item>

            <Form.Item {...tailFormItemLayout}>
                <Button type='primary' htmlType='submit'>
                    修改
                </Button>
            </Form.Item>
        </Form>
    )
}
