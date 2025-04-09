package nz.co.test.transactions.data.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class TransactionDto(
    @SerializedName("id") val id: Int,
    @SerializedName("transactionDate") val transactionDate: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("debit") val debit: BigDecimal,
    @SerializedName("credit") val credit: BigDecimal
)