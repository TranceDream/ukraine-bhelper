import store from 'store'
const USER_KEY = 'username'
export default {
  //保存user
  SaveUser(user) {
    store.set(USER_KEY, user)
  },

  //读取user
  GetUser() {
    return store.get(USER_KEY)
  },

  //删除user
  RemoveUser() {
    store.remove(USER_KEY)
  },
}
