/*
 * @Author: Linhao Yu
 * @Date: 2022-05-11 01:30:31
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-11 02:48:45
 */

import { Button, Form, Input } from 'antd'
import TextArea from 'antd/lib/input/TextArea'
import React from 'react'
import { reqUpdateHouse } from '../../../api'
import PubSub from '../../../Utils/pubsub'

interface Props {
    record: any
    // houseId: number
    // country: string
    // city: string
    // province: string
    // address: string
    // guests: number
    // duration: number
    // description:string
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

export default function EditHouseModal(props:Props) {
    const [form] = Form.useForm()
    // console.log(props)
    const onFinish = async (values: any) => {
        const res = await reqUpdateHouse({
            ...values
        })
        // console.log('Received values of form: ', values)
        if (res.code === 200) {
            PubSub.publish('updateHouse', 'success')
        } else {
            // message.error('还没有实现')
            PubSub.publish('updateHouse', 'fail')
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
                name='houseId'
                label='房源ID'
                preserve={false}
                rules={[
                    {
                        required: true,
                        message: '请输入信息',
                    },
                ]}
                initialValue={props.record.houseId}>
                <Input disabled={true} />
            </Form.Item>

            <Form.Item
                name='country'
                label='所在国家'
                preserve={false}
                rules={[
                    {
                        required: true,
                        message: '请输入国家',
                    },
                ]}
                initialValue={props.record.country}>
                <Input />
            </Form.Item>

            <Form.Item
                name='province'
                label='所在省份'
                preserve={false}
                rules={[
                    {
                        required: true,
                        message: '请输入省份',
                    },
                ]}
                initialValue={props.record.province}>
                <Input />
            </Form.Item>

            <Form.Item
                name='city'
                label='城市'
                preserve={false}
                rules={[
                    {
                        required: true,
                        message: '请输入城市',
                    },
                ]}
                initialValue={props.record.city}>
                <Input />
            </Form.Item>

            <Form.Item
                name='address'
                label='地址'
                preserve={false}
                rules={[
                    {
                        required: true,
                        message: '请输入地址',
                    },
                ]}
                initialValue={props.record.address}>
                <Input />
            </Form.Item>

            <Form.Item
                name='guests'
                label='接纳人数'
                preserve={false}
                rules={[
                    {
                        required: true,
                        message: '请输入人数',
                    },
                ]}
                initialValue={props.record.guests}>
                <Input />
            </Form.Item>

            <Form.Item
                name='duration'
                label='接纳时长'
                preserve={false}
                rules={[
                    {
                        required: true,
                        message: '请输入时长',
                    },
                ]}
                initialValue={props.record.duration}>
                <Input />
            </Form.Item>

            <Form.Item
                name='description'
                label='描述'
                preserve={false}
                rules={[
                    {
                        required: true,
                        message: '请输入描述信息',
                    },
                ]}
                initialValue={props.record.description}>
                <TextArea rows={4} />
            </Form.Item>

            <Form.Item {...tailFormItemLayout}>
                <Button type='primary' htmlType='submit'>
                    修改
                </Button>
            </Form.Item>
        </Form>
    )
}
