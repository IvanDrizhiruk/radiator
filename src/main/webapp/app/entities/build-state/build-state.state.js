(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('build-state', {
            parent: 'entity',
            url: '/build-state',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BuildStates'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/build-state/build-states.html',
                    controller: 'BuildStateController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('build-state-detail', {
            parent: 'entity',
            url: '/build-state/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BuildState'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/build-state/build-state-detail.html',
                    controller: 'BuildStateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'BuildState', function($stateParams, BuildState) {
                    return BuildState.get({id : $stateParams.id});
                }]
            }
        })
        .state('build-state.new', {
            parent: 'build-state',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/build-state/build-state-dialog.html',
                    controller: 'BuildStateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                instancesName: null,
                                state: null,
                                errorMessage: null,
                                lastRunTimestemp: null,
                                extractingDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('build-state', null, { reload: true });
                }, function() {
                    $state.go('build-state');
                });
            }]
        })
        .state('build-state.edit', {
            parent: 'build-state',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/build-state/build-state-dialog.html',
                    controller: 'BuildStateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BuildState', function(BuildState) {
                            return BuildState.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('build-state', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('build-state.delete', {
            parent: 'build-state',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/build-state/build-state-delete-dialog.html',
                    controller: 'BuildStateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BuildState', function(BuildState) {
                            return BuildState.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('build-state', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
