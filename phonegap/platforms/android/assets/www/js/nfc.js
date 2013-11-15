/*jshint quotmark: false */
/*global nfc, ndef, toast, alert, cordova, checkbox, statusDiv, sample */

"use strict";

var android = (cordova.platformId === 'android'), windowsphone = (cordova.platformId === 'windowsphone'), bb10 = (cordova.platformId === 'blackberry10'), sampleData;

var app = {
	sampleDataIndex : 0,
	initialize : function() {
		this.bind();
	},
	bind : function() {
		document.addEventListener('deviceready', app.deviceready, false);
	},
	deviceready : function() {
		app.shareMessage();
		app.listenMessage();
	},
	shareMessage : function() {
		var mimeType = 'text/stuxpet',
		payload = stats.name + "/" + stats.species + "/" + stats.type + "/"
				+ stats.birthday,
		record = ndef.mimeMediaRecord(mimeType, nfc.stringToBytes(payload));

		nfc.share([ record ], function() {
			alert("Sent " + stats.name + "'s details");
			console.log("ndef success", "Sent " + stats.name + "'s details");
		}, function(reason) {
			console.log("Failed to share tag " + reason);
		});
	},
	listenMessage : function() {
		nfc.addNdefListener(function(nfcEvent) {
			var tag = nfcEvent.tag, ndefMessage = tag.ndefMessage;

			alert(nfc.bytesToString(ndefMessage[0].payload));
		}, function() { // success callback
		}, function(error) { // error callback
			alert("Error adding NDEF listener " + JSON.stringify(error));
		});
	}
}

app.initialize();