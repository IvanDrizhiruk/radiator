(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('KanbanFlowCellInfoDetailController', KanbanFlowCellInfoDetailController);

    KanbanFlowCellInfoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'KanbanFlowCellInfo', 'KanbanFlowBoard', 'KanbanFlowColumn', 'KanbanFlowSwimlane'];

    function KanbanFlowCellInfoDetailController($scope, $rootScope, $stateParams, entity, KanbanFlowCellInfo, KanbanFlowBoard, KanbanFlowColumn, KanbanFlowSwimlane) {
        var vm = this;
        vm.kanbanFlowCellInfo = entity;
        
        var unsubscribe = $rootScope.$on('radiatorApp:kanbanFlowCellInfoUpdate', function(event, result) {
            vm.kanbanFlowCellInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
