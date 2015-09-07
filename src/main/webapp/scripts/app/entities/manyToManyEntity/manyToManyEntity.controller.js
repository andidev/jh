'use strict';

angular.module('jhipsterApp')
    .controller('ManyToManyEntityController', function ($scope, ManyToManyEntity, ParseLinks) {
        $scope.manyToManyEntitys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            ManyToManyEntity.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.manyToManyEntitys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
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
