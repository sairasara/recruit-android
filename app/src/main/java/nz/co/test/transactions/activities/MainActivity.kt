package nz.co.test.transactions.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nz.co.test.transactions.R
import nz.co.test.transactions.activities.adapter.TransactionItemAdapter
import nz.co.test.transactions.di.network.TransactionsServiceClient

class MainActivity : AppCompatActivity() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        uiScope.launch {
            withContext(Dispatchers.IO) {
                //Do background tasks...
                val transactionsResponse = TransactionsServiceClient.transactionsService.retrieveTransactions()

                withContext(Dispatchers.Main){

                    //Update UI
                    val transactionItemAdapter = TransactionItemAdapter(this@MainActivity, transactionsResponse.sortedByDescending { it.transactionDate }.toTypedArray()){
                        val intent = Intent(this@MainActivity, TransactionItemDetailActivity::class.java)
                        intent.putExtra(getString(R.string.summary), it.summary)
                        intent.putExtra(getString(R.string.transactionDate), it.transactionDate)
                        intent.putExtra(getString(R.string.credit), it.credit.toString())
                        intent.putExtra(getString(R.string.debit), it.debit.toString())
                        startActivity(intent)
                    }
                    val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
                    recyclerView.adapter = transactionItemAdapter
                }
            }
        }
    }
}