'use strict';

angular.module('management')

    .directive('focusAndSelect', [function () {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                element.on('click', function ($event) {
                    if ($event.target.nodeName !== 'INPUT') {
                        element.find('input')[0].select();
                    }
                });
            }
		};
	}]);