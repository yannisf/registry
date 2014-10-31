module.exports = function(connect, options) {
	var proxy, base, middlewares = [];
	proxy = require("grunt-connect-proxy/lib/utils").proxyRequest;
	middlewares.push(proxy);
	middlewares.push(require('./getLazyRegistry'));
	// see https://github.com/drewzboto/grunt-connect-proxy/issues/65
	base = Array.isArray(options.base) ? options.base : [options.base];
	base.forEach(function(b) {
		middlewares.push(connect.static(b));
	});
	return middlewares;
};
