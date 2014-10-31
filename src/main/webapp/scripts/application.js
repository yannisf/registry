define([
    'angular', 'scripts/vendor/angular-require-lazy/bootstrap', 'scripts/vendor/angular-require-lazy/routeConfig', 'lazy-registry',

    // side-effect dependencies
    'common/values',
    'common/filters',
    'common/directives',
//    'child/services/childService',
//    'child/services/statefulChildService',
    'scripts/address',
    'scripts/vendor/angular-route/angular-route',
    'scripts/vendor/angular-ui-utils/ui-utils',
    'scripts/vendor/angular-uuid4/angular-uuid4',
    'scripts/vendor/angular-bootstrap/ui-bootstrap-tpls',
    'scripts/typeaheads'
],
function(angular, bootstrap, routeConfig, lazyRegistry) {

    var schoolApp = angular.module('schoolApp', ['ngRoute', 'ui.bootstrap', 'ui.utils', 'uuid4', 'typeaheads', 'address', 'values', 'filters', 'directives']);

    function addAllRoutes($routeProvider) {
        var module, i, modules = lazyRegistry.getModules();
        for( i=0; i < modules.length; i++ ) {
            module = modules[i];
            addAllRoutesForModule($routeProvider, module);
        }
        $routeProvider.otherwise({redirectTo: '/child/list'});
    }
    
    function addAllRoutesForModule($routeProvider, module) {
        var routes, i, route;
        if( module.metadata && module.metadata['angular-routes'] ) {
            routes = module.metadata['angular-routes'];
            for( i=0; i < routes.length; i++ ) {
                route = routes[i];
                $routeProvider.when(route.path, routeConfig.fromAmdModule(route,module));
            }
        }
    }

    schoolApp
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

        .service('statefulChildService', ['$routeParams', function ($routeParams) {
            var childId;
            var childIds;
            var childAddressId;

            return {
                getScopedChildId: function () {
                    if ($routeParams.childId) {
                        childId = $routeParams.childId;
                    }

                    return childId;
                },
                setScopedChildId: function (value) {
                    childId = value;
                },
                getScopedChildAddressId: function () {
                    return childAddressId;
                },
                setScopedChildAddressId: function (value) {
                    childAddressId = value;
                },
                getChildIds: function() {
                    return childIds;
                },
                setChildIds: function(value) {
                    childIds = value;
                }
            }
        }])

        .service('childService', ['$http', function ($http) {
            return {
                fetch: function (childId) {
                    return $http.get('api/child/' + childId).then(function (response) {
                        return response.data;
                    });
                },
                fetchAll: function () {
                    return $http.get('api/child/all').then(function (response) {
                        return response.data;
                    });
                },
                update: function (child) {
                    return $http.put('api/child/', child).then(function (response) {
                        return response.data;
                    });
                },
                remove: function (childId) {
                    return $http({method: 'DELETE', url: 'api/child/' + childId}).then(function (response) {
                        return response.data;
                    });
                },
                fetchRelationships: function (childId) {
                    return $http.get('api/relationship/child/' + childId + '/guardian').then(function (response) {
                        return response.data;
                    });
                },
                removeRelationship: function (relationshipId) {
                    return $http({method: 'DELETE', url: 'api/relationship/' + relationshipId}).then(function (response) {
                        return response.data;
                    });
                }
            };
        }])

        .run(['$rootScope', '$location', 'statefulChildService', 'ListService',
            function ($rootScope, $location, statefulChildService, ListService) {
                angular.extend($rootScope, {
                    toChildList: function () {
                        $location.url('/child/list');
                    },
                    toScopedChild: function () {
                        if (statefulChildService.getScopedChildId()) {
                            $location.url('/child/' + statefulChildService.getScopedChildId() + '/view');
                        } else {
                            this.toChildList();
                        }
                    },
                    go: function (path, $event) {
                        console.log('Requested ', path);
                        if ($event) {
                            $event.stopPropagation();
                        }
                        $location.path(path);
                    },
                    nextChild: function () {
                        var nextChildId = calculatePreviousAndNextChildId().next;
                        if (nextChildId) {
                            $location.url('/child/' + nextChildId + '/view');
                        }
                    },
                    previousChild: function () {
                        var previousChildId = calculatePreviousAndNextChildId().previous;
                        if (previousChildId) {
                            $location.url('/child/' + previousChildId + '/view');
                        }
                    },
                    relationshipTypes: [],
                    telephoneTypes: []
                });

                function calculatePreviousAndNextChildId() {
                    var nextAndPrevious = {};
                    var numberOfChildren = statefulChildService.getChildIds().length;
                    var currentChildId = statefulChildService.getScopedChildId();
                    var currentChildIdIndex = statefulChildService.getChildIds().indexOf(currentChildId)
                    if (currentChildIdIndex + 1 < numberOfChildren) {
                        var nextChildIdIndex = currentChildIdIndex + 1;
                        nextAndPrevious.next = statefulChildService.getChildIds()[nextChildIdIndex];
                    }
                    if (currentChildIdIndex != 0) {
                        var previousChildIdIndex = currentChildIdIndex - 1;
                        nextAndPrevious.previous = statefulChildService.getChildIds()[previousChildIdIndex];
                    }
                    return nextAndPrevious;
                }

                ListService.relationshipTypes().then(function (data) {
                    $rootScope.relationshipTypes = data;
                });

                ListService.telephoneTypes().then(function (data) {
                    $rootScope.telephoneTypes = data;
                });

            }
        ])
    ;

    schoolApp.config(['$routeProvider', addAllRoutes]);
    bootstrap(document, schoolApp);
    return schoolApp;
});
