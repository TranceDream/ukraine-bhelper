import { Button, Form, Input } from 'antd'
import styles from './index.module.scss'
const onFinish = (values: any) => {
    console.log('Success:', values)
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
export default function index() {
    return (
        <Form
            className={styles.form}
            name='basic'
            labelCol={{ span: 8 }}
            wrapperCol={{ span: 16 }}
            initialValues={{ remember: true }}
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
            autoComplete='off'>
            <Form.Item
                label='用户ID'
                name='userid'
                rules={[
                    { required: true, message: 'Please input your username!' },
                ]}>
                <Input />
            </Form.Item>

            <Form.Item
                label='用户名'
                name='username'
                rules={[
                    { required: true, message: 'Please input your username!' },
                ]}>
                <Input />
            </Form.Item>

            <Form.Item
                label='昵称'
                name='nicknam'
                rules={[
                    { required: true, message: 'Please input your username!' },
                ]}>
                <Input />
            </Form.Item>

            <Form.Item
                label='角色'
                name='role'
                rules={[
                    { required: true, message: 'Please input your username!' },
                ]}>
                <Input />
            </Form.Item>

            <Form.Item
                label='国家'
                name='country'
                rules={[
                    { required: true, message: 'Please input your username!' },
                ]}>
                <Input />
            </Form.Item>

            <Form.Item
                label='城市'
                name='city'
                rules={[
                    { required: true, message: 'Please input your username!' },
                ]}>
                <Input />
            </Form.Item>

            <Form.Item name='DatePicker' label='创建日期' {...config}>
                {/* <DatePicker /> */}
            </Form.Item>
            <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                <Button type='primary' htmlType='submit'>
                    Submit
                </Button>
            </Form.Item>
        </Form>
    )
}
