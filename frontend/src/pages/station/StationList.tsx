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
import Cookie from 'universal-cookie'
import { Button, Form, Pagination, Select } from 'antd'
import { Option } from 'antd/es/mentions'
import { PlusOutlined } from '@ant-design/icons'
import { NavLink, useNavigate } from 'react-router-dom'
import { getStationList, StationModel } from '../../lib/request'

/**
 * 寻求援助页面，用于查找救助站和显示救助站列表
 * @author TranceDream
 * @constructor
 */
export const StationList = () => {
    const [index, setIndex] = useState(1)
    const [stationList, setStationList] = useState<Array<StationModel>>([])
    const navigate = useNavigate()
    useEffect(() => {
        getStationList(index, {}).then((res) => {
            if (res.code === 401) {
                const cookie = new Cookie()
                cookie.remove('token')
                navigate('/login', { replace: true })
            } else if (res.code === 200) {
                setStationList(res.data.houseinfo)
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
                        <Form.Item name={'country'} label={'Country'}>
                            <Select placeholder={'N/A'}>
                                <Option value={''}>N/A</Option>
                                <Option value={'china'}>China</Option>
                            </Select>
                        </Form.Item>
                    </Form>
                </div>
                {stationList.map((station, index) => (
                    <StationItem key={'station' + index} station={station} />
                ))}
                <div className={styles.pagination}>
                    <Pagination
                        defaultCurrent={index}
                        total={50}
                        onChange={(page) => {
                            setIndex(page)
                        }}
                    />
                </div>
            </main>
            <footer>
                <div>footer</div>
            </footer>
        </div>
    )
}
