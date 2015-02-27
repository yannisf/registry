'use strict';

angular.module('foundation', ['ngRoute', 'ngResource', 'ui.bootstrap', 'child'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/foundation/list', {
                templateUrl: 'foundation/list.html',
                controller: 'foundationController'
            })
            .when('/group/:groupId', {
                templateUrl: 'foundation/group.html',
                controller: 'listGroupController'
            });
    }])

    .factory('Foundation', ['$resource', function($resource) {
        return $resource('api/foundation', { }, {
            system: {method: 'GET', isArray: true},
            groupInfo: {method: 'GET', url: 'api/foundation/group/:groupId/info'},
            groupChildren: {method: 'GET', url: 'api/foundation/group/:groupId/children', isArray: true},
            groupStatistics: {method: 'GET', url: 'api/foundation/group/:groupId/statistics'}
        });
    }])

    .service('FoundationService', ['Foundation', 'ChildService', function (Foundation, ChildService) {
		var school = { id: null, name: "Σχολείο" };
		var classroom = { id: null, name: "Τμήμα" };
		var term = { id: null, name: "Χρονιά" };
		var group = { id: null };
		var initializeGroup = function(groupId) {
			group.id = groupId;
			var groupInfo = Foundation.groupInfo({groupId: groupId});
			groupInfo.$promise.then(function(response) {
				school.name = response.school;
				classroom.name = response.classroom;
				term.name = response.term;
				console.log('school (F): ', school.name);
			});
		};
		var groupChildren = function() {
			var children = Foundation.groupChildren({groupId: group.id});
			children.$promise.then(function(response) {
			   ChildService.childIds = response.map(function (child) {
				   return child.id;
			   });
			});

			return children;
		};
		var fetchSystem = function() {
			return Foundation.system();
		};

		return {
			school: school,
			classroom: classroom,
			term: term,
			group: group,
			initializeGroup: initializeGroup,
			fetchSystem: fetchSystem,
			groupChildren: groupChildren
		};
    }])

    .controller('foundationController', ['$scope', 'FoundationService',
        function ($scope, FoundationService) {
            angular.extend($scope, {
                data: {
                    schools: FoundationService.fetchSystem()
                }
            });
        }
    ])

    .controller('listGroupController', ['$scope', 'FoundationService', 'ChildService',
        function ($scope, FoundationService, ChildService) {
           angular.extend($scope, {
               data: {
                   children: FoundationService.groupChildren()
               },
               viewData: {
					groupId: FoundationService.group.id,
                   	noChildren: false
               }
           });

			$scope.data.children.$promise.then(function(response) {
				$scope.viewData.noChildren = response.length === 0;
			});

           	$scope.goToChild = function ($event) {
				var clickedElement = angular.element($event.target);
               	var childId = clickedElement.scope().child.id;
               	console.log('Child service name set to...');
               	ChildService.childName = "Someone";
               	console.log(ChildService.childName);
               	$scope.go('/child/' + childId + '/view', $event);
           	};
       	}
	]);

