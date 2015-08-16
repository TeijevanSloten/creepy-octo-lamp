app.registerCtrl('DeviceController', function($routeParams, $http){
    var self = this;
    
    self.init = function(){
        self.getDevice();
    };
    
    self.session = $routeParams.session;
    
    self.getDevice = function(){
        $http.get('resources/action/devices/' + self.session).success(function(response){
            self.device = response;
        });
    };
    
    self.init();
});