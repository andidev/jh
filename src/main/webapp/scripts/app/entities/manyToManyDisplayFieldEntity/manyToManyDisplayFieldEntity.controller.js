'use strict';

angular.module('jhipsterApp')
    .controller('ManyToManyDisplayFieldEntityController', function ($scope, ManyToManyDisplayFieldEntity, ParseLinks) {
        $scope.manyToManyDisplayFieldEntitys = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            ManyToManyDisplayFieldEntity.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.manyToManyDisplayFieldEntitys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
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
