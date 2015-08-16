app.registerCtrl('DeviceController', function ($routeParams, $http, $timeout) {
    var self = this;

    self.init = function () {
        self.session = $routeParams.session;
        self.terminalContext = 'resources/action/terminal/';

        self.getDevice();
        self.poll();
    };

    self.getDevice = function () {
        $http.get('resources/action/devices/' + self.session).success(function (response) {
            self.device = response;
        });
    };

    self.sendCommand = function () {
        $http.post(self.terminalContext + self.session,
            "command=" + encodeURIComponent(self.terminalRequest),
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