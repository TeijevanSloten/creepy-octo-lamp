app.registerCtrl('DeviceController', function (keyboard, serverEventWatcher, $routeParams, $http, $timeout, $location, $scope) {
    var self = this;
    keyboard
    .on('ENTER', function(name, code) { 
        if(self.terminalRequest !== ""){
            self.sendCommand();
        }
    });
    
    self.init = function () {
        self.session = $routeParams.session;
        self.terminalContext = 'resources/action/terminal/';

        self.getDevice();
        serverEventWatcher.watchSSE('resources/sse/sessionevent/' + self.session);
        serverEventWatcher.watchEvent('clientAlive',self.handleClientAlive);
        serverEventWatcher.watchEvent('clientTerminalResponse',self.handleClientTerminalResponse);
    };

    self.handleClientAlive = function(event){
        if(event.data === "false") {
            console.log(event);
            $timeout($location.path("/clients"), 200);
        }
    };

    self.handleClientTerminalResponse = function(event){
        console.log(event);
        self.terminalResponse = event.data;
    };
    
    $scope.$on('$destroy', function() {
        serverEventWatcher.close();
    });

    self.getDevice = function () {
        $http.get('resources/action/devices/' + self.session).success(function (response) {
            self.device = response;
        });
    };

    self.sendCommand = function () {
        $http.post(self.terminalContext + self.session,
            'command=' + encodeURIComponent(self.terminalRequest),
            {headers: {'Content-Type': 'application/x-www-form-urlencoded'}});
    };

    self.init();
});