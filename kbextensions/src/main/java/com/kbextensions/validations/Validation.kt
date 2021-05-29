package com.kbextensions.validations

import android.content.Context
import com.kbextensions.getFormattedDate
import com.kbextensions.showSnackBar
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created By Kanak Boro 22 May, 2021
 **/

class Validation(private val baseContext: Context?) {
    private var validators: ArrayList<Validator>? = null

    init {
        validators = ArrayList()
    }

    companion object {
        fun create(baseContext: Context?): Validation {
            return Validation(baseContext)
        }
    }

    fun isEmpty(editable: String?, message: String): Validation {
        validators?.add(CheckValidation((editable ?: "").trim().isNotEmpty(), message))
        return this
    }

    fun areEqual(editable: String?, editable2: String?, message: String): Validation {
        validators?.add(
            CheckValidation(
                (editable ?: "").trim() == (editable2 ?: "").trim(),
                message
            )
        )
        return this
    }

    fun areNotEqual(editable: String?, editable2: String?, message: String): Validation {
        validators?.add(
            CheckValidation(
                (editable ?: "").trim() != (editable2 ?: "").trim(),
                message
            )
        )
        return this
    }

    fun isValid(): Boolean {
        validators.let {
            it?.forEach { validator ->
                if (!validator.isValid()) {
                    baseContext?.showSnackBar(validator.message() ?: "")
                    return false
                }
            }
        }
        return true
    }

}