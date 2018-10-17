'use strict';

angular.module('ctn-edge-directive', [])

.directive('ctnPathable', function () {
    return {
        templateUrl : 'components/ctn-edge/ctn-edge.html',
        restrict : 'E',
        link : function (scope, element) {
            var el = element[0];

            var a = 3.75;
            var h = a / 2 * Math.sqrt(3);
            var width = 1;
            var height = 3;
            var edge = scope.edge;

            var top = (edge.y - 1) * a * 1.5;
            var left = (edge.x - 1) * 2 * h + edge.y % 2 * +h

            if (edge.z == 3) {
                top = top + a * 1.75 - height * 0.5;
                left = left - width * 0.5 + 0.5 * h;

            }

            if (edge.z == 1) {
                top = top - 0.5 * height + 0.25 * a;
                left = left - width * 0.5 + 0.5 * h;
            }

            if (edge.z == 2) {
                top = top - 0.5 * height + a;
                left = left - width * 0.5;

            }

            scope.edge.top = top;
            scope.edge.left = left;

        }
    };
});