/*
 * @Author: Linhao Yu
 * @Date: 2022-05-03 15:36:14
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-03 17:20:12
 */
import { Button, Form, Input, Select } from 'antd'
import React from 'react'
import { reqDoAudit } from '../../api'
import PubSub from '../../Utils/pubsub'

const { Option } = Select
interface Props {
    defense: string
    objtypeId: string
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

export default function Audit(props: Props) {
    const [form] = Form.useForm()

    const onFinish = async (values: any) => {
        values.defense = parseInt(values.defense)
        values.objtypeId = parseInt(values.objtypeId)
        console.table(values)

        const res = await reqDoAudit({ ...values })
        if (res.code === 200) {
            // message.success('修改成功')
            PubSub.publish('audit', 'success')
        } else {
            // message.error(res.msg)
            PubSub.publish('audit', 'fail')
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
                name='objtypeId'
                label='举报模块ID'
                initialValue={props.objtypeId}>
                <Input disabled={true} />
            </Form.Item>

            <Form.Item
                name='defense'
                label='被举报者ID'
                preserve={false}
                initialValue={props.defense}>
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

            <Form.Item {...tailFormItemLayout}>
                <Button type='primary' htmlType='submit'>
                    确认
                </Button>
            </Form.Item>
        </Form>
    )
}
