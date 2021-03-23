package com.akinci.moneybox.jsonresponses

class GetApiErrorResponse {
    companion object {
        val apiErrorResponse = """
            {
                "Message":"Parse Error",
                "Name":"Dummy Name",
                "ValidationErrors":[]
            }
        """
    }
}