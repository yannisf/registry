module.exports = function(grunt) {
	var userConfig, taskConfig;

	grunt.loadNpmTasks('grunt-contrib-connect');
	grunt.loadNpmTasks('grunt-connect-proxy');
	grunt.loadNpmTasks('require-lazy-grunt');
	grunt.loadNpmTasks('grunt-contrib-clean');
	grunt.loadNpmTasks('grunt-contrib-concat');

	userConfig = require('./build.config.js');

	taskConfig = {
		connect: {
			options: {
				port: 8110
			},
			serve: {
				options: {
					base: '<%= WEB_ROOT %>',
					keepalive: true,
					middleware: require('./src/build/grunt-connect-proxy-middleware')
				},
				proxies: [{
					context: '/api',
					host: 'localhost',
					port: 8080,
					rewrite: {
						'^/api': '/school/api'
					}
				}]
			}
		},
		require_lazy_grunt: {
			options: {
				buildOptions: require('./src/build/buildOptions'),
				config: require('./src/build/buildConfig')
			}
		},
		clean: {
			require_lazy: ['target/require-lazy']
		},
		concat: {
			options: {
				separator: ';'
			},
			require_lazy: {
				src: [
					'src/build/compiled/almond-trick.js',
					'src/main/webapp/scripts/vendor/almond/almond.js',
					'src/build/compiled/require-cfg-almond.js',
					'src/main/webapp/scripts/vendor/angular/angular.min.js',
					'src/build/compiled/lazyload.js'
				],
				dest: 'target/require-lazy/scripts/bootstrap-built.js'
			}
		}
	};

	grunt.initConfig(grunt.util._.extend(taskConfig, userConfig));

	grunt.registerTask('server', ['configureProxies:serve', 'connect:serve']);
	grunt.registerTask('build', ['clean:require_lazy', 'require_lazy_grunt', 'concat:require_lazy']);
};
