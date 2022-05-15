/*
 * @Author: Linhao Yu
 * @Date: 2022-04-24 17:17:56
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-15 19:05:06
 */
import { EditOutlined, ExclamationCircleOutlined } from '@ant-design/icons'
import type { ActionType, ProColumns } from '@ant-design/pro-table'
import ProTable from '@ant-design/pro-table'
import { Button, message, Modal, Space } from 'antd'
import React, { useEffect, useRef, useState } from 'react'
import { reqMenuPermissionData, reqUpdatePermission } from '../../api'
import AddModule from '../../components/AddModule'
import EditModule from '../../components/EditModule'
import Icon from '../../components/Icon/Icon'
import PubSub from '../../Utils/pubsub'
import './ant-pro-card.scss'
import styles from './index.module.scss'
export type Status = {
    color: string
    text: string
}

// const statusMap = {
//     0: {
//         color: 'blue',
//         text: '进行中',
//     },
//     1: {
//         color: 'green',
//         text: '已完成',
//     },
//     2: {
//         color: 'volcano',
//         text: '警告',
//     },
//     3: {
//         color: 'red',
//         text: '失败',
//     },
//     4: {
//         color: '',
//         text: '未完成',
//     },
// }

export type TableListItem = {
    key: number
    // name: string
    // containers: number
    // creator: string
    // status: Status
    // createdAt: number

    title: string
    icon?: string
    moduleId?: number
    deleteMark?: string
    url?: string
    location?: string //对应Ke'y
}
// const tableListDataSource: TableListItem[] = []

// const creators = ['付小小', '曲丽丽', '林东东', '陈帅帅', '兼某某']

// for (let i = 0; i < 5; i += 1) {
//     tableListDataSource.push({
//         key: i,
//         title: 'AppName',
//     })
// }
const MapModule: any = {}
const menu: any[] = []

