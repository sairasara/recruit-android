package nz.co.test.transactions.data.mapper

import nz.co.test.transactions.data.model.TransactionDto
import nz.co.test.transactions.domain.model.Transaction
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

object TransactionMapper {

    fun toDomain(transactionDto: TransactionDto): Transaction {
        return Transaction(
            id = transactionDto.id,
            summary = transactionDto.summary,
            debit = transactionDto.debit,
            credit = transactionDto.credit,
            transactionDate = transactionDto.transactionDate
        )
    }
}