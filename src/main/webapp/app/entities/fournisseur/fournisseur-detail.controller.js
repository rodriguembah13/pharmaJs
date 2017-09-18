(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .controller('FournisseurDetailController', FournisseurDetailController);

    FournisseurDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Fournisseur'];

    function FournisseurDetailController($scope, $rootScope, $stateParams, previousState, entity, Fournisseur) {
        var vm = this;

        vm.fournisseur = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pharmaApp:fournisseurUpdate', function(event, result) {
            vm.fournisseur = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
