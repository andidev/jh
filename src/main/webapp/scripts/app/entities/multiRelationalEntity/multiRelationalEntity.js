'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('multiRelationalEntity', {
                parent: 'entity',
                url: '/multiRelationalEntitys',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.multiRelationalEntity.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/multiRelationalEntity/multiRelationalEntitys.html',
                        controller: 'MultiRelationalEntityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('multiRelationalEntity');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('multiRelationalEntity.detail', {
                parent: 'entity',
                url: '/multiRelationalEntity/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.multiRelationalEntity.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/multiRelationalEntity/multiRelationalEntity-detail.html',
                        controller: 'MultiRelationalEntityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('multiRelationalEntity');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MultiRelationalEntity', function($stateParams, MultiRelationalEntity) {
                        return MultiRelationalEntity.get({id : $stateParams.id});
                    }]
                }
            })
            .state('multiRelationalEntity.new', {
                parent: 'multiRelationalEntity',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/multiRelationalEntity/multiRelationalEntity-dialog.html',
                        controller: 'MultiRelationalEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('multiRelationalEntity', null, { reload: true });
                    }, function() {
                        $state.go('multiRelationalEntity');
                    })
                }]
            })
            .state('multiRelationalEntity.edit', {
                parent: 'multiRelationalEntity',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/multiRelationalEntity/multiRelationalEntity-dialog.html',
                        controller: 'MultiRelationalEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MultiRelationalEntity', function(MultiRelationalEntity) {
                                return MultiRelationalEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('multiRelationalEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
