app.registerCtrl('DeviceListController', function ($scope, $http, $timeout, $location) {
    var self = this;
    
    self.init = function () {
        self.getClients();
        self.poll();
    };

    self.showDevice = function(session){
        $location.path('/device/' + session);
    };

    self.getClients = function () {
        $http.get('resources/action/devices')
            .success(function (response) {
                self.clients = response;
            });
    };

    self.poll = function () {
        $timeout(function () {
            self.getClients();
            self.poll();
        }, 500);
    };

    self.init();
});