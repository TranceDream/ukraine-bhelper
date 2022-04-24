/**
 * StationList
 * @file 寻求援助页面，用于查找救助站和显示救助站列表
 * @author TranceDream
 */
import * as React from 'react'
import styles from './StationList.module.scss'
import Header from '../../components/Header'
import StationItem from '../../components/StationItem'

/**
 * 寻求援助页面，用于查找救助站和显示救助站列表
 * @author TranceDream
 * @constructor
 */
export const StationList = () => {
    return (
        <div className={styles.container}>
            <header>
                <Header />
            </header>
            <main>
                {[1, 2, 3, 4, 5].map((e) => (
                    <StationItem
                        key={'station' + e}
                        station={{
                            title: 'Title',
                            description: 'Description',
                            address: 'Address',
                            tags: [
                                '可容纳xxx人',
                                '允许宠物',
                                '可短期居住(2周内)',
                            ],
                        }}
                    />
                ))}
            </main>
            <footer>
                <div>footer</div>
            </footer>
        </div>
    )
}
