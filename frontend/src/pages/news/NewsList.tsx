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
import { Empty, message, Pagination, Spin } from 'antd'
import { LoadingOutlined } from '@ant-design/icons'

const NewsList = () => {
    const [groupList, setGroupList] = useState<Array<NewsGroupModel>>()
    const [newsList, setNewsList] = useState<NewsModel[]>([])
    const [loading, setLoading] = useState<boolean>(true)
    const [count, setCount] = useState(0)
    const [index, setIndex] = useState(1)
    const [currentGroup, setGroup] = useState<number>()

    useEffect(() => {
        setLoading(true)
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
                        setLoading(false)
                        setNewsList(res.data.articles)
                        setCount(res.data.count)
                    } else {
                        message.error('出错了: ').then()
                    }
                })
            } else {
                message.error('出错了: ').then()
            }
        })
    }, [index, currentGroup])

    return (
        <div className={styles.container}>
            <header>
                <Header />
            </header>
            <main>
                {loading ? (
                    <Spin
                        indicator={
                            <LoadingOutlined
                                style={{ fontSize: 'xxx-large' }}
                            />
                        }
                    />
                ) : (
                    <>
                        <div className={styles.column}>
                            {groupList?.map((g) => (
                                <span
                                    key={'g' + g.id}
                                    onClick={() => {
                                        setGroup(g.id)
                                    }}>
                                    {g.name}
                                </span>
                            ))}
                        </div>
                        {newsList.length === 0 ? (
                            <Empty description={'没有数据'} />
                        ) : (
                            <>
                                <div className={styles.news}>
                                    {newsList.map((news) => (
                                        <NewsItem
                                            key={news.articleId}
                                            news={news}
                                        />
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
                        )}
                    </>
                )}
            </main>
            <footer>
                <Footer />
            </footer>
        </div>
    )
}

export default NewsList
