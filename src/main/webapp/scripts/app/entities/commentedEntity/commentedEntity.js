'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('commentedEntity', {
                parent: 'entity',
                url: '/commentedEntitys',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.commentedEntity.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/commentedEntity/commentedEntitys.html',
                        controller: 'CommentedEntityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('commentedEntity');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('commentedEntity.detail', {
                parent: 'entity',
                url: '/commentedEntity/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.commentedEntity.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/commentedEntity/commentedEntity-detail.html',
                        controller: 'CommentedEntityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('commentedEntity');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CommentedEntity', function($stateParams, CommentedEntity) {
                        return CommentedEntity.get({id : $stateParams.id});
                    }]
                }
            })
            .state('commentedEntity.new', {
                parent: 'commentedEntity',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/commentedEntity/commentedEntity-dialog.html',
                        controller: 'CommentedEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {commentedField: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('commentedEntity', null, { reload: true });
                    }, function() {
                        $state.go('commentedEntity');
                    })
                }]
            })
            .state('commentedEntity.edit', {
                parent: 'commentedEntity',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/commentedEntity/commentedEntity-dialog.html',
                        controller: 'CommentedEntityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CommentedEntity', function(CommentedEntity) {
                                return CommentedEntity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('commentedEntity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
