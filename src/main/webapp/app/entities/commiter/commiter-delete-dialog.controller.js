(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('CommiterDeleteController',CommiterDeleteController);

    CommiterDeleteController.$inject = ['$uibModalInstance', 'entity', 'Commiter'];

    function CommiterDeleteController($uibModalInstance, entity, Commiter) {
        var vm = this;
        vm.commiter = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Commiter.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
