(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .controller('LigneVenteDetailController', LigneVenteDetailController);

    LigneVenteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LigneVente', 'Medicament', 'Vente'];

    function LigneVenteDetailController($scope, $rootScope, $stateParams, previousState, entity, LigneVente, Medicament, Vente) {
        var vm = this;

        vm.ligneVente = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pharmaApp:ligneVenteUpdate', function(event, result) {
            vm.ligneVente = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
