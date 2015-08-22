'use strict';

angular.module('jhipsterApp')
    .controller('OneToManyDisplayFieldEntityController', function ($scope, OneToManyDisplayFieldEntity) {
        $scope.oneToManyDisplayFieldEntitys = [];
        $scope.loadAll = function() {
            OneToManyDisplayFieldEntity.query(function(result) {
               $scope.oneToManyDisplayFieldEntitys = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            OneToManyDisplayFieldEntity.get({id: id}, function(result) {
                $scope.oneToManyDisplayFieldEntity = result;
                $('#deleteOneToManyDisplayFieldEntityConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OneToManyDisplayFieldEntity.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOneToManyDisplayFieldEntityConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.oneToManyDisplayFieldEntity = {displayField: null, id: null};
        };
    });
