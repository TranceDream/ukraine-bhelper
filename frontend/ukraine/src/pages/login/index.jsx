import { LockOutlined, UserOutlined } from '@ant-design/icons'
import { Button, Checkbox, Form, Input, message } from 'antd'
import React from 'react'
import { Navigate, useNavigate } from 'react-router-dom'
import { reqLogin } from '../../api' // 默认暴露不用写大括号，分别暴露需要
import memoryUtil from '../../utils/memoryUtil'
import storageUtil from '../../utils/storageUtil'
import './index.less'

export default function Login() {
  const navigate = useNavigate()
  const onFinish = async values => {
    console.log('Received values of form: ', values)
    const { username, password } = values
    const result = await reqLogin(username, password) //{status: 0, data: user} {status:1, msg:登录失败}
    // console.log('请求成功了', response)
    if (result.status === 0) {
      //登录成功
      message.success('登录成功')
      // 保存user在内存
      const user = result.data
      memoryUtil.user = user
      //跳转
      // this.props.history.replace('/')
      navigate('/', { replace: true })
    } else {
      //登录失败
      message.error(result.msg)
    }
  }

  const tempOnFinish = values => {
    if (values.username === 'admin' && values.password === 'admin') {
      //登录成功
      message.success('登录成功')
      // 保存user在内存
      const user = { username: 'admin', password: 'password', id: 1 }
      memoryUtil.user = user

      //跳转
      navigate('/', { replace: true })
      storageUtil.SaveUser(user)
    } else {
      message.error('用户名或密码错误')
    }
  }

  let user = memoryUtil.user
  if (user) {
    return <Navigate to="/" />
  }
  return (
    <div className="page">
      <div className="form">
        <Form
          name="normal_login"
          className="login-form"
          initialValues={{
            remember: true
          }}
          onFinish={tempOnFinish}
        >
          <Form.Item
            name="username"
            rules={[
              {
                required: true,
                message: 'Please input your Username!'
              },
              {
                min: 4,
                message: 'The min length is 4.'
              },
              {
                max: 10,
                message: 'The max length is 10.'
              },
              {
                pattern: /^[a-zA-Z0-9_]+$/,
                message: "Containd by numbers, letters and '_'"
              }
            ]}
          >
            <Input
              prefix={<UserOutlined className="site-form-item-icon" />}
              placeholder="Username"
            />
          </Form.Item>
          <Form.Item
            name="password"
            rules={[
              {
                required: true,
                message: 'Please input your Password!'
              }
            ]}
          >
            <Input
              prefix={<LockOutlined className="site-form-item-icon" />}
              type="password"
              placeholder="Password"
            />
          </Form.Item>
          <Form.Item>
            <Form.Item name="remember" valuePropName="checked" noStyle>
              <Checkbox>Remember me</Checkbox>
            </Form.Item>

            <a className="login-form-forgot" href="#">
              Forgot password
            </a>
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit" className="login-form-button">
              Log in
            </Button>
            <a href="#">register now!</a>
          </Form.Item>
        </Form>
      </div>
      <div className="cover"></div>
    </div>
  )
}
