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
            templateUrl: "child/displayChild.tpl.html",
            link: function(scope) {
                scope.isBlankAddress = addressService.isBlank;
            }
        };
    }]);
