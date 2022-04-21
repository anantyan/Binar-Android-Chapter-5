package id.anantyan.utils.validator.rules.common

import androidx.annotation.StringRes
import id.anantyan.utils.validator.rules.Rule

class NotNullRule : Rule {

    constructor(@StringRes errorRes: Int) : super(errorRes)

    constructor(errorMessage: String) : super(errorMessage)

    override fun validate(value: String?): Boolean {
        return value != null
    }
}

fun notNull(@StringRes errorRes: Int): NotNullRule = NotNullRule(errorRes)

fun notNull(errorMessage: String): NotNullRule = NotNullRule(errorMessage)