package nz.co.test.transactions.repository

import androidx.lifecycle.MutableLiveData
import nz.co.test.transactions.services.Transaction
import nz.co.test.transactions.services.TransactionsService
import javax.inject.Inject

class TransactionRepository @Inject constructor(private val transactionsService: TransactionsService) {

    val transactionSuccessData = MutableLiveData<Array<Transaction>>()
    val transactionFailureData = MutableLiveData<Boolean>()

    suspend fun getTransactions() {
        val response = transactionsService.retrieveTransactions()
        if(response.isSuccessful && response.body() !=null){
            transactionSuccessData.postValue(response.body())
        }else if(response.errorBody() !=null){
            transactionFailureData.postValue(true)
        }else{
            transactionFailureData.postValue(true)
        }
    }
}