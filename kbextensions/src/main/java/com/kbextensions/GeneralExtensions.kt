package com.kbextensions

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.*
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.AnimRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.ImageViewCompat
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Created By Kanak Boro 22 May, 2021
 **/


/**
 * Common Functions
 */

/** This method is useful disable the view **/
fun View.disabled() = run { isEnabled = false }

/** This method is useful enable the view **/
fun View.enable() = run { isEnabled = true }

/** This method is useful to make visibility as GONE of the view **/
fun View?.gone() = run { this?.visibility = View.GONE }

/** This method is useful to make visibility as VISIBLE of the view **/
fun View?.visible() = run { this?.visibility = View.VISIBLE }

/** This method is useful to make visibility as INVISIBLE of the view **/
fun View?.invisible() = run { this?.visibility = View.INVISIBLE }


/**
 *   Toast,Log,SnackBar Related Functions
 */

/** This method is useful for showing Toast **/
fun Context.showToastMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/** This method is useful for showing Toast on the top of the screen**/
fun Activity.showToastOnTop(message: String, fontSize: Float, fontFace: Typeface?) {
    try {
        Handler(Looper.getMainLooper()).post {
            val toast = Toast(this)
            // inflate custom view
            val view: View = this.layoutInflater.inflate(android.R.layout.test_list_item, null)
            val textView = view as AppCompatTextView
            textView.setPadding(20, 40, 10, 40)
            textView.setBackgroundColor(Color.BLACK)
            textView.setTextColor(Color.WHITE)
            textView.textSize = fontSize
            fontFace?.let { textView.typeface = it }
            textView.text = message
            toast.view = view
            toast.duration = Toast.LENGTH_SHORT
            toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)
            toast.show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
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
            "e" -> Log.e(tag ?: "", "" + this)
            "d" -> Log.d(tag ?: "", "" + this)
            "w" -> Log.w(tag ?: "", "" + this)
            "i" -> Log.i(tag ?: "", "" + this)
            "v" -> Log.v(tag ?: "", "" + this)
            else -> Log.e(tag ?: "", "" + this)
        }
    }
}


/**
 *  Alert Dialog
 */

