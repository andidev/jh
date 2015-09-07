'use strict';

angular.module('jhipsterApp')
    .controller('OneToManyEntityController', function ($scope, OneToManyEntity, ParseLinks) {
        $scope.oneToManyEntitys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            OneToManyEntity.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.oneToManyEntitys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
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
