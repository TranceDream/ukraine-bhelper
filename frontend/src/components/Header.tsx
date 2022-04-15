// @flow
import * as React from 'react'
import styles from './Home.module.scss'
import { Typography } from 'antd'

type Props = {}
const Header = (props: Props) => {
    return (
        <header className={styles.header}>
            <Typography.Title level={2}>Header</Typography.Title>
            <ul className={styles.list}>
                <li>寻求援助</li>
                <li>新闻中心</li>
            </ul>
        </header>
    )
}

export default Header
