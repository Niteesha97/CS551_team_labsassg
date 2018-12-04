var MongoClient = require('mongodb').MongoClient;
var assert = require('assert');
var url = 'mongodb://niteesha:niteesha1@ds237735.mlab.com:37735/icp9';
MongoClient.connect(url, function(err, db) {
    assert.equal(null, err);
    console.log("Connected correctly to server.");
    db.close();
});
