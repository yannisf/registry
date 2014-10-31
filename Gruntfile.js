module.exports = function(grunt) {
	var userConfig, taskConfig;

	grunt.loadNpmTasks('grunt-contrib-connect');
	grunt.loadNpmTasks('grunt-connect-proxy');

	userConfig = require('./build.config.js');

	taskConfig = {
		connect: {
			options: {
				port: 8110
			},
			serve: {
				options: {
					base: "<%= WEB_ROOT %>",
					keepalive: true,
					middleware: require("./src/build/grunt-connect-proxy-middleware")
				},
				proxies: [{
					context: "/api",
					host: "localhost",
					port: 8080,
					rewrite: {
						'^/api': '/school/api'
					}
				}]
			}
		}
	};

	grunt.initConfig(grunt.util._.extend(taskConfig, userConfig));

	grunt.registerTask("server", ["configureProxies:serve","connect:serve"]);
};
