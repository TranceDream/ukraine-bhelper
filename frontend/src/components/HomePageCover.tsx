/**
 * HomePageCover
 * @file 主页封面组件，显示Slogan和导航按钮
 * @author TranceDream
 */
import * as React from 'react'
import styles from './HomePageCover.module.scss'
import { NavLink } from 'react-router-dom'
import { useTranslation } from 'react-i18next'

/**
 * 主页封面组件，显示Slogan和导航按钮
 * @author TranceDream
 * @constructor
 */
export const HomePageCover = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.container}>
            <h1>Housing for Ukrainian Refugees</h1>
            <div className={styles.buttonGroup}>
                <NavLink to={'/station'}>
                    <button>{t('station.stationList')}</button>
                </NavLink>
                <NavLink to={'/news'}>
                    <button>{t('news.newsList')}</button>
                </NavLink>
            </div>
        </div>
    )
}
