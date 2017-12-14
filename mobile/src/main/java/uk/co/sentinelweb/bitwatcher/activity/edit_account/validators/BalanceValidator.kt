package uk.co.sentinelweb.bitwatcher.activity.edit_account.validators

import uk.co.sentinelweb.bitwatcher.common.validation.ValidationError
import uk.co.sentinelweb.bitwatcher.common.validation.Validator
import uk.co.sentinelweb.domain.BalanceDomain
import uk.co.sentinelweb.domain.CurrencyCode
import java.math.BigDecimal
import javax.inject.Inject


class BalanceValidator @Inject constructor():Validator<BalanceDomain> {
    override fun validate(input: BalanceDomain): ValidationError {
        if (input.currency==CurrencyCode.NONE) {
            return ValidationError("No Currency code set", ValidationError.Type.VALIDATION)
        }
        if (input.available <= BigDecimal.ZERO) {
            return ValidationError("Amount should be > 0", ValidationError.Type.VALIDATION)
        }
        return ValidationError.OK
    }
}