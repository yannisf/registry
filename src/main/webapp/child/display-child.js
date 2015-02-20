'use strict';

angular.module('schoolApp')

    .directive('displayChild', ['addressService', function (addressService) {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                child: "=",
                address: "="
            },
            templateUrl: "child/display-child.tpl.html",
            link: function(scope) {
                scope.isBlankAddress = addressService.isBlank;
            }
        };
    }]);
