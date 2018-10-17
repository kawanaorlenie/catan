// Define the `catanApp` module
var catanApp = angular.module('catanApp', [ 'ctn-hex-directive',
        'ctn-vertix-directive' , 'ctn-edge-directive','ctn-droppable-directive']);

// Define the `BoardController` controller on the `catanApp` module
catanApp.controller('BoardController', function BoardController($scope,
        CatanService) {

    $scope.handleDrop = function () {
        alert('Item has been dropped');
    }
//
//    $scope.init = function () {
//        CatanService.init();
//    };

    $scope.addMessage = function (name) {
        console.log('in addMessage: ' + name);
        CatanService.send($scope.game);
    };

    CatanService.receive().then(null, null, function (message) {
        $scope.game = message;
        $scope.game.hand = generateHand();
    });
    
//    CatanService.init();

});

catanApp.directive('ctnPath', [ '$document', '$compile',
        function ($document, $compile) {
            return {

                link : function (scope, element) {

                    element.css({
                        cursor : 'pointer'
                    });

                    var el = element[0];

                    el.addEventListener('dragstart', function (e) {
                        e.dataTransfer.effectAllowed = 'move';
                        e.dataTransfer.setData('Text', this.id);
                        this.classList.add('drag');
                        return false;
                    }, false);

                    el.addEventListener('dragend', function (e) {
                        this.classList.remove('drag');
                        return false;
                    }, false);

                }

            };
        } ]);

catanApp.service("CatanService", function ($q, $timeout) {
    var service = {}, listener = $q.defer(), socket = {
        client : null,
        stomp : null
    }, messageIds = [];

    service.RECONNECT_TIMEOUT = 30000;
    service.SOCKET_URL = '/gs-guide-websocket';
    service.CHAT_TOPIC = '/topic/game';
    service.CHAT_BROKER = "/app/game";

    service.receive = function () {
        return listener.promise;
    };

    service.send = function (message) {
        var id = Math.floor(Math.random() * 1000000);
        socket.stomp.send(service.CHAT_BROKER, {
            priority : 9
        }, JSON.stringify(message));
        messageIds.push(id);
    };

    service.init = function (message) {
        socket.stomp.send("/app/initgame", {});
    };

    var reconnect = function () {
        $timeout(function () {
            initialize();
        }, this.RECONNECT_TIMEOUT);
    };

    var getMessage = function (data) {
        return JSON.parse(data);
    };

    var startListener = function () {
        socket.stomp.subscribe(service.CHAT_TOPIC, function (data) {
            listener.notify(getMessage(data.body));
        });
        socket.stomp.send("/app/initgame", {});
    };

    var initialize = function () {
        socket.client = new SockJS(service.SOCKET_URL);
        socket.stomp = Stomp.over(socket.client);
        socket.stomp.connect({}, startListener);
        socket.stomp.onclose = reconnect;
//        socket.stomp.onopen = 
    };

    initialize();
    return service;
});

function generateHand() {

    var paths = [];
    for (var i = 0; i < 15; i++) {
        paths.push({
            "id" : i
        });
    }

    var settlements = [];
    for (var i = 0; i < 5; i++) {
        settlements.push({
            "id" : i
        });
    }

    var cities = [];
    for (var i = 0; i < 4; i++) {
        cities.push({
            "id" : i
        });
    }

    return {
        "paths" : paths,
        "settlements" : settlements,
        "cities" : cities
    };
}