const processRecords = require('./processor').process

exports.handler = async function (event, context) {
    let recordsToLoad = []
    event.Records.forEach(recordToLoad => {
        console.log("We have a new record");
        const { body } = record;
        recordsToLoad.push(JSON.parse(body))
        console.log(`ETL ID: ${JSON.parse(body).EtlID}`)
        console.log(body)
    });
    await processRecords(recordsToLoad)
    return {}
}