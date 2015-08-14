app.registerCtrl('ClientController', function ($scope, $http, $timeout) {
    var self = this;

    self.init = function () {
        self.selecterProps = null;
        self.getClients();
        self.poll();
    };


    self.getClients = function () {
        $http.get("resources/action/devices")
            .success(function (response) {
                $scope.clients = response;
                console.log($scope.clients);
            });
    };

    self.poll = function () {
        $timeout(function () {
            self.getClients()
            self.poll();
        }, 500);
    };

    self.showProperties = function (index) {
        if (self.selecterProps === null)
            return false;
        return self.selecterProps === index;
    };

    self.openProperties = function (index) {
        console.log(index);
        if (self.showProperties(index) === true) {
            self.selecterProps = null;
        } else {
            self.selecterProps = index;
        }
    };

    self.init();
});