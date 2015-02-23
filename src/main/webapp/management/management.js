'use strict';

angular.module('management', ['ngRoute', 'ngResource', 'ui.bootstrap', 'uuid4'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/management', {
                templateUrl: 'management/management.html',
                controller: 'manageSchools'
            });
    }])

    .controller('manageSchools', ['$scope', 'uuid4', function ($scope, uuid4) {
            angular.extend($scope, {
                data: {
                    schools: [
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
                    activeGroup: null
                }
            });
            
            $scope.setActive = function(school, event) {
                //jQuery(event.target.closest('tbody')).find('input:visible').length === 0
                $scope.viewData.activeSchool = school;
            };
            
            $scope.setActiveGroup = function(group, event) {
                //jQuery(event.target.closest('tbody')).find('input:visible').length === 0
                $scope.viewData.activeGroup = group;
            };

            $scope.activeSchoolHasGroups = function() {
                return angular.isDefined($scope.viewData.activeSchool) && 
                    $scope.viewData.activeSchool !== null &&
                    angular.isDefined($scope.viewData.activeSchool.groups) &&
                    $scope.viewData.activeSchool.groups.length > 0;
            };
            
            $scope.addSchool = function() {
                var school = {
                    id: uuid4.generate(),
                    name: 'Νεο σχολείο',
                    groups: []
                };
                $scope.data.schools.push(school);
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