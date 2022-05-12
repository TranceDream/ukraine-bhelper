import React from 'react'
import styles from './StationDetail.module.scss'
import { AlertOutlined } from '@ant-design/icons'
import { Button } from 'antd'

interface Station {
    id: number
    title: string
    description: string
    address: string
    tags: Array<string>
    images: Array<string>
}

interface StationDetailProps {
    station: Station
    onReport: Function
}

const StationDetail = (props: StationDetailProps) => {
    return (
        <div className={styles.container}>
            <div className={styles.inner}>
                <div className={styles.header}>
                    <div className={styles.title}>Title</div>
                    <Button
                        className={styles.report}
                        shape={'circle'}
                        size={'large'}
                        title={'举报'}
                        onClick={props.onReport.bind(this)}>
                        <AlertOutlined />
                    </Button>
                </div>
                <div className={styles.address}>Address</div>
                <div className={styles.desc}>
                    Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                    Autem eius odit sequi. Accusantium architecto deleniti dolor
                    enim expedita magnam necessitatibus odit omnis, possimus,
                    quam quos recusandae sint! Ad blanditiis commodi culpa
                    debitis earum ex exercitationem fugiat harum ipsa ipsum,
                    neque nesciunt nulla quae quas ratione rem reprehenderit
                    sapiente velit, voluptatibus.
                </div>
                <div className={styles.tags}>
                    {props.station.tags.map((tag) => (
                        <div>{tag}</div>
                    ))}
                </div>
                <div className={styles.images}>
                    {props.station.images.map((img) => (
                        <img src={img} alt={'house preview'} />
                    ))}
                </div>
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
