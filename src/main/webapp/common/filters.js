define(['angular'], function(angular) {
    'use strict';

    angular.module('filters', [])
        .filter('relationshipFilter', ['relationshipMap', function (relationshipMap) {
            return function (value) {
                return relationshipMap[value];
            }
        }])

        .filter('telephoneTypeFilter', ['telephoneTypeMap', function (telephoneTypeMap) {
            return function (value) {
                return telephoneTypeMap[value];
            }
        }])

        .filter('childGenreTypeFilter', ['childGenreTypeMap', function (childGenreTypeMap) {
            return function (value) {
                return childGenreTypeMap[value];
            }
        }])

        .filter('guardianGenreTypeFilter', ['guardianGenreTypeMap', function (guardianGenreTypeMap) {
            return function (value) {
                return guardianGenreTypeMap[value];
            }
        }])

        .filter('preSchoolLevelFilter', ['preSchoolLevelMap', function (preSchoolLevelMap) {
            return function (value) {
                return preSchoolLevelMap[value];
            }
        }]);
});
