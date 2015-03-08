'use strict';

angular.module('typeaheads', [])

    .service('typeAheadService', ['$http', function ($http) {
        return {
            getFirstNames: function (startsWith) {
                return $http.get('api/typeahead/firstnames', {params:{search:startsWith}}).then(
                    function (response) {
                        return response.data;
                    }
                );
            },
            getLastNames: function (startsWith) {
                return $http.get('api/typeahead/lastnames', {params:{search:startsWith}}).then(
                    function (response) {
                        return response.data;
                    }
                );
            },
            getProfessions: function (startsWith) {
                return $http.get('api/typeahead/professions', {params:{search:startsWith}}).then(
                    function (response) {
                        return response.data;
                    }
                );
            },
            getNationalities: function (startsWith) {
                return $http.get('api/typeahead/nationalities', {params:{search:startsWith}}).then(
                    function (response) {
                        return response.data;
                    }
                );
            },
            getStreetNames: function (startsWith) {
                return $http.get('api/typeahead/streetnames', {params:{search:startsWith}}).then(
                    function (response) {
                        return response.data;
                    }
                );
            },
            getNeighbourhoods: function (startsWith) {
                return $http.get('api/typeahead/neighbourhoods', {params:{search:startsWith}}).then(
                    function (response) {
                        return response.data;
                    }
                );
            },
            getCities: function (startsWith) {
                return $http.get('api/typeahead/cities', {params:{search:startsWith}}).then(
                    function (response) {
                        return response.data;
                    }
                );
            }
        };
    }])

    .run(['$rootScope', 'typeAheadService',
        function ($rootScope, typeAheadService) {
            angular.extend($rootScope, {
                typeaheads: {
                    getFirstNames: function (startsWith) {
                        return typeAheadService.getFirstNames(startsWith);
                    },
                    getLastNames: function (startsWith) {
                        return typeAheadService.getLastNames(startsWith);
                    },
                    getNationalities: function (startsWith) {
                        return typeAheadService.getNationalities(startsWith);
                    },
                    getProfessions: function (startsWith) {
                        return typeAheadService.getProfessions(startsWith);
                    },
                    getStreetNames: function (startsWith) {
                        return typeAheadService.getStreetNames(startsWith);
                    },
                    getNeighbourhoods: function (startsWith) {
                        return typeAheadService.getNeighbourhoods(startsWith);
                    },
                    getCities: function (startsWith) {
                        return typeAheadService.getCities(startsWith);
                    }
                }
            });
        }
    ]);

