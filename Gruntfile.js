module.exports = function(grunt) {

  grunt.initConfig({
  
    pkg: grunt.file.readJSON("package.json"),

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

  grunt.loadNpmTasks("grunt-contrib-less");
  grunt.loadNpmTasks("grunt-contrib-watch");
  
  grunt.registerTask("default", ["less", "watch"]);

};
