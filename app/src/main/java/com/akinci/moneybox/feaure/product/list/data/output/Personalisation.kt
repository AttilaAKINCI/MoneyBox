package com.akinci.moneybox.feaure.product.list.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Personalisation(
        val HideAccounts: HideAccounts,
        val QuickAddDeposit: QuickAddDeposit
)