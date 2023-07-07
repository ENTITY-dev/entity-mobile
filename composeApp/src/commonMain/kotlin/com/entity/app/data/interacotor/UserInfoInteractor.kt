package com.entity.app.data.interacotor

import com.entity.app.data.api.UserInfoApi

class UserInfoInteractor(
  private val userInfoApi: UserInfoApi
) {

  suspend fun getUserInfo() {
    userInfoApi.getCurrentUserInfo()
  }

}