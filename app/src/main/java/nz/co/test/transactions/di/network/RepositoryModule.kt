package nz.co.test.transactions.di.network

import nz.co.test.transactions.data.api.TransactionsService
import nz.co.test.transactions.data.repository.TransactionRepositoryImpl
import nz.co.test.transactions.domain.repository.TransactionRepository
import nz.co.test.transactions.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    // Provide the TransactionRepository instance
    @Provides
    fun provideTransactionRepository(transactionsService: TransactionsService,
                                     networkUtils: NetworkUtils): TransactionRepository {
        return TransactionRepositoryImpl(transactionsService, networkUtils)
    }
}
