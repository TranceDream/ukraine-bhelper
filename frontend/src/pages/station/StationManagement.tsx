import React, { useState } from 'react'
import styles from './StationList.module.scss'
import Header from '../../components/Header'
import { NavLink } from 'react-router-dom'
import { Button, Pagination } from 'antd'
import { PlusOutlined } from '@ant-design/icons'
import StationItem from '../../components/StationItem'
import Footer from '../../components/Footer'
import { StationModel } from '../../lib/request'

const StationManagement = () => {
    const [index, setIndex] = useState(1)
    const [count, setCount] = useState(0)
    const [stationList, setStationList] = useState<Array<StationModel>>([])
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
                {stationList.map((station, index) => (
                    <StationItem
                        key={'station' + index}
                        station={station}
                        edit
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

export default StationManagement
