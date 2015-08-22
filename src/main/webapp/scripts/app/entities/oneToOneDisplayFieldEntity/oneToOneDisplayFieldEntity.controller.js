'use strict';

angular.module('jhipsterApp')
    .controller('OneToOneDisplayFieldEntityController', function ($scope, OneToOneDisplayFieldEntity) {
        $scope.oneToOneDisplayFieldEntitys = [];
        $scope.loadAll = function() {
            OneToOneDisplayFieldEntity.query(function(result) {
               $scope.oneToOneDisplayFieldEntitys = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            OneToOneDisplayFieldEntity.get({id: id}, function(result) {
                $scope.oneToOneDisplayFieldEntity = result;
                $('#deleteOneToOneDisplayFieldEntityConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OneToOneDisplayFieldEntity.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOneToOneDisplayFieldEntityConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.oneToOneDisplayFieldEntity = {displayField: null, id: null};
        };
    });
