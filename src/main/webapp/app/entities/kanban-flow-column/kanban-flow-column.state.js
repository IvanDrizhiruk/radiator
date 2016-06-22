(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('kanban-flow-column', {
            parent: 'entity',
            url: '/kanban-flow-column',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'KanbanFlowColumns'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/kanban-flow-column/kanban-flow-columns.html',
                    controller: 'KanbanFlowColumnController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('kanban-flow-column-detail', {
            parent: 'entity',
            url: '/kanban-flow-column/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'KanbanFlowColumn'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/kanban-flow-column/kanban-flow-column-detail.html',
                    controller: 'KanbanFlowColumnDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'KanbanFlowColumn', function($stateParams, KanbanFlowColumn) {
                    return KanbanFlowColumn.get({id : $stateParams.id});
                }]
            }
        })
        .state('kanban-flow-column.new', {
            parent: 'kanban-flow-column',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kanban-flow-column/kanban-flow-column-dialog.html',
                    controller: 'KanbanFlowColumnDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                indexNumber: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('kanban-flow-column', null, { reload: true });
                }, function() {
                    $state.go('kanban-flow-column');
                });
            }]
        })
        .state('kanban-flow-column.edit', {
            parent: 'kanban-flow-column',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kanban-flow-column/kanban-flow-column-dialog.html',
                    controller: 'KanbanFlowColumnDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['KanbanFlowColumn', function(KanbanFlowColumn) {
                            return KanbanFlowColumn.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('kanban-flow-column', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('kanban-flow-column.delete', {
            parent: 'kanban-flow-column',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kanban-flow-column/kanban-flow-column-delete-dialog.html',
                    controller: 'KanbanFlowColumnDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['KanbanFlowColumn', function(KanbanFlowColumn) {
                            return KanbanFlowColumn.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('kanban-flow-column', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
