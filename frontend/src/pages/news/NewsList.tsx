import React from 'react'
import Header from '../../components/Header'
import styles from './NewsList.module.scss'
import Footer from '../../components/Footer'
import NewsItem from '../../components/NewsItem'

const NewsList = () => {
    return (
        <div className={styles.container}>
            <header>
                <Header />
            </header>
            <main>
                <div className={styles.column}>
                    <span>军事</span>
                    <span>经济</span>
                    <span>国际</span>
                </div>
                <div className={styles.news}>
                    <NewsItem />
                    <NewsItem />
                    <NewsItem />
                </div>
            </main>
            <footer>
                <Footer />
            </footer>
        </div>
    )
}

export default NewsList
