'use strict';

angular.module('jhipsterApp')
    .controller('OneDetailController', function ($scope, $rootScope, $stateParams, entity, One) {
        $scope.one = entity;
        $scope.load = function (id) {
            One.get({id: id}, function(result) {
                $scope.one = result;
            });
        };
        $rootScope.$on('jhipsterApp:oneUpdate', function(event, result) {
            $scope.one = result;
        });
    });
