(function() {
    'use strict';

    angular
        .module('pharmaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('medicament', {
            parent: 'entity',
            url: '/medicament?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pharmaApp.medicament.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medicament/medicaments.html',
                    controller: 'MedicamentController',
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
                    $translatePartialLoader.addPart('medicament');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('medicament-detail', {
            parent: 'medicament',
            url: '/medicament/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pharmaApp.medicament.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medicament/medicament-detail.html',
                    controller: 'MedicamentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('medicament');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Medicament', function($stateParams, Medicament) {
                    return Medicament.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'medicament',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('medicament-detail.edit', {
            parent: 'medicament-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medicament/medicament-dialog.html',
                    controller: 'MedicamentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medicament', function(Medicament) {
                            return Medicament.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medicament.new', {
            parent: 'medicament',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medicament/medicament-dialog.html',
                    controller: 'MedicamentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                libelle: null,
                                prix: null,
                                image: null,
                                imageContentType: null,
                                code: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('medicament', null, { reload: 'medicament' });
                }, function() {
                    $state.go('medicament');
                });
            }]
        })
        .state('medicament.edit', {
            parent: 'medicament',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medicament/medicament-dialog.html',
                    controller: 'MedicamentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medicament', function(Medicament) {
                            return Medicament.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medicament', null, { reload: 'medicament' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medicament.delete', {
            parent: 'medicament',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medicament/medicament-delete-dialog.html',
                    controller: 'MedicamentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Medicament', function(Medicament) {
                            return Medicament.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medicament', null, { reload: 'medicament' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
