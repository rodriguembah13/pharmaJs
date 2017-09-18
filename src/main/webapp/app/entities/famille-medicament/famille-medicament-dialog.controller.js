(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .controller('FamilleMedicamentDialogController', FamilleMedicamentDialogController);

    FamilleMedicamentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FamilleMedicament'];

    function FamilleMedicamentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FamilleMedicament) {
        var vm = this;

        vm.familleMedicament = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.familleMedicament.id !== null) {
                FamilleMedicament.update(vm.familleMedicament, onSaveSuccess, onSaveError);
            } else {
                FamilleMedicament.save(vm.familleMedicament, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pharmaApp:familleMedicamentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
