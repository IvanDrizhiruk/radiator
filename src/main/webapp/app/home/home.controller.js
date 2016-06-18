(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'BuildState'];

    function HomeController ($scope, Principal, LoginService, $state, BuildState) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });


        BuildState.query(function(result) {
            vm.buildStates = result;

            vm.labels = ["Download Sales", "In-Store Sales", "Mail-Order Sales"];
            vm.data = [300, 500, 100];
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
