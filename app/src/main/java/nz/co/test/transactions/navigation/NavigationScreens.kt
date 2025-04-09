package nz.co.test.transactions.navigation

sealed class NavigationItem(val route: String) {
    object TransactionList : NavigationItem("transactionList")
}