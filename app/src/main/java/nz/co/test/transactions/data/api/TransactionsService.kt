package nz.co.test.transactions.data.api

import nz.co.test.transactions.data.model.TransactionDto
import nz.co.test.transactions.utils.Constants.TRANSACTIONS_ENDPOINT
import retrofit2.http.GET

interface TransactionsService {

    @GET(TRANSACTIONS_ENDPOINT)
    suspend fun getTransactions(): List<TransactionDto>
}