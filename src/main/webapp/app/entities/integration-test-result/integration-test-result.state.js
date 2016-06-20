(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('integration-test-result', {
            parent: 'entity',
            url: '/integration-test-result',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'IntegrationTestResults'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/integration-test-result/integration-test-results.html',
                    controller: 'IntegrationTestResultController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('integration-test-result-detail', {
            parent: 'entity',
            url: '/integration-test-result/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'IntegrationTestResult'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/integration-test-result/integration-test-result-detail.html',
                    controller: 'IntegrationTestResultDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'IntegrationTestResult', function($stateParams, IntegrationTestResult) {
                    return IntegrationTestResult.get({id : $stateParams.id});
                }]
            }
        })
        .state('integration-test-result.new', {
            parent: 'integration-test-result',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/integration-test-result/integration-test-result-dialog.html',
                    controller: 'IntegrationTestResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                total: null,
                                passed: null,
                                pending: null,
                                failed: null,
                                extractingDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('integration-test-result', null, { reload: true });
                }, function() {
                    $state.go('integration-test-result');
                });
            }]
        })
        .state('integration-test-result.edit', {
            parent: 'integration-test-result',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/integration-test-result/integration-test-result-dialog.html',
                    controller: 'IntegrationTestResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['IntegrationTestResult', function(IntegrationTestResult) {
                            return IntegrationTestResult.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('integration-test-result', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('integration-test-result.delete', {
            parent: 'integration-test-result',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/integration-test-result/integration-test-result-delete-dialog.html',
                    controller: 'IntegrationTestResultDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['IntegrationTestResult', function(IntegrationTestResult) {
                            return IntegrationTestResult.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('integration-test-result', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
