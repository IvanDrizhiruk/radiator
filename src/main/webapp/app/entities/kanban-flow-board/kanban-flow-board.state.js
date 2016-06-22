(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('kanban-flow-board', {
            parent: 'entity',
            url: '/kanban-flow-board',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'KanbanFlowBoards'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/kanban-flow-board/kanban-flow-boards.html',
                    controller: 'KanbanFlowBoardController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('kanban-flow-board-detail', {
            parent: 'entity',
            url: '/kanban-flow-board/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'KanbanFlowBoard'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/kanban-flow-board/kanban-flow-board-detail.html',
                    controller: 'KanbanFlowBoardDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'KanbanFlowBoard', function($stateParams, KanbanFlowBoard) {
                    return KanbanFlowBoard.get({id : $stateParams.id});
                }]
            }
        })
        .state('kanban-flow-board.new', {
            parent: 'kanban-flow-board',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kanban-flow-board/kanban-flow-board-dialog.html',
                    controller: 'KanbanFlowBoardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('kanban-flow-board', null, { reload: true });
                }, function() {
                    $state.go('kanban-flow-board');
                });
            }]
        })
        .state('kanban-flow-board.edit', {
            parent: 'kanban-flow-board',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kanban-flow-board/kanban-flow-board-dialog.html',
                    controller: 'KanbanFlowBoardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['KanbanFlowBoard', function(KanbanFlowBoard) {
                            return KanbanFlowBoard.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('kanban-flow-board', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('kanban-flow-board.delete', {
            parent: 'kanban-flow-board',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kanban-flow-board/kanban-flow-board-delete-dialog.html',
                    controller: 'KanbanFlowBoardDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['KanbanFlowBoard', function(KanbanFlowBoard) {
                            return KanbanFlowBoard.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('kanban-flow-board', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