/** This method is useful for showing Alert **/
fun Context.showAlertDialog(
    title: String,
    message: String,
    positiveButton: String,
    cancelable: Boolean
) {
    Handler(Looper.getMainLooper()).post {
        message.takeIf { it.isNotEmpty() }?.let {
            val dialogBuilder = AlertDialog.Builder(this)
                .setMessage(it)
                .setCancelable(cancelable)
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


/**
 *  Date And Time Related Functions
 */

/** This method will return date in given format **/
fun getFormattedDate(
    fromFormat: String,
    toFormat: String,
    locale: Locale = Locale.US,
    timezone: TimeZone
): String {
    return try {
        if (fromFormat.isNotEmpty() && toFormat.isNotEmpty()) {
            val sdf = SimpleDateFormat(fromFormat, locale)
            sdf.timeZone = timezone
            val formattedDate: Date? = sdf.parse(fromFormat)
            SimpleDateFormat(toFormat, locale).format(formattedDate!!)
        } else ""
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

/** It will return date in milliseconds of given time and day
 * pass value for time like this -> 02:00 AM or 02:00 PM
 * pass value for dayOfDate like this -> today = 0 ,yesterday = -1 and tomorrow = +1
 * If param will not match the format it will return milli of current day **/
fun getConvertedDate(
    time: String, dayOfDate: Int,
    locale: Locale = Locale.US,
    timezone: TimeZone
): Long {
    return try {
        val calender = Calendar.getInstance()

        if (dayOfDate > 0)
            calender.add(Calendar.DAY_OF_MONTH, dayOfDate)

        val sdf = SimpleDateFormat("dd-M-yyyy hh:mm a", locale)
        sdf.timeZone = timezone
        val parseFormat = SimpleDateFormat("hh:mm a", locale)
        parseFormat.timeZone = timezone
        val parseFormatFinal = SimpleDateFormat("dd-M-yyyy", locale)
        parseFormatFinal.timeZone = timezone
        val date: Date = parseFormat.parse(time) ?: Date()
        (sdf.parse(parseFormatFinal.format(calender.timeInMillis) + " " + parseFormat.format(date))
            ?: Date()).time
    } catch (e: Exception) {
        e.printStackTrace()
        Date().time
    }
}

/** This method will return date in given format from millisecond **/
fun Long?.getDateFromMilli(
    format: String,
    locale: Locale = Locale.US,
    timezone: TimeZone
): String {
    return try {
        if (this != null && this > 0) {
            format.takeIf { it.isNotEmpty() }?.let {
                val sdf = SimpleDateFormat(it, locale)
                sdf.timeZone = timezone
                sdf.format(this)
            } ?: ""
        } else ""
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

/** This method will return date in given format from date **/
fun Date?.getFormattedDate(
    formatTo: String,
    locale: Locale = Locale.US,
    timezone: TimeZone
): String {
    return try {
        if (this != null) {
            formatTo.takeIf { it.isNotEmpty() }?.let {
                val sdf = SimpleDateFormat(it, locale)
                sdf.timeZone = timezone
                sdf.format(this)
            } ?: ""
        } else ""
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}


/** This method will return calender of given String Date
 * @param dateFormat - is a format of given String date which you want to convert as calender, make sure that pass the same format as you are passing string date.
 * @param dateFormat - will set by default as yyyy-MM-dd'T'HH:mm:ss.SSS'Z' format.
 **/
val tzDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply { timeZone = TimeZone.getTimeZone("UTC") }
fun String.toCalender(dateFormat: SimpleDateFormat = tzDateFormat): Calendar? {
    return try {
        dateFormat.parse(this)?.let {
            val cal = Calendar.getInstance()
            cal.time = it
            cal
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Calendar.getInstance()
    }
}


/**
 *  Views Related Functions
 */

/** This method is useful to get TypeFace by passing name of font i.e. R.font.arial **/
fun Activity.getTypeFace(fontName: Int): Typeface? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        resources.getFont(fontName)
    } else {
        ResourcesCompat.getFont(this, fontName)
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

/** This method is useful to add drawable in textview
 * @param left - if you want to set drawable in left position in textview then pass your drawable in this param i.e. textview.setDrawable(R.drawable.icon,0,0,0)
 * @param top - if you want to set drawable in top position in textview then pass your drawable in this param i.e. textview.setDrawable(0,R.drawable.icon,0,0)
 * @param right - if you want to set drawable in right position in textview then pass your drawable in this param i.e. textview.setDrawable(0,0,R.drawable.icon,0)
 * @param bottom - if you want to set drawable in bottom position in textview then pass your drawable in this param i.e. textview.setDrawable(0,0,0,R.drawable.icon)
 **/
fun TextView.setDrawable(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
    try {
        this.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    } catch (e: Exception) {
        e.printStackTrace()
    }
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

/** This method is useful to get filter for your required regex
 * Set filter into Edittext by ->  EditText.filters = getCustomFilter()/getCustomFilter(regex)
 * @param regex - if it will not get any data then it will take "defaultBlockCharacterSet" as param
 **/
var defaultBlockCharacterSet: String? = "+~#^|$%&*!£,€1234567890;`•√π÷×¶∆¢¥={}\\[]<>@_-()/\"':?"
fun getCustomFilter(regex: String? = defaultBlockCharacterSet): Array<out InputFilter>? {
    return EmojiFilter.getFilter(regex)
}

/** This method is useful to set animation in views **/
fun Context.playAnim(view: View, @AnimRes anim: Int) {
    view.startAnimation(AnimationUtils.loadAnimation(this, anim))
}


/**
 *  KeyBoard Related Functions
 */

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
    (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
        view,
        0
    )
}


/**
 *  Regex Validation Related Functions
 */

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


/**
 *  String Related Functions
 */

/** This method is useful to display HTML format data into textview **/
fun String?.toHtml(): Spanned? {
    if (this.isNullOrEmpty()) return null
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
        else
            Html.fromHtml(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/** This method is useful to add asterisk at the ent of the string with your required color **/
fun String.setColoredAsterisk(@ColorInt color: Int): SpannableStringBuilder {
    val colored = "*"
    val builder = SpannableStringBuilder()

    builder.append(this)
    val start = builder.length
    builder.append(colored)
    val end = builder.length

    builder.setSpan(
        ForegroundColorSpan(color), start, end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return builder
}

