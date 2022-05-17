/**
 * Home
 * @file 前端主页
 * @author TranceDream
 */

import React, { useEffect, useState } from 'react'
import Header from '../../components/Header'
import { HomePageCover } from '../../components/HomePageCover'
import styles from './Home.module.scss'
import Footer from '../../components/Footer'

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
                <Header hideNav={hideNav} />
            </header>
            <main>
                <HomePageCover />
                <section>
                    <div>
                        <h2>About Ukraine BHelper</h2>
                        <h3>What is Ukraine BHelper?</h3>
                        <p>
                            Ukraine BHelper is an independent platform helping
                            to connect Ukrainian refugees with potential hosts
                            and housing. This website is a public bulletin. We
                            encourage everyone with spare space to post a
                            listing and to mark their listing as filled once
                            they have successfully taken in refugees.
                        </p>
                        <h3>Staff</h3>
                        <div className={styles.info}>
                            <div className={styles.subInfo}>
                                <p>Frontend:</p>
                                <ul>
                                    <li>
                                        <span>Hasuer</span>{' '}
                                        <a href={'https://github.com/Hasuer'}>
                                            Github
                                        </a>
                                    </li>
                                    <li>
                                        <span>TranceDream</span>{' '}
                                        <a
                                            href={
                                                'https://github.com/TranceDream'
                                            }>
                                            Github
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div className={styles.subInfo}>
                                <p>Backend:</p>
                                <ul>
                                    <li>
                                        <span>慕容暮色</span>{' '}
                                        <a href={'https://github.com/murong1'}>
                                            Github
                                        </a>
                                    </li>
                                    <li>
                                        <span>杜义恒</span>{' '}
                                        <a href={'https://gitee.com/du-yiheng'}>
                                            Gitee
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </section>
            </main>
            <footer>
                <Footer />
            </footer>
        </div>
    )
}
