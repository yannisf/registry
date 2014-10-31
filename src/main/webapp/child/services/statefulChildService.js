//define(['angular', 'currentModule', 'scripts/vendor/angular-route/angular-route'],
//function(angular, currentModule) {
//    'use strict';
//
//    currentModule.addDependencies('ngRoute').service('statefulChildService', ['$routeParams', function ($routeParams) {
//        var childId;
//        var childIds;
//        var childAddressId;
//
//        return {
//            getScopedChildId: function () {
//                if ($routeParams.childId) {
//                    childId = $routeParams.childId;
//                }
//
//                return childId;
//            },
//            setScopedChildId: function (value) {
//                childId = value;
//            },
//            getScopedChildAddressId: function () {
//                return childAddressId;
//            },
//            setScopedChildAddressId: function (value) {
//                childAddressId = value;
//            },
//            getChildIds: function() {
//                return childIds;
//            },
//            setChildIds: function(value) {
//                childIds = value;
//            }
//        }
//    }]);
//});
