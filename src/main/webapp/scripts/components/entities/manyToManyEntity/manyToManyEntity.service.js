'use strict';

angular.module('jhipsterApp')
    .factory('ManyToManyEntity', function ($resource, DateUtils) {
        return $resource('api/manyToManyEntitys/:id', {}, {
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
