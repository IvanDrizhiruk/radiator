'use strict';

describe('Controller Tests', function() {

    describe('Commiter Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCommiter;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCommiter = jasmine.createSpy('MockCommiter');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Commiter': MockCommiter
            };
            createController = function() {
                $injector.get('$controller')("CommiterDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'radiatorApp:commiterUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
