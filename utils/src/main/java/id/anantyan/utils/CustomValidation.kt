package id.anantyan.utils

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import id.anantyan.utils.validator.Validation
import id.anantyan.utils.validator.rules.common.equalTo
import id.anantyan.utils.validator.rules.common.minimumLength
import id.anantyan.utils.validator.rules.common.notEmpty
import id.anantyan.utils.validator.rules.common.notNull
import id.anantyan.utils.validator.rules.regex.PasswordRule
import id.anantyan.utils.validator.rules.regex.alphanumericOnly
import id.anantyan.utils.validator.rules.regex.email
import id.anantyan.utils.validator.rules.regex.withPassword
import id.anantyan.utils.validator.validation

fun emailValid(textInputLayout: TextInputLayout): Validation {
    return validation(textInputLayout) {
        rules {
            +notNull(R.string.txt_not_null)
            +notEmpty(R.string.txt_not_empty)
            +email(R.string.txt_not_email)
        }
    }
}

fun passwordValid(textInputLayout: TextInputLayout): Validation {
    return validation(textInputLayout) {
        rules {
            +notNull(R.string.txt_not_null)
            +notEmpty(R.string.txt_not_empty)
            +minimumLength(8, R.string.txt_not_min_length_8)
            +withPassword(PasswordRule.PasswordRegex.ALPHA_MIXED_CASE, R.string.txt_not_lowercase_and_uppercase)
        }
    }
}

fun confirmPasswordValid(textInputLayout: TextInputLayout, textInputEditText: TextInputEditText): Validation {
    return validation(textInputLayout) {
        rules {
            +notNull(R.string.txt_not_null)
            +notEmpty(R.string.txt_not_empty)
            +minimumLength(8, R.string.txt_not_min_length_8)
            +withPassword(PasswordRule.PasswordRegex.ALPHA_MIXED_CASE, R.string.txt_not_lowercase_and_uppercase)
            +equalTo(textInputEditText.text.toString(), R.string.txt_not_valid_confirm_password)
        }
    }
}

fun usernameValid(textInputLayout: TextInputLayout): Validation {
    return validation(textInputLayout) {
        rules {
            +notNull(R.string.txt_not_null)
            +notEmpty(R.string.txt_not_empty)
            +minimumLength(6, R.string.txt_not_min_length_6)
            +alphanumericOnly(R.string.txt_not_alphanumeric)
        }
    }
}

fun generalValid(textInputLayout: TextInputLayout): Validation {
    return validation(textInputLayout) {
        rules {
            +notNull(R.string.txt_not_null)
            +notEmpty(R.string.txt_not_empty)
        }
    }
}