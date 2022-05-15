/**
 * StationList
 * @file 寻求援助页面，用于查找救助站和显示救助站列表
 * @author TranceDream
 */
import * as React from 'react'
import { useEffect, useState } from 'react'
import styles from './StationList.module.scss'
import Header from '../../components/Header'
import StationItem from '../../components/StationItem'
import { Button, Form, Pagination, Select } from 'antd'
import { Option } from 'antd/es/mentions'
import { PlusOutlined } from '@ant-design/icons'
import { NavLink, useNavigate } from 'react-router-dom'
import {
    ContactTypeModel,
    getContactTypeList,
    getStationList,
    getTagTypeList,
    StationModel,
} from '../../lib/request'
import Footer from '../../components/Footer'
import { cleanCookies } from 'universal-cookie/lib/utils'

/**
 * 寻求援助页面，用于查找救助站和显示救助站列表
 * @author TranceDream
 * @constructor
 */
export const StationList = () => {
    const [index, setIndex] = useState(1)
    const [count, setCount] = useState(0)
    const [stationList, setStationList] = useState<Array<StationModel>>([])
    const [contactType, setContactType] = useState<Array<ContactTypeModel>>([])
    const navigate = useNavigate()
    useEffect(() => {
        getContactTypeList().then((res) => {
            setContactType(res.data.data)
            console.log(res.data.data)
        })
        getTagTypeList().then((res) => {
            console.log(res.data)
        })
        getStationList(index, {}).then((res) => {
            if (res.code === 401) {
                cleanCookies()
                navigate('/login', { replace: true })
            } else if (res.code === 200) {
                console.log(res.data)
                setStationList(res.data.houseinfo)
                setCount(res.data.count)
            }
        })
    }, [index, navigate])

    return (
        <div className={styles.container}>
            <header>
                <Header />
            </header>
            <NavLink to={'/station/add'} className={styles.fab}>
                <Button
                    shape={'circle'}
                    style={{ width: '96px', height: '96px' }}>
                    <PlusOutlined
                        style={{ fontSize: '32px', textAlign: 'center' }}
                    />
                </Button>
            </NavLink>
            <main>
                <div className={styles.search}>
                    <Form>
                        <Form.Item name='country' label='Country'>
                            <Select placeholder='Please select a country'>
                                <Option value='china'>China</Option>
                                <Option value='usa'>U.S.A</Option>
                            </Select>
                        </Form.Item>
                        <Form.Item name='province' label='Province'>
                            <Select placeholder='Please select a province'>
                                <Option value='tianjin'>TianJin</Option>
                                <Option value='hebei'>HeBei</Option>
                            </Select>
                        </Form.Item>
                        <Form.Item name='city' label='City'>
                            <Select placeholder='Please select a city'>
                                <Option value='nankai'>NanKai</Option>
                                <Option value='caoxian'>CaoXian</Option>
                            </Select>
                        </Form.Item>
                    </Form>
                </div>
                {stationList.map((station, index) => (
                    <StationItem
                        key={'station' + index}
                        station={station}
                        edit={false}
                    />
                ))}
                <div className={styles.pagination}>
                    <Pagination
                        defaultCurrent={index}
                        total={count}
                        onChange={(page) => {
                            setIndex(page)
                        }}
                    />
                </div>
            </main>
            <footer>
                <Footer />
            </footer>
        </div>
    )
}
