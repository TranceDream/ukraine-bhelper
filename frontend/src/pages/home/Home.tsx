import { Button, Layout, Typography } from 'antd'
import React from 'react'
import { useNavigate } from 'react-router-dom'
import styles from './Home.module.scss'
const { Header, Footer, Content } = Layout

export default function Home() {
    const navigate = useNavigate()
    const toAdmin = () => {
        navigate('/admin', { replace: false })
    }
    return (
        <Layout className={styles.layout}>
            <Header>
                <Typography.Title level={2}>
                    LOGO
                    <Button type='primary' onClick={toAdmin}>
                        Admin
                    </Button>
                </Typography.Title>
                1111
            </Header>
            <Content className={styles.content}>Content</Content>
            <Footer>Footer</Footer>
        </Layout>
    )
}
