(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .controller('VenteDialogController', VenteDialogController);

    VenteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Vente', 'Client', 'LigneVente', 'User'];

    function VenteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Vente, Client, LigneVente, User) {
        var vm = this;

        vm.vente = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.clients = Client.query();
        vm.ligneventes = LigneVente.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.vente.id !== null) {
                Vente.update(vm.vente, onSaveSuccess, onSaveError);
            } else {
                Vente.save(vm.vente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pharmaApp:venteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date_vente = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
