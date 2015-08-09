angular.module('base', [])
        .controller('ClientController', function ($scope, $http) {
            this.getClients = function(){
                $http.get("http://localhost:8080/creepy-octo-lamp-server/resources/action/sessions")
                    .success(function(response) {
                        $scope.clients = response;
                        console.log($scope.clients);
                    });
            };
            this.selecterProps = null;
            this.showProperties = function(index){
                if(this.selecterProps === null) return false;
                return this.selecterProps === index;
            };
            this.openProperties = function(index){
                console.log(index);
                if(this.showProperties(index) === true){
                    this.selecterProps = null;
                } else {
                    this.selecterProps = index;
                }
            };
        });