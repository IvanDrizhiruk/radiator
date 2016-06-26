'use strict';

describe('Controller Tests', function() {

    describe('KanbanFlowColumn Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockKanbanFlowColumn, MockKanbanFlowBoard;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockKanbanFlowColumn = jasmine.createSpy('MockKanbanFlowColumn');
            MockKanbanFlowBoard = jasmine.createSpy('MockKanbanFlowBoard');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'KanbanFlowColumn': MockKanbanFlowColumn,
                'KanbanFlowBoard': MockKanbanFlowBoard
            };
            createController = function() {
                $injector.get('$controller')("KanbanFlowColumnDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'radiatorApp:kanbanFlowColumnUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
