'use strict';

angular.module('guardian')

    .directive('telephones', ['uuid4', 'ListService',
        function (uuid4, ListService) {

            var telephoneTypes;

            return {
                restrict: 'E',
                replace: true,
                scope: {
                    model: "=",
                },
                templateUrl: "application/guardian/telephones.tpl.html",
                link: function(scope) {
                    scope.addTelephone = function () {
                        var telephone = { id: uuid4.generate() };
                        scope.model.push(telephone);
                    };
        
                    scope.removeTelephone = function (telephoneIndex) {
                        scope.model.splice(telephoneIndex, 1);
                    };
                    
                    if (telephoneTypes) {
                        scope.telephoneTypes = telephoneTypes;
                    } else {
                        ListService.telephoneTypes().then(
                            function (data) {
                                telephoneTypes = data;
                                scope.telephoneTypes = telephoneTypes;
                            }
                        );
                    }
                }
            };
        }]);
