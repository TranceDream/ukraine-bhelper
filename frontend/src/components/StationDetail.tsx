import React from 'react'
import styles from './StationDetail.module.scss'
import {
    AlertOutlined,
    CalendarOutlined,
    GithubOutlined,
    TeamOutlined,
} from '@ant-design/icons'
import { Button } from 'antd'
import { imageUrl, StationModel } from '../lib/request'
import Tag from './Tag'

interface StationDetailProps {
    station: StationModel
    contactList: any[]
    onReport: Function
}

const StationDetail = (props: StationDetailProps) => {
    console.log(props.contactList)
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
                <div className={styles.address}>
                    <span>
                        {props.station.country +
                            ' ' +
                            props.station.province +
                            ' ' +
                            props.station.city}
                    </span>
                    <span>{props.station.address}</span>
                </div>
                <div className={styles.desc}>{props.station.description}</div>
                <div className={styles.tags}>
                    {props.station.pets ? (
                        <Tag
                            content={
                                props.station.pets === 'YES'
                                    ? '允许宠物'
                                    : '禁止宠物'
                            }
                            icon={<GithubOutlined />}
                            bg={'lightgreen'}
                        />
                    ) : (
                        <></>
                    )}
                    {props.station.duration ? (
                        <Tag
                            content={'可居住' + props.station.duration + '月'}
                            icon={<CalendarOutlined />}
                            bg={'lightblue'}
                        />
                    ) : (
                        <></>
                    )}
                    {props.station.guests ? (
                        <Tag
                            content={'可容纳' + props.station.guests + '人'}
                            icon={<TeamOutlined />}
                            bg={'lightyellow'}
                        />
                    ) : (
                        <></>
                    )}
                </div>
                <div className={styles.images}>
                    {props.station.fileNames!.map((img) => (
                        <img src={imageUrl + '/' + img} alt={'House Preview'} />
                    ))}
                </div>
                <div className={styles.contact}>
                    <ul>
                        {props.contactList.map(
                            ({ contactName, content }, index) => (
                                <li key={contactName + index}>
                                    {contactName}: {content}
                                </li>
                            )
                        )}
                    </ul>
                </div>
            </div>
        </div>
    )
}

export default StationDetail
