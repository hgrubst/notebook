var compress = require('koa-compress');
var serve = require('koa-static');
var logger = require('koa-logger');
var parse = require('co-body');
var koa = require('koa');
var routes = require('./server/routes.js')

var app = koa();


// middleware

app.use(logger());
app.use(serve(__dirname + '/public'));
app.use(compress());

// routes

routes(app);


app.listen(8888);
