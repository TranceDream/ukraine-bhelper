import { Button, Form, Input, message, Select } from 'antd'
import React, { useEffect, useState } from 'react'
import { reqAddPermission, reqGetRoleList } from '../../api'
import PubSub from '../../Utils/pubsub'

const { Option } = Select
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
    const [roleList, setroleList] = useState<any[]>([])
    const handleChange = (value: any) => {
        console.log(value)
    }
    const [options, setOptions] = useState([])
    const onFinish = async (values: any) => {
        if (values.parentId === '主目录') {
            values.parentId = 1
        } else {
            values.parentId = props.MapModule[values.parentId]
        }
        values.permissionName = values.name
        const res = await reqAddPermission(values)
        if (res.code === 200) {
            PubSub.publish('addModule', 'success')
        } else {
            PubSub.publish('addModule', 'fail')
        }
    }
    useEffect(() => {
        async function getrolelist() {
            const res = await reqGetRoleList()
            // console.log(res)
            if (res.code === 200) {
                setroleList(res.data.data)
            } else {
                message.error('请求用户列表出错：' + res.msg)
            }
        }
        getrolelist()
        let menu = props.menu.slice()
        menu.push({ value: '主目录' })
        setOptions(menu)
    }, [])
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
                    options={options}
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

            <Form.Item
                label='赋予角色'
                name='roles'
                preserve={false}
                rules={[{ required: true, message: 'Please input pageKey!' }]}>
                <Select
                    mode='multiple'
                    allowClear
                    style={{ width: '100%' }}
                    placeholder='Please select'
                    onChange={handleChange}>
                    {roleList.map((item: any) => {
                        return (
                            <Option key={item.roleId}>{item.roleName}</Option>
                        )
                    })}
                </Select>
            </Form.Item>
            <Form.Item wrapperCol={{ offset: 8, span: 12 }}>
                <Button type='primary' htmlType='submit'>
                    Submit
                </Button>
            </Form.Item>
        </Form>
    )
}
