cd ..;
./zip.sh;
cd test;
aws lambda update-function-code --function-name aais-dev-openidl-etl-idm-loader --zip-file fileb://../success-processor.zip --publish --profile nd