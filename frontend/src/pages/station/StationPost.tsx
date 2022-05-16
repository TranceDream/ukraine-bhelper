import React, { useEffect, useState } from 'react'
import styles from './StationPost.module.scss'
import Header from '../../components/Header'
import {
    Button,
    Checkbox,
    Form,
    Input,
    InputNumber,
    message,
    Modal,
    Result,
    Select,
    Space,
    Spin,
    Table,
    Upload,
} from 'antd'
import { Option } from 'antd/es/mentions'
import {
    ContactModel,
    ContactTypeModel,
    deleteContact,
    deleteTag,
    getContactTypeList,
    getStationDetail,
    getTagTypeList,
    postTag,
    publishStation,
    StationModel,
    TagModel,
    updateContact,
    updateStation,
} from '../../lib/request'
import { useLocation, useNavigate } from 'react-router-dom'
import {
    DeleteOutlined,
    EditOutlined,
    LoadingOutlined,
    MinusOutlined,
    PlusOutlined,
} from '@ant-design/icons'
import Footer from '../../components/Footer'
import {
    CityModel,
    CountryModel,
    getCities,
    getCountries,
    getStates,
    StateModel,
} from '../../lib/district'
import { values } from 'lodash'

const StationPost = () => {
    const useQuery = () => new URLSearchParams(useLocation().search)
    const query = useQuery()
    const id = query.get('id')
    const navigate = useNavigate()

    const [countryList, setCountryList] = useState<CountryModel[]>([])
    const [stateList, setStateList] = useState<StateModel[]>([])
    const [cityList, setCityList] = useState<CityModel[]>([])

    const [station, setStation] = useState<StationModel>({
        country: '',
        province: '',
        city: '',
    })
    const [error, setError] = useState<boolean>(false)
    const [loading, setLoadingState] = useState(true)
    const [fileList, setFileList] = useState<any[]>([])
    const [contactTypeList, setContactTypeList] = useState<ContactTypeModel[]>()
    const [contactList, setContactList] = useState<ContactModel[]>([
        {
            content: '',
        },
    ])
    const [currentContact, setCurrentContact] = useState<ContactModel>()
    const [showModal, setModal] = useState<boolean>(false)
    const [tagList, setTagList] = useState<TagModel[]>([])
    const [selectedTags, setSelectedTags] = useState<number[]>([])
    const [tagTypeList, setTagTypeList] = useState<TagModel[]>([])

    const columns = [
        { title: 'Contact type', key: 'contactName', dataIndex: 'contactName' },
        { title: 'Content', key: 'content', dataIndex: 'content' },
        {
            title: 'Action',
            key: 'action',
            render: (_: any, record: any) => (
                <Space size={'middle'}>
                    <Button
                        shape={'circle'}
                        icon={<EditOutlined />}
                        onClick={() => {
                            setCurrentContact(record)
                            setModal(true)
                        }}></Button>
                    <Button
                        shape={'circle'}
                        icon={<DeleteOutlined />}
                        onClick={() => {
                            deleteContact(record.contactId).then((res) => {
                                if (res.code === 200) {
                                    let list = contactList.filter(
                                        (c) => c.contactId !== record.contactId
                                    )
                                    setContactList(list)
                                }
                            })
                        }}></Button>
                </Space>
            ),
        },
    ]

    useEffect(() => {
        setCountryList(getCountries())

        if (id) {
            getStationDetail(parseInt(id!)).then((res) => {
                if (res.code === 200) {
                    const info = res.data.houseInfo
                    setStation(info)
                    setStateList(getStates(info.country))
                    setCityList(getCities(info.country, info.province))
                    setContactList(res.data.ContactList)
                    setTagList(res.data.tagList)
                    setSelectedTags(
                        res.data.tagList.map(
                            (tag: { typeId: any }) => tag.typeId
                        )
                    )
                    getTagTypeList().then((tag) => {
                        if (tag.code === 200) {
                            setTagTypeList(tag.data.tagTypeid)
                            getContactTypeList().then((c) => {
                                if (c.code === 200) {
                                    setContactTypeList(c.data.data)
                                } else {
                                    setError(true)
                                }
                                setLoadingState(false)
                            })
                        } else {
                            message.error('出错了: ' + tag.msg).then()
                            setError(true)
                            setLoadingState(false)
                        }
                    })
                } else {
                    message.error('出错了: ' + res.msg).then()
                    setError(true)
                }
            })
        } else {
            getTagTypeList().then((tag) => {
                if (tag.code === 200) {
                    setTagTypeList(tag.data.tagTypeid)
                    getContactTypeList().then((c) => {
                        if (c.code === 200) {
                            setContactTypeList(c.data.data)
                        } else {
                            setError(true)
                        }
                        setLoadingState(false)
                    })
                } else {
                    message.error('出错了: ' + tag.msg).then()
                    setError(true)
                    setLoadingState(false)
                }
            })
        }
    }, [id, navigate])

    let getCheckStatus = (typeId: number): boolean => {
        let ret = false
        selectedTags.forEach((tag) => {
            if (tag === typeId) {
                ret = true
            }
        })
        return ret
    }

    return (
        <div className={styles.container}>
            <header>
                <Header />
            </header>
            <main>
                <Modal
                    visible={showModal}
                    onOk={() => {
                        updateContact(
                            currentContact!.contactId!,
                            parseInt(id!),
                            currentContact!.typeId!,
                            currentContact!.content!
                        ).then((res) => {
                            if (res.code === 200) {
                                let list = contactList.map((c) =>
                                    c.contactId === currentContact!.contactId
                                        ? currentContact!
                                        : c
                                )
                                setContactList(list)
                                setModal(false)
                                setCurrentContact(undefined)
                            }
                        })
                    }}
                    onCancel={() => {
                        setModal(false)
                        setCurrentContact(undefined)
                    }}
                    title={'修改联系方式'}
                    centered>
                    <Form.Item label={currentContact?.contactName}>
                        <Input
                            defaultValue={currentContact?.content}
                            onChange={(e) => {
                                let obj = Object.assign(currentContact!)
                                obj.content = e.target.value
                                setCurrentContact(obj)
                            }}></Input>
                    </Form.Item>
                </Modal>
                <div className={styles.form}>
                    {!loading ? (
                        error ? (
                            <Result
                                status='error'
                                title='失败'
                                subTitle='请检查网络或权限'
                                extra={[
                                    <Button
                                        type='primary'
                                        key='console'
                                        onClick={() => {
                                            navigate('/station')
                                        }}>
                                        回到房源
                                    </Button>,
                                ]}
                            />
                        ) : (
                            <Form
                                onValuesChange={(values) => {
                                    setStation(values)
                                }}
                                labelCol={{ span: 2 }}
                                size={'large'}
                                onFinish={(values: StationModel) => {
                                    if (id) {
                                        Promise.all(
                                            tagList.map((tag) => {
                                                return deleteTag(tag.tagId!)
                                            })
                                        ).then((res) => {
                                            postTag(
                                                selectedTags.map((st) => ({
                                                    houseId: parseInt(id!),
                                                    typeId: st,
                                                }))
                                            )
                                        })
                                        updateStation(
                                            parseInt(id!),
                                            values
                                        ).then((res) => {
                                            if (res.code === 200) {
                                                navigate('/station')
                                            }
                                        })
                                    } else {
                                        publishStation(
                                            values,
                                            fileList.map(
                                                (file) => file.originFileObj
                                            ),
                                            contactList,
                                            selectedTags.map((st) => ({
                                                typeId: st,
                                            }))
                                        ).then((res) => {
                                            if (res.code === 200) {
                                                navigate('/station')
                                            }
                                        })
                                    }
                                }}>
                                <h2>House information</h2>
                                <Form.Item
                                    initialValue={station.title}
                                    name={'title'}
                                    label={'Title'}>
                                    <Input
                                        // value={station.title}
                                        maxLength={100}
                                        showCount
                                        onChange={(e) => {
                                            let updatedStation =
                                                Object.assign(station)
                                            updatedStation.title =
                                                e.target.value
                                            setStation(updatedStation)
                                        }}></Input>
                                </Form.Item>
                                <Form.Item
                                    name='country'
                                    label='Country'
                                    initialValue={station.country}
                                    rules={[
                                        {
                                            required: true,
                                            message:
                                                'Please select your country!',
                                        },
                                    ]}>
                                    <Select
                                        placeholder='Please select a country'
                                        value={station.country}
                                        onChange={(value) => {
                                            let obj = Object.assign(station)
                                            obj.country = value
                                            if (value !== station.country) {
                                                obj.province = ''
                                                obj.city = ''
                                            }
                                            setStation(obj)
                                            setStateList(getStates(value))
                                            setCityList([])
                                        }}>
                                        {countryList.map((country) => (
                                            <Option
                                                key={'c' + country.code}
                                                value={country.code}>
                                                {country.country}
                                            </Option>
                                        ))}
                                    </Select>
                                </Form.Item>
                                <Form.Item
                                    name='province'
                                    label='Province'
                                    rules={[
                                        {
                                            required: true,
                                            message:
                                                'Please select your province!',
                                        },
                                    ]}
                                    initialValue={station.province}>
                                    <Select
                                        key={station.province}
                                        value={station.province}
                                        placeholder='Please select a province'
                                        disabled={station.country === ''}
                                        onChange={(e) => {
                                            let obj = Object.assign(station)
                                            obj.province = e
                                            if (e !== station.province) {
                                                obj.city = ''
                                            }
                                            setStation(obj)
                                            setCityList(
                                                getCities(station.country, e)
                                            )
                                        }}>
                                        {stateList.map((state) => (
                                            <Option
                                                key={'s' + state.code}
                                                value={state.code}>
                                                {state.state}
                                            </Option>
                                        ))}
                                    </Select>
                                </Form.Item>
                                <Form.Item
                                    name='city'
                                    label='City'
                                    initialValue={station.city}
                                    rules={[
                                        {
                                            required: true,
                                            message: 'Please select your city!',
                                        },
                                    ]}>
                                    <Select
                                        defaultValue={station.city}
                                        disabled={station.province === ''}
                                        placeholder='Please select a city'>
                                        {cityList.map((city) => (
                                            <Option
                                                key={'t' + city.city}
                                                value={city.city}>
                                                {city.city}
                                            </Option>
                                        ))}
                                    </Select>
                                </Form.Item>
                                <Form.Item
                                    initialValue={station.address}
                                    name={'address'}
                                    label={'Address'}>
                                    <Input maxLength={200} showCount></Input>
                                </Form.Item>
                                <Form.Item
                                    initialValue={station.guests}
                                    name={'guests'}
                                    label={'Capacity'}>
                                    <InputNumber min={0}></InputNumber>
                                </Form.Item>
                                <Form.Item
                                    name='pets'
                                    label='Pets'
                                    initialValue={station.pets}
                                    wrapperCol={{ span: 3 }}>
                                    <Select placeholder='N/A'>
                                        <Option value='YES'>Allow</Option>
                                        <Option value='NO'>Disallow</Option>
                                    </Select>
                                </Form.Item>
                                <Form.Item
                                    name='duration'
                                    label='Duration'
                                    initialValue={station.duration}
                                    wrapperCol={{ span: 3 }}>
                                    <Select placeholder='N/A'>
                                        <Option value='0'>N/A</Option>
                                        {[
                                            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                                            12,
                                        ].map((e) => (
                                            <Option value={e.toString()}>
                                                {e}
                                            </Option>
                                        ))}
                                    </Select>
                                </Form.Item>
                                <Form.Item
                                    name='description'
                                    label='Description'
                                    initialValue={station.description}
                                    rules={[
                                        {
                                            required: true,
                                            message:
                                                'Please input Description!',
                                        },
                                    ]}>
                                    <Input.TextArea
                                        autoSize={{ minRows: 3, maxRows: 15 }}
                                        showCount
                                        maxLength={500}
                                    />
                                </Form.Item>

                                {id ? (
                                    <>
                                        <Form.Item>
                                            <Table
                                                columns={columns}
                                                dataSource={contactList}
                                            />
                                        </Form.Item>
                                    </>
                                ) : (
                                    <>
                                        <Form.Item>
                                            <Button
                                                className={
                                                    styles.loginFormButton
                                                }
                                                onClick={() => {
                                                    let list =
                                                        contactList.slice()
                                                    list.push({ content: '' })
                                                    setContactList(list)
                                                }}>
                                                Add Contact
                                            </Button>
                                        </Form.Item>
                                        {contactList.map((contact, index) => (
                                            <Form.Item
                                                label={'Contact'}
                                                rules={[
                                                    {
                                                        required: true,
                                                        message:
                                                            'Please input Contact!',
                                                    },
                                                ]}>
                                                <div
                                                    style={{
                                                        display: 'flex',
                                                        width: '100%',
                                                        gap: '16px',
                                                    }}>
                                                    <Form.Item
                                                        style={{
                                                            width: '120px',
                                                        }}
                                                        rules={[
                                                            {
                                                                required: true,
                                                                message:
                                                                    'Please input Contact!',
                                                            },
                                                        ]}>
                                                        <Select
                                                            value={
                                                                contact.typeId
                                                            }
                                                            onChange={(v) => {
                                                                let list =
                                                                    contactList.slice()
                                                                list[
                                                                    index
                                                                ].typeId = v
                                                                setContactList(
                                                                    list
                                                                )
                                                            }}>
                                                            {contactTypeList?.map(
                                                                (c) => (
                                                                    <Option
                                                                        key={c.typeId.toString()}
                                                                        value={c.typeId.toString()}>
                                                                        {
                                                                            c.contactName
                                                                        }
                                                                    </Option>
                                                                )
                                                            )}
                                                        </Select>
                                                    </Form.Item>
                                                    <Form.Item
                                                        style={{ flex: 1 }}
                                                        rules={[
                                                            {
                                                                required: true,
                                                                message:
                                                                    'Please input Contact!',
                                                            },
                                                        ]}>
                                                        <Input
                                                            value={
                                                                contact.content
                                                            }
                                                            onChange={(v) => {
                                                                let list =
                                                                    contactList.slice()
                                                                list[
                                                                    index
                                                                ].content =
                                                                    v.target.value
                                                                setContactList(
                                                                    list
                                                                )
                                                            }}></Input>
                                                    </Form.Item>
                                                    <Form.Item>
                                                        <Button
                                                            shape={'circle'}
                                                            icon={
                                                                <MinusOutlined />
                                                            }
                                                            onClick={() => {
                                                                if (
                                                                    contactList.length >
                                                                    1
                                                                ) {
                                                                    let list =
                                                                        contactList.slice()
                                                                    list.splice(
                                                                        index,
                                                                        1
                                                                    )
                                                                    setContactList(
                                                                        list
                                                                    )
                                                                }
                                                            }}></Button>
                                                    </Form.Item>
                                                </div>
                                            </Form.Item>
                                        ))}
                                    </>
                                )}

                                <Form.Item label='Tags'>
                                    <div
                                        style={{
                                            display: 'flex',
                                            flexDirection: 'column',
                                        }}>
                                        {tagTypeList.map((tagType) => (
                                            <Checkbox
                                                style={{
                                                    margin: 0,
                                                    lineHeight: '32px',
                                                }}
                                                value={tagType.typeId!}
                                                checked={getCheckStatus(
                                                    tagType.typeId!
                                                )}
                                                onChange={(v) => {
                                                    let list =
                                                        selectedTags.slice()
                                                    if (
                                                        list.includes(
                                                            v.target.value
                                                        )
                                                    ) {
                                                        list = list.filter(
                                                            (e) =>
                                                                e !==
                                                                v.target.value
                                                        )
                                                        setSelectedTags(list)
                                                    } else {
                                                        list.push(
                                                            v.target.value
                                                        )
                                                        setSelectedTags(list)
                                                    }
                                                }}>
                                                {tagType.tagName!}
                                            </Checkbox>
                                        ))}
                                    </div>
                                </Form.Item>

                                {id ? (
                                    <></>
                                ) : (
                                    <Form.Item
                                        name='image'
                                        label='Image'
                                        rules={[
                                            {
                                                required: true,
                                                message: 'Please upload Image!',
                                            },
                                        ]}>
                                        <Upload
                                            maxCount={9}
                                            beforeUpload={() => {
                                                return false
                                            }}
                                            fileList={fileList}
                                            listType='picture-card'
                                            onChange={(info) => {
                                                setFileList(info.fileList)
                                            }}>
                                            <div>
                                                <PlusOutlined />
                                                <div
                                                    style={{
                                                        marginTop: 8,
                                                        color: '#666',
                                                    }}>
                                                    上传图片
                                                </div>
                                            </div>
                                        </Upload>
                                    </Form.Item>
                                )}
                                <Form.Item>
                                    <Button
                                        type='primary'
                                        htmlType='submit'
                                        className={styles.loginFormButton}>
                                        Submit
                                    </Button>
                                </Form.Item>
                            </Form>
                        )
                    ) : (
                        <Spin indicator={<LoadingOutlined />} />
                    )}
                </div>
            </main>
            <footer>
                <Footer />
            </footer>
        </div>
    )
}

export default StationPost
