module.exports = function(grunt) {
  	var scripts = [
  		"scripts/vendor/jquery/dist/jquery.js",
  		"scripts/vendor/angular/angular.js",
  		"scripts/vendor/angular-route/angular-route.js",
  		"scripts/vendor/angular-resource/angular-resource.js",
  		"scripts/vendor/angular-ui-utils/ui-utils.js",
  		"scripts/vendor/angular-uuid4/angular-uuid4.js",
  		"scripts/vendor/angular-bootstrap/ui-bootstrap-tpls.js",
  		"application/typeaheads.js",
  		"application/values.js",
  		"application/application.js",
  		"application/navbar/navbar.js",
  		"application/foundation/foundation.js",
  		"application/foundation/breadcrumb.js",
  		"application/foundation/groupStatistics.js",
  		"application/relationship.js",
  		"application/address/address.js",
  		"application/address/input-address.js",
  		"application/management/management.js",
  		"application/management/school-control.js",
  		"application/management/group-control.js",
  		"application/child/child.js",
  		"application/child/childService.js",
  		"application/child/createChildController.js",
  		"application/child/updateChildController.js",
  		"application/child/displayChild.js",
  		"application/child/previousNext.js",
  		"application/guardian/guardian.js",
  		"application/guardian/displayGuardian.js"
  	];

	grunt.initConfig({
    	pkg: grunt.file.readJSON("package.json"),
    	jshint: {
    		options: {
    			globalstrict: true,
    			globals: {
    				angular: true,
    				console: true
    			}
    		},
    		sources: ['src/main/webapp/**/*.js', '!src/main/webapp/scripts/vendor/**']
    	},
		copy: {
			main: {
				src: 'src/main/webapp/application/index.tpl.html',
				dest: 'src/main/webapp/index.html',
				options: {
			    	process: function (content, srcpath) {
				  		return grunt.template.process(content, {
							data: {
								scripts: scripts
							}
				  		});
			  		}
				}
		  	}
		},
		less: {
			development: {
				options: {
			  		paths: ["src/main/webapp/scripts/vendor/bootstrap/less"]
				},
				files: {
			  		"src/main/webapp/styles/application.css": "src/main/less/master.less"
				}
		  	}
		},
		watch: {
			styles: {
				files: ["src/main/less/local.less"],
				tasks: ["less"],
				options: {
					nospawn: true
				}
			}
    	}
  	});

	grunt.loadNpmTasks("grunt-contrib-copy");
	grunt.loadNpmTasks("grunt-contrib-less");
	grunt.loadNpmTasks('grunt-contrib-jshint');
	grunt.loadNpmTasks("grunt-contrib-watch");

	grunt.registerTask("default", ["jshint", "copy", "less"]);

};
