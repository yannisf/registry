'use strict';

angular.module('schoolApp')

    .run(['$rootScope', '$location', 'School', function ($rootScope, $location, School) {
        var school = { id: null, name: "Σχολείο" };
        var classroom = { id: null, name: "Τμήμα" };
        var term = { id: null, name: "Χρονιά" };
        var group = { id: null, name: "Τμήμα/Χρονιά" };
        var child = { id: null, name: "Παιδί" };
        
        $rootScope.central = {
            school: school,
            classroom: classroom,
            term: term,
            group: group,
            child: child
        };
        
        $rootScope.toChildList = function (groupId) {
            console.log('Group id is: ', groupId);
            var groupInfo = School.groupInfo({groupId: groupId});
            $rootScope.central.group.id = groupId;
            $rootScope.central.school.name = groupInfo.school;
            $rootScope.central.classroom.name = groupInfo.classroom;
            $rootScope.central.term.name = groupInfo.term;
            $location.url('/child/class/' + groupId + '/list');
        };

    }]);
