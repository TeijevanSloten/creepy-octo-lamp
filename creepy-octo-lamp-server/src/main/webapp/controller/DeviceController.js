app.registerCtrl('DeviceController', function ($routeParams, $http, $timeout) {
    var self = this;
    self.terminalRequest = '';


    self.init = function () {
        self.session = $routeParams.session;
        self.terminalResponse = '';
        self.getDevice();
        self.poll();
    };

    self.getDevice = function () {
        $http.get('resources/action/devices/' + self.session).success(function (response) {
            self.device = response;
        });
    };

    self.sendCommand = function () {
        $http.post('resources/action/terminal/' + self.session + '/' + self.terminalRequest, {});
        self.terminalRequest = '';
    };

    self.getTerminalResponse = function () {
        $http.get('resources/action/terminal/' + self.session)
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