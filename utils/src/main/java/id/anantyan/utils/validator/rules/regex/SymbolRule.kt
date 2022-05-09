package id.anantyan.utils.validator.rules.regex

import androidx.annotation.StringRes
import id.anantyan.utils.validator.rules.common.RegexRule

class SymbolRule : RegexRule {

    constructor(@StringRes errorRes: Int) : super(ALPHABET_REGEX, errorRes)

    constructor(errorMessage: String) : super(ALPHABET_REGEX, errorMessage)

    companion object {
        private const val ALPHABET_REGEX = "^[-!@#\$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/]*\$"
    }
}

fun symbolsOnly(@StringRes errorRes: Int): SymbolRule = SymbolRule(errorRes)

fun symbolsOnly(errorMessage: String): SymbolRule = SymbolRule(errorMessage)