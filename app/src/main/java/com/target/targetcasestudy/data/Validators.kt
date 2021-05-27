package com.target.targetcasestudy.data

import android.util.Log
import java.lang.StringBuilder

/**
 * For an explanation of how to validate credit card numbers read:
 *
 * https://www.freeformatter.com/credit-card-number-generator-validator.html#fakeNumbers
 *
 * This contains a breakdown of how this algorithm should work as
 * well as a way to generate fake credit card numbers for testing
 *
 * The structure and signature of this is open to modification, however
 * it *must* include a method, field, etc that returns a [Boolean]
 * indicating if the input is valid or not
 *
 * Additional notes:
 *  * This method does not need to validate the credit card issuer
 *  * This method must validate card number length (13 - 19 digits), but does not
 *    need to validate the length based on the issuer.
 *
 * @param creditCardNumber - credit card number of (13, 19) digits
 * @return true if a credit card number is believed to be valid,
 * otherwise false
 */
fun validateCreditCard(creditCardNumber: CharSequence): Boolean {
  if(creditCardNumber.length in 13..19) {
    //Drop the last digit and reverse the string
    val subCreditCardNumber = creditCardNumber.subSequence(0, creditCardNumber.length - 1).reversed()

    var sum = 0
    for(i in subCreditCardNumber.indices) {
      sum += getDigit(subCreditCardNumber, i)
    }

    val lastDigit = Character.getNumericValue(creditCardNumber.last())
    return (sum + lastDigit) % 10 == 0
  }
  return false
}

fun getDigit(sequence: CharSequence, index: Int): Int {
  val digit = Character.getNumericValue(sequence[index])
  return if(index % 2 == 0) digit * 2 - if(digit * 2 > 9) 9 else 0 else digit
}


