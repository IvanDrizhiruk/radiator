(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('KanbanFlowColumnController', KanbanFlowColumnController);

    KanbanFlowColumnController.$inject = ['$scope', '$state', 'KanbanFlowColumn'];

    function KanbanFlowColumnController ($scope, $state, KanbanFlowColumn) {
        var vm = this;
        vm.kanbanFlowColumns = [];
        vm.loadAll = function() {
            KanbanFlowColumn.query(function(result) {
                vm.kanbanFlowColumns = result;
            });
        };

        vm.loadAll();
        
    }
})();
