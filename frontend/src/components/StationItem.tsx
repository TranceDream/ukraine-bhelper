/**
 * StationItem
 * @file 救助站基本信息显示
 * @author TranceDream
 */
import React from 'react'
import { useNavigate } from 'react-router-dom'
import { imageUrl, StationModel } from '../lib/request'
import styles from './StationItem.module.scss'
import { Button } from 'antd'
import { DeleteOutlined, EditOutlined } from '@ant-design/icons'

interface StationItemProps {
    station: StationModel
    edit: boolean
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
                    props.station.fileNames!.length === 0
                        ? 'https://www.51gaifang.com/tu/UploadSoftPic/201906/2019060313023213.jpg'
                        : imageUrl + '/' + props.station.fileNames![0]
                }
                alt={'House Preview'}
            />
            <div className={styles.inner}>
                <div className={styles.title}>{props.station.title ?? ''}</div>
                <div className={styles.desc}>
                    {props.station.description ?? ''}
                </div>
                <div className={styles.address}>
                    <span>
                        {props.station.country +
                            ' ' +
                            props.station.province +
                            ' ' +
                            props.station.city}
                    </span>
                    {props.edit ? (
                        <div className={styles.edit}>
                            <Button
                                shape={'circle'}
                                onClick={(event) => {
                                    event.stopPropagation()
                                    navigate(
                                        '/station/add?id=' +
                                            props.station.hou,seId
                                    )
                                }}
                                icon={<EditOutlined />}></Button>
                            <Button
                                shape={'circle'}
                                onClick={(event) => {
                                    event.stopPropagation()
                                    navigate(
                                        '/station/add?id=' +
                                            props.station,.houseId
                                    )
                                }}
                                icon={<DeleteOutlined />}></Button>
                        </div>
                    ) : (
                        <></>
                    )}
                </div>
                <div className={styles.tags}></div>
            </div>
        </div>
    )
}

export default StationItem
