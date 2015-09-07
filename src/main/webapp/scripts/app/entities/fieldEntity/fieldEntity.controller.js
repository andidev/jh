'use strict';

angular.module('jhipsterApp')
    .controller('FieldEntityController', function ($scope, FieldEntity) {
        $scope.fieldEntitys = [];
        $scope.loadAll = function() {
            FieldEntity.query(function(result) {
               $scope.fieldEntitys = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            FieldEntity.get({id: id}, function(result) {
                $scope.fieldEntity = result;
                $('#deleteFieldEntityConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            FieldEntity.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFieldEntityConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.fieldEntity = {name: null, email: null, aDouble: null, aRequiredDouble: null, anEnum: null, id: null};
        };
    });
