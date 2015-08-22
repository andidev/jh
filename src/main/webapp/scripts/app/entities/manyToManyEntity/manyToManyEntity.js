'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('manyToManyEntity', {
                parent: 'entity',
                url: '/manyToManyEntitys',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.manyToManyEntity.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/manyToManyEntity/manyToManyEntitys.html',
                        controller: 'ManyToManyEntityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('manyToManyEntity');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('manyToManyEntity.detail', {
                parent: 'entity',
                url: '/manyToManyEntity/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.manyToManyEntity.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/manyToManyEntity/manyToManyEntity-detail.html',
                        controller: 'ManyToManyEntityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('manyToManyEntity');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ManyToManyEntity', function($stateParams, ManyToManyEntity) {
                        return ManyToManyEntity.get({id : $stateParams.id});
                    }]
                }
            })
            .state('manyToManyEntity.new', {
                parent: 'manyToManyEntity',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/manyToManyEntity/manyToManyEntity-dialog.html',
                        controller: 'ManyToManyEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('manyToManyEntity', null, { reload: true });
                    }, function() {
                        $state.go('manyToManyEntity');
                    })
                }]
            })
            .state('manyToManyEntity.edit', {
                parent: 'manyToManyEntity',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/manyToManyEntity/manyToManyEntity-dialog.html',
                        controller: 'ManyToManyEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ManyToManyEntity', function(ManyToManyEntity) {
                                return ManyToManyEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('manyToManyEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
