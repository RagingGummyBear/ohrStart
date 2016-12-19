'use strict';

app.controller('userController',['$scope','$window','HotelServices', function($scope,$window,HotelServices) {
	var self = this;


	self.checkLogged=false;
	self.user={
		logged:'',
		type:'',
		name:'',
		surname:'',
		email:'',
		password:''
	}
	self.users = [];

	self.pushUsers = function(){
		var admin = {
			logged:'false',
			type:'admin',
			name:'admin',
			surname:'administrator',
			email:'admin@gmail.com',
			password:'123456789'
		}
		var korisnik = {
			logged:'false',
			type:'korisnik',
			name:'Trpe',
			surname:'Trpevski',
			email:'trpe@gmail.com',
			password:'123457689'
		}
		var direktor = {
			logged:'false',
			type:'direktor',
			name:'Vlado',
			surname:'Primer',
			email:'primer_vlado@gmail.com',
			password:'1234567810'
		}
		var validator = {
			logged:'false',
			type:'validator',
			name:'Mirko',
			surname:'Primer',
			email:'primer_mirko@gmail.com',
			password:'12345678910'
		}
		var tehnickapodrshka = {
			logged:'false',
			type:'tehnickapodrshka',
			name:'Spase',
			surname:'Primer',
			email:'primer_spase@gmail.com',
			password:'12345689'
		}
		self.users.push(admin);
		self.users.push(korisnik);
		self.users.push(direktor);
		self.users.push(validator);
		self.users.push(tehnickapodrshka);
		for(var i = 0; i < self.users.length; i++){
			if(self.users[i].logged===true){
				self.checkLogged=true;
				break;
			}
		}
	}
	self.pushUsers();

	self.checkSingIn = function(){
		alert(self.user.username+" "+self.user.password);
		HotelServices.LogginUser(self.user).then(
				function (d) {
                    if(self.user.type==='admin'){
						self.checkLogged=true;
						$window.location.href ="#/admin";
					}else if(self.user.type==='korisnik'){
						self.checkLogged=true;
						$window.location.href ="#/korisnik";
						
					}else if(self.user.type==='direktor'){
						self.checkLogged=true;
						$window.location.href ="#/direktor";
					}else if(self.user.type==='validator'){
						self.checkLogged=true;
						$window.location.href ="#/validator";
					}else if(self.user.type==='tehnickapodrshka'){
						alert("Yes");
						self.checkLogged=true;
						$window.location.href ="#/tehnickapodrska";
					}
                },
                function (errResponse) {
                    console.log('Error while fetching ticket in TicketController');
                }
)}

}])