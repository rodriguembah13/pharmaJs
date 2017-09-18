(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .controller('MedicamentDetailController', MedicamentDetailController);

    MedicamentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Medicament', 'FamilleMedicament', 'Stock'];

    function MedicamentDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Medicament, FamilleMedicament, Stock) {
        var vm = this;

        vm.medicament = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('pharmaApp:medicamentUpdate', function(event, result) {
            vm.medicament = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
