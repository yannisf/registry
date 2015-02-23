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
                        {id: uuid4.generate(), name:'100ο Νηπιαγωγείο Αθηνών'},
                        {id: uuid4.generate(), name:'5ο Νηπιαγωγείο Αθηνών'},
                        {id: uuid4.generate(), name:'22ο Νηπιαγωγείο Νέας Ιωνίας'}
                    ]
                },
                viewData: {
                    activeSchool: null
                }
            });
            
            $scope.addSchool = function() {
                var school = {
                    id: uuid4.generate(),
                    name: 'Νεο σχολείο'
                };
                $scope.data.schools.push(school);
            };
            
            $scope.setActive = function(school) {
                $scope.viewData.activeSchool = school;
            };
            
            console.log("Into management");
        }

    ]);