'use strict';

angular.module('child')

    .directive('displayChild', ['addressService', function (addressService) {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                child: "=",
                address: "="
            },
            templateUrl: "application/child/displayChild.tpl.html",
            link: function(scope) {
                scope.isBlankAddress = addressService.isBlank;
            }
        };
    }]);
