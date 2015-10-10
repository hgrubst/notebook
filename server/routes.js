var route = require('koa-route');
var send = require('koa-send');
var path = require('path');

module.exports = function(app) {

	//anything without a dot (the dot files should be served statically)
	app.use(route.get(/^[^\.]+$/, index));


	
}

function *index(){
	yield send(this, path.join(__dirname + '../public/', 'index.html'))
}