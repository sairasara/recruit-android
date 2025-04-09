package nz.co.test.transactions.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import nz.co.test.transactions.domain.model.Transaction
import nz.co.test.transactions.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _transactions: LiveData<List<Transaction>> = repository.transactions.map { list ->
        list.sortedByDescending { transaction ->
            try {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                LocalDateTime.parse(transaction.transactionDate, formatter)
            } catch (e: Exception) {
                Log.e("TransactionViewModel", "Error parsing date: ${transaction.transactionDate}", e)
                LocalDateTime.MIN
            }
        }
    }

    val transactions: LiveData<List<Transaction>> = _transactions
    val networkError: LiveData<Boolean> = repository.networkError

    init {
        getTransactions()
    }

    private fun getTransactions() {
        viewModelScope.launch {
           try {
               repository.getTransactions()
           } catch (e: Exception) {
               Log.e("TransactionViewModel", "Error fetching transactions", e)
           }
        }
    }

    fun getTransactionById(id: Int?): Transaction? {
        return transactions.value?.find { it.id == id }
    }
}
