/*
    How to use:
    1. create package.json with all devDependencies
    2. install all dependencies by 'npm install' (step 4, 4.1 can be skipped when you already jshint installed)
    3. install grunt-cli by 'npm install -g grunt-cli'
    4. install jshint by '$ npm install jshint -g', 'npm install grunt-contrib-jshint --save-dev'
    4.1 install jshint stylish by 'npm install --save-dev jshint-stylish'
    5. if you like ng-annotate installed: 'npm install grunt-ng-annotate --save-dev'
    6. run grunt: 'grunt'
    7. run jshint: 'grunt jshint' (more detail check commands in jshint below)

    Note: run grunt will delete two files under 'src/main/webapp': bizislife.js & bizislife-min.js,
          and then create these two files based on all js files under 'src/main/webapp/js' folder
          Your application should use one of these generated files.
          ('<script src="bizislife.js"></script>' or'<script src="bizislife-min.js"></script>')
          DO NOT MODIFY THERE TWO FILES, LETS GRUNT CREATES FOR YOU!!!
 */

'use strict';
module.exports = function(grunt) {

    // Project configuration.
    grunt.initConfig({
        // Metadata.
        pkg: grunt.file.readJSON('package.json'),
        banner: '/*! <%= pkg.title || pkg.name %> - v<%= pkg.version %> - ' +
            '<%= grunt.template.today("yyyy-mm-dd") %>\n' +
            '<%= pkg.homepage ? "* " + pkg.homepage + "\\n" : "" %>' +
            '* Copyright (c) <%= grunt.template.today("yyyy") %> <%= pkg.author.name %>;' +
            ' Licensed <%= _.pluck(pkg.licenses, "type").join(", ") %> */\n',
        // Task configuration.
        clean: {
            src: ['src/main/webapp/bizislife.js', 'src/main/webapp/bizislife-min.js']
        },
        concat: {
            options: {
                banner: '<%= banner %>',
                stripBanners: true,
                // define a string to put between each file in the concatenated output
                separator: ';'
            },
            dist: {
                src: ['src/main/webapp/js/agency.js', 'src/main/webapp/js/biz-angular.js', 'src/main/webapp/js/service/*.js', 'src/main/webapp/js/controller/*.js'],
                dest: 'src/main/webapp/bizislife.js'
            }
        },
        uglify: {
            options: {
                banner: '<%= banner %>'
            },
            dist: {
                src: ['src/main/webapp/js/agency.js', 'src/main/webapp/js/biz-angular.js', 'src/main/webapp/js/service/*.js', 'src/main/webapp/js/controller/*.js'],
                dest: 'src/main/webapp/bizislife-min.js'
            }
        },
        // run jshint by 'grunt jshint' or 'grunt jshint:myFiles'
        // run jshint for single file: 'jshint modules\itemcentre\itemcentre.js'
        jshint: {
            options: {
                jshintrc: '.jshintrc',
                reporter: require('jshint-stylish')
            },
            // The paths tell JSHint which files to validate
            myFiles: ['src/main/webapp/js/*.js']
        }

    });

    // These plugins provide necessary tasks.
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-jshint');

    // Default task.
    grunt.registerTask('default', ['clean', 'concat', 'uglify']);
    grunt.registerTask('test', ['jshint']);

};
