'use strict';

angular.module('jhipsterApp')
    .factory('OneToManyEntity', function ($resource, DateUtils) {
        return $resource('api/oneToManyEntitys/:id', {}, {
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
