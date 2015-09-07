'use strict';

angular.module('jhipsterApp')
    .controller('MultiRelationalDisplayFieldEntityController', function ($scope, MultiRelationalDisplayFieldEntity, ParseLinks) {
        $scope.multiRelationalDisplayFieldEntitys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            MultiRelationalDisplayFieldEntity.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.multiRelationalDisplayFieldEntitys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            MultiRelationalDisplayFieldEntity.get({id: id}, function(result) {
                $scope.multiRelationalDisplayFieldEntity = result;
                $('#deleteMultiRelationalDisplayFieldEntityConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            MultiRelationalDisplayFieldEntity.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMultiRelationalDisplayFieldEntityConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.multiRelationalDisplayFieldEntity = {id: null};
        };
    });
