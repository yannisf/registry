'use strict';

angular.module('schoolApp', ['ngRoute', 'ui.bootstrap', 'child', 'guardian'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .otherwise({
                redirectTo: '/child/list'
            });
    }])

    .run(['$rootScope', '$location', 'statefulChildService', function ($rootScope, $location, statefulChildService) {
        angular.extend($rootScope, {
            cancel: function () { //rename this: toChildList
                $location.url('/child/list');
            },
            toScopedChild: function() {
                $location.url('/child/' + statefulChildService.getScopedChildId() + '/view');
            }
        });
    }]);

