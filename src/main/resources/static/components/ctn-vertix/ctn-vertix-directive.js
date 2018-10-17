'use strict';

angular.module('ctn-vertix-directive', []).directive(
        'ctnBuildable',
        function () {
            return {
                templateUrl : 'components/ctn-vertix/ctn-vertix.html',
                restrict : 'E',
                link : function (scope, element) {

                    var buildable = 2;
                    var a = 3.75;
                    var h = a / 2 * Math.sqrt(3);

                    var vertix = scope.vertix;

                    scope.vertix.top = (vertix.y - 1) * 1.5 * a - 0.5
                            * buildable + (vertix.z - 1) * 2 * a;

                    scope.vertix.left = (vertix.x - 1) * 2 * h + h - 0.5
                            * buildable + vertix.y % 2 * h;

                }
            };
        });