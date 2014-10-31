define(['angular'], function(angular) {
    'use strict';

    angular.module('directives', [])
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

        .directive('focus', function focus() {
            return {
                restrict: 'A',
                link: function(scope, element) {
                    element[0].focus();
                }
            };
        });
});
