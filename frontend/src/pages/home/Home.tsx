import { Col, Layout, Row } from 'antd'
import React from 'react'
import styles from './Home.module.scss'
import GenericHeader from '../../components/Header'
import { HomePageCover } from '../../components/HomePageCover'

const { Header, Footer, Content } = Layout

export default function Home() {
    return (
        <Layout className={styles.layout}>
            <Header>
                <GenericHeader />
            </Header>
            <Content className={styles.contentWrapper}>
                <Row className={styles.content}>
                    <Col className={styles.cover}>
                        <HomePageCover />
                    </Col>
                    <Col>item 2</Col>
                    <Col>item 3</Col>
                </Row>
            </Content>
            <Footer>Footer</Footer>
        </Layout>
    )
}
