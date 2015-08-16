app.registerCtrl('ClientController', function ($scope, $http, $timeout) {
    var self = this;
    
    self.init = function () {
        self.selecterProps = null;
        self.getClients();
        self.poll();
    };

    self.log = function(){
        console.log('log');
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