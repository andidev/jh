'use strict';

angular.module('jhipsterApp')
    .factory('CommentedEntity', function ($resource, DateUtils) {
        return $resource('api/commentedEntitys/:id', {}, {
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
