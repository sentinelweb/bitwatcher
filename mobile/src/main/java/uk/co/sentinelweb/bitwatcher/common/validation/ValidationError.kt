package uk.co.sentinelweb.bitwatcher.common.validation


data class ValidationError(val message:String, val code:Type = Type.OK){
    enum class Type {
        OK,GENERIC, VALIDATION
    }
    companion object {
        val OK = ValidationError("", Type.OK)
    }
}