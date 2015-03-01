'use strict';

angular.module('management')

    .directive('groupControl', [function () {
        return {
            restrict: 'A',
            scope: {
                group: "=groupControl",
                groups: "="
            },
            templateUrl: "management/group-control.tpl.html",
            link: function(scope, element, attrs, controllers) {

                scope.remove = function() {
                    var index = scope.groups.indexOf(scope.group);
                    scope.groups.splice(index,1);
                };
                
            }
        };
    }]);