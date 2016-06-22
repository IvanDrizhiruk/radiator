(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('kanban-flow-swimlane', {
            parent: 'entity',
            url: '/kanban-flow-swimlane',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'KanbanFlowSwimlanes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/kanban-flow-swimlane/kanban-flow-swimlanes.html',
                    controller: 'KanbanFlowSwimlaneController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('kanban-flow-swimlane-detail', {
            parent: 'entity',
            url: '/kanban-flow-swimlane/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'KanbanFlowSwimlane'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/kanban-flow-swimlane/kanban-flow-swimlane-detail.html',
                    controller: 'KanbanFlowSwimlaneDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'KanbanFlowSwimlane', function($stateParams, KanbanFlowSwimlane) {
                    return KanbanFlowSwimlane.get({id : $stateParams.id});
                }]
            }
        })
        .state('kanban-flow-swimlane.new', {
            parent: 'kanban-flow-swimlane',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kanban-flow-swimlane/kanban-flow-swimlane-dialog.html',
                    controller: 'KanbanFlowSwimlaneDialogController',
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
                    $state.go('kanban-flow-swimlane', null, { reload: true });
                }, function() {
                    $state.go('kanban-flow-swimlane');
                });
            }]
        })
        .state('kanban-flow-swimlane.edit', {
            parent: 'kanban-flow-swimlane',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kanban-flow-swimlane/kanban-flow-swimlane-dialog.html',
                    controller: 'KanbanFlowSwimlaneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['KanbanFlowSwimlane', function(KanbanFlowSwimlane) {
                            return KanbanFlowSwimlane.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('kanban-flow-swimlane', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('kanban-flow-swimlane.delete', {
            parent: 'kanban-flow-swimlane',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kanban-flow-swimlane/kanban-flow-swimlane-delete-dialog.html',
                    controller: 'KanbanFlowSwimlaneDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['KanbanFlowSwimlane', function(KanbanFlowSwimlane) {
                            return KanbanFlowSwimlane.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('kanban-flow-swimlane', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
