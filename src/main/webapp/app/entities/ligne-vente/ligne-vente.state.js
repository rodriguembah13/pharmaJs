(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ligne-vente', {
            parent: 'entity',
            url: '/ligne-vente',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pharmaApp.ligneVente.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ligne-vente/ligne-ventes.html',
                    controller: 'LigneVenteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ligneVente');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ligne-vente-detail', {
            parent: 'ligne-vente',
            url: '/ligne-vente/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pharmaApp.ligneVente.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ligne-vente/ligne-vente-detail.html',
                    controller: 'LigneVenteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ligneVente');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LigneVente', function($stateParams, LigneVente) {
                    return LigneVente.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ligne-vente',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ligne-vente-detail.edit', {
            parent: 'ligne-vente-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ligne-vente/ligne-vente-dialog.html',
                    controller: 'LigneVenteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LigneVente', function(LigneVente) {
                            return LigneVente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ligne-vente.new', {
            parent: 'ligne-vente',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ligne-vente/ligne-vente-dialog.html',
                    controller: 'LigneVenteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                quantite: null,
                                prix: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ligne-vente', null, { reload: 'ligne-vente' });
                }, function() {
                    $state.go('ligne-vente');
                });
            }]
        })
        .state('ligne-vente.edit', {
            parent: 'ligne-vente',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ligne-vente/ligne-vente-dialog.html',
                    controller: 'LigneVenteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LigneVente', function(LigneVente) {
                            return LigneVente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ligne-vente', null, { reload: 'ligne-vente' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ligne-vente.delete', {
            parent: 'ligne-vente',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ligne-vente/ligne-vente-delete-dialog.html',
                    controller: 'LigneVenteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LigneVente', function(LigneVente) {
                            return LigneVente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ligne-vente', null, { reload: 'ligne-vente' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
