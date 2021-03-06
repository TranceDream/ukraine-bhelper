/**
 * request
 * @file 包含了各种客户端请求的接口
 */

import Cookie from 'universal-cookie'
import { cleanCookies } from 'universal-cookie/lib/utils'
import { message } from 'antd'

export const baseUrl = 'http://139.9.231.20:81'

export const imageUrl = 'http://139.9.231.20:8007/image/houseimage'

export interface Response {
    msg: string
    code: number
    data: any
}

/**
 * 封装get请求
 * @param url
 * @param params
 * @param withCookie
 */
const get = async (
    url: string,
    params: any,
    withCookie: boolean = true
): Promise<Response> => {
    const cookie = new Cookie()
    const token = cookie.get('token')
    if (withCookie && !token) {
        return {
            code: 0,
            msg: 'No token',
            data: null,
        }
    }
    const raw = await fetch(
        baseUrl + url + (params ? '?' : '') + new URLSearchParams(params),
        {
            method: 'get',
            mode: 'cors',
            headers: withCookie
                ? {
                      token,
                  }
                : {},
        }
    )
    return await raw.json()
}

/**
 * 封装post请求
 * @param url
 * @param body
 * @param withCookie
 * @param redirect
 */
const post = async (
    url: string,
    body: any,
    withCookie: boolean = true,
    redirect: boolean = true
): Promise<Response> => {
    const cookie = new Cookie()
    const token = cookie.get('token')
    if (withCookie && !token) {
        if (redirect) {
            cookie.remove('token')
            window.location.replace('/login')
        }
        return {
            code: 0,
            msg: 'No token',
            data: null,
        }
    }
    const raw = await fetch(baseUrl + url, {
        method: 'post',
        mode: 'cors',
        headers: withCookie
            ? {
                  'content-type': 'application/json',
                  token,
              }
            : { 'content-type': 'application/json' },
        body: JSON.stringify(body),
    })
    const res = await raw.json()
    if ((res === 401 || res === 403) && redirect) {
        cookie.remove('token')
        cleanCookies()
        window.location.replace('/login')
    }
    return res
}

/**
 * 登录接口，登录成功将token存入cookie
 * Tested
 * @param {string} identifier 用户名或邮箱，当前仅邮箱
 * @param {string} credential 密码
 * @param {number} identityType 登录类型，分为系统登录10000和邮箱登录10001，目前仅一个帐号使用系统登录
 * @author TranceDream
 */
export const login = async (
    identifier: string,
    credential: string,
    identityType: number
): Promise<Response> => {
    const res = await post(
        '/user/login',
        { identifier, credential, identityType },
        false,
        false
    )
    if (res.code === 200) {
        const cookie = new Cookie()
        cookie.set('token', res.data.token, { path: ' /' })
        localStorage.setItem('menus', JSON.stringify(res.data.menus))
    }
    return res
}

/**
 * 注册接口，返回状态码和状态信息
 * Tested
 * @param identifier
 * @param credential
 * @param city
 * @param country
 * @return Object
 */
export const register = async (
    identifier: string,
    credential: string,
    city: string,
    country: string
) => {
    return post(
        '/user/addUserByEmail',
        {
            identifier,
            credential,
            city,
            country,
        },
        false,
        false
    )
}

/**
 * 发布房源
 * Tested
 * @param station
 * @param images
 * @param contactList
 * @param tagList
 */
export const publishStation = async (
    station: StationModel,
    images: File[],
    contactList: ContactModel[],
    tagList: TagModel[]
): Promise<Response> => {
    const cookie = new Cookie()
    const token = cookie.get('token')
    if (!token) {
        message.error('请登录')
        cookie.remove('token')
        window.location.replace('/login')

        return {
            code: 0,
            msg: 'No token',
            data: null,
        }
    }
    const form = new FormData()
    form.append('houseinfoVo', JSON.stringify(station))
    form.append('tagList', JSON.stringify(tagList))
    form.append('contactList', JSON.stringify(contactList))
    images.forEach((img) => {
        form.append('fileinfo', img)
    })
    const raw = await fetch(baseUrl + '/house/postHouse', {
        method: 'post',
        headers: {
            token: token,
        },
        body: form,
    })
    const res = await raw.json()
    if (res === 401 || res === 403) {
        cookie.remove('token')
        window.location.replace('/login')
    }
    return res
}

/**
 * 修改房源信息
 * @todo 待修改
 */
