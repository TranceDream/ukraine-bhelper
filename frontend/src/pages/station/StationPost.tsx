import React from 'react'
import styles from './StationPost.module.scss'
import Header from '../../components/Header'
import { Button, Form, Input, InputNumber, Select } from 'antd'
import { Option } from 'antd/es/mentions'

const StationPost = () => {
    return (
        <div className={styles.container}>
            <header>
                <Header />
            </header>
            <main>
                <div className={styles.form}>
                    <Form labelCol={{ span: 2 }} size={'large'}>
                        <h2>House information</h2>
                        <Form.Item name={'title'} label={'Title'}>
                            <Input maxLength={100} showCount></Input>
                        </Form.Item>
                        <Form.Item
                            name='country'
                            label='Country'
                            rules={[
                                {
                                    required: true,
                                    message: 'Please select your country!',
                                },
                            ]}>
                            <Select placeholder='Please select a country'>
                                <Option value='china'>China</Option>
                                <Option value='usa'>U.S.A</Option>
                            </Select>
                        </Form.Item>
                        <Form.Item
                            name='province'
                            label='Province'
                            rules={[
                                {
                                    required: true,
                                    message: 'Please select your province!',
                                },
                            ]}>
                            <Select placeholder='Please select a province'>
                                <Option value='tianjin'>TianJin</Option>
                                <Option value='hebei'>HeBei</Option>
                            </Select>
                        </Form.Item>
                        <Form.Item
                            name='city'
                            label='City'
                            rules={[
                                {
                                    required: true,
                                    message: 'Please select your city!',
                                },
                            ]}>
                            <Select placeholder='Please select a city'>
                                <Option value='nankai'>NanKai</Option>
                                <Option value='caoxian'>CaoXian</Option>
                            </Select>
                        </Form.Item>
                        <Form.Item name={'address'} label={'Address'}>
                            <Input maxLength={200} showCount></Input>
                        </Form.Item>
                        <Form.Item name={'guests'} label={'Capacity'}>
                            <InputNumber min={0}></InputNumber>
                        </Form.Item>
                        <Form.Item
                            name='pets'
                            label='Pets'
                            wrapperCol={{ span: 3 }}>
                            <Select placeholder='N/A'>
                                <Option value='YES'>Allow</Option>
                                <Option value='NO'>Disallow</Option>
                            </Select>
                        </Form.Item>
                        <Form.Item
                            name='duration'
                            label='Duration'
                            wrapperCol={{ span: 3 }}>
                            <Select placeholder='N/A'>
                                <Option value='0'>N/A</Option>
                                {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12].map(
                                    (e) => (
                                        <Option value={e.toString()}>
                                            {e}
                                        </Option>
                                    )
                                )}
                            </Select>
                        </Form.Item>
                        <Form.Item
                            name='description'
                            label='Description'
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input Description!',
                                },
                            ]}>
                            <Input.TextArea
                                autoSize={{ minRows: 3, maxRows: 15 }}
                                showCount
                                maxLength={500}
                            />
                        </Form.Item>
                        <Form.Item>
                            <Button
                                type='primary'
                                htmlType='submit'
                                className={styles.loginFormButton}>
                                Submit
                            </Button>
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

export default StationPost
