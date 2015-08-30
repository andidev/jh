'use strict';

angular.module('jhipsterApp')
    .factory('MultiRelationalDisplayFieldEntity', function ($resource, DateUtils) {
        return $resource('api/multiRelationalDisplayFieldEntitys/:id', {}, {
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
