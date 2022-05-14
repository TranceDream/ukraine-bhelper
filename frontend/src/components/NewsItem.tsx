import React, { useState } from 'react'
import styles from './NewsItem.module.scss'
import {
    EmailIcon,
    EmailShareButton,
    FacebookIcon,
    FacebookShareButton,
    RedditIcon,
    RedditShareButton,
    TelegramIcon,
    TelegramShareButton,
    TwitterIcon,
    TwitterShareButton,
    WhatsappIcon,
    WhatsappShareButton,
} from 'react-share'
import { ShareAltOutlined } from '@ant-design/icons'
import { NewsModel } from '../lib/request'
import { NavLink } from 'react-router-dom'

interface NewsItemProps {
    news: NewsModel
}

const NewsItem = (props: NewsItemProps) => {
    const url = window.location.host + '/news/detail?id=' + props.news.articleId
    const [showShare, setShare] = useState(false)

    return (
        <div className={styles.container}>
            <NavLink to={'/news/detail?id=' + props.news.articleId}>
                <h3 className={styles.title}>{props.news.title}</h3>
            </NavLink>
            <div className={styles.info}>
                <span className={styles.date}>{props.news.createTime}</span>
                <div className={styles.flex}></div>
                <div
                    className={
                        styles.shareGroup +
                        (showShare ? ' ' + styles.expanded : '')
                    }>
                    <EmailShareButton url={url}>
                        <EmailIcon size={32} />
                    </EmailShareButton>
                    <FacebookShareButton url={url}>
                        <FacebookIcon size={32} />
                    </FacebookShareButton>
                    <TwitterShareButton url={url}>
                        <TwitterIcon size={32} />
                    </TwitterShareButton>
                    <RedditShareButton url={url}>
                        <RedditIcon size={32} />
                    </RedditShareButton>
                    <WhatsappShareButton url={url}>
                        <WhatsappIcon size={32} />
                    </WhatsappShareButton>
                    <TelegramShareButton url={url}>
                        <TelegramIcon size={32} />
                    </TelegramShareButton>
                </div>
                <ShareAltOutlined
                    className={styles.share}
                    onClick={() => {
                        setShare(!showShare)
                    }}
                />
            </div>
        </div>
    )
}

export default NewsItem
