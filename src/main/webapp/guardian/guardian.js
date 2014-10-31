define([
    'angular', 'currentModule',

    // side-effect dependencies
    './controllers/createGuardianController',
    './controllers/updateGuardianController',
    './services/guardianService'
],
function(angular, currentModule) {
    'use strict';

    return angular.module('guardian', currentModule.combineDependencies([]));
});
