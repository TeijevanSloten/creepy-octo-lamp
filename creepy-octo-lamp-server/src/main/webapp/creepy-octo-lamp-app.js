var app = angular.module('creepyoctolamp', ['ngRoute', 'ui.bootstrap']);

app.config(['$routeProvider', '$controllerProvider', function ($routeProvider, $controllerProvider) {

        app.registerCtrl = $controllerProvider.register;

        function requireCtrl(name) {
            return ['$q', '$rootScope', function ($q, $rootScope) {
                    var deferred = $q.defer();
                    $.getScript('controller/' + name + '.js').success(function () {
                        $rootScope.$apply(function () {
                            deferred.resolve();
                        });
                    });
                    return deferred.promise;
                }];
        }

        $routeProvider.when('/', {
            templateUrl: 'view/app.html'
        })
                .when('/clients', {
                    templateUrl: "view/deviceList.html",
                    resolve: requireCtrl("DeviceListController")
                })
                .when('/device/:session', {
                    templateUrl: "view/device.html",
                    resolve: requireCtrl("DeviceController")
                })
                .when('/sendMessage', {
                    templateUrl: "view/message.html",
                    resolve: requireCtrl("MessageController")
                })
                .otherwise({
                    redirectTo: "/"
                });
    }]);

app.service('serverEventWatcher', function(){
    var self = this;
        self.setSource = function(url){

        };
        
        self.watchSSE = function(url, event, f, auth){
            self.source = new EventSource(url);
            self.source.addEventListener(event, f,auth);
        };
        
        self.close = function(){
            self.source.close();
        };
        
});

app.service('keyboard', function ($document, $timeout, keyCodes) {
    var self = this;
    this.keyHandlers = {};

    $document.on('keydown', function (event) {
        var keyDown = self.keyHandlers[event.keyCode];
        if (keyDown) {
            event.preventDefault();
            $timeout(function () {
                keyDown.callback(keyDown.name, event.keyCode);
            });
        }
    });
    this.on = function (keyName, callback) {
        var keyCode = keyCodes[keyName];
        this.keyHandlers[keyCode] = {callback: callback, name: keyName};
        return this;
    };
});