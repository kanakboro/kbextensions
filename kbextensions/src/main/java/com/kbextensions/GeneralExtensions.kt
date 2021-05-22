package com.kbextensions

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.text.InputFilter
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.AnimRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.ImageViewCompat
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created By Kanak Boro 22 May, 2021
 **/


/** This method is useful for showing Toast **/
fun Context.showToastMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/** This method is useful for showing SnackBar **/
fun Context.showSnackBar(message: String) {
    Snackbar.make(
        (this as Activity).findViewById(android.R.id.content),
        message,
        Snackbar.LENGTH_SHORT
    ).show()
}

/** This method is useful for showing different types of logs **/
fun Any.showLogMessage(tag: String?, type: String) {
    type.takeIf { it.isNotEmpty() }.let { logType ->
        when (logType) {
            "e" -> Log.e(tag?:"", "" + this)
            "d" -> Log.d(tag?:"", "" + this)
            "w" -> Log.w(tag?:"", "" + this)
            "i" -> Log.i(tag?:"", "" + this)
            "v" -> Log.v(tag?:"", "" + this)
            else -> Log.e(tag?:"", "" + this)
        }
    }
}

/** This method is useful for showing Alert **/
fun Context.showAlertDialog(title: String, message: String, positiveButton: String) {

    Handler(Looper.getMainLooper()).post {
        if (message.isNotEmpty()) {
            val dialogBuilder = AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(
                    positiveButton
                ) { arg0, _ -> arg0.dismiss() }

            val alert = dialogBuilder.create()
            alert.setTitle(title)

            alert.show()
            val posButton = alert.getButton(DialogInterface.BUTTON_POSITIVE)
            with(posButton) { setTextColor(Color.RED) }
        }
    }
}

/** This method will return date in given format **/
fun getFormattedDate(fromFormat: String, toFormat: String): String {
    return if (fromFormat.isNotEmpty()) {
        val formattedDate: Date? =
            SimpleDateFormat(fromFormat, Locale.getDefault()).parse(fromFormat)
        SimpleDateFormat(toFormat, Locale.getDefault()).format(formattedDate!!)
    } else ""
}

/** It will return date in milliseconds of given time and day
 * pass value for time like this -> 02:00 AM or 02:00 PM
 * pass value for dayOfDate like this -> today = 0 ,yesterday = -1 and tomorrow = +1
 * If param will not match the format it will return milli of current day **/
fun getConvertedDate(time: String, dayOfDate: Int): Long {
    return try {
        val calender = Calendar.getInstance()

        if (dayOfDate > 0)
            calender.add(Calendar.DAY_OF_MONTH, dayOfDate)

        val sdf = SimpleDateFormat("dd-M-yyyy hh:mm a", Locale.US)
        val parseFormat = SimpleDateFormat("hh:mm a", Locale.US)
        val date: Date = parseFormat.parse(time) ?: Date()
        (sdf.parse(
            SimpleDateFormat(
                "dd-M-yyyy",
                Locale.US
            ).format(calender.timeInMillis) + " " + parseFormat.format(date)
        ) ?: Date()).time
    } catch (e: Exception) {
        e.printStackTrace()
        Date().time
    }
}

/** This method will return date in given format from millisecond **/
fun Long?.getDateFromMilli(format: String): String {
    return try {
        if (this != null && this > 0)
            SimpleDateFormat(format, Locale.US).format(this)
        else ""
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

/** This method will return date in given format from date **/
fun Date?.getFormattedDate(format: String): String {
    return try {
        if (this != null)
            SimpleDateFormat(format, Locale.US).format(this)
        else ""
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

/** This method is useful to Tint the image into given color **/
fun ImageView.setTint(@ColorInt colorRes: Int) {
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(colorRes))
}

/** This method is useful to Tint the checkbox into given color **/
fun CheckBox.setColorState(@ColorInt colorRes: Int) {
    this.buttonTintList = ColorStateList.valueOf(colorRes)
}

/** This method is useful to change background color of view into given color **/
fun View.changeBackgroundColor(@ColorInt colorRes: Int) {
    this.backgroundTintList = ColorStateList.valueOf(colorRes)
}

/** This method is useful to strike the textview **/
fun TextView.strikeTextView() {
    this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

/** This method is useful to set marque in textview **/
fun TextView.setMarque() {
    this.ellipsize = TextUtils.TruncateAt.MARQUEE
    this.isSingleLine = true
    this.marqueeRepeatLimit = 5
    this.isSelected = true
}

/** This method is useful to move cursor at the end of line **/
fun EditText.moveCursorAtEnd() {
    this.setSelection(this.text.toString().length)
}

/** This method is useful to restrict user to enter special character, numbers and emojis
 * Set filter into Edittext by ->  EditText.filters = getFilter()
 **/
fun getFilter(): Array<out InputFilter>? {
    return EmojiFilter.getFilter()
}

/** This method is useful to set animation in views **/
fun Context.playAnim(view: View, @AnimRes anim: Int) {
    view.startAnimation(AnimationUtils.loadAnimation(this, anim))
}

/** This method is useful to hide keyboard **/
fun Activity.hideSoftKeyboard() {

    val inputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = this.currentFocus

    if (view == null)
        view = View(this)

    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

/** This method is useful to show keyboard **/
fun Context.showKeyBoard(view: View) {
    (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(view, 0)
}

/** This method is useful to check is given email is valid or not **/
fun String.isEmailValid(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/** This method is useful to check any string is it valid or not with given regex **/
fun String.isValid(regex: String): Boolean {
    return Pattern.compile(regex)
        .matcher(this.trim())
        .matches()
}

