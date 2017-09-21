(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('caisse', {
            parent: 'app',url: '/caisse',
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
                    size: 'sm'
                 
                }).result.then(function() {
                    $state.go('caisse', null, { reload: 'caisse' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('caisse.new', {
            parent: 'caisse',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/caisse/caisse.dialog.html',
                    controller: 'ClientDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg'
                }).result.then(function() {
                    $state.go('caisse', null, { reload: 'caisse' });
                }, function() {
                    $state.go('caisse');
                });
            }]
        })
        
}
})();
