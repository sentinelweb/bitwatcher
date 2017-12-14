package uk.co.sentinelweb.bitwatcher.activity.edit_account.validators

import uk.co.sentinelweb.bitwatcher.common.validation.ValidationError
import uk.co.sentinelweb.bitwatcher.common.validation.Validator
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.AccountType
import javax.inject.Inject


class AccountValidator @Inject constructor(
        private val nameValidator: NameValidator,
        private val balanceValidator: BalanceValidator

) : Validator<AccountDomain>{
    override fun validate(input: AccountDomain): ValidationError {

        if (input.type == AccountType.INITIAL) {
            return ValidationError("Account type is initial", ValidationError.Type.VALIDATION)
        }
        val nameValid = nameValidator.validate(input.name)
        if (nameValid != ValidationError.OK) {
            return nameValid
        }
        var balanceError:ValidationError? = null
        input.balances.forEach({balance->
            val validation = balanceValidator.validate(balance)
            if (validation != ValidationError.OK) {
                balanceError =  validation
            }
        })
        return balanceError?:ValidationError.OK
    }
}