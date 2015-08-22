'use strict';

angular.module('jhipsterApp')
    .controller('OneToManyEntityController', function ($scope, OneToManyEntity) {
        $scope.oneToManyEntitys = [];
        $scope.loadAll = function() {
            OneToManyEntity.query(function(result) {
               $scope.oneToManyEntitys = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            OneToManyEntity.get({id: id}, function(result) {
                $scope.oneToManyEntity = result;
                $('#deleteOneToManyEntityConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OneToManyEntity.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOneToManyEntityConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.oneToManyEntity = {id: null};
        };
    });
