'use strict';

angular.module('schoolApp', ['ngRoute', 'ngResource', 'ui.bootstrap', 'ui.utils', 'uuid4', 'values', 
        'child', 'guardian', 'typeaheads', 'overview'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.otherwise({
            redirectTo: '/overview'
        });
    }])

    .filter('firstNameFilter', [function () {
        return function (child) {
            var value = child.firstName;
            if (child.callName) {
                value += " (" + child.callName + ")";
            }
            return value;
        };
    }])

    .filter('relationshipFilter', ['relationshipMap', function (relationshipMap) {
        return function (value) {
            return relationshipMap[value];
        };
    }])

    .filter('telephoneTypeFilter', ['telephoneTypeMap', function (telephoneTypeMap) {
        return function (value) {
            return telephoneTypeMap[value];
        };
    }])

    .filter('childGenderTypeFilter', ['childGenderTypeMap', function (childGenderTypeMap) {
        return function (value) {
            return childGenderTypeMap[value];
        };
    }])

    .filter('guardianGenderTypeFilter', ['guardianGenderTypeMap', function (guardianGenderTypeMap) {
        return function (value) {
            return guardianGenderTypeMap[value];
        };
    }])

    .filter('preSchoolLevelFilter', ['preSchoolLevelMap', function (preSchoolLevelMap) {
        return function (value) {
            return preSchoolLevelMap[value];
        };
    }])

	.directive('focus', function focus(){
		return {
			restrict: 'A',
			link: function(scope, element) {
				element[0].focus();
			}
		};
	})

    //TODO: This can be a filter
    .directive('personName', function () {
        return {
            restrict: 'E',
            scope: {
                person: "="
            },
            link: function (scope, element) {
                var unwatch = scope.$watch('person', function (newval) {
                    if (newval.$resolved) {
                        var copiedPerson = angular.copy(newval);
                        var name = '';
                        if (copiedPerson.firstName) {
                            name = copiedPerson.firstName + ' ';
                        }
                        if (copiedPerson.callName) {
                            name += " (" + copiedPerson.callName + ") ";
                        }
                        if (copiedPerson.lastName) {
                            name += copiedPerson.lastName + ' ';
                        }
                        element.html(name);
                        unwatch();
                    }
                }, true);
            }
        };
    })

    //TODO: This could be a filter
    .directive('telephone', function () {
        return {
            restrict: 'E',
            scope: {
                telephone: "=model"
            },
            template: '{{telephone.number}} <span class="phone-type">{{telephone.type|telephoneTypeFilter}}</span>'
        };
    })

    .service('ListService', ['$http', function ($http) {
        return {
            relationshipTypes: function () {
                return $http.get('api/types/relationship').then(
                    function (response) {
                        return response.data;
                    }
                );
            },
            telephoneTypes: function () {
                return $http.get('api/types/telephone').then(
                    function (response) {
                        return response.data;
                    });
            }
        };
    }])

    .run(['$rootScope', '$location', '$window', 'ListService', 'ActiveCache',
        function ($rootScope, $location, $window, ListService, ActiveCache) {
            angular.extend($rootScope, {
                go: function (path, $event) {
                    if ($event) {
                        $event.stopPropagation();
                    }
                    $location.path(path);
                },
                relationshipTypes: []
            });

            ListService.relationshipTypes().then(function (data) {
                $rootScope.relationshipTypes = data;
            });

        }]);
