package nz.co.test.transactions.activities.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nz.co.test.transactions.R
import nz.co.test.transactions.services.Transaction
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder

class TransactionItemAdapter(private val context: Context,
                             private val onItemClick: (Transaction) -> Unit)
    : RecyclerView.Adapter<TransactionItemAdapter.ViewHolder>() {

    private var transactionsList = mutableListOf<Transaction>()
    fun setTransactionsList(transactionsList: Array<Transaction>) {
        this.transactionsList = transactionsList.toMutableList()
        notifyDataSetChanged()
    }

    // create new views
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view that is used to hold list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.transaction_item, viewGroup, false)

        return ViewHolder(view){
            onItemClick(transactionsList[it])
        }
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = transactionsList[position]
        // sets the values view from itemHolder class
        val formatter = DateTimeFormatterBuilder()
            .appendPattern("dd-MM-YYYY hh:mm a").toFormatter()

        holder.summaryText.text= itemsViewModel.summary
        holder.dateText.text = formatter.format(LocalDateTime.parse(itemsViewModel.transactionDate))

        if(itemsViewModel.credit> BigDecimal(0) && itemsViewModel.debit == BigDecimal(0)) {
            holder.creditDebitText.text = "${context.getString(R.string.creditPrefix)}${itemsViewModel.credit}"
            holder.creditDebitText.setTextColor(Color.parseColor("#228B22"))
        }else {
            holder.creditDebitText.text = "${context.getString(R.string.debitPrefix)}${itemsViewModel.debit}"
            holder.creditDebitText.setTextColor(Color.parseColor("#A91D36"))
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return transactionsList.size
    }

    // Holds the views for adding text
    class ViewHolder(itemView: View,onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val summaryText: TextView = itemView.findViewById(R.id.summaryText)
        val dateText: TextView = itemView.findViewById(R.id.transactionDateText)
        val creditDebitText: TextView = itemView.findViewById(R.id.creditDebitText)

        init {
            itemView.setOnClickListener {
                // this will be called only once.
                onItemClicked(bindingAdapterPosition)
            }
        }
    }
}