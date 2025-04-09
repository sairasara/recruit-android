package nz.co.test.transactions.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import nz.co.test.transactions.domain.model.Transaction
import nz.co.test.transactions.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.math.BigDecimal
import kotlin.jvm.java

@ExperimentalCoroutinesApi
class TransactionViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: TransactionRepository
    private lateinit var viewModel: TransactionViewModel

    private val mockTransactions = listOf(
        Transaction(1,  "2025-03-10T06:24:06", "Description 1", BigDecimal(10.50),  BigDecimal(0)),
        Transaction(2,  "2024-12-18T06:24:06", "Description 2",BigDecimal(45.00),  BigDecimal(0)),
        Transaction(3,  "2024-03-22T06:24:06", "Description 3",BigDecimal(0),  BigDecimal(87.53)),
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = Mockito.mock(TransactionRepository::class.java)
        Mockito.`when`(repository.transactions).thenReturn(MutableLiveData(mockTransactions))
        Mockito.`when`(repository.networkError).thenReturn(MutableLiveData(false))
        viewModel = TransactionViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should call getTransactions`() = runTest {
        Mockito.verify(repository, Mockito.times(1)).getTransactions()
    }

    @Test
    fun `transactions should emit the list of transactions from repository`() {
        Assert.assertEquals(mockTransactions, viewModel.transactions.value)
    }

    @Test
    fun `networkError should emit the network error state from repository`() {
        val errorState = true
        Mockito.`when`(repository.networkError).thenReturn(MutableLiveData(errorState))
        viewModel = TransactionViewModel(repository) // Re-initialize to pick up the new LiveData
        Assert.assertEquals(errorState, viewModel.networkError.value)
    }
}