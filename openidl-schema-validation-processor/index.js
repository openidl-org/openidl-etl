const Validator = require("jsonschema").Validator;
var v = new Validator();
var instance = require("./examples/personalAutoMessage1.json");
var schema = require("./schemas/personalAutoPolicyMessage.json");
console.log(v.validate(instance, schema));
