app.registerCtrl('DeviceListController', function ($scope, $http, $timeout, $location, serverEventWatcher) {
    var self = this;

    self.init = function () {
        self.getClients();
        serverEventWatcher.watchSSE('resources/action/serverevent', 'updateClients', self.handler);
    };

    self.handler = function (event) {
        console.log(event);
        self.getClients();
    };

    $scope.$on('$destroy', function () {
        serverEventWatcher.close();
    });

    self.showDevice = function (session) {
        $location.path('/device/' + session);
    };

    self.getClients = function () {
        $http.get('resources/action/devices')
            .success(function (response) {
                self.clients = response;
            });
    };

    self.init();
});