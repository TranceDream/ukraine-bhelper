import React, { useEffect, useState } from 'react'
import styles from './Station.module.scss'
import Header from '../../components/Header'
// import { useLocation } from 'react-router-dom'
import StationDetail from '../../components/StationDetail'
import { Button, Form, Input, message, Modal, Spin } from 'antd'
import { useLocation, useNavigate } from 'react-router-dom'
import {
    ContactModel,
    getStationDetail,
    reportStation,
    StationModel,
    TagModel,
} from '../../lib/request'
import { LoadingOutlined } from '@ant-design/icons'
import Footer from '../../components/Footer'

const Station = () => {
    const useQuery = () => new URLSearchParams(useLocation().search)
    const query = useQuery()
    const id = query.get('id')
    const navigate = useNavigate()
    const [modalVisible, setModal] = useState(false)
    const [reportReason, setReportReason] = useState('')
    const [station, setStation] = useState<StationModel>()
    const [contactList, setContactList] = useState<ContactModel[]>()
    const [tagList, setTagList] = useState<TagModel[]>()

    useEffect(() => {
        if (!id) {
            navigate('/404', { replace: true })
        }
        getStationDetail(parseInt(id!)).then((res) => {
            if (res.code === 200) {
                const obj = {
                    ...res.data.houseInfo,
                    fileNames: res.data.filePicList,
                }
                setContactList(res.data.ContactList)
                setTagList(res.data.tagList)
                setStation(obj)
            } else {
                message.error('出错了: ' + res.msg).then()
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
                        reportStation(parseInt(id!), reportReason).then(
                            (res) => {
                                if (res.code === 200) {
                                    message.success('举报成功').then()
                                    setModal(false)
                                    setReportReason('')
                                }
                            }
                        )
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
                {station ? (
                    <StationDetail
                        station={station!}
                        contactList={contactList!}
                        tagList={tagList!}
                        onReport={() => {
                            setModal(true)
                        }}
                    />
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

export default Station
