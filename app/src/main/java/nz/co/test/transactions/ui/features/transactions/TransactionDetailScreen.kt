package nz.co.test.transactions.ui.features.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import nz.co.test.transactions.R
import nz.co.test.transactions.domain.model.Transaction
import java.math.BigDecimal
import java.util.Locale
import kotlin.text.format
import kotlin.times

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailScreen(navController: NavController, transaction: Transaction) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transaction Detail") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }

    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(
                    horizontal = 10.dp,
                    vertical = 10.dp
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = transaction.summary,
                    style = TextStyle(fontSize = 18.sp, color = Color.Black, fontWeight = Bold)
                )
                CreditDebitText(transaction.credit, transaction.debit)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Divider
            Divider()

            CalculateTransactionDetails(transaction)

            // Transaction Date Placeholder and Value
            Text(stringResource(R.string.transactionDateText),
                style = TextStyle(fontSize = 15.sp, color = Color.Black, fontWeight = Bold),
                modifier = Modifier.padding(
                    top = 8.dp,
                    bottom = 8.dp
                ))
            FormatDate(transaction.transactionDate)

            Divider()

            Spacer(modifier = Modifier.weight(1f))

            // Chatbot Section at Bottom
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_talkback),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Talk to us",
                    style = TextStyle(fontSize = 15.sp, color = Color.Black)
                )
            }
        }
    }
}

@Composable
fun Divider(){
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .height(3.dp), // Thickness is now set through height
        thickness = 1.dp, color = Color.Gray
    )
}

@Composable
fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 5.dp)) {
        Text(text = label,
            style = TextStyle(fontSize = 15.sp, color = Color.Black, fontWeight = Bold),
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 8.dp
            ))
        Text(text = value,
            style = TextStyle(fontSize = 15.sp, color = Color.Black),
            modifier = Modifier.padding(
                top = 4.dp,
                bottom = 4.dp
            ))
    }
}

@Composable
private fun CalculateTransactionDetails(transaction: Transaction) {

    val credit = transaction.credit
    val debit = transaction.debit

    val toPlaceholderText: String
    val amount: String
    val gst: String

    if (credit > BigDecimal.ZERO && debit.compareTo(BigDecimal.ZERO) == 0) {
        toPlaceholderText = stringResource(R.string.from)
        amount = "${stringResource(R.string.creditPrefix)}$credit"
        val gstValue  = String.format(Locale.getDefault(), "%.2f", (credit * BigDecimal("0.15")))
        gst = "$${gstValue}"
    } else {
        toPlaceholderText = stringResource(R.string.to)
        amount = "${stringResource(R.string.debitPrefix)}$debit"
        val gstValue = String.format(Locale.getDefault(), "%.2f", (debit * BigDecimal("0.15")))
        gst = "$${gstValue}"
    }

    // To Placeholder and Value
    DetailRow(label = stringResource(R.string.to), value = transaction.summary)
    Divider()

    // Amount Placeholder and Value
    DetailRow(label = stringResource(R.string.amount), value = amount)
    Divider()

    // GST Placeholder and Value
    DetailRow(label = stringResource(R.string.gst), value = gst)
    Divider()

}

