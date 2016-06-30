(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('kanban-flow-cell-info', {
            parent: 'entity',
            url: '/kanban-flow-cell-info',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'KanbanFlowCellInfos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/kanban-flow-cell-info/kanban-flow-cell-infos.html',
                    controller: 'KanbanFlowCellInfoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('kanban-flow-cell-info-detail', {
            parent: 'entity',
            url: '/kanban-flow-cell-info/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'KanbanFlowCellInfo'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/kanban-flow-cell-info/kanban-flow-cell-info-detail.html',
                    controller: 'KanbanFlowCellInfoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'KanbanFlowCellInfo', function($stateParams, KanbanFlowCellInfo) {
                    return KanbanFlowCellInfo.get({id : $stateParams.id});
                }]
            }
        })
        .state('kanban-flow-cell-info.new', {
            parent: 'kanban-flow-cell-info',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kanban-flow-cell-info/kanban-flow-cell-info-dialog.html',
                    controller: 'KanbanFlowCellInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                totalSecondsEstimated: null,
                                totalSecondsSpent: null,
                                extractingDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('kanban-flow-cell-info', null, { reload: true });
                }, function() {
                    $state.go('kanban-flow-cell-info');
                });
            }]
        })
        .state('kanban-flow-cell-info.edit', {
            parent: 'kanban-flow-cell-info',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kanban-flow-cell-info/kanban-flow-cell-info-dialog.html',
                    controller: 'KanbanFlowCellInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['KanbanFlowCellInfo', function(KanbanFlowCellInfo) {
                            return KanbanFlowCellInfo.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('kanban-flow-cell-info', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('kanban-flow-cell-info.delete', {
            parent: 'kanban-flow-cell-info',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kanban-flow-cell-info/kanban-flow-cell-info-delete-dialog.html',
                    controller: 'KanbanFlowCellInfoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['KanbanFlowCellInfo', function(KanbanFlowCellInfo) {
                            return KanbanFlowCellInfo.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('kanban-flow-cell-info', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
