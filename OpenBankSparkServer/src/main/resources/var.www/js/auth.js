'use strict';

var i = 0;

var usersJson = {
    "users": {
        "admin": {
            "username": "admin",
            "password": "admin",
            "userRole": "admin"
        },
        "editor": {
            "username": "editor",
            "password": "editor",
            "userRole": "editor"
        },
        "guest": {
            "username": "guest",
            "password": "guest",
            "userRole": "guest"
        }
    }
};

angular.module('loginApp')
.factory('Auth', [ '$http', '$rootScope', '$window', 'Session', 'AUTH_EVENTS', 
function($http, $rootScope, $window, Session, AUTH_EVENTS) {
	var authService = {};
			
	authService.login = function(user, success, error) {
		if(i > 0){
		  $http({
			    method: 'POST',
			    url: "login",
			    dataType: 'json',
			    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest: function(obj) {
			        var str = [];
			        for(var p in obj)
			        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			        return str.join("&");
			    },
			    data: {username:user.username, password:user.password}
			}).success(function (result) {
				
				console.log(result)
				if(result.hasOwnProperty("authentication")) {
					if(result.authentication === true) {
						console.log("Success")
						
						$window.sessionStorage["userInfo"] = JSON.stringify(result);
						
						Session.create(result);
						$rootScope.currentUser = result;
	
						console.log($rootScope);
						
						$rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
						success(result);
					}else{
						console.log("Failure")
						$rootScope.$broadcast(AUTH_EVENTS.loginFailed);
						error();
					}
				}
				
				delete user.password;
			});
		}
		i++;
	};

	//check if the user is authenticated
	authService.isAuthenticated = function() {
		return !!Session.user;
	};
	
	//check if the user is authorized to access the next route
	//this function can be also used on element level
	//e.g. <p ng-if="isAuthorized(authorizedRoles)">show this only to admins</p>
	authService.isAuthorized = function(authorizedRoles) {
		if (!angular.isArray(authorizedRoles)) {
	      authorizedRoles = [authorizedRoles];
	    }
	    return (authService.isAuthenticated() &&
	      authorizedRoles.indexOf(Session.userRole) !== -1);
	};
	
	//log out the user and broadcast the logoutSuccess event
	authService.logout = function(){
		Session.destroy();
		$window.sessionStorage.removeItem("userInfo");
		$rootScope.$broadcast(AUTH_EVENTS.logoutSuccess);
	}

	return authService;
} ]);