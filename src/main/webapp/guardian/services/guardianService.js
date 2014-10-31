define(['angular', 'currentModule'],
function(angular, currentModule) {
	'use strict';

    currentModule.service('guardianService', ['$http', function ($http) {
            return {
                fetch: function (guardianId) {
                    return $http.get('api/guardian/' + guardianId).then(function (response) {
                            return response.data;
                        }
                    );
                },
                fetchRelationship: function (childId, guardianId) {
                    return $http.get('api/relationship/child/' + childId + '/guardian/' + guardianId).then(function (response) {
                            return response.data;
                        }
                    );
                },
                updateGuardianAndRelationship: function (childId, guardianId, relationship) {
                    return $http.put('api/relationship/child/' + childId + '/guardian/' + guardianId, relationship).then(function (response) {
                            return response.data;
                        }
                    );
                }
            };
        }
    ]);
});
