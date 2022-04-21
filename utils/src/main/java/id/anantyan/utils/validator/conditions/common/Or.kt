package id.anantyan.utils.validator.conditions.common

import androidx.annotation.StringRes
import id.anantyan.utils.validator.conditions.Condition
import id.anantyan.utils.validator.conditions.ConditionBuilder
import id.anantyan.utils.validator.conditions.ConditionsBuilder
import id.anantyan.utils.validator.rules.Rule

class Or : Condition {

    constructor(rules: List<Rule>, @StringRes errorRes: Int) : super(rules, errorRes)

    constructor(rules: List<Rule>, errorMessage: String) : super(rules, errorMessage)

    constructor(rule: Rule, @StringRes errorRes: Int) : super(listOf(rule), errorRes)

    constructor(rule: Rule, errorMessage: String) : super(listOf(rule), errorMessage)

    override fun validate(value: String?): Boolean {
        for (baseRule in rules) {
            if (baseRule.validate(value)) {
                return true
            }
        }
        return false
    }
}

class OrBuilder : ConditionBuilder() {
}

fun ConditionsBuilder.or(
    @StringRes errorRes: Int,
    init: OrBuilder.() -> Unit
): Or {
    val or = OrBuilder()
    or.init()
    return Or(or.ruleList, errorRes)
}

fun ConditionsBuilder.or(
    errorMessage: String,
    init: OrBuilder.() -> Unit
): Or {
    val or = OrBuilder()
    or.init()
    return Or(or.ruleList, errorMessage)
}