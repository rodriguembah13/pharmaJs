(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .controller('VenteDeleteController',VenteDeleteController);

    VenteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Vente'];

    function VenteDeleteController($uibModalInstance, entity, Vente) {
        var vm = this;

        vm.vente = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Vente.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
