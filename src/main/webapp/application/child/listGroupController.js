'use strict';

angular.module('child').controller('listGroupController', ['$scope', '$routeParams', '$cookieStore', 'ActiveCache', 'Group',

    function ($scope, $routeParams, $cookieStore, ActiveCache, Group) {
        angular.extend($scope, {
            data: {
               children: []
            },
            viewData: {
                groupId: $routeParams.groupId,
                noChildren: false
            }
        });

        ActiveCache.resolveGroup($routeParams.groupId).then(function() {
            $cookieStore.put('group', $routeParams.groupId);
            $scope.data.children = Group.children({id: $routeParams.groupId}, function(response) {
                 $scope.viewData.noChildren = response.length === 0;
                 ActiveCache.children = response;
                 ActiveCache.childIds = response.map(function (child) {
                     return child.id;
                });
            });
        });
    
    
        $scope.goToChild = function ($event) {
                var clickedElement = angular.element($event.target);
                var childId = clickedElement.scope().child.id;
                $scope.go('/child/' + childId + '/view', $event);
            };
        }

]);
