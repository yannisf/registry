'use strict';

angular.module('relationship', ['ngResource'])

    .factory('Relationship', ['$resource', function($resource) {
        return $resource('api/relationship/:id', {}, {
            fetchRelationship: {method:'GET', url:'api/relationship/child/:childId/guardian/:guardianId'},
            fetchRelationships: {method:'GET', url:'api/relationship/child/:childId', isArray: true},
            save: {method: 'PUT', url: 'api/relationship/child/:childId/guardian/:guardianId'}
        });
    }])

	.service('RelationshipService', ['ChildService', 'Relationship', 'Guardian', 'Address',
		function (ChildService, Relationship, Guardian, Address) {
		
			var fetchRelationships = function() {
				console.log('Child id is: ', ChildService.child.id);
				return Relationship.fetchRelationships({childId: ChildService.child.id});
			};

			var saveWithAddress = function (addressId, guardian, relationship) {
				return Guardian.save({addressId: addressId}, guardian).$promise.then(function() {
					return Relationship.save({
						childId: ChildService.child.id,
						guardianId: guardian.id
					}, relationship);
				});
			};

			var saveWithoutAddress = function (address, guardian, relationship) {
				Address.save(address).$promise.then(function() {
					return saveWithAddress(address.id, guardian, relationship);
				});
			};
			
			return {
				fetchRelationships: fetchRelationships,
				saveWithAddress: saveWithAddress,
				saveWithoutAddress: saveWithoutAddress
			};
		}
	]);

