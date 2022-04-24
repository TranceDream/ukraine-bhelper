// @flow
import { Button, Typography } from 'antd'
import * as React from 'react'
import { useNavigate } from 'react-router-dom'
import styles from './Home.module.scss'

type Props = {}
const Header = (props: Props) => {
    const navigate = useNavigate()
    const toAdmin = () => {
        navigate('/admin/home', { replace: false })
    }
    return (
        <header className={styles.header}>
            <Typography.Title level={2}>Header</Typography.Title>
            <Button type='primary' onClick={toAdmin}>
                Admin
            </Button>
            <ul className={styles.list}>
                <li>寻求援助</li>
                <li>新闻中心</li>
            </ul>
        </header>
    )
}

export default Header
