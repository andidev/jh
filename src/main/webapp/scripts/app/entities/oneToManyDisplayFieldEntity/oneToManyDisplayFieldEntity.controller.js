'use strict';

angular.module('jhipsterApp')
    .controller('OneToManyDisplayFieldEntityController', function ($scope, OneToManyDisplayFieldEntity, ParseLinks) {
        $scope.oneToManyDisplayFieldEntitys = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            OneToManyDisplayFieldEntity.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.oneToManyDisplayFieldEntitys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
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
