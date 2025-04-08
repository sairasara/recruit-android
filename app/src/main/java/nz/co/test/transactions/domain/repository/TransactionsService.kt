package nz.co.test.transactions.domain.repository

import nz.co.test.transactions.domain.model.Transaction
import retrofit2.http.GET

interface TransactionsService {
    @GET("transactions")
    suspend fun retrieveTransactions(): Array<Transaction>
}