(function() {
    'use strict';
    angular
        .module('radiatorApp')
        .factory('KanbanFlowCellInfo', KanbanFlowCellInfo);

    KanbanFlowCellInfo.$inject = ['$resource', 'DateUtils'];

    function KanbanFlowCellInfo ($resource, DateUtils) {
        var resourceUrl =  'api/kanban-flow-cell-infos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.extractingDate = DateUtils.convertDateTimeFromServer(data.extractingDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
