# openIDL Validation Processor

## Description:

This projects allows running of drools based excel rules to validate records.

## Getting Start4ed

-   Install JDK 11
-   Install Maven 3.8.4

## Getting Started - Visual Studio Code

-   open the project in visual studio code
-   navigate to the java/org/openidl/processor directory
-   right click on AWSSQSProcessor.java
-   select 'run java'

## Getting started - command line

-   run `mvn package`
-   will generate a fat jar
    -   `target\aais-rules-runner-0.0.1.jar`
-   change to the `target` directory
    -   `cd target`
-   run the main file
    -   `java -cp openidl-validation-processor-0.0.1.jar org.openidl.processors.AWSSQSProcessor`

## Deploy to AWS Lambda

-   Create staging SQS Queue in AWS
    -   Name: 'openidl-<org>-staging-queue'
    -   Type: `Standard`
    -   Visibility timeout: `2 minute`
    -   Message retention period: `2 minutes`
-   Create failure SQS Queue in AWS
    -   Name: 'openidl-<org>-failure-queue'
    -   Type: `Standard`
    -   Visibility timeout: `2 minute`
    -   Message retention period: `2 minutes`
-   Create success SQS Queue in AWS
    -   Name: 'openidl-<org>-success-queue'
    -   Type: `Standard`
    -   Visibility timeout: `2 minute`
    -   Message retention period: `2 minutes`
-   Create a lambda
    -   Create from scratch
    -   Runtime: `Java 11 (Corretto)`
    -   Upload fat jar
        -   `target\openidl-validation-processor-0.0.1.jar`
    -   Handler
        `org.openidl.etl.processors.AWSSQSProcessor::handleRequest`
    -   Edit configuration
        -   Memory: `512 MB`
        -   Timeout: `2 min`
-   Edit the Execution Role for the lambda to have access to SQS queue
    -   Go to lambda `Configuration` tab
    -   Go to lambda `Permission` section
    -   Click on Execution role to go to IAM section of AWS
    -   Click attach policies
        -   `AmazonSQSFullAccess`
        -   Please note full access is only for demo purposes only. Limit the scope of the permission to that queue only
-   Attach trigger to lambda
    -   Add a trigger and select the SQS queue you created earlier

## Send a message to the SQS queue

