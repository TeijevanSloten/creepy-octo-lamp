app.registerCtrl('DeviceListController', function ($scope, $http, $timeout, $location, $interval) {
    var self = this;
    
    self.init = function () {
        self.getClients();
            self.source = new EventSource('http://localhost:8080/creepy-octo-lamp-server/resources/action/serverevent');
            self.source.addEventListener('updateClients', function(event){
                console.log(event);
                self.getClients();
            },false);
    };
    $scope.$on('$destroy', function() {
        self.source.close();
    });

    self.showDevice = function(session){
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