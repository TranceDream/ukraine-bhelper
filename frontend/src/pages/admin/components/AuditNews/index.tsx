/*
 * @Author: Linhao Yu
 * @Date: 2022-05-03 15:36:14
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-15 23:06:48
 */
import { Button, Form, Input, Select } from 'antd'
import TextArea from 'antd/lib/input/TextArea'
import React from 'react'
import { reqNewsDoAudit } from '../../api'
import PubSub from '../../Utils/pubsub'

const { Option } = Select
interface Props {
    record: any
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

export default function AuditNews(props: Props) {
    const [form] = Form.useForm()

    const onFinish = async (values: any) => {
        // values.defense = parseInt(values.defense)
        // values.objtypeId = parseInt(values.objtypeId)
        // console.table(values)
        const res = await reqNewsDoAudit({ ...values })
        if (res.code === 200) {
            // message.success('修改成功')
            PubSub.publish('newsaudit', 'success')
        } else {
            // message.error(res.msg)
            PubSub.publish('newsaudit', 'fail')
        }
    }
    return (
        <Form
            {...formItemLayout}
            form={form}
            onFinish={onFinish}
            preserve={false}
            scrollToFirstError>
            <Form.Item
                name='articleId'
                label='新闻ID'
                initialValue={props.record.articleId}>
                <Input disabled={true} />
            </Form.Item>

            <Form.Item
                name='title'
                label='新闻标题'
                preserve={false}
                initialValue={props.record.title}>
                <Input disabled={true} />
            </Form.Item>

            <Form.Item
                name='oper'
                label='操作'
                preserve={false}
                initialValue={'驳回'}
                hasFeedback>
                <Select style={{ width: 120 }}>
                    <Option value='通过'>通过</Option>
                    <Option value='驳回'>驳回</Option>
                </Select>
            </Form.Item>

            <Form.Item name='message' label='反馈' preserve={false} hasFeedback>
                <TextArea rows={4} placeholder='理由' maxLength={6} />
            </Form.Item>

            <Form.Item {...tailFormItemLayout}>
                <Button type='primary' htmlType='submit'>
                    确认
                </Button>
            </Form.Item>
        </Form>
    )
}
