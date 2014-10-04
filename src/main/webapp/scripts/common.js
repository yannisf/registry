'use strict';

angular.module('commonSchoolServices', [])

    .service('ListService', ['$http', function ($http) {
        return {
            relationshipTypes: function () {
                return $http.get('api/relationship/types').then(
                    function (response) {
                        return response.data;
                    }
                );
            },
            telephoneTypes: function () {
                return $http.get('api/guardian/telephone/types').then(
                    function (response) {
                        return response.data;
                    });
                }
            };
        }
    ]);