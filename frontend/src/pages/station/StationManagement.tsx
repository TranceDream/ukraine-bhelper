import React, { useEffect, useState } from 'react'
import styles from './StationList.module.scss'
import Header from '../../components/Header'
import { NavLink } from 'react-router-dom'
import { Button, Empty, message, Pagination, Spin } from 'antd'
import { LoadingOutlined, PlusOutlined } from '@ant-design/icons'
import StationItem from '../../components/StationItem'
import Footer from '../../components/Footer'
import { getMyStations, StationModel } from '../../lib/request'
import { useTranslation } from 'react-i18next'

const StationManagement = () => {
    const { t } = useTranslation()

    const [index, setIndex] = useState(1)
    const [loading, setLoading] = useState<boolean>(true)
    const [count, setCount] = useState(0)
    const [stationList, setStationList] = useState<Array<StationModel>>([])

    useEffect(() => {
        setLoading(true)
        getMyStations(index).then((res) => {
            if (res.code === 200) {
                setLoading(false)
                setStationList(res.data.houseinfo)
                setCount(res.data.count)
            } else {
                message.error(res.msg).then()
                setLoading(false)
            }
        })
    }, [index])

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
                {loading ? (
                    <Spin
                        indicator={
                            <LoadingOutlined
                                style={{ fontSize: 'xxx-large' }}
                            />
                        }
                    />
                ) : stationList.length === 0 ? (
                    <Empty description={t('noData')} />
                ) : (
                    <>
                        {stationList.map((station, index) => (
                            <StationItem
                                key={'station' + index}
                                station={station}
                                edit
                                onDelete={() => {
                                    setIndex(index)
                                }}
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
                    </>
                )}
            </main>
            <footer>
                <Footer />
            </footer>
        </div>
    )
}

export default StationManagement
