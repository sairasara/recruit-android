package nz.co.test.transactions.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import nz.co.test.transactions.R
import nz.co.test.transactions.activities.adapter.TransactionItemAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import nz.co.test.transactions.di.network.NetworkModule
import nz.co.test.transactions.repository.TransactionRepository
import nz.co.test.transactions.viewmodel.MainViewModel
import nz.co.test.transactions.viewmodel.MainViewModelFactory

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    private lateinit var transactionItemAdapter: TransactionItemAdapter
    lateinit var recyclerView: RecyclerView
    private val transactionService = NetworkModule().providesTransactionService(NetworkModule().providesRetrofit())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(TransactionRepository(transactionService))).get(
            MainViewModel::class.java)

        recyclerView = findViewById(R.id.recyclerview)
        transactionItemAdapter = TransactionItemAdapter(this@MainActivity, ){
            val intent = Intent(this@MainActivity, TransactionItemDetailActivity::class.java)
            intent.putExtra(getString(R.string.summary), it.summary)
            intent.putExtra(getString(R.string.transactionDate), it.transactionDate)
            intent.putExtra(getString(R.string.credit), it.credit.toString())
            intent.putExtra(getString(R.string.debit), it.debit.toString())
            startActivity(intent)
        }
        recyclerView.adapter = transactionItemAdapter

        //before calling api, register live data observer
        registerObservers()

        //calling user list api
        mainViewModel.getTransactions()
    }

    private fun registerObservers() {
        mainViewModel.transactionSuccessData.observe(this, Observer { transactionList ->
            //if it is not null then display all transactions
            transactionItemAdapter.setTransactionsList(transactionList.sortedByDescending { it.transactionDate }.toTypedArray())
        })

        mainViewModel.transactionFailureData.observe(this, Observer { isFailed ->
            //if null error message is shown
            isFailed?.let {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }
}