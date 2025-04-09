package nz.co.test.transactions.navigation

sealed class NavigationItem(val route: String) {
    object TransactionList : NavigationItem("transactionList")
    object TransactionDetail : NavigationItem("transactionDetail/{id}"){
        fun createRoute(id: Int) = "transactionDetail/$id"
    }
}