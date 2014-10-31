module.exports = function getLazyRegistry(req, res, next) {
	var modules, text, i, metadata, pmresult;
	
	if( req.url !== '/lazy-registry.js' ) {
		return next();
	}

	modules = require('./discoverModules')();
	pmresult = makePmresult(modules);
	text = require('require-lazy').shared.createModulesRegistryText(pmresult, {
		retrieveMetaForModule: require('./retrieveMetaForModule')
	}, {
		inludeModuleName: false,
		generateBody: true,
		nullBundleDeps: true,
		writeBundleRegistrations: false
	});
	
	res.end(text);
};

function makePmresult(modules) {
	var i, dummyParents = ['DUMMY'], pmresult = {
		modulesArray: []
	};
	
	for( i=0; i < modules.length; i++ ) {
		pmresult.modulesArray.push({
			name: modules[i],
			index: i,
			parents: dummyParents
		});
	}
	
	return pmresult;
}
