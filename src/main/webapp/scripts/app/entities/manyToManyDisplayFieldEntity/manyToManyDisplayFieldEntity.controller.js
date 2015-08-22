'use strict';

angular.module('jhipsterApp')
    .controller('ManyToManyDisplayFieldEntityController', function ($scope, ManyToManyDisplayFieldEntity) {
        $scope.manyToManyDisplayFieldEntitys = [];
        $scope.loadAll = function() {
            ManyToManyDisplayFieldEntity.query(function(result) {
               $scope.manyToManyDisplayFieldEntitys = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            ManyToManyDisplayFieldEntity.get({id: id}, function(result) {
                $scope.manyToManyDisplayFieldEntity = result;
                $('#deleteManyToManyDisplayFieldEntityConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ManyToManyDisplayFieldEntity.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteManyToManyDisplayFieldEntityConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.manyToManyDisplayFieldEntity = {displayField: null, id: null};
        };
    });
