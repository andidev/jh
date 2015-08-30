'use strict';

angular.module('jhipsterApp')
    .factory('ManyToManyDisplayFieldEntity', function ($resource, DateUtils) {
        return $resource('api/manyToManyDisplayFieldEntitys/:id', {}, {
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
