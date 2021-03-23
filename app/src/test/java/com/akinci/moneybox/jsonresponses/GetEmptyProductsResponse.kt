package com.akinci.moneybox.jsonresponses

class GetEmptyProductsResponse {
    companion object{
        val productsResponse = """
            {
                "MoneyboxEndOfTaxYear": "2021-03-31T12:00:00.000",
                "TotalPlanValue": 38898.420000,
                "TotalEarnings": 2841.510000,
                "TotalContributionsNet": 36120.85,
                "TotalEarningsAsPercentage": 7.87,
                "ProductResponses": [ ],
                "Accounts": [
                    {
                        "Type": "Isa",
                        "Name": "Stocks & Shares ISA",
                        "Wrapper": {
                            "Id": "24a41343-86b4-4d93-8765-82236af7c56d",
                            "TotalValue": 17431.420000,
                            "TotalContributions": 15705.00,
                            "EarningsNet": 1726.420000,
                            "EarningsAsPercentage": 10.99
                        }
                    },
                    {
                        "Type": "Lisa",
                        "Name": "Lifetime ISA",
                        "Wrapper": {
                            "Id": "690687b7-3dcd-42b4-af13-ec62f4d151a1",
                            "TotalValue": 2895.810000,
                            "TotalContributions": 4295.00,
                            "EarningsNet": 23.900000,
                            "EarningsAsPercentage": -1.39
                        }
                    },
                    {
                        "Type": "Gia",
                        "Name": "General Investment Account",
                        "Wrapper": {
                            "Id": "9443dbbf-96db-4114-9966-517e2054af32",
                            "TotalValue": 18571.19,
                            "TotalContributions": 17480.00,
                            "EarningsNet": 1091.19,
                            "EarningsAsPercentage": 6.24
                        }
                    }
                ]
            }          
        """
    }
}