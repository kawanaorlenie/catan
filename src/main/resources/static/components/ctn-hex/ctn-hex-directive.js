'use strict';

angular.module('ctn-hex-directive', [])

.directive('ctnHex',  function () {
    return {
        restrict : 'E',
        templateUrl : 'components/ctn-hex/ctn-hex.html',
        link : function (scope, element) {

            var a = 3.75;
            var h = a / 2 * Math.sqrt(3);

            var field = scope.field;

            var top = (field.y - 1) * a * 1.5;
            var left = (field.x - 1) * 2 * h + field.y % 2 * +h;

            scope.field.top = top;
            scope.field.left = left;

        }
    };
} );
