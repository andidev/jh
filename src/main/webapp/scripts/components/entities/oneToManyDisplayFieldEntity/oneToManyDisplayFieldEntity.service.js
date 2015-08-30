'use strict';

angular.module('jhipsterApp')
    .factory('OneToManyDisplayFieldEntity', function ($resource, DateUtils) {
        return $resource('api/oneToManyDisplayFieldEntitys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
