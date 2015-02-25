'use strict';

angular.module('schoolApp', ['ngRoute', 'ngResource', 'ui.bootstrap', 'ui.utils', 'uuid4', 'values', 
        'child', 'guardian', 'typeaheads', 'school', 'management'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.otherwise({
            redirectTo: '/school/list'
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

    .filter('childGenreTypeFilter', ['childGenreTypeMap', function (childGenreTypeMap) {
        return function (value) {
            return childGenreTypeMap[value];
        };
    }])

    .filter('guardianGenreTypeFilter', ['guardianGenreTypeMap', function (guardianGenreTypeMap) {
        return function (value) {
            return guardianGenreTypeMap[value];
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
    }
    ])

    .factory("Flash", ['$rootScope', function($rootScope) {
      var infoQueue = [];
      var currentInfoMessage = "";

      var successQueue = [];
      var currentSuccessMessage = "";

      var warningQueue = [];
      var currentWarningMessage = "";

      var errorQueue = [];
      var currentErrorMessage = "";

      $rootScope.$on("$routeChangeSuccess", function() {
        currentInfoMessage = infoQueue.shift() || "";
        currentWarningMessage = warningQueue.shift() || "";
        currentSuccessMessage = successQueue.shift() || "";
        currentErrorMessage = errorQueue.shift() || "";
      });

      return {
        setMessage: function(message) {
          infoQueue.push(message);
        },
        getMessage: function() {
          return currentInfoMessage;
        },
        setSuccessMessage: function(message) {
          successQueue.push(message);
        },
        getSuccessMessage: function() {
          return currentSuccessMessage;
        },
        setWarningMessage: function(message) {
          warningQueue.push(message);
        },
        getWarningMessage: function() {
          return currentWarningMessage;
        },
        setErrorMessage: function(message) {
          errorQueue.push(message);
        },
        getErrorMessage: function() {
          return currentErrorMessage;
        },

      };
    }])

    .run(['$rootScope', '$location', '$window', 'ChildService', 'Flash', 'ListService', 'SchoolService',
        function ($rootScope, $location, $window, ChildService, Flash, ListService, SchoolService) {
            angular.extend($rootScope, {
                scopedSchoolInfo: null,
                toSchoolList: function() {
                    $rootScope.scopedSchoolInfo = null;
                    $location.url('/school/list');
                },
                toChildList: function (groupId) {
                    if (angular.isDefined(groupId)) {
                        SchoolService.groupId = groupId;
                        $rootScope.scopedSchoolInfo = SchoolService.info();
                    }
                    $location.url('/child/class/' + SchoolService.groupId + '/list');
                },
                toScopedChild: function () {
                    if (ChildService.child) {
                        $location.url('/child/' + ChildService.child.id + '/view');
                    } else {
                        this.toChildList();
                    }
                },
                go: function (path, $event) {
                    if ($event) {
                        $event.stopPropagation();
                    }
                    $location.path(path);
                },
                Flash: Flash,
                relationshipTypes: [],
                telephoneTypes: []
            });

            ListService.relationshipTypes().then(function (data) {
                $rootScope.relationshipTypes = data;
            });

            ListService.telephoneTypes().then(function (data) {
                $rootScope.telephoneTypes = data;
            });

        }]);

