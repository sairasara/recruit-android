package nz.co.test.transactions.activities

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import nz.co.test.transactions.R
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder

class TransactionItemDetailActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_item_detail)

        // calling the action bar
        val actionBar: ActionBar? = supportActionBar
        // showing the back button in action bar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        setValues()
    }

    private fun setValues(){
        val summaryText : TextView = findViewById(R.id.summaryText)
        val creditDebitText : TextView = findViewById(R.id.creditDebitText)
        val toPlaceholder : TextView = findViewById(R.id.toPlaceholder)
        val toValueText : TextView = findViewById(R.id.toValueText)
        val amountValueText : TextView = findViewById(R.id.amountValueText)
        val gstValueText : TextView = findViewById(R.id.gstValueText)
        val transactionDateValueText : TextView = findViewById(R.id.transactionDateValueText)

        val bundle = intent.extras
        summaryText.text = bundle!!.getString(getString(R.string.summary))
        toValueText.text = bundle.getString(getString(R.string.summary))

        val credit = bundle.getString(getString(R.string.credit))?.toBigDecimal()
        val debit = bundle.getString(getString(R.string.debit))?.toBigDecimal()

        if(credit!! >  BigDecimal(0) && debit!! == BigDecimal(0)) {
            creditDebitText.text = "${getString(R.string.creditPrefix)}${credit}"
            creditDebitText.setTextColor(Color.parseColor("#228B22"))

            toPlaceholder.text = getString(R.string.from)
            amountValueText.text = "${getString(R.string.creditPrefix)}${credit}"
            gstValueText.text = String.format("%.2f", (credit * BigDecimal(0.15)))

        }else if(debit!! >  BigDecimal(0) && credit == BigDecimal(0)) {
            creditDebitText.text = "${getString(R.string.debitPrefix)}${debit}"
            creditDebitText.setTextColor(Color.parseColor("#A91D36"))

            toPlaceholder.text = getString(R.string.to)
            amountValueText.text ="${getString(R.string.debitPrefix)}${debit}"
            gstValueText.text = String.format("%.2f", (debit * BigDecimal(0.15)))
        }
        val formatter = DateTimeFormatterBuilder()
            .appendPattern("hh:mm:ss a dd-MM-YYYY").toFormatter()
        transactionDateValueText.text = formatter.format(LocalDateTime.parse(bundle.getString(getString(R.string.transactionDate))))
    }

    // function for back button press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}