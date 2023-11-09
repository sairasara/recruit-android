package nz.co.test.transactions.di.network

import dagger.Module
import dagger.Provides
import nz.co.test.transactions.services.TransactionsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {
    private const val baseUrl = "https://gist.githubusercontent.com/Josh-Ng/500f2716604dc1e8e2a3c6d31ad01830/raw/4d73acaa7caa1167676445c922835554c5572e82/test-data.json/"

    // Used GSON converter instead of Moshi converter
    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

object TransactionsServiceClient {
    val transactionsService: TransactionsService = NetworkModule.retrofit.create(TransactionsService::class.java)
}