let crypto = require('crypto')

function hashString(text) {
    let shasum = crypto.createHash('sha1')
    shasum.update(text)
    return shasum.digest('hex')
};

console.log(hashString("VIN123456789ABCDE"))

