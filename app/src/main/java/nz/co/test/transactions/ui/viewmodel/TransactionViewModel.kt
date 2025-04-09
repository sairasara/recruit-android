package nz.co.test.transactions.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import nz.co.test.transactions.domain.model.Transaction
import nz.co.test.transactions.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    val transactions: LiveData<List<Transaction>> = repository.transactions
    val networkError: LiveData<Boolean> = repository.networkError

    //val sortedTransactions= transactions.value?.sortedByDescending { it.transactionDate }

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
}