export const updateStation = async (
    houseId: number,
    station: StationModel
): Promise<Response> => {
    return post('/house/updateinfo', { houseId, ...station })
}

export interface ContactTypeModel {
    typeId: number
    contactName: string
}

/**
 * 获取联系方式类型列表
 * Tested
 */
export const getContactTypeList = async (): Promise<Response> => {
    return post('/house/selectcontacttype', {})
}

/**
 * 获取Tag类型列表
 */
export const getTagTypeList = async (): Promise<Response> => {
    return post('/house/selecttagtype', {})
}

/**
 * 根据ID获取联系方式
 * @param houseId
 */
export const getContactList = async (houseId: number): Promise<Response> => {
    return post('/house/selectcontact', { houseId })
}

export const updateContact = async (
    contactId: number,
    houseId: number,
    typeId: number,
    content: string
): Promise<Response> => {
    return post('/house/updatecontact', { contactId, houseId, typeId, content })
}

export const deleteContact = async (contactId: number): Promise<Response> => {
    return post('/house/deletecontact', { contactId })
}

export const deleteTag = async (tagId: number): Promise<Response> => {
    return post('/house/deletetag', { TagId: tagId })
}

export const postTag = async (list: any[]): Promise<Response> => {
    return post('/house/posttag', { date: list })
}

/**
 * 删除房源
 * @param houseId
 */
export const deleteStation = async (houseId: number): Promise<Response> => {
    return post('/house/deleteinfo', { houseId })
}

export interface StationModel {
    houseId?: number
    userId?: number
    country: string
    province: string
    city: string
    address?: string
    guests?: number
    pets?: string
    duration?: number
    description?: string
    title?: string
    fileNames?: string[]
}

export interface ContactModel {
    typeId?: number
    houseId?: number
    contactId?: number
    contactName?: string
    content?: string
}

export interface TagModel {
    tagId?: number
    houseId?: number
    tagName?: string
    typeId?: number
}

export interface StationFilter {
    country?: string
    province?: string
    city?: string
    durationmin?: number
    durationmax?: number
    guestmin?: number
    guestmax?: number
    pets?: string
}

export const getStationList = async (current: number, filter: any) => {
    return post(
        '/house/selectHouseAdmin',
        {
            current,
            pageSize: 10,
            ...filter,
        },
        false
    )
}

/**
 * 根据房源ID获取房屋信息
 * Tested
 * @param houseId
 */
export const getStationDetail = async (houseId: number): Promise<Response> => {
    return post('/house/housedetail', { houseId }, false)
}

export const getMyStations = async (current: number) => {
    return post('/house/userHouse', { current, pageSize: 10 })
}

/**
 * 举报房源
 * Tested
 * @param postId
 * @param reason
 */
export const reportStation = async (postId: number, reason: string) => {
    return post('/house/report', { postId, reason })
}

export interface NewsModel {
    articleId: number
    createTime?: Date
    groupId: number
    title: string
    content?: string
    author?: number
}

export interface NewsGroupModel {
    id: number
    name: string
}

/**
 * 获取新闻列表
 * Tested
 * @param current
 * @param groupId
 */
export const getNewsList = async (
    current: number,
    groupId?: number
): Promise<Response> => {
    const body = groupId
        ? { status: 2, pageSize: 10, groupId, current }
        : { pageSize: 10, status: 2, current }
    return post('/news/selectArticleForC', body, false)
}

/**
 * 获取新闻内容
 * Tested
 * @param articleId
 */
export const getNewsDetail = async (articleId: number): Promise<Response> => {
    return post('/news/selectArticleForC', { articleId }, false)
}

/**
 * 获取新闻组列表
 * Tested
 */
export const getNewsGroupList = async (): Promise<Response> => {
    return post('/news/getNewsGroup', {}, false)
}

export const updateNews = async (
    articleId: number,
    title: string,
    content: string
): Promise<Response> => {
    return post('/news/updateArticle', { articleId, title, content })
}

/**
 * 举办新闻
 * Tested
 * @param articleId
 * @param reason
 */
export const reportNews = async (articleId: number, reason: string) => {
    return post('/news/report', { articleId, reason })
}

export const getGroupIdList = async (): Promise<Response> => {
    return post('/user/getChildGroupVos', {})
}

export const getPermissionForRole = async (): Promise<Response> => {
    return post('/user/getPermission4Role', {})
}

export const getMenuList = async (): Promise<Response> => {
    return post('/user/getMenuList', {})
}
