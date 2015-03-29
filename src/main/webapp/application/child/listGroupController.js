'use strict';

angular.module('child').controller('listGroupController', ['$scope', 'ActiveCache', 'Group',
    function ($scope, ActiveCache, Group) {
       angular.extend($scope, {
           data: {
               children: Group.children({id: ActiveCache.group.id}, function(response) {
                    $scope.viewData.noChildren = response.length === 0;
                    ActiveCache.children = response;
                    ActiveCache.childIds = response.map(function (child) {
                        return child.id;
                    });
                })
           },
           viewData: {
                noChildren: false
           }
       });

        $scope.goToChild = function ($event) {
            var clickedElement = angular.element($event.target);
            var childId = clickedElement.scope().child.id;
            $scope.go('/child/' + childId + '/view', $event);
        };
    }
]);

