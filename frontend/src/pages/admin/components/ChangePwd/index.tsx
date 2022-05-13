/*
 * @Author: Linhao Yu
 * @Date: 2022-05-03 15:36:14
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-03 17:20:12
 */
import { Button, Form, Input, message } from 'antd'
import React from 'react'
import { reqChangePwd } from '../../api'

interface Props {
    userName: string
    userId: number
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

export default function ChangePwd(props: Props) {
    const [form] = Form.useForm()

    const onFinish = async (values: any) => {
        console.table(values)
        let params = Object()
        params = {}
        params['userId'] = props.userId
        params['credential'] = values.password
        console.log(params)
        const res = await reqChangePwd(params)
        if (res.code === 200) {
            message.success('修改成功')
        } else {
            message.error(res.msg)
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
                name='id'
                label='用户ID'
                rules={[
                    {
                        required: true,
                        message: 'Please input your E-mail!',
                    },
                ]}
                initialValue={props.userId}>
                <Input disabled={true} />
            </Form.Item>

            <Form.Item
                name='username'
                label='用户名'
                preserve={false}
                rules={[
                    {
                        required: true,
                        message: 'Please input your E-mail!',
                    },
                ]}
                initialValue={props.userName}>
                <Input disabled={true} />
            </Form.Item>
            <Form.Item
                name='password'
                label='新密码'
                preserve={false}
                rules={[
                    {
                        required: true,
                        message: 'Please input your password!',
                    },
                ]}
                hasFeedback>
                <Input.Password />
            </Form.Item>

            <Form.Item
                name='confirm'
                label='确认密码'
                preserve={false}
                dependencies={['password']}
                hasFeedback
                rules={[
                    {
                        required: true,
                        message: 'Please confirm your password!',
                    },
                    ({ getFieldValue }) => ({
                        validator(_, value) {
                            if (!value || getFieldValue('password') === value) {
                                return Promise.resolve()
                            }
                            return Promise.reject(
                                new Error(
                                    'The two passwords that you entered do not match!'
                                )
                            )
                        },
                    }),
                ]}>
                <Input.Password />
            </Form.Item>
            <Form.Item {...tailFormItemLayout}>
                <Button type='primary' htmlType='submit'>
                    修改
                </Button>
            </Form.Item>
        </Form>
    )
}
