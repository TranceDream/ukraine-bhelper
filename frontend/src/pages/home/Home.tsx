/**
 * Home
 * @file 前端主页
 * @author TranceDream
 */

import React, { useEffect, useState } from 'react'
import Header from '../../components/Header'
import { HomePageCover } from '../../components/HomePageCover'
import styles from './Home.module.scss'

/**
 * 前端主页
 * @constructor
 * @author TranceDream
 */
export default function Home() {
    const [hideNav, setHideNav] = useState(true)
    useEffect(() => {
        window.addEventListener(
            'scroll',
            () => {
                setHideNav(window.scrollY + 80 <= window.innerHeight)
            },
            { passive: true }
        )
    }, [])

    return (
        <div className={styles.container}>
            <header>
                <Header hideNav={hideNav} adminhideNav={false} />
            </header>
            <main>
                <HomePageCover />
                <section />
            </main>
            <footer>
                <div>footer</div>
            </footer>
        </div>
    )
}
