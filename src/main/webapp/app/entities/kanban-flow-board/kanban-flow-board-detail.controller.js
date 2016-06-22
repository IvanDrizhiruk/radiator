(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('KanbanFlowBoardDetailController', KanbanFlowBoardDetailController);

    KanbanFlowBoardDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'KanbanFlowBoard'];

    function KanbanFlowBoardDetailController($scope, $rootScope, $stateParams, entity, KanbanFlowBoard) {
        var vm = this;
        vm.kanbanFlowBoard = entity;
        
        var unsubscribe = $rootScope.$on('radiatorApp:kanbanFlowBoardUpdate', function(event, result) {
            vm.kanbanFlowBoard = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
