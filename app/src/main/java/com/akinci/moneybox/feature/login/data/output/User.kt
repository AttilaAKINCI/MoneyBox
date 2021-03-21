package com.akinci.moneybox.feature.login.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val AmlAttempts: Int,
    val AmlStatus: String,
    val Animal: String,
    val CanReinstateMandate: Boolean,
    val Cohort: Int,
    val DateCreated: String,
    val DirectDebitHasBeenSubmitted: Boolean,
    val DirectDebitMandateStatus: String,
    val DoubleRoundUps: Boolean,
    val Email: String,
    val EmailTwoFactorEnabled: Boolean,
    val FirstName: String,
    val HasCompletedTutorial: Boolean,
    val HasVerifiedEmail: Boolean,
    val IntercomHmac: String,
    val IntercomHmacAndroid: String,
    val IntercomHmaciOS: String,
    val InvestmentTotal: Double,
    val InvestorProduct: String,
    val IsPinSet: Boolean,
    val JisaRegistrationStatus: String,
    val JisaRoundUpMode: String,
    val LastName: String,
    val LastPayment: Double,
    val MobileNumber: String,
    val MoneyboxAmount: Double,
    val MoneyboxRegistrationStatus: String,
    val MonthlyBoostAmount: Double,
    val MonthlyBoostDay: Int,
    val MonthlyBoostEnabled: Boolean,
    val PreviousMoneyboxAmount: Double,
    val ReferralCode: String,
    val RegistrationStatus: String,
    val RestrictedDevice: Boolean,
    val RoundUpMode: String,
    val RoundUpWholePounds: Boolean,
    val UserId: String
)