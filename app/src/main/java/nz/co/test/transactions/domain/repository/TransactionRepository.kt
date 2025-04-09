package nz.co.test.transactions.domain.repository

import androidx.lifecycle.LiveData
import nz.co.test.transactions.domain.model.Transaction

interface TransactionRepository {
     val transactions: LiveData<List<Transaction>>
     val networkError: LiveData<Boolean>
     suspend fun getTransactions()
}
