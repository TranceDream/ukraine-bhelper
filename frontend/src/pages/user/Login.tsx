import {
    EyeInvisibleOutlined,
    EyeTwoTone,
    LockOutlined,
    UserOutlined,
} from '@ant-design/icons'
import { Button, Form, Input, message, Modal } from 'antd'
import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import Cookie from 'universal-cookie'
import Header from '../../components/Header'
import { login } from '../../lib/request'
import styles from './Login.module.scss'
import Footer from '../../components/Footer'

/**
 * 登录页面
 * @constructor
 * @author TranceDream
 */
const Login = () => {
    const navigate = useNavigate()
    const [modalVisible, setModal] = useState(false)
    useEffect(() => {
        const cookie = new Cookie()
        if (cookie.get('token')) {
            navigate('/')
        }
    }, [navigate])
    return (
        <div className={styles.container}>
            <header>
                <Header hideNav hideUser />
            </header>
            <main>
                <Modal
                    title={'Oops!'}
                    visible={modalVisible}
                    footer={[
                        <Button
                            key={'ok'}
                            onClick={() => {
                                setModal(false)
                            }}>
                            OK
                        </Button>,
                    ]}>
                    用户名或密码错误
                </Modal>
                <div className={styles.form}>
                    <h2>登录</h2>
                    <Form
                        name='normal_login'
                        className='login-form'
                        size={'large'}
                        initialValues={{ remember: true }}
                        onFinish={(values: any) => {
                            login(
                                values.identifier,
                                values.credential,
                                10001
                            ).then((result) => {
                                if (result.code === 200) {
                                    navigate('/')
                                } else {
                                    message
                                        .error('出错了: ' + result.msg)
                                        .then()
                                }
                            })
                        }}>
                        <Form.Item
                            name='identifier'
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input your Username!',
                                },
                            ]}>
                            <Input
                                prefix={
                                    <UserOutlined className='site-form-item-icon' />
                                }
                                placeholder='Username'
                            />
                        </Form.Item>
                        <Form.Item
                            name='credential'
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input your Password!',
                                },
                            ]}>
                            <Input.Password
                                prefix={
                                    <LockOutlined className='site-form-item-icon' />
                                }
                                type='password'
                                placeholder='Password'
                                iconRender={(visible) =>
                                    visible ? (
                                        <EyeTwoTone />
                                    ) : (
                                        <EyeInvisibleOutlined />
                                    )
                                }
                            />
                        </Form.Item>
                        <Form.Item>
                            <Button
                                type='primary'
                                htmlType='submit'
                                className={styles.loginFormButton}>
                                Log in
                            </Button>
                            Or <a href='/register'>register now!</a>
                        </Form.Item>
                    </Form>
                </div>
            </main>
            <footer>
                <Footer />
            </footer>
        </div>
    )
}

export default Login
