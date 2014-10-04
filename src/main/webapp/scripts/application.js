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

    .directive('inputAddress', function() {
        return {
          restrict: 'E',
          scope: {
            address: "=address"
          },
          templateUrl: "templates/input-address.html"
        };
    })

    .run(['$rootScope', '$location', 'statefulChildService', function ($rootScope, $location, statefulChildService) {
        angular.extend($rootScope, {
            cancel: function () { //rename this: toChildList
                $location.url('/child/list');
            },
            toChildList: function () {
                $location.url('/child/list');
            },
            toScopedChild: function() {
                $location.url('/child/' + statefulChildService.getScopedChildId() + '/view');
            },
            go: function(path) {
                console.log('Requested ', path);
              $location.path(path);
            }
        });
    }]);

