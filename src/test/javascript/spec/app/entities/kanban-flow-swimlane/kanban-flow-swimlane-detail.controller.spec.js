'use strict';

describe('Controller Tests', function() {

    describe('KanbanFlowSwimlane Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockKanbanFlowSwimlane;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockKanbanFlowSwimlane = jasmine.createSpy('MockKanbanFlowSwimlane');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'KanbanFlowSwimlane': MockKanbanFlowSwimlane
            };
            createController = function() {
                $injector.get('$controller')("KanbanFlowSwimlaneDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'radiatorApp:kanbanFlowSwimlaneUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
