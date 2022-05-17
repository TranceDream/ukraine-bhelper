import React from 'react'
import styles from './StationDetail.module.scss'
import {
    AlertOutlined,
    CalendarOutlined,
    GithubOutlined,
    TeamOutlined,
} from '@ant-design/icons'
import { Button } from 'antd'
import { imageUrl, StationModel, TagModel } from '../lib/request'
import Tag from './Tag'
import { useTranslation } from 'react-i18next'

interface StationDetailProps {
    station: StationModel
    contactList: any[]
    tagList: TagModel[]
    onReport: Function
}

const StationDetail = (props: StationDetailProps) => {
    const { t } = useTranslation()

    return (
        <div className={styles.container}>
            <div className={styles.inner}>
                <div className={styles.header}>
                    <div className={styles.title}>{props.station.title}</div>
                    <Button
                        className={styles.report}
                        shape={'circle'}
                        size={'large'}
                        title={t('report')}
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
                                    ? t('station.allow')
                                    : t('station.disallow')
                            }
                            icon={<GithubOutlined />}
                            bg={'lightgreen'}
                        />
                    ) : (
                        <></>
                    )}
                    {props.station.duration ? (
                        <Tag
                            content={t('station.durationTag', {
                                value: props.station.duration,
                            })}
                            icon={<CalendarOutlined />}
                            bg={'lightblue'}
                        />
                    ) : (
                        <></>
                    )}
                    {props.station.guests ? (
                        <Tag
                            content={t('station.capacityTag', {
                                value: props.station.guests,
                            })}
                            icon={<TeamOutlined />}
                            bg={'lightyellow'}
                        />
                    ) : (
                        <></>
                    )}
                </div>
                {props.tagList.length === 0 ? null : (
                    <div className={styles.newTags}>
                        <ul>
                            {props.tagList.map((tag) => (
                                <li key={'tagId' + tag.tagId}>{tag.tagName}</li>
                            ))}
                        </ul>
                    </div>
                )}
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
