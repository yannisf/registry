'use strict';

angular.module('management')

    .directive('schoolControl', [function () {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                school: "="
            },
            templateUrl: "management/school-control.tpl.html",
            link: function(scope, element) {
            }
        };
    }]);