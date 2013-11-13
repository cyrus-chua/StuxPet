cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
    	"file": "plugins/org.apache.cordova.device-motion/www/Acceleration.js",
    	"id": "org.apache.cordova.device-motion.Acceleration",
    	"clobbers": [
    	    "Acceleration"
    	]
    },
    {
    	"file": "plugins/org.apache.cordova.device-motion/www/accelerometer.js",
    	"id": "org.apache.cordova.device-motion.accelerometer",
    	"clobbers": [
    	    "navigator.accelerometer"
    	]
    },
    {
    	"file": "plugins/org.apache.cordova.splashscreen/www/splashscreen.js",
        "id": "org.apache.cordova.splashscreen.SplashScreen",
        "clobbers": [
            "navigator.splashscreen"
         ]
    },
    {
        "file": "plugins/com.chariotsolutions.nfc.plugin/www/phonegap-nfc.js",
        "id": "com.chariotsolutions.nfc.plugin.NFC",
        "runs": true
    },
    {
        "file": "plugins/com.phonegap.plugins.sqlite/www/SQLitePlugin.js",
        "id": "com.phonegap.plugins.sqlite.SQLitePlugin",
        "clobbers": [
            "SQLitePlugin"
        ]
    }
]
});