/**
 * StationItem
 * @file 救助站基本信息显示
 * @author TranceDream
 */
import React from 'react'
import { useNavigate } from 'react-router-dom'
import { StationModel } from '../lib/request'
import styles from './StationItem.module.scss'

interface StationItemProps {
    station: StationModel
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
                navigate('/station/detail?id=' + props.station.houseId)
            }}>
            <img
                src={
                    'https://cdn.jsdelivr.net/gh/TranceDream/ImgHost@master/img/IMG_20220319_165950__01.5v96fzjetqo0.webp'
                }
                alt={'House Preview'}
            />
            <div className={styles.inner}>
                <div className={styles.title}>{props.station.title ?? ''}</div>
                <div className={styles.desc}>
                    {props.station.description ?? ''}
                </div>
                <div className={styles.address}>
                    {props.station.country +
                        ' ' +
                        props.station.province +
                        ' ' +
                        props.station.city}
                </div>
                <div className={styles.tags}></div>
            </div>
        </div>
    )
}

export default StationItem
