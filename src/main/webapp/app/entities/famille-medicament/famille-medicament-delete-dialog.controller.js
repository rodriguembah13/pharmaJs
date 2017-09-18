(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .controller('FamilleMedicamentDeleteController',FamilleMedicamentDeleteController);

    FamilleMedicamentDeleteController.$inject = ['$uibModalInstance', 'entity', 'FamilleMedicament'];

    function FamilleMedicamentDeleteController($uibModalInstance, entity, FamilleMedicament) {
        var vm = this;

        vm.familleMedicament = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FamilleMedicament.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
