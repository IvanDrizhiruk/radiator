(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('commiter', {
            parent: 'entity',
            url: '/commiter',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Commiters'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/commiter/commiters.html',
                    controller: 'CommiterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('commiter-detail', {
            parent: 'entity',
            url: '/commiter/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Commiter'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/commiter/commiter-detail.html',
                    controller: 'CommiterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Commiter', function($stateParams, Commiter) {
                    return Commiter.get({id : $stateParams.id});
                }]
            }
        })
        .state('commiter.new', {
            parent: 'commiter',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commiter/commiter-dialog.html',
                    controller: 'CommiterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                email: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('commiter', null, { reload: true });
                }, function() {
                    $state.go('commiter');
                });
            }]
        })
        .state('commiter.edit', {
            parent: 'commiter',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commiter/commiter-dialog.html',
                    controller: 'CommiterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Commiter', function(Commiter) {
                            return Commiter.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('commiter', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('commiter.delete', {
            parent: 'commiter',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commiter/commiter-delete-dialog.html',
                    controller: 'CommiterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Commiter', function(Commiter) {
                            return Commiter.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('commiter', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
