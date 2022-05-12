import React, { useState } from 'react'
import styles from './Station.module.scss'
import Header from '../../components/Header'
// import { useLocation } from 'react-router-dom'
import StationDetail from '../../components/StationDetail'
import { Button, Form, Input, Modal } from 'antd'

const Station = () => {
    // const useQuery = () => new URLSearchParams(useLocation().search)
    // const query = useQuery()
    // const id = query.get('id')
    const [modalVisible, setModal] = useState(false)
    const [reportReason, setReportReason] = useState('')
    return (
        <div className={styles.container}>
            <header>
                <Header />
            </header>
            <main>
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
                            console.log(
                                'Report Success, reason: ' + reportReason
                            )
                            setModal(false)
                            setReportReason('')
                        }}>
                        <Form.Item>
                            <span style={{ fontSize: 'large' }}>
                                请填写举报理由
                            </span>
                        </Form.Item>
                        <Form.Item
                            name={'reason'}
                            rules={[
                                { required: true, message: '请输入举报理由' },
                            ]}>
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
                <StationDetail
                    station={{
                        id: 1,
                        title: 'House',
                        description: 'Description',
                        address: 'Address',
                        tags: ['可容纳xxx人', '允许宠物', '可短期居住(2周内)'],
                        images: [
                            'https://cdn.jsdelivr.net/gh/TranceDream/ImgHost@master/img/IMG_20220319_165950__01.5v96fzjetqo0.webp',
                            'https://cdn.jsdelivr.net/gh/TranceDream/ImgHost@master/img/IMG_20220319_165950__01.5v96fzjetqo0.webp',
                        ],
                    }}
                    onReport={() => {
                        console.log('modal')
                        setModal(true)
                    }}
                />
            </main>
            <footer>
                <div>footer</div>
            </footer>
        </div>
    )
}

export default Station
