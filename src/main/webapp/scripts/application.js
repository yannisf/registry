'use strict';

angular.module('schoolApp', ['ngRoute', 'ui.bootstrap', 'uuid4', 'child', 'guardian'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.otherwise({
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

    .value('childGenreTypeMap', {
        MALE: "Αγόρι",
        FEMALE: "Κορίτσι",
        OTHER: "Άλλο"
    })

    .value('guardianGenreTypeMap', {
        MALE: "Άρρεν",
        FEMALE: "Θύλη",
        OTHER: "Άλλο"
    })

    .value('preSchoolLevelMap', {
        PRE_SCHOOL_LEVEL_A: "Προνήπιο",
        PRE_SCHOOL_LEVEL_B: "Νήπιο"
    })

    .filter('relationshipFilter', ['relationshipMap', function (relationshipMap) {
        return function (value) {
            return relationshipMap[value];
        }
    }])

    .filter('telephoneTypeFilter', ['telephoneTypeMap', function (telephoneTypeMap) {
        return function (value) {
            return telephoneTypeMap[value];
        }
    }])

    .filter('childGenreTypeFilter', ['childGenreTypeMap', function (childGenreTypeMap) {
        return function (value) {
            return childGenreTypeMap[value];
        }
    }])

    .filter('guardianGenreTypeFilter', ['guardianGenreTypeMap', function (guardianGenreTypeMap) {
        return function (value) {
            return guardianGenreTypeMap[value];
        }
    }])

    .filter('preSchoolLevelFilter', ['preSchoolLevelMap', function (preSchoolLevelMap) {
        return function (value) {
            return preSchoolLevelMap[value];
        }
    }])

    .directive('displayGuardian', function () {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                guardian: "=",
                address: "="
            },
            templateUrl: "templates/display-guardian.html"
        };
    })

    .directive('displayChild', function () {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                child: "=",
                address: "="
            },
            templateUrl: "templates/display-child.html"
        };
    })

    .directive('displayAddress', function () {
        return {
            restrict: 'E',
            scope: {
                address: "="
            },
            link: function (scope, element) {
                scope.$watch('address', function (newval) {
                    if (newval && element) {
                        var addressString = "";
                        if (newval.streetName) {
                            addressString = newval.streetName;
                            if (newval.streetNumber) {
                                addressString += " " + newval.streetNumber;
                            }
                        }
                        if (newval.neighbourhood) {
                            if (addressString.length > 0) {
                                addressString += ", " + newval.neighbourhood;
                            } else {
                                addressString += newval.neighbourhood;
                            }
                        }
                        if (newval.postalCode) {
                            if (addressString.length > 0) {
                                addressString += ", " + newval.postalCode;
                            } else {
                                addressString += newval.postalCode;
                            }
                        }
                        if (newval.city) {
                            if (addressString.length > 0) {
                                addressString += ", " + newval.city;
                            } else {
                                addressString += newval.city;
                            }
                        }
                        element.html(addressString);
                    }
                })
            }
        };
    })

    .directive('inputAddress', ['statefulChildService', 'childService', 'addressService', 'uuid4',
        function (statefulChildService, childService, addressService, uuid4) {
            return {
                restrict: 'E',
                scope: {
                    address: "=",
                    shareOption: "="
                },
                templateUrl: "templates/input-address.html",
                link: function (scope) {
                    scope.viewData = {
                        commonAddress: false
                    };

                    if (scope.shareOption) {
                        scope.$watch('address.id', function (newval) {
                            scope.viewData.commonAddress = (newval == statefulChildService.getScopedChildAddressId());
                        });

                        scope.$watch('viewData.commonAddress', function (newval) {
                            if (newval) {
                                useChildAddress();
                            } else {
                                scope.address = {
                                    id: uuid4.generate()
                                };
                            }
                        });
                    }

                    function useChildAddress() {
                        var childId = statefulChildService.getScopedChildId();
                        childService.fetch(childId).then(function (response) {
                            return addressService.fetch(response.addressId);
                        }).then(function (response) {
                            scope.address = response;
                        });
                    };
                }
            };
        }])

    .directive('personName', function () {
        return {
            restrict: 'E',
            scope: {
                person: "="
            },
            template: '{{::person.firstName}} {{::person.lastName}} <span ng-if="::person.callName">({{::person.callName}})</span>'
        };
    })

    .directive('telephone', function () {
        return {
            restrict: 'E',
            scope: {
                telephone: "=model"
            },
            template: '{{telephone.number}} <span class="label label-info">{{telephone.type|telephoneTypeFilter}}</span>'
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

    .service('addressService', ['$http', function ($http) {
        return {
            fetch: function (addressId) {
                return $http.get('api/address/' + addressId).then( function (response) {
                    return response.data;
                })
            },
            remove: function (addressId) {
                return $http.delete('api/address/' + addressId).then( function (response) {
                    return response.data;
                })
            },
            update: function (address) {
                return $http.put('api/address', address).then(function (response) {
                    return response.data;
                })
            }
        }
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
                	if( $event ) {
                		$event.stopPropagation();
                	}
                    console.log('Requested ', path);
                    $location.path(path);
                },
                nextChild: function() {
                    var nextChildId = calculatePreviousAndNextChildId().next;
                    if (nextChildId) {
                        $location.url('/child/' + nextChildId + '/view');
                    }
                },
                previousChild: function() {
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

        }]);

