package com.kbextensions.validations


/**
 * Created By Kanak Boro 22 May, 2021
 **/

class CheckValidation(private val isValid: Boolean, private val message: String) : Validator {

    override fun isValid(): Boolean {
        return isValid
    }

    override fun message(): String {
        return message
    }
}