'use strict';

describe('Controller Tests', function() {

    describe('IntegrationTestResult Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockIntegrationTestResult;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockIntegrationTestResult = jasmine.createSpy('MockIntegrationTestResult');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'IntegrationTestResult': MockIntegrationTestResult
            };
            createController = function() {
                $injector.get('$controller')("IntegrationTestResultDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'radiatorApp:integrationTestResultUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
