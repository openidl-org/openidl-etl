const putIntoS3 = require('s3-utility').putIntoS3

exports.handler = async function (event, context) {
    let records = []
    let key = new Date().toISOString()
    event.Records.forEach(record => {
        console.log("We have a new record");
        const { body } = record;
        records.push(JSON.parse(body))
        console.log(`ETL ID: ${JSON.parse(body).EtlID}`)
        console.log(body)
    });
    console.log("Calling procesds records")
    await putIntoS3(key, records)
    return {}
}
