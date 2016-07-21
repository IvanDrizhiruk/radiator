(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('RKanbanFlowController', rKanbanFlowController);

    rKanbanFlowController.$inject = ['$scope', '$state', 'RKanbanFlow', 'RRefresher'];

    function rKanbanFlowController ($scope, $state, RKanbanFlow, RRefresher) {
        var vm = this;
        vm.boards = [];

        vm.calculateCellKey = calculateCellKey;


        vm.loadAll = function() {
            RKanbanFlow.query(function(result) {
                    vm.boards = transformToUIBoard(result);
            });
        };

        RRefresher.registrate(vm.loadAll);

        vm.loadAll();



        function transformToUIBoard(serverBoard) {
            var uiBoards = [];
            serverBoard.forEach(function(serverBoard) {
                var uiBoard = {
                    name: serverBoard.boardName
                }

                var columns = {};
                var swimlanes = {};
                var cells = {};

                serverBoard.cells.forEach(function (cell) {
                    if(!columns[cell.column.indexNumber]) {
                        columns[cell.column.indexNumber] = cell.column
                    }
                    var column = columns[cell.column.indexNumber];

                    if(!swimlanes[cell.swimlane.indexNumber]) {
                        swimlanes[cell.swimlane.indexNumber] = cell.swimlane
                    }
                    var swimlane = swimlanes[cell.swimlane.indexNumber];

                    cells[calculateCellKey(column, swimlane)] = cell;
                });

                uiBoard.columns = columns;
                uiBoard.swimlanes = swimlanes;
                uiBoard.cells = cells;

                prepareCellsInSwimlanes(uiBoard);

                uiBoards.push(uiBoard);

            });

            return uiBoards;
        }

        function calculateCellKey(column, swimlane) {
            return column.indexNumber + '-' + swimlane.indexNumber;
        }

        function prepareCellsInSwimlanes(uiBoard) {
            for(var swimlaneIndex in uiBoard.swimlanes) {
                var cells = [];
                for(var columnIndex in uiBoard.columns) {
                    var column =  uiBoard.columns[columnIndex];
                    var swimlane =  uiBoard.swimlanes[swimlaneIndex];
                    var cellInHours = uiBoard.cells[calculateCellKey(column, swimlane)].totalSecondsEstimated / 60 / 60;

                    cells.push({
                        column: column,
                        totalHoursEstimated: cellInHours
                    });
                }

                swimlane.cells = cells
            }
        }
    }
})();
