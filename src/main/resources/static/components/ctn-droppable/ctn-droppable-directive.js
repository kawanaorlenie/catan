'use strict';

angular
        .module('ctn-droppable-directive', [])
        .directive(
                'ctnDroppable',
                function () {
                    return {
                        restrict : 'A',
                        link : function (scope, element) {

                            scope.dragSupported = function () {

                                var getEdgeByCoordinates = function (x, y, z) {
                                    console.log(x,y,z);
                                    var edges = scope.game.board.edges;
                                    return edges.filter(function (obj) {
                                        return obj.x == x && obj.y == y
                                                && obj.z == z;
                                    });
                                };
                                var edge = scope.edge;
                                
                                if (edge.z == 2){
                                console.log('x: ' + edge.x + ' y: ' + edge.y + ' z: '+edge.z);
                                console.log(getEdgeByCoordinates(edge.x
                                        + (edge.y % 2) * (-1),
                                        edge.y + 1, edge.z - 1));

                                if (getEdgeByCoordinates(edge.x
                                        + (edge.y % 2) * (-1),
                                        edge.y + 1, edge.z - 1).pathColor == 'blue')
                                    console.log('blue');

                                }
                                return scope.edge.pathColor == null;
//                                        && getEdgeByCoordinates(egde.x, egde.y,
//                                                egde.z + 1).pathColor == 'blue'
//                                        && getEdgeByCoordinates(egde.x, egde.y,
//                                                egde.z - 1).pathColor == 'blue'
//                                        && getEdgeByCoordinates(egde.x
//                                                + (egde.y % 2) * (-1),
//                                                egde.y - 1, egde.z + 1).pathColor == 'blue'
//                                        && getEdgeByCoordinates(egde.x
//                                                + (egde.y % 2) * (-1),
//                                                egde.y + 1, egde.z - 1).pathColor == 'blue';
                            };

                            console.log('droppable')
                            element.on('dragover', function (e) {
                                e.preventDefault();
                                console.log('dragover');
                            });

                            element.on('drop', function (e) {
                                console.log('drop');
                                // Stops some browsers from redirecting.
                                if (e.stopPropagation)
                                    e.stopPropagation();

                                // this.classList.remove('over');

                                var item = document
                                        .getElementById(e.dataTransfer
                                                .getData('Text'));
                                this.appendChild(item);
                                // var child = el.childNodes[0];
                                // child.setAttribute('edge-pathcolor', 'whoa');
                                scope.edge.pathColor = "blue";
                                // console.log(child.attributes);
                                console.log(scope.game.board.edges);
                                scope.addMessage("balbla2"
                                        + item.getAttribute("id")
                                // + " " + child.getAttribute("edge-x")
                                // + child.getAttribute("edge-y")
                                // + child.getAttribute("edge-z")
                                );
                            });

                            element.on('dragenter', function (e) {
                                e.preventDefault();
                                console.log('dragenter');
                            });

                            element.on('dragleave', function (e) {
                                console.log('dragleave');
                            });
                        }
                    }
                });