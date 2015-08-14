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
            templateUrl: "view/clientList.html",
            resolve: requireCtrl("ClientController")
        })
        .when("/sendMessage", {
            templateUrl: "view/message.html",
            resolve: requireCtrl("MessageController")
        })
        .otherwise({
            redirectTo: "/"
        });
}]);