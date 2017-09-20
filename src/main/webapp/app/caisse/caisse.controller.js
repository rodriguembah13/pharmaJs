(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .controller('CaisseController', CaisseController);

    CaisseController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService','$scope','Med','AlertService'];

    function CaisseController ($state, Auth, Principal, ProfileService, LoginService,$scope,Med,AlertService, $filter, $http, $q) {
    	var vm = this;
    	$scope.lines = [];
		loadAll ();
        $scope.addLine = function () {
            $scope.lines.push({quantite:$scope.qte,
            					prix:$scope.prix,
            					codeMedi:$scope.codeMed,
            					prixTotal:($scope.qte)*($scope.prixT)
            						});
        }
  		function loadAll () {
            Med.query({
            }, onSuccess, onError);
            function onSuccess(data) {
              vm.medicaments = data;
            }function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        function getEtatSt (id) {
            Med.get({id: id}, onSuccess, onError);
            function onSuccess(data) {
              vm.etat = data;
            }function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        vm.counter = 1;
			function getMedicament (id) {
            Med.get({id: id}, onSuccess, onError);
            function onSuccess(data) {
              vm.medicament = data;
            }function onError(error) {
                AlertService.error(error.data.message);
            }
        }
  function onSelectCallback (){
    vm.counter++;
    
  };
  // add user
  $scope.addUser = function addUser() {
    $scope.lines.push({
      id: $scope.lines.length+1,
      medicament: '',
      prix: null,
      quantite: null,
      prixTotal: ""
    });
  }; 
    $scope.showGroup = function(user) {
    if(user.group && $scope.groups.length) {
      var selected = $filter('filter')($scope.groups, {id: user.group});
      return selected.length ? selected[0].text : 'Not set';
    } else {
      return user.groupName || 'Not set';
    }
  };
  $scope.checkName = function(data, id) {
   Med.get({id: id}, onSuccess, onError);
            function onSuccess(data) {
              vm.etat = data;
            }function onError(error) {
                AlertService.error(error.data.message);
            }
         if(vm.etat){
      return "petit";
    }
  };

  // filter users to show
  $scope.filterUser = function(user) {
    return user.isDeleted !== true;
  };

  // mark user as deleted
  $scope.deleteUser = function(id) {
    var filtered = $filter('filter')($scope.users, {id: id});
    if (filtered.length) {
      filtered[0].isDeleted = true;
    }
  };
  // cancel all changes
  $scope.cancel = function() {
    for (var i = $scope.lines.length; i--;) {
      var user = $scope.lines[i];    
      // undelete
      if (user.isDeleted) {
        delete user.isDeleted;
      }
      // remove new 
      if (user.isNew) {
        $scope.lines.splice(i, 1);
      }      
    };
  };

  // save edits
/*  $scope.saveTable = function() {
    var results = [];
  for (var i = $scope.lines.length; i--;) {
      var user = $scope.lines[i];
     // LigneVente.save(user, onSaveSuccess, onSaveError);
      function onSuccess(data) {
              vm.meds = data;
            }function onError(error) {
                AlertService.error(error.data.message);
            }
        
    }
    return $q.all(results);
  };*/
  }

  
})();

