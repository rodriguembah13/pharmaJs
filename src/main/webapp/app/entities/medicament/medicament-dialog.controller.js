(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .controller('MedicamentDialogController', MedicamentDialogController);

    MedicamentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Medicament', 'FamilleMedicament', 'Stock'];

    function MedicamentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Medicament, FamilleMedicament, Stock) {
        var vm = this;

        vm.medicament = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.famillemedicaments = FamilleMedicament.query();
        vm.stocks = Stock.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.medicament.id !== null) {
                Medicament.update(vm.medicament, onSaveSuccess, onSaveError);
            } else {
                Medicament.save(vm.medicament, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pharmaApp:medicamentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImage = function ($file, medicament) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        medicament.image = base64Data;
                        medicament.imageContentType = $file.type;
                    });
                });
            }
        };

    }
})();
