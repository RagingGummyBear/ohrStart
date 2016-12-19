var app = angular.module('OHR', ['ngRoute'])

app.config(['$routeProvider', function($routeProvider){
    $routeProvider
        .when("/",{
            templateUrl: "template/Home.html"
        })
        .when("/korisnik",{
            templateUrl: "template/Korisnik.html"
        })
        .when("/direktor",{
            templateUrl:"template/Direktor.html"
        })
        .when("/prijaviproblem",{
            templateUrl: "template/PrijaviProblem.html"
        })
        .when("/tehnickapodrskaodgovor",{
            templateUrl:"template/TehnickaPodrskaOdgovor.html"
        })
        .when("/baranjezarevalidiranje",{
            templateUrl:"template/BaranjeZaRevalidiranje.html"
        })
        .when("/prikazihotelzavalidator",{
            templateUrl:"template/PrikaziHotelZaValidator.html"
        })
        .when("/tehnickapodrska",{
            templateUrl:"template/TehnickaPodrska.html"
        })
        .when("/validator",{
            templateUrl:"template/Validator.html"
        })
        .when("/admin",{
            templateUrl:"template/Admin.html"
        })
        .when("/searchresult",{
            templateUrl:"template/searchresults.html"
        })
        .when("/payment",{
            templateUrl:"template/Payment.html"
        })
        .when("/chosenHotel",{
            templateUrl:"template/chosenHotel.html"
        }).otherwise({
         redirectTo: "/"

    })
}]);
app.config(['$httpProvider', function ($httpProvider) {
  // Intercept POST requests, convert to standard form encoding
  $httpProvider.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
  $httpProvider.defaults.transformRequest.unshift(function (data, headersGetter) {
    var key, result = [];
 
    if (typeof data === "string")
      return data;
 
    for (key in data) {
      if (data.hasOwnProperty(key))
        result.push(encodeURIComponent(key) + "=" + encodeURIComponent(data[key]));
    }
    return result.join("&");
  });
}]);
