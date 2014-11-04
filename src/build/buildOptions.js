// require-lazy configuration
var
    path = require('path'),
    fs = require('fs'),
    discoverModules = require('./discoverModules'),
    retrieveMetaForModule = require('./retrieveMetaForModule');

module.exports = {
    // this must match the map['*'].lazy property in require-cfg.js
    libLazy: 'scripts/vendor/require-lazy/lazy',
    basePath: path.normalize(path.join(__dirname, '..', 'main', 'webapp')),
    outputBaseDir: path.normalize(path.join(__dirname, '..', '..', 'target', 'require-lazy')),
    discoverModules: discoverModules,
    retrieveMetaForModule: retrieveMetaForModule,
    makeBuildRelativePath: function(x) {
        return path.normalize(path.join(__dirname, '..', '..', x));
    }
};
