/**
 * StationList
 * @file 寻求援助页面，用于查找救助站和显示救助站列表
 * @author TranceDream
 */
import { LoadingOutlined, PlusOutlined } from '@ant-design/icons'
import {
    Button,
    Empty,
    Form,
    InputNumber,
    message,
    Pagination,
    Select,
    Spin
} from 'antd'
import { Option } from 'antd/es/mentions'
import * as React from 'react'
import { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { NavLink, useNavigate } from 'react-router-dom'
import Footer from '../../components/Footer'
import Header from '../../components/Header'
import StationItem from '../../components/StationItem'
import {
    CityModel,
    CountryModel,
    getCities,
    getCountries,
    getStates,
    StateModel
} from '../../lib/district'
import { getStationList, StationFilter, StationModel } from '../../lib/request'
import styles from './StationList.module.scss'

/**
 * 寻求援助页面，用于查找救助站和显示救助站列表
 * @author TranceDream
 * @constructor
 */
export const StationList = () => {
    const { t } = useTranslation()

    const [index, setIndex] = useState(1)
    const [count, setCount] = useState(0)
    const [stationList, setStationList] = useState<Array<StationModel>>([])
    const [loading, setLoading] = useState<boolean>(true)
    const [filter, setFilter] = useState<StationFilter>({})
    const [requestFilter, setRequestFilter] = useState<StationFilter>({})

    const [countryList, setCountryList] = useState<CountryModel[]>([])
    const [stateList, setStateList] = useState<StateModel[]>([])
    const [cityList, setCityList] = useState<CityModel[]>([])

    const navigate = useNavigate()
    useEffect(() => {
        setCountryList(getCountries())
        setLoading(true)
        setStationList([])
        getStationList(index, requestFilter).then((res) => {
            if (res.code === 200) {
                setStationList(res.data.houseinfo)
                setCount(res.data.count)
                setLoading(false)
            } else {
                setLoading(false)
                message.error('出错了: ' + res.msg).then()
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
                    <Form labelCol={{ span: 3 }} labelAlign={'left'}>
                        <Form.Item
                            name='country'
                            label={t('station.country')}
                            preserve={false}>
                            <Select
                                placeholder='Please select a country'
                                onChange={(e) => {
                                    let f = Object.assign(filter)
                                    f.country = e
                                    setFilter(f)
                                    setStateList(getStates(e))
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
                        <Form.Item
                            name='province'
                            label={t('station.province')}
                            preserve={false}>
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
                        <Form.Item
                            name='city'
                            label={t('station.city')}
                            preserve={false}>
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
                        <Form.Item name='pets' label={t('station.pets')}>
                            <Select
                                placeholder='N/A'
                                onChange={(e) => {
                                    let f = Object.assign(filter)
                                    f.pets = e
                                    setFilter(f)
                                }}>
                                <Option value='YES'>
                                    {t('station.allow')}
                                </Option>
                                <Option value='NO'>
                                    {t('station.disallow')}
                                </Option>
                            </Select>
                        </Form.Item>
                        <Form.Item
                            name='duration'
                            label={t('station.duration')}>
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
                            &nbsp;{t('station.month')}
                        </Form.Item>
                        <Form.Item name='guest' label={t('station.capacity')}>
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
                            &nbsp;{t('station.people')}
                        </Form.Item>
                        <Form.Item>
                            <div
                                style={{
                                    width: '100%',
                                    display: 'flex',
                                    justifyContent: 'flex-end',
                                }}>
                                <Button
                                    type={'primary'}
                                    style={{ width: '20%' }}
                                    size={'large'}
                                    onClick={() => {
                                        console.log(filter)
                                        setRequestFilter({ ...filter })
                                        console.log(requestFilter)
                                    }}>
                                    {t('station.search')}
                                </Button>
                            </div>
                        </Form.Item>
                    </Form>
                </div>
                {stationList.length === 0 ? (
                    loading ? (
                        <Spin
                            indicator={
                                <LoadingOutlined
                                    style={{ fontSize: 'xxx-large' }}
                                />
                            }
                        />
                    ) : (
                        <Empty description={t('noData')} />
                    )
                ) : (
                    <>
                        {stationList.map((station, index) => (
                            <StationItem
                                key={'station' + index}
                                station={station}
                                edit={false}
                            />
                        ))}
                    </>
                )}
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
