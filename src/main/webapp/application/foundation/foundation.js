'use strict';

angular.module('foundation', ['ngRoute', 'ngResource', 'ui.bootstrap', 'child'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/foundation/list', {
                templateUrl: 'application/foundation/list.html',
                controller: 'foundationController'
            })
            .when('/group/:groupId', {
                templateUrl: 'application/foundation/group.html',
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

    .service('FoundationService', ['Foundation', function (Foundation) {
		var school = { id: null, name: null };
		var classroom = { id: null, name: null };
		var term = { id: null, name: null };
		var group = { id: null };
		var initializeGroup = function(groupId) {
			group.id = groupId;
			var groupInfo = Foundation.groupInfo({groupId: groupId});
			groupInfo.$promise.then(function(response) {
				school.name = response.school;
				classroom.name = response.classroom;
				term.name = response.term;
			});
		};
		var groupChildren = function() {
			var self = this;
			var children = Foundation.groupChildren({groupId: group.id});
			children.$promise.then(function(response) {
				self.groupChildrenIds = response.map(function (child) {
				   return child.id;
				});
			});

			return children;
		};
		var groupChildrenIds = [];
		var fetchSystem = function() {
			return Foundation.system();
		};
		var reset = function() {
			this.school.id = null;
			this.school.name = null;
			this.classroom.id = null;
			this.classroom.name = null;
			this.term.id = null;
			this.term.name = null;
			this.group.id = null;
			this.groupChildrenIds = [];
		};

		return {
			school: school,
			classroom: classroom,
			term: term,
			group: group,
			reset: reset,
			initializeGroup: initializeGroup,
			fetchSystem: fetchSystem,
			groupChildren: groupChildren,
			groupChildrenIds: groupChildrenIds
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
               	$scope.go('/child/' + childId + '/view', $event);
           	};
       	}
	]);

