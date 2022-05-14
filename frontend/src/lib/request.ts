/**
 * request
 * @file 包含了各种客户端请求的接口
 */

import Cookie from 'universal-cookie'

const baseUrl = 'http://139.9.231.20:81'

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
): Promise<boolean> => {
    const res = await post(
        '/user/login',
        { identifier, credential, identityType },
        false,
        false
    )
    if (res.code === 200) {
        const cookie = new Cookie()
        cookie.set('token', res.data.token, { path: ' /' })
        console.table(res)
        localStorage.setItem('menus', JSON.stringify(res.data.menus))
        return true
    } else {
        console.error(res.code + '\t' + res.msg)
    }
    return false
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
 * 更新用户信息
 * @param userId
 * @param city
 * @param country
 */
export const updateUser = async (
    userId: number,
    city: string | null,
    country: string | null
) => {
    const cookie = new Cookie()
    const token = cookie.get('token')
    if (!token) {
        return
    }
    let user: Map<string, number | string> = new Map()
    user.set('userId', userId)
    if (city) {
        user.set('city', city)
    }
    if (country) {
        user.set('country', country)
    }
    const raw = await fetch(baseUrl + '/user/addUserByEmail', {
        method: 'post',
        headers: {
            'content-type': 'application/json',
            token: token,
        },
        body: JSON.stringify(user),
    })
    const res = await raw.json()
    return {
        code: res.code,
        msg: res.msg,
    }
}

/**
 * 发布房源
 * @todo 待修改
 * @param station
 */
export const publishStation = async (
    station: StationModel
): Promise<Response> => {
    return post('/house/postinfo', station)
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
}

export const getStationList = async (current: number, filter: any) => {
    return post('/house/selectHouseAdmin', {
        current,
        pageSize: 10,
    })
}

/**
 * 根据房源ID获取房屋信息
 * Tested
 * @param houseId
 */
export const getStationDetail = async (houseId: number): Promise<Response> => {
    return post('/house/housedetail', { houseId })
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

export const reportNews = async (articleId: number, reason: string) => {
    return post('/news/report', { articleId, reason })
}
