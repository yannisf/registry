'use strict';

angular.module('schoolApp', ['ngRoute', 'ui.bootstrap', 'uuid4', 'child', 'guardian', 'typeaheads'])

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
        MALE: "Άνδρας",
        FEMALE: "Γυναίκα",
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

    .directive('personName', function () {
        return {
            restrict: 'E',
            scope: {
                person: "="
            },
            link: function (scope, element) {
                var unwatch = scope.$watch('person', function (newval) {
                    if (newval) {
                        var copiedPerson = angular.copy(newval);
                        var name = '';
                        if (copiedPerson.firstName) {
                            name = copiedPerson.firstName + ' ';
                        }
                        if (copiedPerson.lastName) {
                            name += copiedPerson.lastName + ' ';
                        }
                        if (copiedPerson.callName) {
                            name += " (" + copiedPerson.callName + ") ";
                        }
                        element.html(name);
                        unwatch();
                    }
                }, true);
            }
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

        }]);

