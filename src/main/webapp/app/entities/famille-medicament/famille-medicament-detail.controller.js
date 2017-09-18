(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .controller('FamilleMedicamentDetailController', FamilleMedicamentDetailController);

    FamilleMedicamentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FamilleMedicament'];

    function FamilleMedicamentDetailController($scope, $rootScope, $stateParams, previousState, entity, FamilleMedicament) {
        var vm = this;

        vm.familleMedicament = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pharmaApp:familleMedicamentUpdate', function(event, result) {
            vm.familleMedicament = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
