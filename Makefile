build_statplan_processor:
	cd openidl-etl-statplan-processor; zip -r statplan-processor.zip . -x "statplan-processor.zip"; cd ..

build_input_processor:
	cd openidl-etl-input-processor; zip -r input-processor.zip . -x "input-processor.zip"; cd ..

build_staging_processor:
	cd openidl-etl-staging-processor; mvn package; cd ..

build_success_processor:
	cd openidl-etl-success-processor; zip -r success-processor.zip . -x "success-processor.zip"; cd ..

build_failure_processor:
	cd openidl-etl-failure-processor; zip -r failure-processor.zip .; -x "failure-processor.zip"; cd ..

build_intake_processor:
	cd openidl-etl-intake-processor; zip -r intake-processor.zip .; -x "intake-processor.zip"; cd ..