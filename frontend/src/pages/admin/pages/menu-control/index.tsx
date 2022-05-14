/*
 * @Author: Linhao Yu
 * @Date: 2022-04-24 17:17:56
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-05-15 04:35:09
 */
import type { ProColumns } from '@ant-design/pro-table'
import ProTable from '@ant-design/pro-table'
import { Button, Space } from 'antd'
import React, { useEffect, useState } from 'react'
import { reqModuleList } from '../../api'
import Icon from '../../components/Icon/Icon'
import './ant-pro-card.scss'
import styles from './index.module.scss'
export type Status = {
    color: string
    text: string
}

const statusMap = {
    0: {
        color: 'blue',
        text: '进行中',
    },
    1: {
        color: 'green',
        text: '已完成',
    },
    2: {
        color: 'volcano',
        text: '警告',
    },
    3: {
        color: 'red',
        text: '失败',
    },
    4: {
        color: '',
        text: '未完成',
    },
}

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
            YES: { text: '运行', status: 'Success' },
            NO: { text: '关闭', status: 'Error' },
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
        render: () => [<a key='1'>修改</a>],
    },
]

export default function MenuControl() {
    const [tableListDataSource, settableListDataSource] = useState<
        TableListItem[]
    >([]) // 记录操作行的数据
    // 展开

    useEffect(() => {
        async function getMenulist() {
            const res = await reqModuleList()
            console.table(res.data)
            // !修改
            let temp: TableListItem[]
            temp = []
            res.data.forEach((item: any) => {
                let newitem: TableListItem
                newitem = { ...item }
                newitem.key = item.moduleId
                newitem.location = item.key
                temp.push(newitem)
            })
            console.log('temp', temp)
            settableListDataSource(temp)
            // setNodes(getMenuNode(menuList, true))
            // setMenuList(res.data)
        }
        getMenulist()
    }, [])

    const expandedRowRender = (record: any, index: any) => {
        console.log(record, index)

        console.log('11111', index, record.childs)
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
        return (
            <ProTable
                columns={[
                    {
                        title: '模块名称',
                        dataIndex: 'moduleId',
                        key: 'moduleId',
                    },
                    { title: '模块ID', dataIndex: 'title', key: 'title' },

                    {
                        title: '状态',
                        dataIndex: 'deleteMark',
                        key: 'deleteMark',
                        valueEnum: {
                            YES: { text: '运行', status: 'Success' },
                            NO: { text: '关闭', status: 'Error' },
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
                        render: () => [
                            <a key='Pause'>修改</a>,
                            // <a key='Stop'>Stop</a>,
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

    return (
        <>
            <ProTable<TableListItem>
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
                            // onClick={() => {
                            //     handleAddNews()
                            // }}
                        >
                            添加新闻
                        </Button>,
                    ],
                }}
            />
        </>
    )
}
