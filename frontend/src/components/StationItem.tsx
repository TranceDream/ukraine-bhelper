/**
 * StationItem
 * @file 救助站基本信息显示
 * @author TranceDream
 */
import React from 'react'
import styles from './StationItem.module.scss'
import { useNavigate } from 'react-router-dom'

interface Station {
    id: number
    title: string
    description: string
    address: string
    tags: Array<string>
    images: Array<string>
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
    const navigate = useNavigate()
    return (
        <div
            className={styles.container}
            onClick={() => {
                navigate('/station/detail')
            }}>
            <img src={props.station.images[0]} alt={'House Preview'} />
            <div className={styles.inner}>
                <div className={styles.title}>{props.station.title}</div>
                <div className={styles.desc}>{props.station.description}</div>
                <div className={styles.address}>{props.station.address}</div>
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
