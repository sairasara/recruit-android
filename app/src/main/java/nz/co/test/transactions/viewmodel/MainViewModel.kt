package nz.co.test.transactions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nz.co.test.transactions.repository.TransactionRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val transactionRepository : TransactionRepository) : ViewModel() {

    val transactionSuccessData = transactionRepository.transactionSuccessData
    val transactionFailureData = transactionRepository.transactionFailureData
    fun getTransactions() {
        //this is coroutine viewmodel scope to call suspend fun of repo
        viewModelScope.launch {
            transactionRepository.getTransactions()
        }
    }
}
