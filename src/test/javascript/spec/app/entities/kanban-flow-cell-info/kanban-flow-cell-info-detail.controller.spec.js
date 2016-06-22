'use strict';

describe('Controller Tests', function() {

    describe('KanbanFlowCellInfo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockKanbanFlowCellInfo, MockKanbanFlowBoard, MockKanbanFlowColumn, MockKanbanFlowSwimlane;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockKanbanFlowCellInfo = jasmine.createSpy('MockKanbanFlowCellInfo');
            MockKanbanFlowBoard = jasmine.createSpy('MockKanbanFlowBoard');
            MockKanbanFlowColumn = jasmine.createSpy('MockKanbanFlowColumn');
            MockKanbanFlowSwimlane = jasmine.createSpy('MockKanbanFlowSwimlane');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'KanbanFlowCellInfo': MockKanbanFlowCellInfo,
                'KanbanFlowBoard': MockKanbanFlowBoard,
                'KanbanFlowColumn': MockKanbanFlowColumn,
                'KanbanFlowSwimlane': MockKanbanFlowSwimlane
            };
            createController = function() {
                $injector.get('$controller')("KanbanFlowCellInfoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'radiatorApp:kanbanFlowCellInfoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
