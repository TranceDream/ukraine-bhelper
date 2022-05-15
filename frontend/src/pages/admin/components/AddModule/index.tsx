import { Button, Form, Input, Select } from 'antd'
import React from 'react'
import { reqAddPermission } from '../../api'
import PubSub from '../../Utils/pubsub'

interface Props {
    MapModule: any
    menu: any
    // record: any
}
const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo)
}
const config = {
    rules: [
        {
            type: 'object' as const,
            required: true,
            message: 'Please select time!',
        },
    ],
}
export default function AddModule(props: Props) {
    const onFinish = async (values: any) => {
        values.parentId = props.MapModule[values.parentId]
        values.permissionName = values.name
        const res = await reqAddPermission(values)
        if (res.code === 200) {
            PubSub.publish('addModule', 'success')
        } else {
            PubSub.publish('addModule', 'fail')
        }
        // console.log(values)
        // message.error('没有实现')
        // console.log('Success:', values)
        // const res = await reqUpdateUser({
        //     userId: values.userId,
        //     city: values.city,
        //     country: values.country,
        // })
        // if (res.code === 200) {
        //     PubSub.publish('updateUser', 'success')
        // } else {
        //     PubSub.publish('updateUser', 'fail')
        // }
    }
    return (
        <Form
            name='basic'
            labelCol={{ span: 8 }}
            wrapperCol={{ span: 16 }}
            initialValues={{ remember: true }}
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
            autoComplete='off'>
            {/* <Form.Item
                label='模块ID'
                name='moduleId'
                preserve={false}
                rules={[{ required: true, message: 'Please input moduleId!' }]}>
                <Input />
            </Form.Item> */}

            <Form.Item
                label='父菜单'
                name='parentId'
                preserve={false}
                rules={[{ required: true, message: 'Please input parentId!' }]}>
                <Select
                    // mode='multiple'
                    showArrow
                    // tagRender={tagRender}
                    // defaultValue={['gold', 'cyan']}
                    style={{ width: '100%' }}
                    options={props.menu}
                />
            </Form.Item>

            <Form.Item
                label='模块名'
                name='name'
                preserve={false}
                rules={[
                    { required: true, message: 'Please input module name!' },
                ]}>
                <Input />
            </Form.Item>

            <Form.Item
                label='后端地址'
                name='url'
                preserve={false}
                rules={[{ required: true, message: 'Please input url!' }]}>
                <Input />
            </Form.Item>

            <Form.Item
                label='前端地址'
                name='pagekey'
                preserve={false}
                rules={[{ required: true, message: 'Please input pageKey!' }]}>
                <Input />
            </Form.Item>
            <Form.Item wrapperCol={{ offset: 8, span: 12 }}>
                <Button type='primary' htmlType='submit'>
                    Submit
                </Button>
            </Form.Item>
        </Form>
    )
}
