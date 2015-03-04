'use strict';

angular.module('schoolApp')

    .directive('displayGuardian', function () {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                guardian: "=",
                address: "="
            },
            templateUrl: "application/guardian/displayGuardian.tpl.html"
        };
    });
    