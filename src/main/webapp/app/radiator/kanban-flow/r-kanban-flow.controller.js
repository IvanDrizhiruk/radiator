(function() {
    'use strict';

    angular
        .module('radiatorApp')
        .controller('RKanbanFlowController', rKanbanFlowController);

    rKanbanFlowController.$inject = ['$scope', '$state', 'RKanbanFlow'];

    function rKanbanFlowController ($scope, $state, RKanbanFlow) {
        var vm = this;
        vm.boards = [];

        vm.calculateCellKey = calculateCellKey;


        vm.loadAll = function() {
            RKanbanFlow.query(function(result) {
                    vm.boards = transformToUIBoard(result);
            });
        };

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


                uiBoards.push(uiBoard);

            });

            return uiBoards;
        }

        function calculateCellKey(column, swimlane) {
            return column.indexNumber + '-' + swimlane.indexNumber;
        }
    }
})();
