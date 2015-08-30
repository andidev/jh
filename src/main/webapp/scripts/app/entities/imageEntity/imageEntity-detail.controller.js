'use strict';

angular.module('jhipsterApp')
    .controller('ImageEntityDetailController', function ($scope, $rootScope, $stateParams, entity, ImageEntity) {
        $scope.imageEntity = entity;
        $scope.load = function (id) {
            ImageEntity.get({id: id}, function(result) {
                $scope.imageEntity = result;
            });
        };
        $rootScope.$on('jhipsterApp:imageEntityUpdate', function(event, result) {
            $scope.imageEntity = result;
        });
    });
