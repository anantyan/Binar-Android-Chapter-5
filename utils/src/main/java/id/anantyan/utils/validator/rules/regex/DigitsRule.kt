package id.anantyan.utils.validator.rules.regex

import androidx.annotation.StringRes
import id.anantyan.utils.validator.rules.common.RegexRule

class DigitsRule : RegexRule {

    constructor(@StringRes errorRes: Int) : super(DIGITS_REGEX, errorRes)

    constructor(errorMessage: String) : super(DIGITS_REGEX, errorMessage)

    companion object {
        private const val DIGITS_REGEX = "\\d+"
    }
}

fun digitsOnly(@StringRes errorRes: Int): DigitsRule = DigitsRule(errorRes)

fun digitsOnly(errorMessage: String): DigitsRule = DigitsRule(errorMessage)