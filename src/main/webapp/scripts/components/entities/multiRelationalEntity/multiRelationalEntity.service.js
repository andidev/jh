'use strict';

angular.module('jhipsterApp')
    .factory('MultiRelationalEntity', function ($resource, DateUtils) {
        return $resource('api/multiRelationalEntitys/:id', {}, {
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
