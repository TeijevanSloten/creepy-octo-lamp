var app = angular.module("creepyoctolamp", ["ngRoute"]);

app.config(["$routeProvider", "$controllerProvider", function ($routeProvider, $controllerProvider) {

    app.registerCtrl = $controllerProvider.register;

    function requireCtrl(name) {
        return ['$q', '$rootScope', function ($q, $rootScope) {
            var deferred = $q.defer();
            $.getScript("controller/" + name + ".js").success(function () {
                $rootScope.$apply(function () {
                    deferred.resolve();
                });
            });
            return deferred.promise;
        }];
    }

    $routeProvider.when("/", {
        templateUrl: "view/app.html"
    })
        .when("/clients", {
            templateUrl: "view/deviceList.html",
            resolve: requireCtrl("DeviceListController")
        })
        .when("/device/:session", {
            templateUrl: "view/device.html",
            resolve: requireCtrl("DeviceController")
        })        
        .when("/sendMessage", {
            templateUrl: "view/message.html",
            resolve: requireCtrl("MessageController")
        })
        .otherwise({
            redirectTo: "/"
        });
}]);