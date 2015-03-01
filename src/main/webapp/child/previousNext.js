'use strict';

angular.module('child')

    .directive('previousNext', ['ChildService', function (ChildService) {
        return {
            restrict: 'E',
            replace: true,
            scope: {
            },
            templateUrl: "child/previousNext.tpl.html",
            link: function(scope) {

            }
        };
    }]);
