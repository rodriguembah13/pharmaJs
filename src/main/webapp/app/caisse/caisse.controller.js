(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .controller('CaisseController', CaisseController);

    CaisseController.$inject = ['$state', 'Auth','Principal', 'ProfileService','Client', 'LigneVente','LoginService','$scope','Medicament','AlertService','$filter','$http','$q'];

    function CaisseController ($state, Auth,Principal, ProfileService,Client,LigneVente, LoginService,$scope,Medicament,AlertService, $filter, $http, $q) {
    	var vm = this;
    	// vm.ligneVente = entity;
    	$scope.lines = [];
    	$scope.visible =false;
    	$scope.cl =null;
		loadAll ();
		loadAllClient ();
		vm.country = {};
        $scope.addLine = function () {
            $scope.lines.push({quantite:$scope.qte,
            					prix:$scope.prix,
            					codeMedi:$scope.codeMed,
            					prixTotal:($scope.qte)*($scope.prixT)
            						});
        }
    		function loadAllClient () {
            Client.query({
            }, onSuccess, onError);
            function onSuccess(data) {
              vm.clients = data;
            }function onError(error) {
                AlertService.error(error.data.message);
            }
        }
  		function loadAll () {
            Medicament.query({
            }, onSuccess, onError);
            function onSuccess(data) {
              vm.medicaments = data;
            }function onError(error) {
                AlertService.error(error.data.message);
            }
        }
$scope.removeUser = function(index) {
    $scope.lines.splice(index, 1);
  };

  // add user
  $scope.addUser = function addUser() {
    $scope.lines.push({
      //idL: $scope.lines.length+1,
      medicament: null,
      client: vm.country,
      quantite: null,
      //prixTotal:null
    });
  }; 
  $scope.checkName1 = function(data, id) {
   Medicament.get({id: id}, onSuccess, onError);
            function onSuccess(data) {
              vm.etat = data;
            }function onError(error) {
                AlertService.error(error.data.message);
            }
         if(vm.etat.stock.quantite<data||vm.etat.stock==null){
      		return "petit";
    }
  };

 // cancel all changes
  $scope.cancel = function() {
    for (var i = $scope.lines.length; i--;) {
          $scope.lines.splice(i, 1);
          
    };
  };

  // save edits
  $scope.saveTable = function() {
    var results = [];
  /*for (var i = $scope.lines.length; i--;) {
      var user = $scope.lines[i];
      LigneVente.save(user, onSaveSuccess, onSaveError);
      function onSuccess(data) {
              vm.meds = data;
            }function onError(error) {
                AlertService.error(error.data.message);
            }

            //user.medicament=Medicament.get({id: 1});
       results.push($http.post('/api/ligne-ventes', user));
        
    }*/
    results.push($http.post('/api/ventesLigne',$scope.lines));
    return $q.all(results);
   // $scope.cancel();
  };  $scope.saveUser = function(data, idL) {
    //$scope.user not updated yet
    //angular.extend(data, {idL: idL});
    $scope.lines.push(data);
    $scope.visible;
  };
    }
})();

