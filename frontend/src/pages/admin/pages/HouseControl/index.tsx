/*
 * @Author: Linhao Yu
 * @Date: 2022-05-11 00:08:19
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-11 02:55:47
 */
import { EditOutlined } from '@ant-design/icons'
import ProTable, { ActionType, ProColumns } from '@ant-design/pro-table'
import { message, Modal } from 'antd'
import React, { useEffect, useRef, useState } from 'react'
import { reqHouseList } from '../../api'
import EditHouseModal from '../../components/House/EditHouse'
import PubSub from '../../Utils/pubsub'
import './ant-pro-card.scss'
import styles from './index.module.scss'

export type TableListItem = {
    key: number
    houseId: number
    country: string
    province: string
    city: string
    createTime: number
    address: string
    guests: number // 容纳人数
    pets: string
    duration: number
    sort: string
    sortop: string
    labels: []
}

export default function HouseControl() {
    const [EditHouseVisible, setEditHouseVisible] = useState(false)
    const [confirmLoading, setConfirmLoading] = useState(false)
    const [record, setRecord] = useState<any>({})
    const [tableListDataSource, settableListDataSource] = useState<
        TableListItem[]
    >([]) // 记录操作行的数据
    const ref = useRef<ActionType>()

    useEffect(() => {
        var token = PubSub.subscribe(
            'updateHouse',
            (msg: string, data: string) => {
                if (data === 'success') {
                    message.success('修改成功')
                } else {
                    message.error('修改失败')
                }
                setEditHouseVisible(false)
                ref.current?.reload()
            }
        )

        return () => {
            PubSub.unsubscribe(token)
        }
    }, [])
    // 处理房源信息
    const getdata = (data: any) => {
        // console.log("datya",data)
        let temp: TableListItem[]
        temp = []
        data.forEach((item: any) => {
            let newitem: TableListItem
            item.createAt = new Date(item.createAt).getTime()
            newitem = { ...item }
            newitem.key = Date.now()
            // newitem.duration = item
            temp.push(newitem)
        })
        console.log('temp', temp)
        settableListDataSource(temp)
    }

    // 编辑房源信息
    const EditHouse = (_: any, record: any, index: number) => {
        // console.log("record",record)
        setRecord(record)
        setEditHouseVisible(true)
    }

    // // 成功编辑房屋
    // const handleEditHouseOk = () => {
    //     message.error('没有实现')
    //     setEditHouseVisible(false)
    // }

    // 取消编辑房屋
    const handleEditHouseCancel = () => {
        setEditHouseVisible(false)
    }

    const handleErr = (msg: any) => {
        message.error(msg.errormsg)
    }
    const columns: ProColumns<TableListItem>[] = [
        {
            title: '房源ID',
            width: 80,
            dataIndex: 'houseId',
            align: 'center',
            render: (_, record: any) => <a>{_}</a>,
        },
        {
            title: '国家',
            width: 100,
            dataIndex: 'country',
            align: 'center',
        },
        {
            title: '省份',
            width: 100,
            dataIndex: 'province',
            align: 'center',
        },
        {
            title: '城市',
            width: 100,
            dataIndex: 'city',
            align: 'center',
        },
        {
            title: '创建时间',
            align: 'center',
            width: 140,
            key: 'since',
            search: false,
            dataIndex: 'createTime',
            valueType: 'date',
        },
        {
            title: '接待时长',
            align: 'center',
            width: 140,
            key: 'duration',
            search: false,
            dataIndex: 'duration',
        },
        {
            title: '接纳人数',
            align: 'center',
            width: 140,
            key: 'guests',
            search: false,
            dataIndex: 'guests',
        },
        {
            title: '排序字段',
            width: 140,
            key: 'sort',
            // dataIndex: 'orderText',
            hideInTable: true,
            valueEnum: {
                duration: '接纳时长',
                guests: '接纳人数',
                createTime: '创建时间',
            },
        },
        {
            title: '排序方式',
            width: 140,
            key: 'sortop',
            // dataIndex: 'orderText',
            hideInTable: true,
            valueEnum: {
                asc: '升序',
                desc: '降序',
            },
        },
        {
            title: '操作',
            width: 180,
            key: 'option',
            valueType: 'option',
            render: (_: any, record: any, index: number) => [
                // <a key='delete' onClick={() => deleteUser(text, record, index)}>
                //     删除
                // </a>,
                <a key='edit' onClick={() => EditHouse(_, record, index)}>
                    编辑
                </a>,
                // <a key='change' onClick={() => showChangePwd(record)}>
                //     重置密码
                // </a>,
            ],
        },
    ]
    return (
        <>
            <ProTable<TableListItem>
                actionRef={ref}
                columns={columns}
                request={async (params, sorter, filter) => {
                    params = Object.assign(params, { pageNo: params.current })
                    // 表单搜索项会从 params 传入，传递给后端接口。
                    if ('houseId' in params) {
                        params.houseId = parseInt(params.houseId)
                    }
                    // console.log('before', params)

                    for (let Key in params) {
                        if (!params[Key]) {
                            delete params[Key]
                        }
                    }
                    // console.log("after",params)
                    // setParams(params)
                    // console.log('UseControl: ', params, sorter, filter)
                    const msg = await reqHouseList({
                        ...params,
                        ...sorter,
                    })
                    if (msg.code === 200) {
                        if (msg.data.houseinfo) {
                            getdata(msg.data.houseinfo)
                        }
                        return {
                            data: tableListDataSource,
                            // success 请返回 true，
                            // 不然 table 会停止解析数据，即使有数据
                            success: true,
                            // 不传会使用 data 的长度，如果是分页一定要传
                            total: msg.data.count,
                        }
                    } else {
                        handleErr(msg)
                        tableListDataSource.length = 0
                        return {
                            data: tableListDataSource,
                            // success 请返回 true，
                            // 不然 table 会停止解析数据，即使有数据
                            success: true,
                            // 不传会使用 data 的长度，如果是分页一定要传
                            total: tableListDataSource.length,
                        }
                    }
                }}
                toolbar={{
                    multipleLine: false,
                    actions: [],
                }}
                dataSource={tableListDataSource}
                rowKey={(record) => {
                    return record.houseId + Date.now().toString() //在这里加上一个时间戳就可以了
                }}
                pagination={{
                    showQuickJumper: true,
                }}
                search={
                    {
                        // optionRender: false,
                        // collapsed: searchCollapsed,
                        // onCollapse: onCollapse,
                    }
                }
                dateFormatter='string'
                headerTitle='所有房源'
                className={styles.protable}
            />
            {/* 编辑房源 */}
            <Modal
                destroyOnClose={true}
                title={
                    <>
                        <EditOutlined
                            style={{
                                fontSize: 20,
                                color: '#000',
                                marginRight: 10,
                            }}
                        />
                        编辑房源信息
                    </>
                }
                visible={EditHouseVisible}
                // onOk={handleEditHouseOk}
                confirmLoading={confirmLoading}
                onCancel={handleEditHouseCancel}
                footer={null}>
                <EditHouseModal record={record} />
                {/* <ChangePwd userName={record.name} userId={record.userId} /> */}
            </Modal>
        </>
    )
}
