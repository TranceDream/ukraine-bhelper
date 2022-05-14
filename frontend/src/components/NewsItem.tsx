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

const NewsItem = () => {
    const url = 'https://baidu.com'
    const [showShare, setShare] = useState(false)

    return (
        <div className={styles.container}>
            <h3 className={styles.title}>
                习近平：把人民生命安全放在第一位 古诗文寄望青年
            </h3>
            <div className={styles.info}>
                <span className={styles.date}>2天前</span>
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
