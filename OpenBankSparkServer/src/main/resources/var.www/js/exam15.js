var app15 = angular.module('app15', []);
 
app15.controller('mainCtrl', function($scope, $http) {
 
  // Get data from the json file and display it
  $scope.getData = function() {
    $http.get("http://54.87.197.206:8080/SparkServer/admin/api/v1/listOf/users").success(
      function(data){
        $scope.users = data;
      }
    );
  }
 
  // Get this Chrome extension if you get the
  // CORS error
  // Allow-Control-Allow-Origin
  
 
});