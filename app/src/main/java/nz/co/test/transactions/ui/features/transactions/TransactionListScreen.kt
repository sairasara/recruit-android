package nz.co.test.transactions.ui.features.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import nz.co.test.transactions.R
import nz.co.test.transactions.domain.model.Transaction
import nz.co.test.transactions.ui.viewmodel.TransactionViewModel
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(
    viewModel: TransactionViewModel = hiltViewModel(),
    navController: NavController
) {
    val transactions: List<Transaction> by viewModel.transactions.observeAsState(initial = emptyList())
    val networkError: Boolean by viewModel.networkError.observeAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transactions") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White)
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            if (networkError) {
                Text("Error loading transactions.")
            } else if (transactions.isEmpty()) {
                Text("No transactions available.")
            }  else {
                LazyColumn { items(transactions) { transaction ->
                    TransactionItem(transaction = transaction) {
                    } }
                }
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction, onClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(),
        // backgroundColor = Color(0xFFC1C2C2) // Same as #C1C2C2 in XML
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = transaction.summary,
                    style = TextStyle(fontSize = 16.sp, color = Color.Black, fontWeight = Bold)
                )
                CreditDebitText(transaction.credit, transaction.debit)
            }

            Spacer(modifier = Modifier.height(5.dp))

            FormatDate(transaction.transactionDate)
        }
    }
}

@Composable
fun CreditDebitText(credit: BigDecimal, debit: BigDecimal) {

    val creditDebitText: String
    val creditDebitColor: Color

    if (credit > BigDecimal.ZERO && debit.compareTo(BigDecimal.ZERO) == 0) {
        creditDebitText = "${stringResource(id = R.string.creditPrefix)}$credit"
        creditDebitColor = Color(0xFF228B22) // Equivalent to #228B22 in XML
    } else {
        creditDebitText = "${stringResource(id = R.string.debitPrefix)}$debit"
        creditDebitColor = Color(0xFFA91D36) // Equivalent to #A91D36 in XML
    }

    Text(
        text = creditDebitText,
        style = TextStyle(fontSize = 18.sp, color = creditDebitColor,fontWeight = Bold )
    )
}

@Composable
fun FormatDate(transactionDate: String){
    // Format the transaction date
    val formatter = DateTimeFormatterBuilder()
        .appendPattern("dd-MM-yyyy hh:mm a") // Using 'yyyy' for year and 'a' for AM/PM
        .toFormatter()

    val formattedDate = try {
        val date = LocalDateTime.parse(transactionDate) // Parse the string into LocalDateTime
        formatter.format(date) // Format the parsed date
    } catch (e: Exception) {
        "Invalid Date" // Handle any parsing issues
    }

    Text(
        text = formattedDate,
        style = TextStyle(fontSize = 15.sp, color = Color.Black),
        modifier = Modifier.padding(
            top = 4.dp,
            bottom = 4.dp
        )
    )
}