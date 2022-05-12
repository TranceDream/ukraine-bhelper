import { Button, Form, Input, Modal } from 'antd'
import React, { useEffect, useState } from 'react'
import Header from '../../components/Header'
import styles from './Register.module.scss'
import {
    GlobalOutlined,
    HomeOutlined,
    LockOutlined,
    UserOutlined,
} from '@ant-design/icons'
import { register } from '../../lib/request'
import { useNavigate } from 'react-router-dom'
import Cookie from 'universal-cookie'

/**
 * 注册页面
 * @constructor
 * @author TranceDream
 */
const Register = () => {
    const [showModal, setModal] = useState(false)
    const [modalText, setModalText] = useState('')
    const [code, setCode] = useState(0)
    const navigate = useNavigate()
    useEffect(() => {
        const cookie = new Cookie()
        if (cookie.get('token')) {
            console.log('get token')
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
                    title={'Notification'}
                    visible={showModal}
                    footer={[
                        <Button
                            onClick={() => {
                                setModal(false)
                                if (code === 200) {
                                    navigate('/login')
                                }
                            }}>
                            OK
                        </Button>,
                    ]}>
                    {modalText}
                </Modal>
                <div className={styles.form}>
                    <h2>注册</h2>
                    <Form
                        name='normal_register'
                        className='register-form'
                        initialValues={{ remember: true }}
                        size={'large'}
                        onFinish={(values: any) => {
                            register(
                                values.identifier,
                                values.credential,
                                values.city,
                                values.country
                            ).then((res) => {
                                console.table(res)
                                setModalText(res.msg)
                                setModal(true)
                                setCode(res.code)
                            })
                        }}>
                        <p>请输入邮箱</p>
                        <Form.Item
                            name='identifier'
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input your Email!',
                                },
                                {
                                    pattern: new RegExp(
                                        /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/
                                    ),
                                    message: 'Pleaser enter an email address',
                                },
                            ]}>
                            <Input
                                prefix={
                                    <UserOutlined className='site-form-item-icon' />
                                }
                                placeholder='Email'
                            />
                        </Form.Item>
                        <p>请输入密码</p>
                        <Form.Item
                            name='credential'
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input your Password!',
                                },
                                {
                                    pattern: new RegExp(
                                        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{8,16}$/
                                    ),
                                    message:
                                        'A password must contain at least 1 lowercase, 1 uppercase and 1 digits',
                                },
                            ]}>
                            <Input
                                prefix={
                                    <LockOutlined className='site-form-item-icon' />
                                }
                                type='password'
                                placeholder='Password'
                            />
                        </Form.Item>
                        <p>请输入您所在的城市</p>
                        <Form.Item
                            name='city'
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input your City!',
                                },
                            ]}>
                            <Input
                                prefix={
                                    <HomeOutlined className='site-form-item-icon' />
                                }
                                placeholder='City'
                            />
                        </Form.Item>
                        <p>请输入您的国家</p>
                        <Form.Item
                            name='country'
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input your Country!',
                                },
                            ]}>
                            <Input
                                prefix={
                                    <GlobalOutlined className='site-form-item-icon' />
                                }
                                placeholder='Country'
                            />
                        </Form.Item>
                        <Form.Item>
                            <Button
                                type='primary'
                                htmlType='submit'
                                className={styles.loginFormButton}>
                                Register
                            </Button>
                            Already have an account?{' '}
                            <a href='/login'>Sign in!</a>
                        </Form.Item>
                    </Form>
                </div>
            </main>
            <footer>
                <div>footer</div>
            </footer>
        </div>
    )
}

export default Register
