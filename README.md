# openidl-etl

Holds ETL code for openidl

## ND HDS Loader

![HDS Loader](/images/HDS-loader.png)

## ND Registered VIN Loader

![Registered VIN Loader](/images/registered-vin-loader.png)

## ND Analytics Node

![Analytics Node](/images/analytics-node.png)

## openidl POC ETL Architecture

![Architecture](/images/architecture.png)

# Backlog

[The backlog can be found here](backlog.md)

# Input schema

-   can be found in openidl-etl-input-processor/schemas

# Developing

-   to use a new schema use jsonschema2pojo.org to convert from the jsonschema into pojos.

![json 2 pojo](/images/jsonschema2pojo.png)

-   put the pojos into the openidl-etl-staging-processor src/java directory

# setup aws recources

## ND setup intake s3 buckets and lambda

-   Create intake bucket
    -   use aws console to create bucket
    -   go to s3
    -   create new bucket
    -   bucket name is {{ org name }}-{{ env }}-openidl-etl-intake-bucket
    -   make sure it is in the same region as the rest of the node resources
    -   leave all other defaults
-   Create success bucket
    -   use aws console to create bucket
    -   go to s3
    -   create new bucket
    -   bucket name is {{ org name }}-{{ env }}-openidl-etl-success-bucket
    -   leave all other defaults
-   Create failure bucket
    -   use aws console to create bucket
    -   go to s3
    -   create new bucket
    -   bucket name is {{ org name }}-{{ env }}-openidl-etl-failure-bucket
    -   leave all other defaults
-   Create intake lambda
    -   use aws console to create the lambda
    -   go to lambda
    -   create function using a blueprint
    -   enter s3 in search
    -   use s3-get-object (for Node.js)
    -   choose configure
    -   for function name enter {{ org name }}-{{ env }}-openidl-etl-intake-processor
    -   for Execution role, choose Create a new role from AWS policy templates.
    -   for role name, enter openidl-etl-intake-role
    -   the role needs access to read the intake bucket and write to the success and failure buckets
    -   the role needs read, write and update for the dynamodb
    -   in s3 trigger, select the s3 bucket
    -   click create function
-   Create success lambda

-   Create control db
    -   go to dynamodb
    -   create table
    -   name is {{ org name }}-{{ env }}-openidl-etl-control-table
    -   key is `SubmissionFileName` of type string
-   Create email notification
    -   create the topics and subscriptions in SNS
    -   topic name - {{ org name }}-{{ env }}-openidl-etl-success and {{ org name }}-{{ env }}-openidl-etl-failure with 'ETL Success' and 'ETL Failure' as the nice name
    -   create subscription - select the topic arn, email and input the email address
-   Setup Secrets
    -   create secrets for:
        -   region
        -   intake bucket
        -   success bucket
        -   failure bucket
        -   state if not providing in records
-   Create config file at `config/config.json`
    -   use config-template.json

## setup queues

-   Create statplan input SQS Queue in AWS
    -   Name: 'openidl-{{ org }}-etl-statplan-input-queue'
    -   Type: `Standard`
    -   Visibility timeout: `2 minute`
    -   Message retention period: `2 minutes`
-   Create input SQS Queue in AWS
    -   Name: 'openidl-{{ org }}-etl-input-queue'
    -   Type: `Standard`
    -   Visibility timeout: `2 minute`
    -   Message retention period: `2 minutes`
-   Create staging SQS Queue in AWS
    -   Name: 'openidl-{{ org }}-etl-staging-queue'
    -   Type: `Standard`
    -   Visibility timeout: `2 minute`
    -   Message retention period: `2 minutes`
-   Create failure SQS Queue in AWS
    -   Name: 'openidl-{{ org }}-etl-failure-queue'
    -   Type: `Standard`
    -   Visibility timeout: `2 minute`
    -   Message retention period: `2 minutes`
-   Create success SQS Queue in AWS
    -   Name: 'openidl-{{ org }}-etl-success-queue'
    -   Type: `Standard`
    -   Visibility timeout: `2 minute`
    -   Message retention period: `2 minutes`

## setup dispatch processor

-   change into the openidl-etl-dispatch-processor directory

run `npm install

-   configure the dispatch processor

    -   create a file in the config directory called config.json from the config-template.json provided

-   build

    -   go back to the top folder
    -   `make build_dispatch_processor`

-   create dispatch processor lambda
    -   create from blueprint
    -   under blueprints search for `s3`
    -   in the results, select `s3-get-object`
    -   choose configure
    -   under basic information
        -   For **Function name** enter 'openidl-{{ org }}-etl-statplan-processor'
        -   For **Function role**, choose **Create a new role from AWS policy template**
        -   For **Role name**, enter 'openidl-{{ org }}-etl-statplan-processor-role'
    -   under **S3 trigger**, choose the s3 bucket previously created
    -   choose **Create function**
    -   Edit the Execution Role for the lambda to have access to SQS queue
        -   Go to lambda `Configuration` tab
        -   Go to lambda `Permission` section
        -   Click on Execution role to go to IAM section of AWS
        -   Click attach policies
            -   `AmazonS3FullAccess`
            -   `AmazonSQSFullAccess`
            -   Please note full access is only for demo purposes only. Limit the scope of the permission to that queue only
    -   Attach trigger to lambda
        -   Add a trigger and select the SQS success queue

## setup input processor

-   change into the openidl-etl-input-processor directory

run `npm install`

-   configure the input processor

    -   create a file in config directory called config.json from the config-template.json provided

-   build

    -   go back to top folder
    -   `make build_input_processor`

-   Create input processor lambda
    -   Create from scratch
    -   Runtime: `Node.js 14`
    -   Upload fat zip file (see openidl-etl-input-processor/README.md for details about building)
        -   `openidl-etl-input-processor/input-processor.zip`
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

## setup statplan processor

-   change into the openidl-etl-statplan-processor directory

run `npm install`

-   configure the statplan processor

    -   create a file in config directory called config.json from the config-template.json provided

-   build

    -   go back to top folder
    -   `make build_statplan_processor`

-   Create statplan processor lambda
    -   Create from scratch
    -   Runtime: `Node.js 14`
    -   Upload fat zip file (see openidl-etl-input-processor/README.md for details about building)
        -   `openidl-etl-statplan-processor/statplan-processor.zip`
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
    -   name: openidl-{{ org }}-etl-staging-processor
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
    -   config files may be available in secrets manager at openidl/etl/{{org}}/{{env}}/input | staging | success | failure
-   change into the openidl-etl-tester directory
-   create sample records
    -   use `openidl-tester/test/samplemessages.json` as a template
-   configure aws
    -   `export AWS_PROFILE=<profile entry>`
    -   `export AWS_REGION=<region>`
-   send test data
    -   run `node sqs-sendmessages.js`
-   check the logs of the respective components
-   view the results in MongoDB

    -   setup port forward
        `aws --profile uat-role eks update-kubeconfig --region us-east-1 --name caru-dev-app-cluster`
        `kubectl port-forward -n database svc/{{ org }}-mongodb-headless 28017:27017`
    -   open mongo compass and enter the connection
        `mongodb://{{ mongo user from secrets manager }}:{{ mongo user token from secrets manager }}@localhost:{{ port forward from above }}/openidl-offchain-db?authSource=openidl-offchain-db`

-   view the records added
-   check the lambda logs to see activity in the processors
-   check the s3 bucket for new objects there
