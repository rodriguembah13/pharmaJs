(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('caisse', {
            parent: 'app',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pharmaApp.client.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/caisse/caisse.html',
                    controller: 'CaisseController',
                    controllerAs: 'vm'
                }
            }
              
            
        }).state('caisse.edit', {
            parent: 'caisse',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/caisse/caisse.dialog.html',
                    controller: 'CaisseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'sm',
                    resolve: {
                        entity: ['Medicament', function(Medicament) {
                            return Medicament.get({id : $stateParams.id}).$promise;
                        }]
                    }
                 
                }).result.then(function() {
                    $state.go('caisse', null, { reload: 'caisse' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        
}
})();
