'use strict';

angular.module('typeaheads', [])

    .service('typeAheadService', ['$http', function ($http) {
        return {
            getFirstNames: function (startsWith) {
                return $http.get('api/typeahead/firstnames', {params:{search:startsWith}}).then(function (response) {
                    return response.data;
                })
            },
            getLastNames: function (startsWith) {
                return $http.get('api/typeahead/lastnames', {params:{search:startsWith}}).then( function (response) {
                    return response.data;
                })
            },
            getProfessions: function (startsWith) {
                return $http.get('api/typeahead/professions', {params:{search:startsWith}}).then( function (response) {
                    return response.data;
                })
            },
            getNationalities: function (startsWith) {
                return $http.get('api/typeahead/nationalities', {params:{search:startsWith}}).then( function (response) {
                    return response.data;
                })
            },
            getStreetNames: function (startsWith) {
                return $http.get('api/typeahead/streetnames', {params:{search:startsWith}}).then( function (response) {
                    return response.data;
                })
            },
            getNeighbourhoods: function (startsWith) {
                console.log('getNeighbourhoods ', startsWith);
                return $http.get('api/typeahead/neighbourhoods', {params:{search:startsWith}}).then( function (response) {
                    return response.data;
                })
            },
            getCities: function (startsWith) {
                return $http.get('api/typeahead/cities', {params:{search:startsWith}}).then( function (response) {
                    return response.data;
                })
            }
        }
    }])

    .run(['$rootScope', 'typeAheadService',
        function ($rootScope, typeAheadService) {
            angular.extend($rootScope, {
                typeaheads: {
                    getFirstNames: function (startsWith) {
                        return typeAheadService.getFirstNames(startsWith).then(function(response) {
                            return response;
                        })
                    },
                    getLastNames: function (startsWith) {
                        return typeAheadService.getLastNames(startsWith).then(function(response) {
                            return response;
                        })
                    },
                    getNationalities: function (startsWith) {
                        return typeAheadService.getNationalities(startsWith).then(function(response) {
                            return response;
                        })
                    },
                    getProfessions: function (startsWith) {
                        return typeAheadService.getProfessions(startsWith).then(function(response) {
                            return response;
                        })
                    },
                    getStreetNames: function (startsWith) {
                        return typeAheadService.getStreetNames(startsWith).then(function(response) {
                            return response;
                        })
                    },
                    getNeighbourhoods: function (startsWith) {
                        return typeAheadService.getNeighbourhoods(startsWith).then(function(response) {
                            return response;
                        })
                    },
                    getCities: function (startsWith) {
                        return typeAheadService.getCities(startsWith).then(function(response) {
                            return response;
                        })
                    }
                }
            });

        }]);

