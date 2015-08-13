app.registerCtrl('MessageController', function ($scope, $http) {
    this.msg;
    $http.get("http://localhost:8080/creepy-octo-lamp-server/resources/action/").
            success(function (response) {
                $scope.msg = response;
            });
});