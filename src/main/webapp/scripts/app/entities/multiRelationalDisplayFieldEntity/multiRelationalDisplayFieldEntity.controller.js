'use strict';

angular.module('jhipsterApp')
    .controller('MultiRelationalDisplayFieldEntityController', function ($scope, MultiRelationalDisplayFieldEntity) {
        $scope.multiRelationalDisplayFieldEntitys = [];
        $scope.loadAll = function() {
            MultiRelationalDisplayFieldEntity.query(function(result) {
               $scope.multiRelationalDisplayFieldEntitys = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            MultiRelationalDisplayFieldEntity.get({id: id}, function(result) {
                $scope.multiRelationalDisplayFieldEntity = result;
                $('#deleteMultiRelationalDisplayFieldEntityConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            MultiRelationalDisplayFieldEntity.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMultiRelationalDisplayFieldEntityConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.multiRelationalDisplayFieldEntity = {id: null};
        };
    });
