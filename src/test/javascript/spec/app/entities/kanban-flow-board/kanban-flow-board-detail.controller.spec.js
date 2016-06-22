'use strict';

describe('Controller Tests', function() {

    describe('KanbanFlowBoard Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockKanbanFlowBoard;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockKanbanFlowBoard = jasmine.createSpy('MockKanbanFlowBoard');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'KanbanFlowBoard': MockKanbanFlowBoard
            };
            createController = function() {
                $injector.get('$controller')("KanbanFlowBoardDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'radiatorApp:kanbanFlowBoardUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
