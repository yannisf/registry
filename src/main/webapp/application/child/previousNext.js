'use strict';

angular.module('child')

    .directive('previousNext', ['$location', 'FoundationService', 'ChildService', function ($location, FoundationService, ChildService) {
        return {
            restrict: 'E',
            replace: true,
            scope: {
            },
            templateUrl: "application/child/previousNext.tpl.html",
            link: function(scope) {
                scope.groupHasMoreThanOneChildren = FoundationService.groupChildrenIds.length > 1;
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
                    var numberOfChildren = FoundationService.groupChildrenIds.length;
                    var currentChildId = ChildService.child.id;
                    var currentChildIdIndex = FoundationService.groupChildrenIds.indexOf(currentChildId);
                    if (currentChildIdIndex + 1 < numberOfChildren) {
                        result.id = FoundationService.groupChildrenIds[currentChildIdIndex + 1];
                    } else {
                        result.id = FoundationService.groupChildrenIds[0];
                        result.rollover = true;
                    }
                    return result;
                }
    
                function findPreviousChild() {
                    var result = {};
                    var numberOfChildren = FoundationService.groupChildrenIds.length;
                    var currentChildId = ChildService.child.id;
                    var currentChildIdIndex = FoundationService.groupChildrenIds.indexOf(currentChildId);
                    if (currentChildIdIndex !== 0) {
                        result.id = FoundationService.groupChildrenIds[currentChildIdIndex - 1];
                    } else {
                        result.id = FoundationService.groupChildrenIds[numberOfChildren - 1];
                        result.rollover = true;
                    }
                    return result;
                }

            }
        };
    }]);
