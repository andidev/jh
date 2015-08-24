'use strict';

angular.module('jhipsterApp')
    .controller('OneToOneDisplayFieldEntityController', function ($scope, OneToOneDisplayFieldEntity, ParseLinks) {
        $scope.oneToOneDisplayFieldEntitys = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            OneToOneDisplayFieldEntity.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.oneToOneDisplayFieldEntitys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
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
