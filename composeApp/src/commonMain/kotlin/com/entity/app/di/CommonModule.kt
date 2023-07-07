package com.entity.app.di

import com.entity.app.data.api.FeedListApi
import com.entity.app.data.api.LaunchScreenApi
import com.entity.app.data.api.UserInfoApi
import com.entity.app.data.api.UserSettingsApi
import com.entity.app.data.interacotor.FeedListInteractor
import com.entity.app.data.interacotor.LaunchInteractor
import com.entity.app.data.interacotor.UserInfoInteractor
import com.entity.app.data.interacotor.UserSettingsInteractor
import com.entity.app.data.repository.FeedListRepository
import com.entity.app.data.repository.UserSettingsRepository
import com.entity.app.utils.EntityHttpClientFactory
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val commonModule = module {
  singleOf(::FeedListRepository)
  singleOf(::UserSettingsRepository)

  singleOf(::FeedListApi)
  singleOf(::UserInfoApi)
  singleOf(::UserSettingsApi)
  singleOf(::LaunchScreenApi)

  factoryOf(::UserSettingsInteractor)
  factoryOf(::FeedListInteractor)
  factoryOf(::LaunchInteractor)
  factoryOf(::UserInfoInteractor)
  factoryOf(::EntityHttpClientFactory)

  single {
    get<EntityHttpClientFactory>().create()
  }
}