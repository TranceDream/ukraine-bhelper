// @flow
import * as React from 'react'
import styles from './HomePageCover.module.scss'
import { Button, Typography } from 'antd'

type Props = {}
export const HomePageCover = (props: Props) => {
    return (
        <div className={styles.container}>
            <Typography.Title level={1}>
                Housing for Ukrainian Refugees
            </Typography.Title>
            <div className={styles.buttonGroup}>
                <Button>寻求援助</Button>
                <Button>查看新闻</Button>
            </div>
        </div>
    )
}
