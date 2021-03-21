package com.akinci.moneybox.jsonresponses

class GetProductsResponse {
    companion object{
        val productsResponse = """
            {
                "MoneyboxEndOfTaxYear": "2021-03-31T12:00:00.000",
                "TotalPlanValue": 38898.420000,
                "TotalEarnings": 2841.510000,
                "TotalContributionsNet": 36120.85,
                "TotalEarningsAsPercentage": 7.87,
                "ProductResponses": [
                    {
                        "Id": 6137,
                        "PlanValue": 17431.42,
                        "Moneybox": 0.00,
                        "SubscriptionAmount": 40.00,
                        "TotalFees": 32.85,
                        "IsSelected": true,
                        "IsFavourite": false,
                        "CollectionDayMessage": "3 days until collection",
                        "WrapperId": "24a41343-86b4-4d93-8765-82236af7c56d",
                        "IsCashBox": false,
                        "AssetBox": {
                            "Title": "Investments"
                        },
                        "Product": {
                            "Id": 1,
                            "Name": "ISA",
                            "CategoryType": "Investment",
                            "Type": "Isa",
                            "FriendlyName": "Stocks & Shares ISA",
                            "CanWithdraw": true,
                            "ProductHexCode": "#c5d561",
                            "AnnualLimit": 20000.00,
                            "DepositLimit": 0.00,
                            "BonusMultiplier": 0.00,
                            "MinimumWeeklyDeposit": 2.0,
                            "MaximumWeeklyDeposit": 400.0,
                            "Documents": {
                                "KeyFeaturesUrl": "https://api-test02.moneyboxapp.com/docs/KeyFeaturesDocumentAll"
                            },
                            "State": "None"
                        },
                        "InvestorAccount": {
                            "ContributionsNet": 15705.00,
                            "EarningsNet": 1726.42,
                            "EarningsAsPercentage": 10.99
                        },
                        "Personalisation": {
                            "QuickAddDeposit": {
                                "Amount": 10.00
                            },
                            "HideAccounts": {
                                "Enabled": false,
                                "IsHidden": false,
                                "Sequence": 1
                            }
                        },
                        "Contributions": {
                            "Status": "Enabled"
                        },
                        "MoneyboxCircle": {
                            "State": "None"
                        },
                        "State": "None"
                    },
                    {
                        "Id": 6136,
                        "PlanValue": 2895.810000,
                        "Moneybox": 0.00,
                        "SubscriptionAmount": 25.00,
                        "TotalFees": 0.00,
                        "IsSelected": false,
                        "IsFavourite": false,
                        "CollectionDayMessage": "3 days until collection",
                        "WrapperId": "690687b7-3dcd-42b4-af13-ec62f4d151a1",
                        "IsCashBox": false,
                        "AssetBox": {
                            "Title": "Cash"
                        },
                        "Product": {
                            "Lisa": {
                                "MaximumBonus": 1000.0000
                            },
                            "Id": 7,
                            "Name": "Lisa Plus",
                            "CategoryType": "Cash",
                            "Type": "Lisa",
                            "FriendlyName": "Lifetime ISA",
                            "CanWithdraw": false,
                            "ProductHexCode": "#49bfbd",
                            "AnnualLimit": 4000.00,
                            "DepositLimit": 0.00,
                            "BonusMultiplier": 0.25,
                            "MinimumWeeklyDeposit": 2.0,
                            "MaximumWeeklyDeposit": 400.0,
                            "InterestRate": "1% AER",
                            "InterestRateAmount": 0.010000,
                            "LogoUrl": "https://assets.moneyboxapp.com/images/app-assets/InvestecIconRebrand.png",
                            "Documents": {
                                "KeyFeaturesUrl": "https://api-test02.moneyboxapp.com/docs/KeyFeaturesDocumentLISACashInvestec"
                            },
                            "State": "None",
                            "Fund": {
                                "FundId": 32,
                                "Name": "Investec",
                                "LogoUrl": "https://assets.moneyboxapp.com/images/app-assets/InvestecIconRebrand.png",
                                "IsFundDMB": false
                            }
                        },
                        "InvestorAccount": {
                            "TodaysInterest": 0.0441,
                            "ContributionsNet": 2935.85,
                            "EarningsNet": 23.900000,
                            "EarningsAsPercentage": -1.39
                        },
                        "Personalisation": {
                            "QuickAddDeposit": {
                                "Amount": 10.00
                            },
                            "HideAccounts": {
                                "Enabled": false,
                                "IsHidden": false,
                                "Sequence": 2
                            }
                        },
                        "Contributions": {
                            "Status": "Enabled"
                        },
                        "MoneyboxCircle": {
                            "State": "None"
                        },
                        "State": "None"
                    },
                    {
                        "Id": 6135,
                        "PlanValue": 18571.19,
                        "Moneybox": 10.00,
                        "SubscriptionAmount": 10.00,
                        "TotalFees": 19.04,
                        "IsSelected": false,
                        "IsFavourite": true,
                        "CollectionDayMessage": "3 days until collection",
                        "WrapperId": "9443dbbf-96db-4114-9966-517e2054af32",
                        "IsCashBox": false,
                        "AssetBox": {
                            "Title": "Investments"
                        },
                        "Product": {
                            "Id": 2,
                            "Name": "GIA",
                            "CategoryType": "Investment",
                            "Type": "Gia",
                            "FriendlyName": "General Investment Account",
                            "CanWithdraw": true,
                            "ProductHexCode": "#546270",
                            "AnnualLimit": 0.00,
                            "DepositLimit": 20000.00,
                            "BonusMultiplier": 0.00,
                            "MinimumWeeklyDeposit": 2.0,
                            "MaximumWeeklyDeposit": 400.0,
                            "Documents": {
                                "KeyFeaturesUrl": "https://api-test02.moneyboxapp.com/docs/KeyFeaturesDocumentAll"
                            },
                            "State": "None"
                        },
                        "InvestorAccount": {
                            "ContributionsNet": 17480.00,
                            "EarningsNet": 1091.19,
                            "EarningsAsPercentage": 6.24
                        },
                        "Personalisation": {
                            "QuickAddDeposit": {
                                "Amount": 10.00
                            },
                            "HideAccounts": {
                                "Enabled": false,
                                "IsHidden": false,
                                "Sequence": 3
                            }
                        },
                        "Contributions": {
                            "Status": "Enabled"
                        },
                        "MoneyboxCircle": {
                            "State": "None"
                        },
                        "State": "None"
                    }
                ],
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