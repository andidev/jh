'use strict';

angular.module('jhipsterApp')
    .controller('OneToOneEntityController', function ($scope, OneToOneEntity, ParseLinks) {
        $scope.oneToOneEntitys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            OneToOneEntity.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.oneToOneEntitys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
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
