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
import { Button, Col, Form, InputNumber, Pagination, Select } from 'antd'
import { Option } from 'antd/es/mentions'
import { PlusOutlined } from '@ant-design/icons'
import { NavLink, useNavigate } from 'react-router-dom'
import {
    ContactTypeModel,
    getContactTypeList,
    getStationList,
    getTagTypeList,
    StationFilter,
    StationModel,
} from '../../lib/request'
import Footer from '../../components/Footer'
import { cleanCookies } from 'universal-cookie/lib/utils'
import {
    CityModel,
    CountryModel,
    getCities,
    getCountries,
    getStates,
    StateModel,
} from '../../lib/district'

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
    const [filter, setFilter] = useState<StationFilter>({})
    const [requestFilter, setRequestFilter] = useState<StationFilter>({})

    const [countryList, setCountryList] = useState<CountryModel[]>([])
    const [stateList, setStateList] = useState<StateModel[]>([])
    const [cityList, setCityList] = useState<CityModel[]>([])

    const navigate = useNavigate()
    useEffect(() => {
        setCountryList(getCountries())
        getContactTypeList().then((res) => {
            setContactType(res.data.data)
            console.log(res.data.data)
        })
        getTagTypeList().then((res) => {
            console.log(res.data)
        })
        getStationList(index, requestFilter).then((res) => {
            if (res.code === 401) {
                cleanCookies()
                navigate('/login', { replace: true })
            } else if (res.code === 200) {
                console.log(res.data)
                setStationList(res.data.houseinfo)
                setCount(res.data.count)
            }
        })
    }, [index, navigate, requestFilter])

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
                    <Form labelCol={{ span: 3 }}>
                        <Form.Item name='country' label='Country'>
                            <Select
                                placeholder='Please select a country'
                                onChange={(e) => {
                                    let f = Object.assign(filter)
                                    f.country = e
                                    setFilter(f)
                                    setStateList(getStates(e))
                                    console.log(filter)
                                }}>
                                {countryList.map((country) => (
                                    <Option
                                        key={'c' + country.code}
                                        value={country.code}>
                                        {country.country}
                                    </Option>
                                ))}
                            </Select>
                        </Form.Item>
                        <Form.Item name='province' label='Province'>
                            <Select
                                disabled={filter.country == null}
                                placeholder='Please select a province'
                                onChange={(e) => {
                                    let f = Object.assign(filter)
                                    f.province = e
                                    setCityList(getCities(filter.country!, e))
                                    setFilter(f)
                                }}>
                                {stateList.map((state) => (
                                    <Option
                                        key={'s' + state.code}
                                        value={state.code}>
                                        {state.state}
                                    </Option>
                                ))}
                            </Select>
                        </Form.Item>
                        <Form.Item name='city' label='City'>
                            <Select
                                disabled={filter.province == null}
                                placeholder='Please select a city'
                                onChange={(e) => {
                                    let f = Object.assign(filter)
                                    f.city = e
                                    setFilter(f)
                                }}>
                                {cityList.map((city) => (
                                    <Option
                                        key={'t' + city.city}
                                        value={city.city}>
                                        {city.city}
                                    </Option>
                                ))}
                            </Select>
                        </Form.Item>
                        <Form.Item name='pets' label='Pets'>
                            <Select
                                placeholder='N/A'
                                onChange={(e) => {
                                    let f = Object.assign(filter)
                                    f.pets = e
                                    setFilter(f)
                                }}>
                                <Option value='YES'>Allow</Option>
                                <Option value='NO'>Disallow</Option>
                            </Select>
                        </Form.Item>
                        <Form.Item name='duration' label='Duration'>
                            <InputNumber
                                min={0}
                                max={filter.durationmax ?? 12}
                                onChange={(e) => {
                                    let f = Object.assign(filter)
                                    f.durationmin = e
                                    setFilter(f)
                                }}></InputNumber>
                            &nbsp;-&nbsp;
                            <InputNumber
                                min={filter.durationmin ?? 1}
                                max={12}
                                onChange={(e) => {
                                    let f = Object.assign(filter)
                                    f.durationmax = e
                                    setFilter(f)
                                }}></InputNumber>
                            &nbsp;月
                        </Form.Item>
                        <Form.Item name='guest' label='Guest'>
                            <InputNumber
                                min={0}
                                max={filter.guestmax ?? Number.MAX_SAFE_INTEGER}
                                onChange={(e) => {
                                    let f = Object.assign(filter)
                                    f.guestmin = e
                                    setFilter(f)
                                }}></InputNumber>
                            &nbsp;-&nbsp;
                            <InputNumber
                                min={filter.guestmin ?? 1}
                                max={Number.MAX_SAFE_INTEGER}
                                onChange={(e) => {
                                    let f = Object.assign(filter)
                                    f.guestmax = e
                                    setFilter(f)
                                }}></InputNumber>
                            &nbsp;人
                        </Form.Item>
                        <Form.Item>
                            <Button
                                style={{ width: '20%' }}
                                onClick={() => {
                                    setRequestFilter(filter)
                                }}>
                                搜索
                            </Button>
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
