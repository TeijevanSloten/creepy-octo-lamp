app.registerCtrl('ClientController', function ($scope, $http) {

    this.initialize = function(){
        this.selecterProps = null;
        this.getClients();
    };


    this.getClients = function () {
        $http.get("resources/action/devices")
            .success(function (response) {
                $scope.clients = response;
                console.log($scope.clients);
            });
    };


    this.showProperties = function (index) {
        if (this.selecterProps === null)
            return false;
        return this.selecterProps === index;
    };

    this.openProperties = function (index) {
        console.log(index);
        if (this.showProperties(index) === true) {
            this.selecterProps = null;
        } else {
            this.selecterProps = index;
        }
    };

    this.initialize();
});