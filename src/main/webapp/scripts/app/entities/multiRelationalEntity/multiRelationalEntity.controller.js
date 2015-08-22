'use strict';

angular.module('jhipsterApp')
    .controller('MultiRelationalEntityController', function ($scope, MultiRelationalEntity) {
        $scope.multiRelationalEntitys = [];
        $scope.loadAll = function() {
            MultiRelationalEntity.query(function(result) {
               $scope.multiRelationalEntitys = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            MultiRelationalEntity.get({id: id}, function(result) {
                $scope.multiRelationalEntity = result;
                $('#deleteMultiRelationalEntityConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            MultiRelationalEntity.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMultiRelationalEntityConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.multiRelationalEntity = {id: null};
        };
    });
