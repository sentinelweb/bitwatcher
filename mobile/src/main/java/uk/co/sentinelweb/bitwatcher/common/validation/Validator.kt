package uk.co.sentinelweb.bitwatcher.common.validation

interface Validator<T> {
    fun validate(input: T): ValidationError
}