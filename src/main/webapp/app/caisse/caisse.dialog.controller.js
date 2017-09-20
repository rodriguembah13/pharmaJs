(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .controller('CaisseDialogController',CaisseDialogController);

    CaisseDialogController.$inject = ['$uibModalInstance', 'entity', 'Medicament'];

    function CaisseDialogController($uibModalInstance,entity, Medicament) {
        var vm = this;
        vm.clear = clear;
        vm.confirmQte = confirmQte;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmQte (id,qte) {
            $uibModalInstance.close(true);
            /*Client.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });*/
        }
    }
})();