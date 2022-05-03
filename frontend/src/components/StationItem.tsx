/**
 * StationItem
 * @file 救助站基本信息显示
 * @author TranceDream
 */
import React from 'react'
import styles from './StationItem.module.scss'

interface Station {
    title: string
    description: string
    address: string
    tags: Array<string>
}

interface StationItemProps {
    station: Station
}

/**
 * 救助站信息预览组件，用于搜索和列表
 * @param {StationItemProps} props 传入包含字段station为救助站信息，具体数据结构等接口出来再修改
 * @author TranceDream
 * @constructor
 */
const StationItem = (props: StationItemProps) => {
    return (
        <div className={styles.container}>
            <img
                src={
                    'https://cdn.jsdelivr.net/gh/TranceDream/ImgHost@master/img/IMG_20220319_165950__01.5v96fzjetqo0.webp'
                }
                alt={'House Preview'}
            />
            <div className={styles.inner}>
                <div className={styles.title}>Title</div>
                <div className={styles.desc}>Description</div>
                <div className={styles.address}>Address</div>
                <div className={styles.tags}>
                    {props.station.tags.map((tag) => (
                        <div>{tag}</div>
                    ))}
                </div>
            </div>
        </div>
    )
}

export default StationItem