export default function MenuControl() {
    const [record, setRecord] = useState<any>({})
    const [EditVisible, setEditVisible] = useState(false)
    // const [EditAdVisible, setAdEditVisible] = useState(false)
    const [AddVisible, setAddVisible] = useState(false)
    const [deleteVisible, setDeleteVisible] = useState(false)
    const [confirmLoading, setconfirmLoading] = useState(true)
    const [pauseloading, setPauseloading] = useState(false)
    const [loading, setLoading] = useState(true)
    const [tableListDataSource, settableListDataSource] = useState<
        TableListItem[]
    >([]) // 记录操作行的数据
    const [modalText, setModalText] = React.useState('Content of the modal')
    // 展开
    const ref = useRef<ActionType>()
    const ref2 = useRef<ActionType>()
    useEffect(() => {
        async function getMenulist() {
            const res = await reqMenuPermissionData()
            console.table(res.data)
            // !修改
            let temp: TableListItem[]
            temp = []
            res.data.data.forEach((item: any) => {
                let newitem: TableListItem
                newitem = { ...item }
                newitem.key = item.moduleId
                newitem.location = item.key
                temp.push(newitem)
                MapModule[newitem.title] = newitem.moduleId
                menu.push({ value: newitem.title })
            })
            console.log('temp', temp)
            settableListDataSource(temp)
            setLoading(false)
            // setNodes(getMenuNode(menuList, true))
            // setMenuList(res.data)
        }
        getMenulist()

        var addToken = PubSub.subscribe(
            'addModule',
            (msg: string, data: string) => {
                if (data === 'success') {
                    message.success('添加模块成功')
                } else {
                    message.error('添加模块失败')
                }
                setAddVisible(false)
                ref.current?.reload()
            }
        )

        var editToken = PubSub.subscribe(
            'EditModule',
            (msg: string, data: string) => {
                if (data === 'success') {
                    message.success('修改模块成功1')
                } else {
                    message.error('修改模块失败')
                }
                setEditVisible(false)
                ref2.current?.reload()
            }
        )
        return () => {
            PubSub.unsubscribe(addToken)
            PubSub.unsubscribe(editToken)
        }
    }, [])

    const expandedRowRender = (record: any, index: any) => {
        // console.log(record, index)

        // console.log('11111', index, record.childs)
        const data = []
        for (let i = 0; i < record.childs.length; i += 1) {
            data.push({
                key: record.childs[i].moduleId,
                moduleId: record.childs[i].moduleId,
                title: record.childs[i].title,
                url: record.childs[i].url,
                location: record.childs[i].key,
                deleteMark: record.childs[i].deleteMark,
            })
        }

        // 点击修改模块（子
        const handleEdit = (record: any) => {
            setEditVisible(true)
            setRecord(record)
            // console.table("menu",menu)
            // console.table('record', record)
            // console.table('MapModule', MapModule)
        }

        // 点击删除/暂停模块(子)
        const pauseModule = (record: any) => {
            setDeleteVisible(true)
            setRecord(record)
            setModalText(
                '你确定' +
                    (record.deleteMark === 'YES' ? '恢复' : '暂停') +
                    '这个模块吗？'
            )
        }

        return (
            <ProTable
                actionRef={ref2}
                columns={[
                    {
                        title: '模块ID',
                        dataIndex: 'moduleId',
                        key: 'moduleId',
                    },
                    { title: '模块名称', dataIndex: 'title', key: 'title' },

                    {
                        title: '状态',
                        dataIndex: 'deleteMark',
                        key: 'deleteMark',
                        valueEnum: {
                            NO: { text: '运行', status: 'Processing' },
                            YES: { text: '关闭', status: 'Error' },
                            // running: { text: '运行中', status: 'Processing' },
                            // online: { text: '已上线', status: 'Success' },
                            // error: { text: '异常', status: 'Error' },
                        },
                    },
                    {
                        title: '前台地址',
                        dataIndex: 'location',
                        key: 'location',
                    },
                    {
                        title: '后台地址',
                        dataIndex: 'url',
                        key: 'url',
                    },
                    {
                        title: '操作',
                        dataIndex: 'operation',
                        key: 'operation',
                        valueType: 'option',
                        render: (_, record, index) => [
                            <a key='Pause' onClick={() => handleEdit(record)}>
                                修改
                            </a>,
                            <a key='Stop' onClick={() => pauseModule(record)}>
                                {record.deleteMark === 'YES' ? '恢复' : '暂停'}
                            </a>,
                        ],
                    },
                ]}
                headerTitle={false}
                search={false}
                options={false}
                dataSource={data}
                pagination={false}
            />
        )
    }

    const columns: ProColumns<TableListItem>[] = [
        {
            title: '模块名称',
            width: 120,
            dataIndex: 'title',
        },
        {
            title: '图标',
            width: 120,
            dataIndex: 'icon',
            align: 'left',
            // renderFormItem: (_, { defaultRender }) => {
            //     return defaultRender(_)
            // },
            render: (_, record) => <Space>{Icon(record.icon)}</Space>,
        },
        {
            title: '模块ID',
            width: 120,
            dataIndex: 'moduleId',
        },
        {
            title: '状态',
            width: 120,
            dataIndex: 'deleteMark',
            // render: (_, record) => (
            //     <Tag color={record.status.color}>{record.status.text}</Tag>
            // ),
            valueEnum: {
                NO: { text: '运行', status: 'Processing' },
                YES: { text: '关闭', status: 'Error' },
                // running: { text: '运行中', status: 'Processing' },
                // online: { text: '已上线', status: 'Success' },
                // error: { text: '异常', status: 'Error' },
            },
        },
        {
            title: '后台地址',
            width: 120,
            dataIndex: 'url',
        },
        {
            title: '前台地址',
            width: 120,
            dataIndex: 'location',
        },
        {
            title: '操作',
            width: 164,
            key: 'option',
            valueType: 'option',
            render: (_, record) => [
                <a key='Pause' onClick={() => handleEdit(record)}>
                    修改
                </a>,
                <a key='2' onClick={() => pauseModule(record)}>
                    {record.deleteMark === 'YES' ? '恢复' : '暂停'}
                </a>,
            ],
        },
    ]

    // 点击‘添加模块’
    const handleAdd = () => {
        setAddVisible(true)
    }

    // 取消添加模块
    const handleAddCancel = () => {
        setAddVisible(false)
    }

    // 点击暂停模块（主
    const pauseModule = (record: any) => {
        console.log('著删除')
        setDeleteVisible(true)
        setRecord(record)
        setModalText(
            '你确定' +
                (record.deleteMark === 'YES' ? '恢复' : '暂停') +
                '这个模块吗？'
        )
    }

    // 取消暂停模块
    const handleDeleteCancel = () => {
        setDeleteVisible(false)
    }

    //确认暂停/恢复模块
    const handleDeleteOk = async () => {
        setPauseloading(true)
        let tempParam = record
        console.log('record', record)
        tempParam.deleteMark = tempParam.deleteMark === 'YES' ? 'NO' : 'YES'
        tempParam['permissionId'] = tempParam.moduleId
        // !=================================
        const res = await reqUpdatePermission(tempParam)
        if (res.code === 200) {
            message.success('修改状态成功')
        } else {
            message.error('修改状态失败')
        }
        setPauseloading(false)
        setDeleteVisible(false)
        ref2.current?.reload()
    }

    // 取消 编辑
    const handleEditCancel = () => {
        setEditVisible(false)
    }

    // 点击编辑模块（主
    const handleEdit = (record: any) => {
        setEditVisible(true)
        setRecord(record)
        console.table('menu', menu)
        console.table('record', record)
        console.table('MapModule', MapModule)
    }

    return (
        <>
            <ProTable<TableListItem>
                actionRef={ref}
                className={styles.protable}
                columns={columns}
                // request={(params, sorter, filter) => {
                //     // 表单搜索项会从 params 传入，传递给后端接口。
                //     console.log(params, sorter, filter)
                //     return Promise.resolve({
                //         data: tableListDataSource,
                //         success: true,
                //     })
                // }}
                dataSource={tableListDataSource}
                rowKey='key'
                pagination={{
                    showQuickJumper: true,
                }}
                loading={loading}
                expandable={{ expandedRowRender }}
                search={false}
                dateFormatter='string'
                headerTitle='模块列表'
                options={false}
                toolbar={{
                    multipleLine: false,
                    actions: [
                        <Button
                            key='add'
                            type='primary'
                            onClick={() => {
                                handleAdd()
                            }}>
                            添加模块
                        </Button>,
                    ],
                }}
            />
            {/* 添加模块 */}
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
                        添加模块
                    </>
                }
                visible={AddVisible}
                // onOk={handleEditHouseOk}
                confirmLoading={confirmLoading}
                onCancel={handleAddCancel}
                footer={null}>
                <AddModule MapModule={MapModule} menu={menu} />
            </Modal>

            {/* 暂停模块 */}
            <Modal
                destroyOnClose={true}
                title={
                    <>
                        <ExclamationCircleOutlined
                            style={{
                                fontSize: 20,
                                color: '#000',
                                marginRight: 10,
                            }}
                        />
                        暂停模块
                    </>
                }
                visible={deleteVisible}
                onOk={handleDeleteOk}
                confirmLoading={pauseloading}
                onCancel={handleDeleteCancel}>
                <p>{modalText}</p>
                {/* <ChangePwd userName={record.name} userId={record.userId} /> */}
            </Modal>

            {/* 修改模块 */}
            <Modal
                destroyOnClose={true}
                title={
                    <>
                        <ExclamationCircleOutlined
                            style={{
                                fontSize: 20,
                                color: '#000',
                                marginRight: 10,
                            }}
                        />
                        编辑模块信息
                    </>
                }
                visible={EditVisible}
                // onOk={handleEditOk}
                confirmLoading={pauseloading}
                onCancel={handleEditCancel}
                footer={null}>
                <EditModule MapModule={MapModule} record={record} menu={menu} />
            </Modal>
        </>
    )
}
