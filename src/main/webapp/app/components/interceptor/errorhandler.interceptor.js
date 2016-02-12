'use strict';

angular.module('jhApp')
    .factory('errorHandlerInterceptor', function ($q, $rootScope) {
        return {
            'responseError': function (response) {
                if (!(response.status === 401 && (response.data === '' || response.data.path.indexOf('/api/account') === 0 ))) {
                    $rootScope.$emit('jhApp.httpError', response);
                }
                return $q.reject(response);
            }
        };
    });
