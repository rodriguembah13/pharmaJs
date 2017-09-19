(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Medicament'];

    function HomeController ($scope, Principal, LoginService, $state,Medicament) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });
        getAccount();
/*$scope.names = [];*/
$scope.lines = [];

        $scope.addLine = function () {
            $scope.lines.push({quantite:$scope.qte,
            					prix:$scope.prix,
            					codeMedi:$scope.codeMed,
            					prixTotal:($scope.qte)*($scope.prixT)
            						});
        }
/*    $scope.AddName = function(){
        $scope.names.push({
            first:  $scope.Namer.name,
            second:"rest"
        });
    }*/
        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
               function loadAll () {
            Medicament.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function onSuccess(data, headers) {
               vm.medicaments = data;
               }
            } 
    }
})();
