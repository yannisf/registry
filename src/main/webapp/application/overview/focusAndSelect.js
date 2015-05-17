'use strict';

angular.module('overview')

    .directive('focusAndSelect', [function () {
        return {
            restrict: 'A',
            link: function (scope, element) {
                element.on('click dblclick', function ($event) {
                    if ($event.target.nodeName !== 'INPUT') {
                        element.find('input')[0].select();
                    }
                });
            }
		};
	}]);