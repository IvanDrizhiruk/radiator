(function() {
    'use strict';
    angular
        .module('radiatorApp')
        .factory('RKanbanFlow', rKanbanFlow);

    rKanbanFlow.$inject = ['$resource', 'DateUtils'];

    function rKanbanFlow ($resource, DateUtils) {
        var resourceUrl =  'api/report/kanbanflow/last';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
            //,
            //'get': {
            //    method: 'GET',
            //    transformResponse: function (data) {
            //        if (data) {
            //            data = angular.fromJson(data);
            //            data.extractingDate = DateUtils.convertDateTimeFromServer(data.extractingDate);
            //        }
            //        return data;
            //    }
            //}
        });
    }
})();
