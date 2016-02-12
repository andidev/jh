'use strict';

angular.module('jhApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


