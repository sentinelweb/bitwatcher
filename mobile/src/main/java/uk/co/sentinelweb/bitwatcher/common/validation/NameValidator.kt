package uk.co.sentinelweb.bitwatcher.common.validation

import android.content.Context
import uk.co.sentinelweb.bitwatcher.R
import java.util.regex.Pattern
import javax.inject.Inject


class NameValidator @Inject constructor(
        private val context:Context
) : Validator<String> {
    private companion object {
        val REGEX = "[A-za-z0-9 ]+"
    }
    private val pattern = Pattern.compile(REGEX)
    override fun validate(input: String): ValidationError {
        val minLength = context.resources.getInteger(R.integer.validation_account_name_min_length)
        val maxLength = context.resources.getInteger(R.integer.validation_account_name_max_length)
        if (input.length < minLength || input.length > maxLength) {
            return ValidationError("length must be between ${minLength} and ${maxLength}", ValidationError.Type.VALIDATION)
        }
        val matcher = pattern.matcher(input)
        if (!matcher.matches()) {
            return ValidationError("Only alphanumeric and spaces", ValidationError.Type.VALIDATION)
        }
        return ValidationError.OK
    }

}