/*
 * HealthyHoneyTving © DND 해커톤 2조. all rights reserved.
 * HealthyHoneyTving license is under the MIT.
 *
 * [RetrofitModule.kt] created by Ji Sungbin on 21. 11. 20. 오전 4:56
 *  
 * Please see: https://github.com/DND-hackathon/HealthyHoneyTving-Android/blob/dev/LICENSE.
 */

package dnd.hackathon.second.healthyhoneytving.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dnd.hackathon.second.healthyhoneytving.activity.main.business.ApiService
import dnd.hackathon.second.healthyhoneytving.util.extension.jacksonMapper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

// TODO
@Module
@InstallIn(ViewModelComponent::class)
object RetrofitModule {
    private const val BaseUrl = "" // TODO

    private fun getInterceptor(vararg interceptors: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        for (interceptor in interceptors) builder.addInterceptor(interceptor)
        return builder.build()
    }

    @Provides
    @ViewModelScoped
    fun provideRetrofit(loggingInterceptor: HttpLoggingInterceptor): ApiService = Retrofit.Builder()
        .baseUrl(BaseUrl)
        .addConverterFactory(JacksonConverterFactory.create(jacksonMapper))
        .client(getInterceptor(loggingInterceptor))
        .build()
        .create(ApiService::class.java)
}
