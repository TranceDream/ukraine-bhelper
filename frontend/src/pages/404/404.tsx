import React from 'react'
import {Link} from 'react-router-dom'
import styles from './404.module.scss'

export default function NotFound() {
    return (
        <div className={styles.container}>
            404 Not Found&nbsp;|&nbsp;<Link className={styles.link} to='/'>回到主页</Link></div>
    )
}
