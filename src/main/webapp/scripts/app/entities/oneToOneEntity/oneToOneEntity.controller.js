'use strict';

angular.module('jhipsterApp')
    .controller('OneToOneEntityController', function ($scope, OneToOneEntity) {
        $scope.oneToOneEntitys = [];
        $scope.loadAll = function() {
            OneToOneEntity.query(function(result) {
               $scope.oneToOneEntitys = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            OneToOneEntity.get({id: id}, function(result) {
                $scope.oneToOneEntity = result;
                $('#deleteOneToOneEntityConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OneToOneEntity.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOneToOneEntityConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.oneToOneEntity = {id: null};
        };
    });
