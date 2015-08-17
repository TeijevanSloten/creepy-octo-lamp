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
        self.terminalRequest='';
        self.getDevice();
//        serverEventWatcher.watchSSE('resources/action/sessionevent/' + self.session,'clientAlive',self.handler,false);
        
        self.poll();
    };

//    self.handler = function(event){
//        if(event.data === "false") {
//            console.log(event);
//            $timeout($location.path("/clients"), 200);
//        }
//    };
//
//    $scope.$on('$destroy', function() {
//        serverEventWatcher.close();
//    });

    self.getDevice = function () {
        $http.get('resources/action/devices/' + self.session).success(function (response) {
            self.device = response;
        });
    };

    self.sendCommand = function () {
        $http.post(self.terminalContext + self.session,
            'command=' + encodeURIComponent(self.terminalRequest),
            {headers: {'Content-Type': 'application/x-www-form-urlencoded'}});
        self.terminalRequest = '';
    };

    self.getTerminalResponse = function () {
        $http.get(self.terminalContext + self.session)
            .success(function (response) {
                if (response) {
                    self.terminalResponse = response;
                }
            });
    };

    self.poll = function () {
        $timeout(function () {
            self.getTerminalResponse();
            self.poll();
        }, 200);
    };

    self.init();
});