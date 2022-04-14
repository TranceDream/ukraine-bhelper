import {Layout, Typography} from 'antd'
import React from 'react'
import styles from './Home.module.scss'

const {Header, Footer, Content} = Layout;

export default function Home() {
    return (
        <Layout className={styles.layout}>
            <Header>
                <Typography.Title level={2}>LOGO</Typography.Title>
            </Header>
            <Content className={styles.content}>
                Content
            </Content>
            <Footer>
                Footer
            </Footer>
        </Layout>
    )
}
