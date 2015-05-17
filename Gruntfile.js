module.exports = function(grunt) {
  	var scripts = [
  		"scripts/lib/angular.js",
  		"scripts/lib/angular-route.js",
  		"scripts/lib/angular-resource.js",
  		"scripts/lib/angular-cookies.js",
  		"scripts/lib/ui-utils.js",
  		"scripts/lib/angular-uuid4.js",
  		"scripts/lib/ui-bootstrap-tpls.js",
  		"application/values.js",
  		"application/application.js",
  		"application/overview/overview.js",
  		"application/overview/schools.js",
  		"application/overview/departments.js",
  		"application/overview/groups.js",
  		"application/overview/school-control.js",
  		"application/overview/department-control.js",
  		"application/overview/group-control.js",
  		"application/overview/focusAndSelect.js",
  		"application/components/typeaheads.js",
  		"application/components/navbar.js",
  		"application/components/logout.js",
  		"application/components/breadcrumb.js",
  		"application/components/statistics.js",
  		"application/child/child.js",
  		"application/child/listGroupController.js",
  		"application/child/createChildController.js",
  		"application/child/updateChildController.js",
  		"application/child/displayChild.js",
  		"application/child/previousNext.js",
  		"application/address/address.js",
  		"application/address/input-address.js",
  		"application/relationship/relationship.js",
  		"application/relationship/guardian.js",
  		"application/relationship/displayGuardian.js",
  		"application/relationship/telephones.js",
  		"application/relationship/createGuardianController.js",
  		"application/relationship/updateGuardianController.js"
  	];

	grunt.initConfig({
    	pkg: grunt.file.readJSON("package.json"),
    	jshint: {
    		options: {
    			globalstrict: true,
    			unused: true,
    			globals: {
    				angular: true,
					alert: true,
    				console: true
    			}
    		},
    		sources: ['src/main/webapp/**/*.js', '!src/main/webapp/scripts/lib/**']
    	},
    	htmlhint: {
			options: {
				'tag-pair': true,
				'tag-self-close': true,
				'attr-no-duplication': true,
			},
			src: ['src/main/webapp/application/**/*.html']
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
		  	},
			compile: {
				src: 'src/main/webapp/application/index.tpl.html',
				dest: 'src/main/webapp/index.html',
				options: {
			    	process: function (content, srcpath) {
				  		return grunt.template.process(content, {
							data: {
								scripts: ['scripts/registry.js', 'scripts/registry.tpl.js']
							}
				  		});
			  		}
				}
		  	},
		  	lib: {
		  		files: [
		  			{expand: true, cwd:'bower_modules/jquery/dist/', src: 'jquery.js', dest: 'src/main/webapp/scripts/lib/'},
		  			{expand: true, cwd:'bower_modules/angular/', src: 'angular.js', dest: 'src/main/webapp/scripts/lib/'},
		  			{expand: true, cwd:'bower_modules/angular-route/', src: 'angular-route.js', dest: 'src/main/webapp/scripts/lib/'},
		  			{expand: true, cwd:'bower_modules/angular-resource/', src: 'angular-resource.js', dest: 'src/main/webapp/scripts/lib/'},
		  			{expand: true, cwd:'bower_modules/angular-cookies/', src: 'angular-cookies.js', dest: 'src/main/webapp/scripts/lib/'},
		  			{expand: true, cwd:'bower_modules/angular-ui-utils/', src: 'ui-utils.js', dest: 'src/main/webapp/scripts/lib/'},
		  			{expand: true, cwd:'bower_modules/angular-uuid4/', src: 'angular-uuid4.js', dest: 'src/main/webapp/scripts/lib/'},
		  			{expand: true, cwd:'bower_modules/angular-bootstrap/', src: 'ui-bootstrap-tpls.js', dest: 'src/main/webapp/scripts/lib/'},
		  			{expand: true, cwd:'bower_modules/bootstrap/', src: 'fonts/**', dest: 'src/main/webapp/scripts/lib/'},
		  		],
		  	}
		},
		uglify: {
			target: {
				files: {
					'src/main/webapp/scripts/registry.tpl.js': 'src/main/webapp/scripts/registry.tpl.js',
					'src/main/webapp/scripts/registry.js': scripts.map(function(script) {
						return 'src/main/webapp/' + script;
					}),
				}
			}
		},
		html2js: {
			options: {
				htmlmin: {
					collapseWhitespace: true,
					removeComments: true,
				}
		  },
            main: {
              src: ['src/main/webapp/application/**/*.tpl.html'],
              dest: 'src/main/webapp/scripts/registry.tpl.js'
            },
          },
		less: {
			options: {
				paths: ["bower_modules/bootstrap/less"]
			},
			development: {
				files: {
			  		"src/main/webapp/styles/application.css": "src/main/less/master.less"
				}
		  	},
			compile: {
				options: {
					compress: true
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
    	},
    	clean: ['src/main/webapp/scripts', 'src/main/webapp/styles/application.css']
  	});

	grunt.loadNpmTasks('grunt-contrib-jshint');
	grunt.loadNpmTasks('grunt-htmlhint');
	grunt.loadNpmTasks("grunt-contrib-copy");
	grunt.loadNpmTasks("grunt-contrib-less");
	grunt.loadNpmTasks("grunt-contrib-watch");
	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('grunt-html2js');
	grunt.loadNpmTasks('grunt-contrib-clean');

	grunt.registerTask("default", ["clean", "htmlhint", "jshint", "copy:main", "copy:lib", "less:development"]);
	grunt.registerTask("compile", ["clean", "copy:lib", "html2js", "uglify", "copy:compile", "less:compile"]);

};
