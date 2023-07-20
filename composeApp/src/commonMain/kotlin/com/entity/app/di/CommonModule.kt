package com.entity.app.di

import com.entity.app.data.api.FeedListApi
import com.entity.app.data.api.LaunchApi
import com.entity.app.data.api.UserInfoApi
import com.entity.app.data.api.UserSettingsApi
import com.entity.app.data.interacotor.FeedListInteractor
import com.entity.app.data.interacotor.LaunchInteractor
import com.entity.app.data.interacotor.UserInfoInteractor
import com.entity.app.data.interacotor.UserSettingsInteractor
import com.entity.app.data.provider.TextErrorProvider
import com.entity.app.data.repository.BottomBarNavigationStateRepository
import com.entity.app.data.repository.FeedListRepository
import com.entity.app.data.repository.LaunchRepository
import com.entity.app.data.repository.UserInfoRepository
import com.entity.app.data.repository.UserSettingsRepository
import com.entity.app.utils.EntityHttpClientFactory
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val commonModule = module {
  singleOf(::FeedListRepository)
  singleOf(::UserSettingsRepository)
  singleOf(::LaunchRepository)
  singleOf(::UserInfoRepository)
  singleOf(::BottomBarNavigationStateRepository)

  singleOf(::FeedListApi)
  singleOf(::UserInfoApi)
  singleOf(::UserSettingsApi)
  singleOf(::LaunchApi)

  factoryOf(::TextErrorProvider)

  factoryOf(::UserSettingsInteractor)
  factoryOf(::FeedListInteractor)
  factoryOf(::LaunchInteractor)
  factoryOf(::UserInfoInteractor)
  factoryOf(::EntityHttpClientFactory)

  single {
    get<EntityHttpClientFactory>().create()
  }
}