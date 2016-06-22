(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('KanbanFlowCellInfoController', KanbanFlowCellInfoController);

    KanbanFlowCellInfoController.$inject = ['$scope', '$state', 'KanbanFlowCellInfo'];

    function KanbanFlowCellInfoController ($scope, $state, KanbanFlowCellInfo) {
        var vm = this;
        vm.kanbanFlowCellInfos = [];
        vm.loadAll = function() {
            KanbanFlowCellInfo.query(function(result) {
                vm.kanbanFlowCellInfos = result;
            });
        };

        vm.loadAll();
        
    }
})();
