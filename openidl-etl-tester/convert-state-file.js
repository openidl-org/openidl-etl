const states = require('../openidl-etl-statplan-processor/reference/states.json')
const fs = require('fs')

let newStates = { "abbreviations": states.abbreviations }

newStates.codes = {}
for (abb in states.abbreviations) {
    let code = states.abbreviations[abb]
    newStates.codes[code] = abb
}
console.log(newStates)

fs.writeFileSync('states.json', JSON.stringify(newStates), 'utf-8')
