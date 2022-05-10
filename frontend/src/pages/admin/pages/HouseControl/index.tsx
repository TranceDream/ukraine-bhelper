/*
 * @Author: Linhao Yu
 * @Date: 2022-05-11 00:08:19
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-11 01:34:27
 */
import { EditOutlined } from '@ant-design/icons'
import ProTable, { ProColumns } from '@ant-design/pro-table'
import { message, Modal } from 'antd'
import React, { useState } from 'react'
import { reqHouseList } from '../../api'
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
    sort: string
    sortop: string
    labels: []
}

export default function HouseControl() {
    const [EditHouseVisible, setEditHouseVisible] = useState(false)
    const [confirmLoading, setConfirmLoading] = useState(false)
    const [tableListDataSource, settableListDataSource] = useState<
        TableListItem[]
    >([]) // 记录操作行的数据

    // 处理房源信息
    const getdata = (data: any) => {
        // console.log("datya",data)
        let temp: TableListItem[]
        temp = []
        data.forEach((item: any) => {
            let newitem: TableListItem
            newitem = { ...item }
            newitem.key = Date.now()
            item.createAt = new Date(item.createAt).getTime()
            temp.push(newitem)
        })
        console.log('temp', temp)
        settableListDataSource(temp)
    }

    // 编辑房源信息
    const EditHouse = (_: any, record: any, index:number) => {
        setEditHouseVisible(true)
    }

    // 成功编辑房屋
    const handleEditHouseOk = () => {
        message.error("没有实现")
    }

    // 取消编辑房屋
    const handleEditHouseCancel = () =>{
        message.error('没有实现')
    }
    const columns: ProColumns<TableListItem>[] = [
        {
            title: '房源ID',
            width: 50,
            dataIndex: 'houseId',
            align: 'center',
            render: (_, record: any) => <a>{_}</a>,
        },
        // {
        //     title: '国家',
        //     width: 120,
        //     dataIndex: 'name',
        //     align: 'center',
        // },
        // {
        //     title: '城市',
        //     width: 80,
        //     dataIndex: 'roleId',
        //     align: 'center',
        //     // search: false,
        //     filters: true,
        //     onFilter: true,
        //     hideInTable: true,
        //     valueEnum: roleList,
        // },
        {
            title: '国家',
            width: 80,
            dataIndex: 'country',
            align: 'center',
        },
        {
            title: '省份',
            width: 80,
            dataIndex: 'province',
            align: 'center',
        },
        {
            title: '城市',
            width: 80,
            dataIndex: 'city',
            align: 'center',
        },
        {
            title: '创建时间',
            align: 'center',
            width: 140,
            key: 'since',
            dataIndex: 'createTime',
            valueType: 'date',
        },
        // {
        //     disable: true,
        //     title: '标签',
        //     dataIndex: 'labels',
        //     search: false,
        //     width: 100,
        //     renderFormItem: (_, { defaultRender }) => {
        //         return defaultRender(_)
        //     },
        //     render: (_, record) => (
        //         <Space>
        //             {record.labels.map(({ name, color }) => (
        //                 <Tag color={color} key={name}>
        //                     {name}
        //                 </Tag>
        //             ))}
        //         </Space>
        //     ),
        // },
        {
            title: '排序方式',
            width: 140,
            key: 'orderText',
            dataIndex: 'orderText',
            hideInTable: true,
            valueEnum: {
                'ur.USER_ID asc': '按用户ID升序',
                'ur.USER_ID des': '按用户ID降序',
                'ur.CREATE_TIME asc': '按创建时间升序',
                'ur.CREATE_TIME desc': '按创建时间降序',
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
                // actionRef={ref}
                columns={columns}
                request={async (params, sorter, filter) => {
                    // 表单搜索项会从 params 传入，传递给后端接口。
                    if ('userId' in params) {
                        params.userId = parseInt(params.userId)
                    }
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
                    }
                    return Promise.resolve({
                        // data: tableListDataSource,
                        success: true,
                    })
                    // if (msg.code === 200) {
                    //     if (msg.data.data) {
                    //         getdata(msg.data.data)
                    //     } else {
                    //         settableListDataSource([])
                    //     }
                    //     return {
                    //         data: tableListDataSource,
                    //         // success 请返回 true，
                    //         // 不然 table 会停止解析数据，即使有数据
                    //         success: true,
                    //         // 不传会使用 data 的长度，如果是分页一定要传
                    //         total: tableListDataSource.length,
                    //     }
                    // } else {
                    //     handleErr(msg)
                    //     tableListDataSource.length = 0
                    //     return {
                    //         data: tableListDataSource,
                    //         // success 请返回 true，
                    //         // 不然 table 会停止解析数据，即使有数据
                    //         success: true,
                    //         // 不传会使用 data 的长度，如果是分页一定要传
                    //         total: tableListDataSource.length,
                    //     }
                    // }
                }}
                toolbar={{
                    multipleLine: false,
                    actions: [],
                }}
                dataSource={tableListDataSource}
                // rowKey={(record) => {
                //     return record.userId + Date.now().toString() //在这里加上一个时间戳就可以了
                // }}
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
                onOk={handleEditHouseOk}
                confirmLoading={confirmLoading}
                onCancel={handleEditHouseCancel}
                footer={null}>
                {/* <ChangePwd userName={record.name} userId={record.userId} /> */}
            </Modal>
        </>
    )
}
