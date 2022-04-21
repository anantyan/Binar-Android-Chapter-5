package id.anantyan.utils.validator.rules.common

import androidx.annotation.StringRes
import id.anantyan.utils.validator.rules.Rule
import java.util.*

class AllLowerCaseRule : Rule {

    private var locale: Locale

    constructor(@StringRes errorRes: Int, locale: Locale = Locale.getDefault())
            : super(errorRes) {
        this.locale = locale
    }

    constructor(errorMessage: String, locale: Locale = Locale.getDefault())
            : super(errorMessage) {
        this.locale = locale
    }

    override fun validate(value: String?): Boolean {
        if (value == null) {
            throw NullPointerException()
        } else {
            return value.lowercase(locale) == value
        }
    }
}

fun allLowercase(
    @StringRes errorRes: Int,
    locale: Locale = Locale.getDefault()
): AllLowerCaseRule = AllLowerCaseRule(errorRes, locale)

fun allLowercase(
    errorMessage: String,
    locale: Locale = Locale.getDefault()
): AllLowerCaseRule = AllLowerCaseRule(errorMessage, locale)