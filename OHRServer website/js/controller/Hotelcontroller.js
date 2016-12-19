/**
 * Created by Muttley on 8/7/2016.
 */
'use strict';

app.controller('HotelController',['$scope', 'HotelServices' , function($scope, HotelServices) {
    var self = this;
    self.init="Yes";
    self.ticket = {action: ''};

    self.fetchAllEvents = function () {
        alert("Yes");
        self.ticket.action="call_this";
        HotelServices.fetchAllEvents(self.ticket)
            .then(
                function (d) {
                    self.tickets = d;
                },
                function (errResponse) {
                    console.log('Error while fetching ticket in TicketController');
                }
            );
    };
}]);