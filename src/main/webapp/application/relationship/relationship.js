'use strict';

angular.module('relationship', ['ngResource'])

    .factory('Relationship', ['$resource', function($resource) {
        return $resource('api/relationship/:id', {id: '@id'}, {
            query: {method:'GET', url:'api/relationship/child/:childId', isArray: true},
            get: {method:'GET', url:'api/relationship/child/:childId/guardian/:guardianId'},
            save: {method: 'PUT', url: 'api/relationship/child/:childId/guardian/:guardianId', params: {id: null} },
        });
    }])

	.service('RelationshipService', ['$rootScope', 'ActiveCache', 'Relationship', 'Guardian', 'Address',
		function ($rootScope, ActiveCache, Relationship, Guardian, Address) {
		
			var fetchRelationships = function(childId) {
				return Relationship.fetchRelationships({childId: childId});
			};

			var saveWithAddress = function (address, guardian, relationship) {
			};

			return {
				fetchRelationships: fetchRelationships,
				saveWithAddress: saveWithAddress
			};
		}
	]);
