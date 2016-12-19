app.factory('HotelServices', ['$http', '$q', function($http, $q){
    return{
        LogginUser:function(tmp){
            tmp.action='loginUser';  
            tmp.role='admin';
          return $http.post('./src/dummyTestPhp/phpDummyTest.php',tmp)
                .then(
                function(d){
                    console.log(d);
                        //console.log(JSON.stringify(self.event.tag_list)+" pri fetching");
                },
                function(errResponse){
                    console.log('Error while fetching all events in EventController');
                }
    )}
}
}]);