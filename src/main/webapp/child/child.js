define([
    'angular', 'currentModule',

    // side-effect dependencies
    './controllers/listChildController',
    './controllers/createChildController',
    './controllers/updateChildController',
    './controllers/removeChildModalController',
    './controllers/removeRelationshipModalController'
//    './services/childService',
//    './services/statefulChildService',
],
function(angular, currentModule) {
    'use strict';

    return angular.module('child', currentModule.combineDependencies([]));
});
