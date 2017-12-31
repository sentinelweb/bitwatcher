package uk.co.sentinelweb.use_case

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.AccountType

interface AccountsRepositoryUseCase {

    fun saveAccount(account: AccountDomain): Single<Boolean>
    fun deleteAccount(account: AccountDomain): Single<Boolean>
    fun flowAllAccounts(): Flowable<List<AccountDomain>>
    fun singleLoadAccount(id: Long): Single<AccountDomain>
    fun maybeLoadAccountOfType(type:AccountType): Maybe<AccountDomain>
    fun singleAllAccounts(): Single<List<AccountDomain>>
}