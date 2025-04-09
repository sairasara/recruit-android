package nz.co.test.transactions.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import nz.co.test.transactions.data.api.TransactionsService
import nz.co.test.transactions.data.mapper.TransactionMapper
import nz.co.test.transactions.domain.model.Transaction
import nz.co.test.transactions.domain.repository.TransactionRepository
import nz.co.test.transactions.utils.NetworkUtils
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionsService: TransactionsService,
    private val networkUtils: NetworkUtils) : TransactionRepository {

    // livedata will hold transactions list
    private val _transactions= MutableLiveData<List<Transaction>>()
    override val transactions: LiveData<List<Transaction>> = _transactions

    // holds network error
    private val _networkError = MutableLiveData<Boolean>()
    override val networkError: LiveData<Boolean> = _networkError

    // Fetch transactions from api and post the result to LiveData
   override suspend fun getTransactions() {

        // Check if the device has internet connectivity
        if(!networkUtils.isInternetAvailable()) {
            _networkError.postValue(true)
            return
        } else{
            _networkError.postValue(false)
        }

        try {
            val response = transactionsService.getTransactions()
            val transactionList = response.map { TransactionMapper.toDomain(it) }
            _transactions.postValue(transactionList)

        } catch (e: Exception) {
            Log.e("TransactionRepository", "Error fetching transactions", e)
            _transactions.postValue(emptyList())
        }
    }
}

