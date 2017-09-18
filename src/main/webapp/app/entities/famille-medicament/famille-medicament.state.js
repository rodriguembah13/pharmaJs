(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('famille-medicament', {
            parent: 'entity',
            url: '/famille-medicament?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pharmaApp.familleMedicament.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/famille-medicament/famille-medicaments.html',
                    controller: 'FamilleMedicamentController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('familleMedicament');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('famille-medicament-detail', {
            parent: 'famille-medicament',
            url: '/famille-medicament/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pharmaApp.familleMedicament.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/famille-medicament/famille-medicament-detail.html',
                    controller: 'FamilleMedicamentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('familleMedicament');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FamilleMedicament', function($stateParams, FamilleMedicament) {
                    return FamilleMedicament.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'famille-medicament',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('famille-medicament-detail.edit', {
            parent: 'famille-medicament-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/famille-medicament/famille-medicament-dialog.html',
                    controller: 'FamilleMedicamentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FamilleMedicament', function(FamilleMedicament) {
                            return FamilleMedicament.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('famille-medicament.new', {
            parent: 'famille-medicament',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/famille-medicament/famille-medicament-dialog.html',
                    controller: 'FamilleMedicamentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                designation: null,
                                description: null,
                                code: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('famille-medicament', null, { reload: 'famille-medicament' });
                }, function() {
                    $state.go('famille-medicament');
                });
            }]
        })
        .state('famille-medicament.edit', {
            parent: 'famille-medicament',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/famille-medicament/famille-medicament-dialog.html',
                    controller: 'FamilleMedicamentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FamilleMedicament', function(FamilleMedicament) {
                            return FamilleMedicament.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('famille-medicament', null, { reload: 'famille-medicament' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('famille-medicament.delete', {
            parent: 'famille-medicament',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/famille-medicament/famille-medicament-delete-dialog.html',
                    controller: 'FamilleMedicamentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FamilleMedicament', function(FamilleMedicament) {
                            return FamilleMedicament.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('famille-medicament', null, { reload: 'famille-medicament' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
