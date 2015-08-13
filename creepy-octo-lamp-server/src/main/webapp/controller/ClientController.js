app.registerCtrl('ClientController', function ($scope, $http) {
            this.selecterProps = null;
    
            $scope.connect = function () {
                return new WebSocket("ws://localhost:8080/creepy-octo-lamp-server/wsGUI");
            };    
    
            this.websock = $scope.connect();
    
            this.websock.onclose = function(event){
                $scope.connect();
            };
            
            this.websock.onmessage = function(event){
                console.log(event.data);
                if(event.data === "updateClients"){
                    $scope.getClients();
                }
            };
            
            $scope.getClients = function () {
                $http.get("http://localhost:8080/creepy-octo-lamp-server/resources/action/sessions")
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
        });