var require = {
	baseUrl: '/',

	paths: {
		'angular': 'scripts/vendor/angular/angular'
	},
	
	map: {
		'*': {
			'text': 'scripts/vendor/requirejs-text/text',
			'lazy': 'scripts/vendor/require-lazy/lazy',
			'lazy-builder': 'scripts/vendor/require-lazy/lazy-builder',
			'promise-adaptor': 'scripts/vendor/angular-require-lazy/promiseAdaptorAngular',
			'currentModule': 'scripts/vendor/angular-require-lazy/currentModule',
			'templateCache': 'scripts/vendor/angular-require-lazy/templateCache',
			'ngLazy': 'scripts/vendor/angular-require-lazy/ngLazy',
			'ngLazyBuilder': 'scripts/vendor/angular-require-lazy/ngLazyBuilder'
		}
	},
	
	shim: {
		'angular': {
			exports: 'angular'
		},
		'scripts/vendor/angular-route/angular-route': {
			deps: ['angular']
		},
		'scripts/vendor/angular-bootstrap/ui-bootstrap-tpls': {
			deps: ['angular']
		},
		'scripts/vendor/angular-uuid4/angular-uuid4': {
			deps: ['angular']
		},
		'scripts/vendor/angular-ui-utils/ui-utils': {
			deps: ['angular']
		}
	}
};
