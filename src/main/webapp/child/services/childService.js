//define(['angular', 'currentModule'],
//function(angular, currentModule) {
//    'use strict';
//
//    currentModule.service('childService', ['$http', function ($http) {
//        return {
//            fetch: function (childId) {
//                return $http.get('api/child/' + childId).then(function (response) {
//                    return response.data;
//                });
//            },
//            fetchAll: function () {
//                return $http.get('api/child/all').then(function (response) {
//                    return response.data;
//                });
//            },
//            update: function (child) {
//                return $http.put('api/child/', child).then(function (response) {
//                    return response.data;
//                });
//            },
//            remove: function (childId) {
//                return $http({method: 'DELETE', url: 'api/child/' + childId}).then(function (response) {
//                    return response.data;
//                });
//            },
//            fetchRelationships: function (childId) {
//                return $http.get('api/relationship/child/' + childId + '/guardian').then(function (response) {
//                    return response.data;
//                });
//            },
//            removeRelationship: function (relationshipId) {
//                return $http({method: 'DELETE', url: 'api/relationship/' + relationshipId}).then(function (response) {
//                    return response.data;
//                });
//            }
//        };
//    }]);
//});
