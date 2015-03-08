'use strict';

angular.module('management')

    .directive('schoolControl', [function () {
        return {
            restrict: 'A',
            scope: {
                school: "=schoolControl",
                schools: "=",
                viewData: "="
            },
            templateUrl: "application/management/school-control.tpl.html",
            link: function(scope, element, attrs, controllers) {

                scope.remove = function() {
                    var index = scope.schools.indexOf(scope.school);
                    scope.schools.splice(index,1);
                    scope.viewData.activeSchool = null;
                    scope.viewData.activeGroup = null;
                };
                
            }
        };
    }]);