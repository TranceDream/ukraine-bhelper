import React, { useEffect, useState } from 'react'
import { getNewsDetail, NewsModel, reportNews } from '../../lib/request'
import styles from './News.module.scss'
import Header from '../../components/Header'
import { Button, Form, Input, message, Modal, Spin } from 'antd'
import { AlertOutlined, LoadingOutlined } from '@ant-design/icons'
import Footer from '../../components/Footer'
import { useLocation, useNavigate } from 'react-router-dom'
import ReactMarkdown from 'react-markdown'
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
import { useTranslation } from 'react-i18next'

const News = () => {
    const useQuery = () => new URLSearchParams(useLocation().search)
    const query = useQuery()
    const id = query.get('id')
    const url = window.location.toString()
    const navigate = useNavigate()
    const [modalVisible, setModal] = useState(false)
    const [reportReason, setReportReason] = useState('')
    const [news, setNews] = useState<NewsModel>()
    const { t } = useTranslation()

    useEffect(() => {
        if (!id) {
            navigate('/404', { replace: true })
        }
        getNewsDetail(parseInt(id!)).then((res) => {
            if (res.code === 200) {
                setNews(res.data.articles[0])
            } else {
                navigate('/404', { replace: true })
            }
        })
    }, [id, navigate])

    return (
        <div className={styles.container}>
            <header>
                <Header />
            </header>
            <Modal
                visible={modalVisible}
                onCancel={() => {
                    setModal(false)
                    setReportReason('')
                }}
                title={t('report')}
                centered
                footer={null}>
                <Form
                    onFinish={() => {
                        reportNews(parseInt(id!), reportReason).then((res) => {
                            if (res.code === 200) {
                                message.success('举报成功').then((_) => {})
                                setModal(false)
                                setReportReason('')
                            } else {
                                message
                                    .error('出错了: ' + res.msg)
                                    .then((_) => {})
                            }
                        })
                    }}>
                    <Form.Item>
                        <span style={{ fontSize: 'large' }}>
                            {t('reportReason')}
                        </span>
                    </Form.Item>
                    <Form.Item
                        name={'reason'}
                        rules={[
                            { required: true, message: t('reportReason') },
                        ]}>
                        <Input.TextArea
                            placeholder={t('reportReason')}
                            size={'large'}
                            autoSize={{ maxRows: 10, minRows: 3 }}
                            value={reportReason}
                            required
                            onChange={(e) => {
                                setReportReason(e.target.value)
                            }}></Input.TextArea>
                    </Form.Item>
                    <Form.Item>
                        <Button
                            htmlType={'submit'}
                            type={'primary'}
                            size={'large'}
                            style={{ width: '100%' }}>
                            {t('submit')}
                        </Button>
                    </Form.Item>
                </Form>
            </Modal>
            <main>
                {news ? (
                    <div className={styles.md}>
                        <div className={styles.header}>
                            <div className={styles.title}>{news.title}</div>
                            <Button
                                className={styles.report}
                                shape={'circle'}
                                size={'large'}
                                title={t('report')}
                                onClick={() => {
                                    setModal(true)
                                }}>
                                <AlertOutlined />
                            </Button>
                        </div>
                        <ReactMarkdown>{news.content ?? ''}</ReactMarkdown>
                        <div className={styles.share}>
                            <span>{t('news.share')}: </span>
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
                    </div>
                ) : (
                    <Spin
                        indicator={
                            <LoadingOutlined
                                style={{ fontSize: 'xxx-large' }}
                            />
                        }
                    />
                )}
            </main>
            <footer>
                <Footer />
            </footer>
        </div>
    )
}

export default News
