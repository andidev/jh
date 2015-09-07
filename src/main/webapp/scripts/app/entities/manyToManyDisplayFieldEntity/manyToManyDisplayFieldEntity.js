'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('manyToManyDisplayFieldEntity', {
                parent: 'entity',
                url: '/manyToManyDisplayFieldEntitys',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.manyToManyDisplayFieldEntity.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/manyToManyDisplayFieldEntity/manyToManyDisplayFieldEntitys.html',
                        controller: 'ManyToManyDisplayFieldEntityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('manyToManyDisplayFieldEntity');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('manyToManyDisplayFieldEntity.detail', {
                parent: 'entity',
                url: '/manyToManyDisplayFieldEntity/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.manyToManyDisplayFieldEntity.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/manyToManyDisplayFieldEntity/manyToManyDisplayFieldEntity-detail.html',
                        controller: 'ManyToManyDisplayFieldEntityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('manyToManyDisplayFieldEntity');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ManyToManyDisplayFieldEntity', function($stateParams, ManyToManyDisplayFieldEntity) {
                        return ManyToManyDisplayFieldEntity.get({id : $stateParams.id});
                    }]
                }
            })
            .state('manyToManyDisplayFieldEntity.new', {
                parent: 'manyToManyDisplayFieldEntity',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/manyToManyDisplayFieldEntity/manyToManyDisplayFieldEntity-dialog.html',
                        controller: 'ManyToManyDisplayFieldEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {displayField: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('manyToManyDisplayFieldEntity', null, { reload: true });
                    }, function() {
                        $state.go('manyToManyDisplayFieldEntity');
                    })
                }]
            })
            .state('manyToManyDisplayFieldEntity.edit', {
                parent: 'manyToManyDisplayFieldEntity',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/manyToManyDisplayFieldEntity/manyToManyDisplayFieldEntity-dialog.html',
                        controller: 'ManyToManyDisplayFieldEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ManyToManyDisplayFieldEntity', function(ManyToManyDisplayFieldEntity) {
                                return ManyToManyDisplayFieldEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('manyToManyDisplayFieldEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
