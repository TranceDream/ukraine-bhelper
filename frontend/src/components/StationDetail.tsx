import React from 'react'
import styles from './StationDetail.module.scss'
import { AlertOutlined } from '@ant-design/icons'
import { Button } from 'antd'
import { StationModel } from '../lib/request'

interface StationDetailProps {
    station: StationModel
    onReport: Function
}

const StationDetail = (props: StationDetailProps) => {
    return (
        <div className={styles.container}>
            <div className={styles.inner}>
                <div className={styles.header}>
                    <div className={styles.title}>{props.station.title}</div>
                    <Button
                        className={styles.report}
                        shape={'circle'}
                        size={'large'}
                        title={'举报'}
                        onClick={props.onReport.bind(this)}>
                        <AlertOutlined />
                    </Button>
                </div>
                <div className={styles.address}>{props.station.address}</div>
                <div className={styles.desc}>{props.station.description}</div>
                <div className={styles.tags}></div>
                <div className={styles.images}></div>
                <div className={styles.contact}>
                    <ul>
                        <li>Tel: 12345678910</li>
                        <li>QQ: 337845818</li>
                        <li>Email: 337845818@qq.com</li>
                    </ul>
                </div>
            </div>
        </div>
    )
}

export default StationDetail
