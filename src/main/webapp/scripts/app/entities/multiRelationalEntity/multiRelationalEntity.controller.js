'use strict';

angular.module('jhipsterApp')
    .controller('MultiRelationalEntityController', function ($scope, MultiRelationalEntity, ParseLinks) {
        $scope.multiRelationalEntitys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            MultiRelationalEntity.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.multiRelationalEntitys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            MultiRelationalEntity.get({id: id}, function(result) {
                $scope.multiRelationalEntity = result;
                $('#deleteMultiRelationalEntityConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            MultiRelationalEntity.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMultiRelationalEntityConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.multiRelationalEntity = {id: null};
        };
    });