-   Go to the SQS queue that you created

    -   Click `Send and receive messages`
    -   Enter in the message body the following

    ```js
    {
      "$schema": "./au-schema.json",
      "ID": "Guid-123456789",
      "EtlID": "loadId123",
      "LineOfInsurance": "56",
      "Coverage": "4",
      "StateCode": "36",
      "AccountingDate": {
        "Month": "String",
        "Year": 2020
      },
      "CompanyCode": "1234",
      "Territory": "01",
      "OptionalZipCodeIndicator": "String",
      "TransactionCode": "Ref",
      "PremiumLossAmounts": {
        "Premium": "Number",
        "Loss": "Number"
      },
      "Program": "3",
      "Subline": "1",
      "OperateAge": "INT",
      "SexMaterialStatus": "Ref",
      "VehicleUse": "Ref",
      "VehicleProformance": "Ref",
      "PrivatePassengerDriversTraining": "Ref",
      "CommercialAutomobileClassification": "Ref",
      "LiabilityLimitsAmount": "Ref",
      "LiabilityLimitsAmountNJ": "Ref",
      "LiabilityLimitsAmountPA": "Ref",
      "DeductibleAmount": "Ref",
      "VehicleClassBodyStyle": "Ref",
      "VehicleClassBodySize": "Ref",
      "ModelYear": "INT",
      "UnisuredUnderinsuredMotorist": "Ref",
      "Exposure": "Ref",
      "ClaimCount": "?INT?",
      "MonthsCovered": "Ref",
      "CasueOfLoss": "Ref",
      "TerrorismIndicator": "Ref",
      "SinlgeMultiCar": "Ref",
      "AccidentDate": {
        "Month": "String",
        "Year": "INT"
      },
      "PackageCode": "Ref",
      "PoolAffiliation": "Ref",
      "OccurenceOrPolicyIdentification": {
        "PolicyNumber": "Ref",
        "ClaimNumber": "Ref",
        "ClaimIdentifier": "Ref"
      },
      "NCProgramEnhancemntIndicator": "Ref",
      "ZipCode": "?String?",
      "ZipeCodeSuffix(Optional)": "?String",
      "UM/UIMStackingIndicator": "Ref",
      "SymbolCode": "Ref",
      "PassiveRestraintDiscountCode": "Ref",
      "Anti-LockBrakesDiscountCode": "Ref",
      "Anti-TheftDeviceDiscountCode": "Ref",
      "DefensiveDriverDiscountCode": "Ref",
      "PIPLimitsDeductibleCode": "Ref",
      "NewJerseyPIPLimit": "Ref",
      "NewJerseyDeductible": "Ref",
      "RatingTerminalZoneCode": "Ref",
      "ClassificationCodesAssignedRisks(PrivatePassengerNon-FleetOnly)RateClassCode": "Ref",
      "ClassificationCodesAssignedRisks(PrivatePassengerNon-FleetOnly)PenaltyPoints/PercentOfSurchargeCode": "Ref",
      "ClassificationCodesAssignedRisks(PrivatePassengerMotorcycles,MotorScooters,Etc.LiabilityRecords)Class": "Ref",
      "ClassificationCodesAssignedRisks(PrivatePassengerMotorcycles,MotorScooters,Etc.LiabilityRecords)MiscellaneousSurchargeCode": "Ref",
      "ClassificationCodesAssignedRisks(PrivatePassengerMotorcycles,MotorScooters,Etc.PhysicalDamageRecords)Class": "Ref",
      "ClassificationCodesAssignedRisks(ExcessIndemnityPolicies(PrivatePassenger-NewYorkOnly(31)))": "Ref",
      "ClassificationCodesAssignedRisks(CommercialTrucks,Trucktractors,AndTrailersLiabilityRecords)TypeOfVehicle/BusinessUse/RadiusOfOperations": "Ref",
      "ClassificationCodesAssignedRisks(CommercialTrucks,Trucktractors,AndTrailersLiabilityRecords)SecondaryClassificationCode": "Ref",
      "ClassificationCodesAssignedRisks(CommercialTrucks,Trucktractors,AndTrailersPhysicalDamageRecords)Class": "Ref",
      "ClassificationCodesAssignedRisks(CommercialTrucks,Trucktractors,AndTrailersPhysicalDamageRecords)CommercialSurchargeCode": "Ref",
      "ClassificationCodesAssignedRisks(PublicsLiabilityRecords)Classification": "Ref",
      "ClassificationCodesAssignedRisks(PublicsLiabilityRecords)SeatingCapacityCode": "Ref",
      "ClassificationCodesAssignedRisks(PublicsLiabilityRecords)CommeericialSurchargeCode": "Ref",
      "ClassificationCodesAssignedRisks(PublicsPhysicalDamageRecords)Classification": "Ref",
      "ClassificationCodesAssignedRisks(PublicsPhysicalDamageRecords)CommercialSurchargeCode": "Ref",
      "ClassificationCodesAssignedRisks(GaragesLiability)Class": "Ref",
      "ClassificationCodesAssignedRisks(GaragesLiability)CommercialSurchargeCode": "Ref",
      "ClassificationCodesAssignedRisks(PrivatePassengerTypes)Class": "Ref",
      "ClassificationCodesAssignedRisks(PrivatePassengerTypes)CommercialSurchargeCode": "Ref",
      "ClassificationCodesAssignedRisks(TransportationOfMigrantWorkers)Class": "Ref",
      "ClassificationCodesAssignedRisks(TransportationOfMigrantWorkers)CommercialSurchargeCode": "Ref",
      "ClassificationCodesAssignedRisks(Non-OwnedAutomobiles)Class": "Ref",
      "ClassificationCodesAssignedRisks(Non-OwnedAutomobiles)CommercialSurchargeCode": "Ref",
      "ClassificationCodesAssignedRisks(CompositeRatingPlan)Class": "Ref",
      "ClassificationCodesAssignedRisks(CompositeRatingPlan)CommercialSurchargeCode": "Ref",
      "ClassificationCodesAssignedRisks(ExcessIndemnityPolicies:OtherThanPrivatePassengerNon-Fleet-NewYorkOnly)Class": "Ref",
      "ClassificationCodesAssignedRisks(ExcessIndemnityPolicies:OtherThanPrivatePassengerNon-Fleet-NewYorkOnly)CommercialSurchargeCode": "Ref",
      "ClassificationCodesAssignedRisks(AllOtherClassifications)Class": "Ref",
      "ClassificationCodesAssignedRisks(AllOtherClassification)CommercialSurchargeCode": "Ref",
      "ExperienceRatingModificationFactor": "Ref",
      "LimitedCode-LossTransactionCode": "Ref",
      "ExceptionCodeA(TortLimitation)(Kentucky)": "Ref",
      "ExceptionCodeA(Intrastate/InterstateCode)(Michigan)": "Ref",
      "ExceptionCodeA(Threshold/TortLimitation)(NewJersey)": "Ref",
      "ExceptionCodeA(AccidentPreventionCredit)(NewYork)": "Ref",
      "ExceptionCodeA(TortLimitation)(Pennesylvania)": "Ref",
      "ExceptionCodeA(S.C.ReinsuranceFacility(S.C.R.F))(SouthCarolina)": "Ref",
      "ExceptionCodeB(Multi-CarRisks)(NewJersey)": "Ref",
      "ExceptionCodeB(PrimaryNo-FaultHealthPlan)(NewYork)": "Ref",
      "ExceptionCodeB(55&Overdiscount)(Pennsylvania)": "Ref",
      "ExceptionCodeC(EngineSize)(NewJersey)": "Ref",
      "ExceptionCodeC(CombinedFirstPartyBenefits(IndivisablePremiumPolicies))": "Ref",
      "ExceptionCodeD(NewYorkTaxicabs&PublicLivery)(NewYork)": "Ref",
      "CompanyUse": "Ref",
      "VehicleIdentification": "Ref"
    }

    ```

    -   click `send message`

## Check Lambda Logs

-   Go to lambda
-   Go to `Monitor` tab
-   Click `View logs in CloudWatch`
-   Click on the log stream that that shows up
