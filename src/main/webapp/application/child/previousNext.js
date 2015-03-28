'use strict';

angular.module('child')

    .directive('previousNext', ['$location', 'ActiveCache', function ($location, ActiveCache) {
        return {
            restrict: 'E',
            replace: true,
            scope: {
            },
            templateUrl: "application/child/previousNext.tpl.html",
            link: function(scope) {
                scope.groupHasMoreThanOneChildren = ActiveCache.childIds.length > 1;
                scope.nextChild = function () {
                    var next = findNextChild();
                    if (next.rollover) {
                        console.log("Ανακύκλωση καταλόγου. ");
                    }
                    $location.url('/child/' + next.id + '/view');
                };
    
                scope.previousChild = function () {
                    var previous = findPreviousChild();
                    if (previous.rollover) {
                        console.log("Ανακύκλωση καταλόγου. ");
                    }
                    $location.url('/child/' + previous.id + '/view');
                };
    
                function findNextChild() {
                    var result = {};
                    var numberOfChildren = ActiveCache.childIds.length;
                    var currentChildId = ActiveCache.child.id;
                    var currentChildIdIndex = ActiveCache.childIds.indexOf(currentChildId);
                    if (currentChildIdIndex + 1 < numberOfChildren) {
                        result.id = ActiveCache.childIds[currentChildIdIndex + 1];
                    } else {
                        result.id = ActiveCache.childIds[0];
                        result.rollover = true;
                    }
                    return result;
                }
    
                function findPreviousChild() {
                    var result = {};
                    var numberOfChildren = ActiveCache.childIds.length;
                    var currentChildId = ActiveCache.child.id;
                    var currentChildIdIndex = ActiveCache.childIds.indexOf(currentChildId);
                    if (currentChildIdIndex !== 0) {
                        result.id = ActiveCache.childIds[currentChildIdIndex - 1];
                    } else {
                        result.id = ActiveCache.childIds[numberOfChildren - 1];
                        result.rollover = true;
                    }
                    return result;
                }

            }
        };
    }]);
