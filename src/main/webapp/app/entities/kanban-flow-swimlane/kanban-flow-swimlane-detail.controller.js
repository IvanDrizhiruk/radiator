(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('KanbanFlowSwimlaneDetailController', KanbanFlowSwimlaneDetailController);

    KanbanFlowSwimlaneDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'KanbanFlowSwimlane'];

    function KanbanFlowSwimlaneDetailController($scope, $rootScope, $stateParams, entity, KanbanFlowSwimlane) {
        var vm = this;
        vm.kanbanFlowSwimlane = entity;
        
        var unsubscribe = $rootScope.$on('radiatorApp:kanbanFlowSwimlaneUpdate', function(event, result) {
            vm.kanbanFlowSwimlane = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
