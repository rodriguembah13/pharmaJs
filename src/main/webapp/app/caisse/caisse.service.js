(function() {
    'use strict';
    angular
        .module('pharmaApp')
        .factory('Med', Med);

    Med.$inject = ['$resource'];

    function Med ($resource) {
        var resourceUrl =  'api/getMedicaments/:id/:qte';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET', transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            
        });
    }
})();
