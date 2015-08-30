'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('imageEntity', {
                parent: 'entity',
                url: '/imageEntitys',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.imageEntity.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/imageEntity/imageEntitys.html',
                        controller: 'ImageEntityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('imageEntity');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('imageEntity.detail', {
                parent: 'entity',
                url: '/imageEntity/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.imageEntity.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/imageEntity/imageEntity-detail.html',
                        controller: 'ImageEntityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('imageEntity');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ImageEntity', function($stateParams, ImageEntity) {
                        return ImageEntity.get({id : $stateParams.id});
                    }]
                }
            })
            .state('imageEntity.new', {
                parent: 'imageEntity',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/imageEntity/imageEntity-dialog.html',
                        controller: 'ImageEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {imageField: null, validatedImageField: null, blobField: null, validatedBlobField: null, stringField: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('imageEntity', null, { reload: true });
                    }, function() {
                        $state.go('imageEntity');
                    })
                }]
            })
            .state('imageEntity.edit', {
                parent: 'imageEntity',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/imageEntity/imageEntity-dialog.html',
                        controller: 'ImageEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ImageEntity', function(ImageEntity) {
                                return ImageEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('imageEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
