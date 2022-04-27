/*
 * @Author: Linhao Yu
 * @Date: 2022-04-24 17:17:45
 * @Last Modified by: Linhao Yu
 * @Last Modified time: 2022-04-27 19:06:31
 */
import { DownOutlined, QuestionCircleOutlined } from '@ant-design/icons'
import type { ProColumns } from '@ant-design/pro-table'
import ProTable from '@ant-design/pro-table'
import '@ant-design/pro-table/dist/table.css'
import { Button, Tooltip } from 'antd'
import React, { useEffect, useState } from 'react'
import PubSub from '../../Utils/pubsub'
import styles from './index.module.scss'
const valueEnum = {
    0: 'close',
    1: 'running',
    2: 'online',
    3: 'error',
}

export type TableListItem = {
    key: number
    id: string
    name: string
    nickname: string
    role: string
    status: string
    country: string
    city: string
    createdAt: number
}
const tableListDataSource: TableListItem[] = []

const creators = ['付小小', '曲丽丽', '林东东', '陈帅帅', '兼某某']

for (let i = 0; i < 100; i += 1) {
    tableListDataSource.push({
        key: i,
        id: i.toString(),
        name: 'name',
        nickname: 'nickname',
        role: 'Admin',
        status: valueEnum[
            (Math.floor(Math.random() * 10) % 4) as keyof typeof valueEnum
        ],
        country: 'China',
        city: 'TianJin',
        createdAt: Date.now() - Math.floor(Math.random() * 100000),
    })
}

const columns: ProColumns<TableListItem>[] = [
    {
        title: '用户ID',
        width: 50,
        dataIndex: 'id',
        align: 'center',
        render: (_) => <a>{_}</a>,
    },
    {
        title: (
            <>
                用户名
                <Tooltip placement='top' title='用户的真实姓名'>
                    <QuestionCircleOutlined style={{ marginLeft: 4 }} />
                </Tooltip>
            </>
        ),
        width: 120,
        dataIndex: 'name',
        align: 'center',
    },
    {
        title: '昵称',
        width: 120,
        dataIndex: 'nickname',
        align: 'center',
    },
    {
        title: '角色',
        width: 80,
        dataIndex: 'role',
        align: 'center',
        search: false,
        filters: true,
        onFilter: true,
        valueEnum: {
            Admin: 'Admin',
            Refugee: 'Refugee',
            Editor: 'Editor',
        },
    },
    {
        title: '状态',
        width: 100,
        dataIndex: 'status',
        search: false,
        filters: true,
        onFilter: true,
        valueEnum: {
            all: { text: '全部', status: 'Default' },
            close: { text: '关闭', status: 'Default' },
            running: { text: '运行中', status: 'Processing' },
            online: { text: '已上线', status: 'Success' },
            error: { text: '异常', status: 'Error' },
        },
    },
    {
        title: '国家',
        width: 80,
        dataIndex: 'country',
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
        width: 140,
        key: 'since',
        dataIndex: 'createdAt',
        valueType: 'date',
        sorter: (a, b) => a.createdAt - b.createdAt,
    },
    {
        title: '操作',
        width: 180,
        key: 'option',
        valueType: 'option',
        render: () => [<a key='delete'>删除</a>, <a key='edit'>编辑</a>],
    },
]

export default function UserControl() {
    const [searchCollapsed, setsearchCollapsed] = useState(false)
    const [navCollpased, setnavCollpased] = useState(false)
    const [tableheight, setTableheight] = useState(window.innerHeight - 522)
    const onCollapse = () => {
        setsearchCollapsed(!searchCollapsed)
    }
    const resettableheight = () => {
        if (!navCollpased && searchCollapsed) {
            // console.log('nav open, search close')
            setTableheight(window.innerHeight - 410)
        }
        if (!navCollpased && !searchCollapsed) {
            // console.log('nav open, search open')
            setTableheight(window.innerHeight - 522)
        }
        if (navCollpased && searchCollapsed) {
            // console.log('nav close, search close')
            setTableheight(window.innerHeight - 410)
        }
        if (navCollpased && !searchCollapsed) {
            // console.log('nav close, search open')
            setTableheight(window.innerHeight - 466)
        }
    }
    useEffect(() => {
        window.addEventListener(
            'resize',
            () => {
                setTableheight(
                    searchCollapsed
                        ? window.innerHeight - 410
                        : window.innerHeight - 522
                )
            },
            { passive: true }
        )
        let token = PubSub.subscribe(
            'navCollpased',
            (msg: string, c: boolean) => {
                console.log('Pubsusb c', c)
                setnavCollpased(c)
                // resettableheight()
            }
        )
        return () => {
            PubSub.unsubscribe(token)
        }
    }, [])

    // updatemount
    useEffect(() => {
        console.log(11111111)
        resettableheight()
    }, [searchCollapsed, navCollpased])
    return (
        <ProTable<TableListItem>
            columns={columns}
            request={(params, sorter, filter) => {
                // 表单搜索项会从 params 传入，传递给后端接口。
                console.log(params, sorter, filter)
                return Promise.resolve({
                    data: tableListDataSource,
                    success: true,
                })
            }}
            rowKey='key'
            pagination={{
                showQuickJumper: true,
            }}
            search={{
                // optionRender: false,
                collapsed: searchCollapsed,
                onCollapse: onCollapse,
            }}
            scroll={{
                y: tableheight,
            }}
            dateFormatter='string'
            headerTitle='所有用户'
            toolBarRender={() => [
                <Button key='show'>查看日志</Button>,
                <Button key='out'>
                    导出数据
                    <DownOutlined />
                </Button>,
                <Button type='primary' key='primary'>
                    添加用户
                </Button>,
            ]}
            className={styles.protable}
        />
    )
}
