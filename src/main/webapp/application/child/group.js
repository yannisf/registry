'use strict';

angular.module('child')

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/group/:groupId', {
                templateUrl: 'application/child/list.html',
                controller: 'listGroupController'
            });
    }])

    .controller('listGroupController', ['$scope', 'ActiveCache','ChildService', 'Group',
        function ($scope, ActiveCache, ChildService, Group) {
           angular.extend($scope, {
               data: {
                   children: []
               },
               viewData: {
					groupId: ActiveCache.group.id,
                   	noChildren: false
               }
           });
           
           $scope.data.children = Group.children({id: ActiveCache.group.id}, function(response) {
				$scope.viewData.noChildren = response.length === 0;
           		ActiveCache.children = response;
				ActiveCache.childIds = response.map(function (child) {
				   return child.id;
				});
           });

           	$scope.goToChild = function ($event) {
				var clickedElement = angular.element($event.target);
               	var childId = clickedElement.scope().child.id;
               	$scope.go('/child/' + childId + '/view', $event);
           	};
       	}
	]);

