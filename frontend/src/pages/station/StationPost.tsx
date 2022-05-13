import React, { useEffect, useState } from 'react'
import styles from './StationPost.module.scss'
import Header from '../../components/Header'
import { Button, Form, Input, InputNumber, Select, Spin } from 'antd'
import { Option } from 'antd/es/mentions'
import {
    getStationDetail,
    publishStation,
    StationModel,
} from '../../lib/request'
import { useLocation, useNavigate } from 'react-router-dom'
import { LoadingOutlined } from '@ant-design/icons'

const StationPost = () => {
    const useQuery = () => new URLSearchParams(useLocation().search)
    const query = useQuery()
    const id = query.get('id')
    const edit = query.get('edit')
    const navigate = useNavigate()
    const [station, setStation] = useState<StationModel>({
        country: '',
        province: '',
        city: '',
    })
    const [loading, setLoadingState] = useState(true)

    useEffect(() => {
        if (id) {
            getStationDetail(parseInt(id!)).then((res) => {
                if (res.code === 200) {
                    const info = res.data.houseInfo
                    setStation(info)
                    setLoadingState(false)
                    console.log(info)
                } else {
                    console.log(res.data)
                    navigate('/404', { replace: true })
                }
            })
        } else {
            setLoadingState(false)
        }
    }, [id])

    return (
        <div className={styles.container}>
            <header>
                <Header />
            </header>
            <main>
                <div className={styles.form}>
                    {!loading ? (
                        <Form
                            labelCol={{ span: 2 }}
                            size={'large'}
                            onFinish={(values) => {
                                publishStation(values).then((res) => {
                                    console.log(res)
                                })
                            }}>
                            <h2>House information</h2>
                            <Form.Item
                                initialValue={station.title}
                                name={'title'}
                                label={'Title'}>
                                <Input
                                    // value={station.title}
                                    maxLength={100}
                                    showCount
                                    onChange={(e) => {
                                        let updatedStation =
                                            Object.assign(station)
                                        updatedStation.title = e.target.value
                                        setStation(updatedStation)
                                        console.log(station)
                                    }}></Input>
                            </Form.Item>
                            <Form.Item
                                name='country'
                                label='Country'
                                initialValue={station.country}
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
                                ]}
                                initialValue={station.province}>
                                <Select placeholder='Please select a province'>
                                    <Option value='tianjin'>TianJin</Option>
                                    <Option value='hebei'>HeBei</Option>
                                </Select>
                            </Form.Item>
                            <Form.Item
                                name='city'
                                label='City'
                                initialValue={station.city}
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
                            <Form.Item
                                initialValue={station.address}
                                name={'address'}
                                label={'Address'}>
                                <Input maxLength={200} showCount></Input>
                            </Form.Item>
                            <Form.Item
                                initialValue={station.guests}
                                name={'guests'}
                                label={'Capacity'}>
                                <InputNumber min={0}></InputNumber>
                            </Form.Item>
                            <Form.Item
                                name='pets'
                                label='Pets'
                                initialValue={station.pets}
                                wrapperCol={{ span: 3 }}>
                                <Select placeholder='N/A'>
                                    <Option value='YES'>Allow</Option>
                                    <Option value='NO'>Disallow</Option>
                                </Select>
                            </Form.Item>
                            <Form.Item
                                name='duration'
                                label='Duration'
                                initialValue={station.duration}
                                wrapperCol={{ span: 3 }}>
                                <Select placeholder='N/A'>
                                    <Option value='0'>N/A</Option>
                                    {[
                                        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                                    ].map((e) => (
                                        <Option value={e.toString()}>
                                            {e}
                                        </Option>
                                    ))}
                                </Select>
                            </Form.Item>
                            <Form.Item
                                name='description'
                                label='Description'
                                initialValue={station.description}
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
                    ) : (
                        <Spin indicator={<LoadingOutlined />} />
                    )}
                </div>
            </main>
            <footer>
                <div>footer</div>
            </footer>
        </div>
    )
}

export default StationPost
