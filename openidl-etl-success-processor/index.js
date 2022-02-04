const processRecords = require('./load-insurance-data').processRecords

exports.handler = async function (event, context) {
    let records = []
    event.Records.forEach(record => {
        console.log("We have a new record");
        const { body } = record;
        records.push(JSON.parse(body))
        console.log(`ETL ID: ${JSON.parse(body).EtlID}`)
        console.log(body)
    });
    console.log("Calling procesds records")
    await processRecords(records)
    return {}
}