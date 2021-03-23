package com.akinci.moneybox.jsonresponses

class GetLoginResponse {
    companion object{
        val loginResponse = """
        { 
            "User": {
                "UserId": "947002e4-4b6a-43ff-9f3d-0d939ef2dbaf",
                "HasVerifiedEmail": true,
                "IsPinSet": true,
                "AmlStatus": "Clear",
                "AmlAttempts": 1,
                "RoundUpMode": "Off",
                "JisaRoundUpMode": "Off",
                "InvestorProduct": "",
                "RegistrationStatus": "IsComplete",
                "JisaRegistrationStatus": "None",
                "DirectDebitMandateStatus": "Active",
                "DateCreated": "2020-04-08T12:24:09.613",
                "Animal": "Fox",
                "ReferralCode": "LPUL98",
                "IntercomHmac": "65daba4358d29dde9c7a453fc75fd323947e4f38fae8da2bc84204ec953a2293",
                "IntercomHmaciOS": "65daba4358d29dde9c7a453fc75fd323947e4f38fae8da2bc84204ec953a2293",
                "IntercomHmacAndroid": "166bf73be7a21bfde873dc01aec82c74575aead20077eefd6f7d55db8ef8a720",
                "HasCompletedTutorial": false,
                "LastPayment": 0.0,
                "PreviousMoneyboxAmount": 0.00,
                "MoneyboxRegistrationStatus": "IsComplete",
                "Email": "jaeren+androidtest@moneyboxapp.com",
                "FirstName": "Jaeren",
                "LastName": "Coathup",
                "MobileNumber": "07812271271",
                "RoundUpWholePounds": true,
                "DoubleRoundUps": false,
                "MoneyboxAmount": 0.00,
                "InvestmentTotal": 0.0,
                "CanReinstateMandate": false,
                "DirectDebitHasBeenSubmitted": true,
                "MonthlyBoostEnabled": false,
                "MonthlyBoostAmount": 0.00,
                "MonthlyBoostDay": 1,
                "RestrictedDevice": false,
                "EmailTwoFactorEnabled": false,
                "Cohort": 29
            },
            "Session": {
                "BearerToken": "AD2rGWqdov3/5+tznfTGgLactbYwn2AiWFNsszomMZ8=",
                "ExternalSessionId": "3fda0007-78f3-4bc7-8265-6617a7c7b575",
                "SessionExternalId": "3fda0007-78f3-4bc7-8265-6617a7c7b575",
                "ExpiryInSeconds": 0
            },
            "ActionMessage": {
                "Type": "Deposit",
                "Message": "Feeling hungry? Why not add the cost of a lunch to your Moneybox?",
                "Actions": [
                    {
                        "Label": "Add £5",
                        "Amount": 5.0,
                        "Type": "Deposit",
                        "Animation": "Shock"
                    }
                ]
            },
            "InformationMessage": "3 days until collection"
        }
        """
    }
}