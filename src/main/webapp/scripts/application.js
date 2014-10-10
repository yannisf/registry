'use strict';

angular.module('schoolApp', ['ngRoute', 'ui.bootstrap', 'child', 'guardian'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .otherwise({
                redirectTo: '/child/list'
            });
    }])

    .value('relationshipMap', {
        FATHER: "Πατέρας",
        MOTHER: "Μητέρα",
        GRANDFATHER: "Παππούς",
        GRANDMOTHER: "Γιαγιά",
        BROTHER: "Αδελφός",
        SISTER: "Αδελφή",
        UNCLE: "Θείος",
        AUNT: "Θεία",
        GODFATHER: "Νονός",
        GODMOTHER: "Νονά",
        OTHER: "Άλλο"
    })

    .value('telephoneTypeMap', {
        HOME: "Σπίτι",
        WORK: "Δουλειά",
        MOBILE: "Κινητό",
        OTHER: "Άλλο"
    })

    .value('sexTypeMap', {
        MALE: "Αγόρι",
        FEMALE: "Κορίτσι",
        OTHER: "Άλλο"
    })

    .value('guardianSexTypeMap', {
        MALE: "Άρρεν",
        FEMALE: "Θύλη",
        OTHER: "Άλλο"
    })

    .value('preSchoolLevelMap', {
        PRE_SCHOOL_LEVEL_A: "Προνήπιο",
        PRE_SCHOOL_LEVEL_B: "Νήπιο"
    })

    .filter('relationshipFilter', ['relationshipMap', function(relationshipMap) {
        return function(value) {
            return relationshipMap[value];
        }
    }])

    .filter('telephoneTypeFilter', ['telephoneTypeMap', function(telephoneTypeMap) {
        return function(value) {
            return telephoneTypeMap[value];
        }
    }])

    .filter('sexTypeFilter', ['sexTypeMap', function(sexTypeMap) {
        return function(value) {
            return sexTypeMap[value];
        }
    }])

    .filter('guardianSexTypeFilter', ['guardianSexTypeMap', function(guardianSexTypeMap) {
        return function(value) {
            return guardianSexTypeMap[value];
        }
    }])

    .filter('preSchoolLevelFilter', ['preSchoolLevelMap', function(preSchoolLevelMap) {
        return function(value) {
            return preSchoolLevelMap[value];
        }
    }])

    .directive('displayGuardian', function() {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                guardian: "=guardian"
            },
            templateUrl: "templates/display-guardian.html"
        };
    })

    .directive('displayChild', function() {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                child: "="
            },
            templateUrl: "templates/display-child.html"
        };
    })

    .directive('displayAddress', function() {
        return {
            restrict: 'E',
            scope: {
                address: "="
            },
            templateUrl: "templates/display-address.html"
        };
    })

    .directive('inputAddress', ['statefulChildService', 'childService', 'addressService',
        function(statefulChildService,childService, addressService) {
            return {
                restrict: 'E',
                scope: {
                    address: "=",
                    shareOption: "@"
                },
                templateUrl: "templates/input-address.html",
                link: function(scope) {
                    scope.useChildAddress = function() {
                        console.log('Into link');
                        var childId = statefulChildService.getScopedChildId();
                        childService.fetch(childId).then(function(response) {
                            var addressId = response.address.id;
                            return addressService.fetch(addressId);
                        }).then(function(response) {
                            console.log('Address is: ', response);
                            scope.address = response;
                        });
                    };
                }
            };
        }])

    .directive('personName', function() {
        return {
            restrict: 'E',
            scope: {
                person: "=person"
            },
            template: '{{::person.firstName}} {{::person.lastName}} <span ng-if="person.callName">({{::person.callName}})</span>'
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

    .service('addressService', ['$http', function($http) {
        return {
            fetch: function(addressId) {
                return $http.get('api/address/' + addressId).then(
                    function(response) {
                        console.log('Into address service: ', response);
                        return response.data;
                    }
                )
            }
        }
    }])

    .run(['$rootScope', '$location', 'statefulChildService', 'ListService',
        function ($rootScope, $location, statefulChildService, ListService) {
            angular.extend($rootScope, {
                toChildList: function () {
                    $location.url('/child/list');
                },
                toScopedChild: function() {
                    if (statefulChildService.getScopedChildId()) {
                        $location.url('/child/' + statefulChildService.getScopedChildId() + '/view');
                    } else {
                        this.toChildList();
                    }
                },
                go: function(path) {
                    console.log('Requested ', path);
                  $location.path(path);
                },
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

