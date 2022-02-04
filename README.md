# openidl-etl

Holds ETL code for openidl

# setup aws recources

## setup queues

-   Create staging SQS Queue in AWS
    -   Name: 'openidl-<org>-etl-staging-queue'
    -   Type: `Standard`
    -   Visibility timeout: `2 minute`
    -   Message retention period: `2 minutes`
-   Create failure SQS Queue in AWS
    -   Name: 'openidl-<org>-etl-failure-queue'
    -   Type: `Standard`
    -   Visibility timeout: `2 minute`
    -   Message retention period: `2 minutes`
-   Create success SQS Queue in AWS
    -   Name: 'openidl-<org>-etl-success-queue'
    -   Type: `Standard`
    -   Visibility timeout: `2 minute`
    -   Message retention period: `2 minutes`

## setup staging processor

-   configure
    -   create a config.properties file in src/recources
    -   put in properties like:

```
SUCCESS_QUEUE_URL = "https://sqs.<region>.amazonaws.com/<account number>/<queue name>";
FAILURE_QUEUE_URL = "https://sqs.<region>.amazonaws.com/<account number>/<queue name>";
```

-   build

    -   `make build_staging_processor`

-   Create staging processor lambda
    -   Create from scratch
    -   name: openidl-<org name>-etl-staging-processor
    -   Runtime: `Java 11 (Corretto)`
    -   Upload fat jar (see openidl-etl-staging-processor/README.md for details about building)
        -   `openidl-etl-staging-processor/target/openidl-etl-staging-processor-0.0.1.jar`
    -   Handler
        `org.openidl.etl.processors.AWSSQSProcessor::handleRequest`
    -   Edit configuration
        -   Memory: `1024 MB`
        -   Timeout: `2 min`
    -   Edit the Execution Role for the lambda to have access to SQS queue
        -   Go to lambda `Configuration` tab
        -   Go to lambda `Permission` section
        -   Click on Execution role to go to IAM section of AWS
        -   Click attach policies
            -   `AmazonSQSFullAccess`
            -   Please note full access is only for demo purposes only. Limit the scope of the permission to that queue only
    -   Attach trigger to lambda
        -   Add a trigger and select the SQS staging queue

## setup success processor

-   change into the openidl-etl-success-processor directory

run `npm install`

-   configure the success processor
    -   create a file in config directory called config.json

```json
{
    "username": "email address of user setup for IDM",
    "password": "password for user setup for IDM",
    "batchId": "1",
    "chunkId": "1",
    "sourceId": "1",
    "carrierId": "<org id>",
    "carrier": {
        "utilities": {
            "url": "utilities-service.dev.<sub domain>.<domain>"
        },
        "insurance-data-manager": {
            "url": "insurance-data-manager-service.dev.<sub domain>.<domain>"
        }
    }
}
```

-   build

    -   `make build_success_processor`

-   Create success processor lambda
    -   Create from scratch
    -   Runtime: `Node.js 14`
    -   Upload fat zip file (see openidl-etl-success-processor/README.md for details about building)
        -   `openidl-etl-success-processor/function.zip`
    -   Handler
        `index.js`
    -   Edit configuration
        -   Memory: `128 MB`
        -   Timeout: `2 min`
    -   Edit the Execution Role for the lambda to have access to SQS queue
        -   Go to lambda `Configuration` tab
        -   Go to lambda `Permission` section
        -   Click on Execution role to go to IAM section of AWS
        -   Click attach policies
            -   `AmazonSQSFullAccess`
            -   Please note full access is only for demo purposes only. Limit the scope of the permission to that queue only
    -   Attach trigger to lambda
        -   Add a trigger and select the SQS success queue

## setup failure processor

-   change into the openidl-etl-failure-processor directory

run `npm install`

-   configure the failure processor
    -   create a config.json file in the config folder

```json
{
    "s3BucketName": "openidl-<org name>-etl-failure-bucket"
}
```

-   build

    -   `make build_failure_processor`

-   Create the bucket configured above

    -   make sure it is private
    -   use the name specified above

-   Create failure processor lambda

    -   Create from scratch
    -   Name: 'openidl-etl-failure-processor'
    -   Runtime: `Node.js 14`
    -   Upload fat zip file (see openidl-etl-failure-processor/README.md for details about building)
        -   `openidl-etl-failure-processor\function.zip`
    -   Handler
        `index.js`
    -   Edit configuration
        -   Memory: `128 MB`
        -   Timeout: `2 min`
    -   Edit the Execution Role for the lambda to have access to SQS queue
        -   Go to lambda `Configuration` tab
        -   Go to lambda `Permission` section
        -   Click on Execution role to go to IAM section of AWS
        -   Click attach policies
            -   `AmazonSQSFullAccess`
            -   `AmazonS3FullAccess`
            -   Please note full access is only for demo purposes only. Limit the scope of the permission to that queue only
    -   Attach trigger to lambda
        -   Add a trigger and select the SQS failure queue

## test

-   configure
    -   copy the template config file and update the url
-   change into the openidl-etl-tester directory
-   configure aws
    -   `export AWS_PROFILE=<profile entry>`
    -   `export AWS_REGION=<region>`
-   send test data
    -   run `node sqs-sendmessages.js`
-   check the logs of the respective components
