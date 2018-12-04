
var MongoClient = require('mongodb').MongoClient;
var url = 'mongodb://niteesha:niteesha1@ds237735.mlab.com:37735/icp9';

console.log(process.env.MONGO_ATLAS_PW);
MongoClient.connect(url, function(err, db) {

    if (err) throw err;
    var dbo = db.db("icp9");
    var myobj = {
        classid: "1",
        Sname:"Niteesha",
        course:"MS",
        major:"cs",
        minor:"ece"

    };
    dbo.collection("Users").insertOne(myobj, function(err, res) {
        if (err) throw err;
        console.log("1 document inserted");
        db.close();
    });
});
