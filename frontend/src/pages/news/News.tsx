import React, { useEffect, useState } from 'react'
import { getNewsDetail, NewsModel, reportNews } from '../../lib/request'
import styles from './News.module.scss'
import Header from '../../components/Header'
import { Button, Form, Input, Modal, Spin } from 'antd'
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

const News = () => {
    const useQuery = () => new URLSearchParams(useLocation().search)
    const query = useQuery()
    const id = query.get('id')
    const url = window.location.toString()
    const navigate = useNavigate()
    const [modalVisible, setModal] = useState(false)
    const [reportReason, setReportReason] = useState('')
    const [news, setNews] = useState<NewsModel>()
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
                title={'举报'}
                centered
                footer={null}>
                <Form
                    onFinish={() => {
                        reportNews(parseInt(id!), reportReason).then((res) => {
                            if (res.code === 200) {
                                console.log('已举办')
                                setModal(false)
                                setReportReason('')
                            }
                        })
                    }}>
                    <Form.Item>
                        <span style={{ fontSize: 'large' }}>
                            请填写举报理由
                        </span>
                    </Form.Item>
                    <Form.Item
                        name={'reason'}
                        rules={[{ required: true, message: '请输入举报理由' }]}>
                        <Input.TextArea
                            placeholder={'填写举报理由'}
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
                            Submit
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
                                title={'举报'}
                                onClick={() => {
                                    setModal(true)
                                }}>
                                <AlertOutlined />
                            </Button>
                        </div>
                        <ReactMarkdown>{news.content ?? ''}</ReactMarkdown>
                        <div className={styles.share}>
                            <span>分享到媒体: </span>
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
