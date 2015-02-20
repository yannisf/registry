module.exports = function(grunt) {
  	var scripts = [
  		"scripts/vendor/angular/angular.js",
  		"scripts/vendor/angular-route/angular-route.js",
  		"scripts/vendor/angular-resource/angular-resource.js",
  		"scripts/vendor/angular-ui-utils/ui-utils.js",
  		"scripts/vendor/angular-uuid4/angular-uuid4.js",
  		"scripts/vendor/angular-bootstrap/ui-bootstrap-tpls.js",
  		"scripts/typeaheads.js",
  		"scripts/values.js",
  		"scripts/application.js",
  		"school/school.js",
  		"scripts/relationship.js",
  		"scripts/address.js",
  		"child/child.js",
  		"guardian/guardian.js",
  	];

	grunt.initConfig({
    	pkg: grunt.file.readJSON("package.json"),
		copy: {
			main: {
				src: 'src/main/webapp/index.tpl.html',
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
			  		"src/main/webapp/styles/application.css": "src/main/webapp/styles/less/master.less"
				}
		  	}
		},
		watch: {
			styles: {
				files: ["src/main/webapp/styles/less/local.less"],
				tasks: ["less"],
				options: {
					nospawn: true
				}
			}
    	}
  	});

	grunt.loadNpmTasks("grunt-contrib-copy");
	grunt.loadNpmTasks("grunt-contrib-less");
	grunt.loadNpmTasks("grunt-contrib-watch");

	grunt.registerTask("default", ["copy", "less"]);

};
