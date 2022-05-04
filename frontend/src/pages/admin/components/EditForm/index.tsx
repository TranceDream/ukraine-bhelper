/*
 * @Author: Linhao Yu
 * @Date: 2022-05-03 15:36:23
 * @Last Modified by:   Linhao Yu
 * @Last Modified time: 2022-05-03 15:36:23
 */
import { Button, Form, Input } from 'antd'
import styles from './index.module.scss'
const onFinish = (values: any) => {
    console.log('Success:', values)
}

interface Props {
    userName: string
    userId: number
    country: string
    city: string
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
export default function index(props: Props) {
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
                initialValue={props.userId}
                rules={[
                    { required: true, message: 'Please input your username!' },
                ]}>
                <Input disabled={true} />
            </Form.Item>

            <Form.Item
                label='用户名'
                name='username'
                initialValue={props.userName}
                rules={[
                    { required: true, message: 'Please input your username!' },
                ]}>
                <Input disabled={true} />
            </Form.Item>

            <Form.Item
                label='国家'
                name='country'
                initialValue={props.country}
                rules={[
                    { required: true, message: 'Please input your username!' },
                ]}>
                <Input />
            </Form.Item>

            <Form.Item
                label='城市'
                name='city'
                initialValue={props.city}
                rules={[
                    { required: true, message: 'Please input your username!' },
                ]}>
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
