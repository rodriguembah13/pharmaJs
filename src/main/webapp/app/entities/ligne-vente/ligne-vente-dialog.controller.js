(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .controller('LigneVenteDialogController', LigneVenteDialogController);

    LigneVenteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LigneVente', 'Medicament', 'Vente', 'Client'];

    function LigneVenteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LigneVente, Medicament, Vente, Client) {
        var vm = this;

        vm.ligneVente = entity;
        vm.clear = clear;
        vm.save = save;
        vm.medicaments = Medicament.query();
        vm.ventes = Vente.query();
        vm.clients = Client.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ligneVente.id !== null) {
                LigneVente.update(vm.ligneVente, onSaveSuccess, onSaveError);
            } else {
                LigneVente.save(vm.ligneVente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pharmaApp:ligneVenteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
