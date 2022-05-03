/**
 * HomePageCover
 * @file 主页封面组件，显示Slogan和导航按钮
 * @author TranceDream
 */
import * as React from 'react'
import styles from './HomePageCover.module.scss'
import { NavLink } from 'react-router-dom'

/**
 * 主页封面组件，显示Slogan和导航按钮
 * @author TranceDream
 * @constructor
 */
export const HomePageCover = () => {
    return (
        <div className={styles.container}>
            <h1>Housing for Ukrainian Refugees</h1>
            <div className={styles.buttonGroup}>
                <NavLink to={'/station'}>
                    <button>寻求援助</button>
                </NavLink>
                <button>查看新闻</button>
            </div>
        </div>
    )
}
