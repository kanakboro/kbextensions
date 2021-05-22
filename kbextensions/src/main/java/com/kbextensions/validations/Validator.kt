package com.kbextensions.validations


/**
 * Created By Kanak Boro 22 May, 2021
 **/

interface Validator {
    fun isValid(): Boolean
    fun message(): String?
}