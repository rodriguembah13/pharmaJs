(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .controller('MedicamentDeleteController',MedicamentDeleteController);

    MedicamentDeleteController.$inject = ['$uibModalInstance', 'entity', 'Medicament'];

    function MedicamentDeleteController($uibModalInstance, entity, Medicament) {
        var vm = this;

        vm.medicament = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Medicament.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
