'use strict';

angular.module('schoolApp')

    .run(['$rootScope', '$location', function ($rootScope, $location) {
        var school = { id: null, name: "Σχολείο" };
        var group = { id: null, name: "Τμήμα" };
        var term = { id: null, name: "Χρονιά" };
        var child = { id: null, name: "Παιδί" };
        
        $rootScope.$watch('central.child.id', function(newval) {
            if (newval) {
                child.name = "New name";
            }
        });
        
        var goToGroup = function() {
            if (group.id !== null) {
                $location.url('/child/class/' + group.id + '/list');
            }
        };
        
        var resetSchool = function() {
            resetGroup();
            school = { id: null, name: null };
        };
        
        var resetGroup = function() {
            resetTerm();
            group = { id: null, name: null };
        };
        
        var resetTerm = function() {
            resetChild();
            term = { id: null, name: null };
        };
        
        var resetChild = function() {
            child = { id: null, name: null };
        };
        
        $rootScope.central = {
            school: school,
            group: group,
            term: term,
            child: child,
            goToGroup: goToGroup,
            resetSchool: resetSchool,
            resetGroup: resetGroup,
            resetTerm: resetTerm,
            resetChild: resetChild
        };
    }]);
