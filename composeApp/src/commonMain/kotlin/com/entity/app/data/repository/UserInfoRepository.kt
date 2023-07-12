package com.entity.app.data.repository

import com.entity.app.data.api.UserInfoApi

class UserInfoRepository constructor(
  private val userInfoApi: UserInfoApi,
) {

  suspend fun getUserInfo() {
    userInfoApi.getCurrentUserInfo()
  }

}