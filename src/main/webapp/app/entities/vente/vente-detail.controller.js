(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .controller('VenteDetailController', VenteDetailController);

    VenteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Vente', 'Client', 'LigneVente', 'User'];

    function VenteDetailController($scope, $rootScope, $stateParams, previousState, entity, Vente, Client, LigneVente, User) {
        var vm = this;

        vm.vente = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pharmaApp:venteUpdate', function(event, result) {
            vm.vente = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
