package nz.co.test.transactions.ui.features.transactions

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.NavController
import nz.co.test.transactions.domain.model.Transaction
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal
import org.mockito.Mockito.mock

class TransactionDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockNavController = mock(NavController::class.java)

    private val testTransaction = Transaction(
        id = 1,
        transactionDate = "2021-03-10T06:24:06",
        summary = "Description 1",
        credit = BigDecimal("100"),
        debit = BigDecimal("0")
    )

    @Test
    fun transactionSummaryIsDisplayed() {
        composeTestRule.setContent {
            TransactionDetailScreen(navController = mockNavController,transaction = testTransaction)
        }
        composeTestRule.onNodeWithText("Test Transaction").assertIsDisplayed()
    }

    @Test
    fun creditAmountIsDisplayedWithPrefix() {
        composeTestRule.setContent {
            TransactionDetailScreen(navController = mockNavController,transaction = testTransaction)
        }
        composeTestRule.onNodeWithText("+$100.00").assertIsDisplayed()
    }

    @Test
    fun debitAmountIsDisplayedWithPrefix() {
        val debitTransaction = testTransaction.copy(credit = BigDecimal(0.0), debit = BigDecimal(50.0))
        composeTestRule.setContent {
            TransactionDetailScreen(navController = mockNavController,transaction = debitTransaction)
        }
        composeTestRule.onNodeWithText("-$50.00").assertIsDisplayed()
    }

    @Test
    fun transactionDateLabelIsDisplayed() {
        composeTestRule.setContent {
            TransactionDetailScreen(navController = mockNavController,transaction = testTransaction)
        }
        composeTestRule.onNodeWithText("Transaction Date").assertIsDisplayed()
    }

    @Test
    fun transactionDateValueIsDisplayed() {
        composeTestRule.setContent {
            TransactionDetailScreen(navController = mockNavController,transaction = testTransaction)
        }
        composeTestRule.onNodeWithText("2025-04-08").assertIsDisplayed()
    }

    @Test
    fun toLabelIsDisplayed() {
        composeTestRule.setContent {
            TransactionDetailScreen(navController = mockNavController,transaction = testTransaction)
        }
        composeTestRule.onNodeWithText("To").assertIsDisplayed()
    }

    @Test
    fun toValueIsDisplayed() {
        composeTestRule.setContent {
            TransactionDetailScreen(navController = mockNavController,transaction = testTransaction)
        }
        composeTestRule.onNodeWithText("Test Transaction").assertIsDisplayed()
    }

    @Test
    fun amountLabelIsDisplayed() {
        composeTestRule.setContent {
            TransactionDetailScreen(navController = mockNavController,transaction = testTransaction)
        }
        composeTestRule.onNodeWithText("Amount").assertIsDisplayed()
    }

    @Test
    fun amountValueIsDisplayedForCredit() {
        composeTestRule.setContent {
            TransactionDetailScreen(navController = mockNavController,transaction = testTransaction)
        }
        composeTestRule.onNodeWithText("+$100.00").assertIsDisplayed()
    }

    @Test
    fun amountValueIsDisplayedForDebit() {
        val debitTransaction = testTransaction.copy(credit = BigDecimal(0.0), debit = BigDecimal(50.0))
        composeTestRule.setContent {
            TransactionDetailScreen(navController = mockNavController,transaction = debitTransaction)
        }
        composeTestRule.onNodeWithText("-$50.00").assertIsDisplayed()
    }

    @Test
    fun gstLabelIsDisplayed() {
        composeTestRule.setContent {
            TransactionDetailScreen(navController = mockNavController,transaction = testTransaction)
        }
        composeTestRule.onNodeWithText("GST").assertIsDisplayed()
    }

    @Test
    fun gstValueIsDisplayedForCredit() {
        composeTestRule.setContent {
            TransactionDetailScreen(navController = mockNavController,transaction = testTransaction)
        }
        composeTestRule.onNodeWithText("15.00").assertIsDisplayed()
    }

    @Test
    fun gstValueIsDisplayedForDebit() {
        val debitTransaction = testTransaction.copy(credit = BigDecimal(0.0), debit = BigDecimal(50.0))
        composeTestRule.setContent {
            TransactionDetailScreen(navController = mockNavController,transaction = debitTransaction)
        }
        composeTestRule.onNodeWithText("7.50").assertIsDisplayed()
    }

    @Test
    fun dividerIsDisplayedBetweenSections() {
        composeTestRule.setContent {
            TransactionDetailScreen(navController = mockNavController,transaction = testTransaction)
        }
        composeTestRule.onNodeWithContentDescription("Horizontal divider").assertExists()
        composeTestRule.onNodeWithContentDescription("Horizontal divider").assertIsDisplayed()
    }
}