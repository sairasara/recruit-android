package nz.co.test.transactions.services

import retrofit2.http.GET

interface TransactionsService {
    @GET(".")
    suspend fun retrieveTransactions(): Array<Transaction>
}

