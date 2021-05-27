package com.target.targetcasestudy

import com.target.targetcasestudy.data.validateCreditCard
import org.junit.Assert
import org.junit.Test

/**
 * Feel free to make modifications to these unit tests! Remember, you have full technical control
 * over the project, so you can use any libraries and testing strategies that see fit.
 */
class CreditCardValidatorTest {
  @Test
  fun `is credit card number valid`() {
    val creditCards = listOf<CharSequence>(
      //Default
      "4539976741512043",
      //VISA
      "4532310470256696", "4556484233027834", "4916672479562688966",
      //Discover
      "6011375043125467", "6011277136279815", "6011996548920865818",
      //Diners Club - Carte Blanche
      "30439927831861", "30143071477824", "30281962258448",
      //Visa Electron
      "4913060860353103", "4917520055482153", "4508525100558696",
      //MasterCard
      "2221001603161222", "2720994180791591", "5528832344046923",
      //JCB
      "3534158148476661", "3530072745215618", "3532725405527805398"
    )

    creditCards.forEach { creditCard ->
      Assert.assertTrue(
        "Credit card $creditCard failed validation",
        validateCreditCard(creditCard)
      )
    }
  }
}
