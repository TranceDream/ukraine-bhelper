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
import { NavLink } from 'react-router-dom'

/**
 * 寻求援助页面，用于查找救助站和显示救助站列表
 * @author TranceDream
 * @constructor
 */
export const StationList = () => {
    const [index, setIndex] = useState(1)

    useEffect(() => {
        console.log('Request ' + index)
        // getStationList(index, {}).then((res) => {
        //     console.table(res.data)
        // })
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
                {[1, 2, 3, 4, 5].map((e) => (
                    <StationItem
                        key={'station' + e}
                        station={{
                            id: e,
                            title: 'House ' + e,
                            description: 'Description ' + e,
                            address: 'Address ' + e,
                            tags: [
                                '可容纳xxx人',
                                '允许宠物',
                                '可短期居住(2周内)',
                            ],
                            images: [
                                'https://cdn.jsdelivr.net/gh/TranceDream/ImgHost@master/img/IMG_20220319_165950__01.5v96fzjetqo0.webp',
                            ],
                        }}
                    />
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
