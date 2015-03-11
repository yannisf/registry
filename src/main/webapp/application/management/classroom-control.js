'use strict';

angular.module('management')

    .directive('classroomControl', [function () {
        return {
            restrict: 'A',
            scope: {
                group: "=groupControl",
                groups: "="
            },
            templateUrl: "application/management/classroom-control.tpl.html",
            link: function(scope, element, attrs, controllers) {

                scope.remove = function() {
                    var index = scope.groups.indexOf(scope.group);
                    scope.groups.splice(index,1);
                };
                
            }
        };
    }]);