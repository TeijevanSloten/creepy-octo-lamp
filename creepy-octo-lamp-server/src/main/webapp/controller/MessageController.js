app.registerCtrl('MessageController', function ($scope, $http) {
    $http.post("http://localhost:8080/creepy-octo-lamp-server/resources/action/", {});
});