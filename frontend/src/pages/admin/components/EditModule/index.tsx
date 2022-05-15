import { Button, Form, Input, message, Select } from 'antd'
import React from 'react'
import { reqUpdatePermission } from '../../api'
import PubSub from '../../Utils/pubsub'

const onFinish = async (values: any) => {
    message.error('没有实现')
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

interface Props {
    MapModule: any
    record: any
    menu: any
}
const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo)
}
// const config = {
//     rules: [
//         {
//             type: 'object' as const,
//             required: true,
//             message: 'Please select time!',
//         },
//     ],
// }
export default function EditModule(props: Props) {
    const onFinish = async (values: any) => {
        values.parentId = props.MapModule[values.parentId]
        values.permissionId = values.moduleId
        values.permissionName = values.name
        console.table('values', values)
        const res = await reqUpdatePermission(values)
        if (res.code === 200) {
            PubSub.publish('EditModule', 'success')
        } else {
            PubSub.publish('EditModule', 'fail')
        }
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
            <Form.Item
                label='模块ID'
                name='moduleId'
                initialValue={props.record.moduleId}
                preserve={false}>
                <Input disabled={true} />
            </Form.Item>

            <Form.Item
                label='父菜单'
                name='parentId'
                preserve={false}
                rules={[{ required: true, message: 'Please input parentId!' }]}>
                <Select
                    // mode='multiple'
                    showArrow
                    // tagRender={tagRender}
                    // defaultValue={[props.record.title]}
                    style={{ width: '100%' }}
                    options={props.menu}
                />
            </Form.Item>

            <Form.Item
                label='模块名'
                name='name'
                initialValue={props.record.title}
                preserve={false}
                rules={[
                    { required: true, message: 'Please input module name!' },
                ]}>
                <Input />
            </Form.Item>

            <Form.Item
                label='后端地址'
                name='url'
                initialValue={props.record.url}
                preserve={false}
                rules={[{ required: true, message: 'Please input url!' }]}>
                <Input />
            </Form.Item>

            <Form.Item
                label='前端地址'
                name='pagekey'
                initialValue={props.record.location}
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
