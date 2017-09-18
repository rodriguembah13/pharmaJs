(function() {
    'use strict';
    angular
        .module('pharmaApp')
        .factory('Vente', Vente);

    Vente.$inject = ['$resource', 'DateUtils'];

    function Vente ($resource, DateUtils) {
        var resourceUrl =  'api/ventes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date_vente = DateUtils.convertLocalDateFromServer(data.date_vente);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.date_vente = DateUtils.convertLocalDateToServer(copy.date_vente);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.date_vente = DateUtils.convertLocalDateToServer(copy.date_vente);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
