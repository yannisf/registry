'use strict';

angular.module('relationship', ['ngResource'])

    .factory('Relationship', ['$resource', function($resource) {
        return $resource('api/relationship/:id', {}, {
            fetchRelationship: {method:'GET', url:'api/relationship/child/:childId/guardian/:guardianId'},
            fetchRelationships: {method:'GET', url:'api/relationship/child/:childId/guardian', isArray: true},
            saveWithGuardianAndAddress: {method: 'PUT', url: 'api/relationship/child/:childId/guardian/:guardianId/address'}
        });
    }]);
