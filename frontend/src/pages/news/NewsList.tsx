import React, { useEffect, useState } from 'react'
import Header from '../../components/Header'
import styles from './NewsList.module.scss'
import Footer from '../../components/Footer'
import NewsItem from '../../components/NewsItem'
import {
    getNewsGroupList,
    getNewsList,
    NewsGroupModel,
    NewsModel,
} from '../../lib/request'
import { Pagination } from 'antd'

const NewsList = () => {
    const [groupList, setGroupList] = useState<Array<NewsGroupModel>>()
    const [newsList, setNewsList] = useState<Array<NewsModel>>()
    const [count, setCount] = useState(0)
    const [index, setIndex] = useState(1)
    const [currentGroup, setGroup] = useState<number>()

    useEffect(() => {
        getNewsGroupList().then((group) => {
            if (group.code === 200) {
                setGroupList(
                    Object.entries(group.data).map((e: [string, any]) => ({
                        id: parseInt(e[0]),
                        name: e[1],
                    }))
                )
                getNewsList(index, currentGroup).then((res) => {
                    if (res.code === 200) {
                        console.log(res.data.articles)
                        setNewsList(res.data.articles)
                        setCount(res.data.count)
                    }
                })
            } else {
                console.log(group)
            }
        })
    }, [index, currentGroup])

    return (
        <div className={styles.container}>
            <header>
                <Header />
            </header>
            <main>
                {newsList ? (
                    <>
                        <div className={styles.column}>
                            {groupList?.map((g) => (
                                <span
                                    key={'g' + g.id}
                                    onClick={() => {
                                        console.log('set group')
                                        setGroup(g.id)
                                    }}>
                                    {g.name}
                                </span>
                            ))}
                        </div>
                        <div className={styles.news}>
                            {newsList.map((news) => (
                                <NewsItem key={news.articleId} news={news} />
                            ))}
                        </div>
                        <div className={styles.pagination}>
                            <Pagination
                                defaultCurrent={1}
                                current={index}
                                pageSize={10}
                                total={count}
                                onChange={(page) => {
                                    setIndex(page)
                                }}
                            />
                        </div>
                    </>
                ) : (
                    <></>
                )}
            </main>
            <footer>
                <Footer />
            </footer>
        </div>
    )
}

export default NewsList
