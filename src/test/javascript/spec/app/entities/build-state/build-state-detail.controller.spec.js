'use strict';

describe('Controller Tests', function() {

    describe('BuildState Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBuildState, MockCommiter;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBuildState = jasmine.createSpy('MockBuildState');
            MockCommiter = jasmine.createSpy('MockCommiter');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'BuildState': MockBuildState,
                'Commiter': MockCommiter
            };
            createController = function() {
                $injector.get('$controller')("BuildStateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'radiatorApp:buildStateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
