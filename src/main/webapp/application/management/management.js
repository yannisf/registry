'use strict';

angular.module('management', ['ngRoute', 'ngResource', 'ui.bootstrap', 'uuid4'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/management', {
                templateUrl: 'application/management/management.html',
                controller: 'manageSchools'
            });
    }])
    
    .factory('School', ['$resource', function($resource) {
        return $resource('api/foundation/school/:id', { }, {
            save: {method: 'PUT', url: 'api/foundation/school'},
            fetchClassroomsForSchool: {method: 'GET', url: 'api/foundation/school/:schoolId/classroom', isArray: true},
        });
    }])

    .controller('manageSchools', ['$scope', 'uuid4', 'School', function ($scope, uuid4, School) {
            angular.extend($scope, {
                data: {
                    schools: School.query(),
                    groups: [],
                    schoolsStatic: [
                        {
                            id: uuid4.generate(), 
                            name:'100ο Νηπιαγωγείο Αθηνών', 
                            groups: [
                                {
                                    id: uuid4.generate(),
                                    name: 'Κλασικό'
                                }
                            ]
                        },                        {
                            id: uuid4.generate(),
                            name:'5ο Νηπιαγωγείο Αθηνών',
                            groups: [
                                {
                                    id: uuid4.generate(),
                                    name: 'Κλασικό'
                                },
                                {
                                    id: uuid4.generate(),
                                    name: 'Ολοήμερο'
                                },
                                {
                                    id: uuid4.generate(),
                                    name: 'Ένταξη'
                                }
                            ]
                        },                        {
                            id: uuid4.generate(),
                            name:'22ο Νηπιαγωγείο Νέας Ιωνίας',
                            groups: [
                                {
                                    id: uuid4.generate(),
                                    name: 'Κλασικό'
                                },
                                {
                                    id: uuid4.generate(),
                                    name: 'Ολοήμερο'
                                }
                            ]
                        }
                    ]
                },
                viewData: {
                    activeSchool: null,
                    activeSchoolHasGroups: false,
                    activeGroup: null
                }
            });
            
            $scope.$watch('viewData.activeSchool', 
                function(newval) {
                    if (newval) {
                        console.log('Active school is now ', newval);
                        $scope.data.groups = School.fetchClassroomsForSchool({schoolId: newval.id});
                    }
                }
            );
            
            $scope.$watchCollection('data.groups',
                function(newval) {
                    $scope.viewData.activeSchoolHasGroups = (newval && newval.length > 0);
                }
            );
            
            $scope.setActive = function(school, event) {
                //jQuery(event.target.closest('tbody')).find('input:visible').length === 0
                $scope.viewData.activeSchool = school;
            };
            
            $scope.setActiveGroup = function(group, event) {
                //jQuery(event.target.closest('tbody')).find('input:visible').length === 0
                $scope.viewData.activeGroup = group;
            };

            $scope.addSchool = function() {
                var school = {
                    id: uuid4.generate(),
                    name: 'Νεο σχολείο',
                };
                School.save(school).$promise.then(
                    function() {
                        $scope.data.schools = School.query();
                    }
                );
            };

            $scope.addGroup = function() {
                var group = {
                    id: uuid4.generate(),
                    name: 'Νεο τμήμα'
                };
                $scope.viewData.activeSchool.groups.push(group);
            };
            
        }

    ]);