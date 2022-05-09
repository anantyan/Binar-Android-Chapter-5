@file:Suppress("UNUSED")

package id.anantyan.utils.validator

import android.content.Context
import com.google.android.material.textfield.TextInputLayout
import id.anantyan.utils.validator.conditions.Condition
import id.anantyan.utils.validator.conditions.ConditionsBuilder
import id.anantyan.utils.validator.interfaces.ErrorImpl
import id.anantyan.utils.validator.rules.Rule
import id.anantyan.utils.validator.rules.RulesBuilder
import java.util.*

class Validation(val textInputLayout: TextInputLayout) {

    val rules = arrayListOf<Rule>()
    val conditions = arrayListOf<Condition>()

    fun add(rule: Rule): Validation {
        rules.add(rule)
        return this
    }

    fun add(condition: Condition): Validation {
        conditions.add(condition)
        return this
    }

    fun setError(context: Context, errorImpl: ErrorImpl, errors: ArrayList<String>) {
        val error = errorImpl.getErrorMessage(context)
        textInputLayout.isErrorEnabled = true
        errors.add(error)
        textInputLayout.error = error
    }
}

class ValidationBuilder {
    val ruleList = arrayListOf<Rule>()
    val conditionList = arrayListOf<Condition>()

    fun rules(init: RulesBuilder.() -> Unit) {
        ruleList.addAll(RulesBuilder().apply(init).ruleList)
    }

    fun conditions(init: ConditionsBuilder.() -> Unit) {
        conditionList.addAll(ConditionsBuilder().apply(init).conditionList)
    }
}

fun validation(textInputLayout: TextInputLayout, init: ValidationBuilder.() -> Unit): Validation {
    val validation = ValidationBuilder()
    validation.init()
    return Validation(textInputLayout).apply {
        this.rules.addAll(validation.ruleList)
        this.conditions.addAll(validation.conditionList)
    }
}