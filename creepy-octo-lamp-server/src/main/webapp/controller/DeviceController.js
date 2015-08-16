app.registerCtrl('DeviceController', function($routeParams){
    var self = this;
    
    self.session = $routeParams.session;
});