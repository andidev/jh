'use strict';

angular.module('jhipsterApp')
    .controller('FieldEntityDetailController', function ($scope, $rootScope, $stateParams, entity, FieldEntity) {
        $scope.fieldEntity = entity;
        $scope.load = function (id) {
            FieldEntity.get({id: id}, function(result) {
                $scope.fieldEntity = result;
            });
        };
        $rootScope.$on('jhipsterApp:fieldEntityUpdate', function(event, result) {
            $scope.fieldEntity = result;
        });
    });
