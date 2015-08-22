'use strict';

angular.module('jhipsterApp')
    .controller('ManyToManyEntityController', function ($scope, ManyToManyEntity) {
        $scope.manyToManyEntitys = [];
        $scope.loadAll = function() {
            ManyToManyEntity.query(function(result) {
               $scope.manyToManyEntitys = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            ManyToManyEntity.get({id: id}, function(result) {
                $scope.manyToManyEntity = result;
                $('#deleteManyToManyEntityConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ManyToManyEntity.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteManyToManyEntityConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.manyToManyEntity = {id: null};
        };
    });
