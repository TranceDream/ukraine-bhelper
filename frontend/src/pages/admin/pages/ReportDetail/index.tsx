import ProTable, { ActionType, ProColumns } from '@ant-design/pro-table'
import { Button, message, Modal } from 'antd'
import React, { useEffect, useRef, useState } from 'react'
import { useLocation } from 'react-router-dom'
import { reqReportDetail } from '../../api'
import Audit from '../../components/Audit'
import PubSub from '../../Utils/pubsub'
import './ant-pro-card.scss'
import styles from './index.module.scss'
export type TableListItem = {
    key: number
    reportId: number
    objtypeId: number
    defense: number // 被举报人
    reason: string
    prosecution: string // 举报人
    auditStatus: string
    count: number
    createTime: number
}
export default function ReportDetail(props: any) {
    const ref = useRef<ActionType>()
    const useQuery = () => new URLSearchParams(useLocation().search)
    const [tableListDataSource, settableListDataSource] = useState<
        TableListItem[]
    >([]) // 记录操作行的数据
    const query = useQuery()
    const defense = query.get('defense')
    const objtypeId = query.get('objtypeId')
    const [visible, setVisible] = React.useState(false)
    const [confirmLoading, setConfirmLoading] = React.useState(false)

    const handleCancel = () => {
        setVisible(false)
    }

    useEffect(() => {
        async function getReportDetail() {
            const res = await reqReportDetail({ defense, objtypeId })
            if (res.code === 200) {
                getData(res.data.data)
            } else {
                message.error(res.msg)
            }
        }

        getReportDetail()
        var token = PubSub.subscribe('audit', (msg: string, data: string) => {
            if (data === 'success') {
                message.success('修改成功')
            } else {
                message.error('修改失败')
            }
            setVisible(false)
            ref.current?.reload()
        })

        return () => {
            PubSub.unsubscribe(token)
        }
    }, [])

    const getData = (data: any) => {
        // console.table(data)
        let temp: TableListItem[] = []
        data.forEach((item: any) => {
            let newitem: TableListItem
            newitem = { ...item }
            // console.log('newitem', newitem)
            newitem.key = item.reportId
            temp.push(newitem)
        })
        // console.table(temp)
        settableListDataSource(data)
    }

    const audit = () => {
        setVisible(true)
    }
    const columns: ProColumns<TableListItem>[] = [
        {
            title: '举报信息ID',
            width: 140,
            dataIndex: 'reportId',
            align: 'center',
        },
        {
            title: '举报模块ID',
            width: 140,
            dataIndex: 'objtypeId',
            align: 'center',
        },
        {
            title: '举报者',
            align: 'center',
            width: 140,
            key: 'prosecution',
            dataIndex: 'prosecution',
        },
        {
            title: '理由',
            align: 'center',
            width: 140,
            key: 'reason',
            ellipsis: true,
            dataIndex: 'reason',
        },
        {
            title: '举报时间',
            align: 'center',
            width: 140,
            key: 'since',
            dataIndex: 'createTime',
            valueType: 'date',
        },
    ]
    return (
        <>
            <ProTable<TableListItem>
                search={false}
                actionRef={ref}
                columns={columns}
                toolbar={{
                    multipleLine: false,
                    actions: [
                        <Button
                            key='key'
                            type='primary'
                            onClick={() => {
                                audit()
                            }}>
                            审核
                        </Button>,
                    ],
                }}
                dataSource={tableListDataSource}
                rowKey={(record) => {
                    return record.reportId + Date.now().toString() //在这里加上一个时间戳就可以了
                }}
                pagination={{
                    showQuickJumper: true,
                }}
                dateFormatter='string'
                headerTitle='待审核举报'
                className={styles.protable}
            />
            <Modal
                destroyOnClose={true}
                title='审核'
                visible={visible}
                footer={null}
                confirmLoading={confirmLoading}
                onCancel={handleCancel}>
                <Audit
                    defense={defense ? defense : 'null'}
                    objtypeId={objtypeId ? objtypeId : 'null'}
                />
            </Modal>
        </>
    )
}
