(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .controller('LigneVenteDeleteController',LigneVenteDeleteController);

    LigneVenteDeleteController.$inject = ['$uibModalInstance', 'entity', 'LigneVente'];

    function LigneVenteDeleteController($uibModalInstance, entity, LigneVente) {
        var vm = this;

        vm.ligneVente = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LigneVente.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
