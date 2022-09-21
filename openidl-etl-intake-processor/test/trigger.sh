aws s3 rm s3://aais-dev-openidl-etl-intake-bucket/nd-sample.csv --profile nd
aws s3 rm s3://aais-dev-openidl-etl-intake-bucket/nd-sample2.csv --profile nd
aws s3 cp ./nd-sample.csv s3://aais-dev-openidl-etl-intake-bucket/nd-sample2.csv --profile nd