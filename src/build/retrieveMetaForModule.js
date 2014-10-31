var path = require("path"), fs = require("fs");

module.exports = function retrieveMetaForModule(moduleName) {
	var
		 stat = null,
		 filename = path.normalize(path.join(__dirname, '..', 'main', 'webapp', moduleName + '.metadata.json')),
		 ret = null;
	try {
		stat = fs.statSync(filename);
	}
	catch(ignore) {
		// IGNORE
	}
	if( stat !== null && stat.isFile() ) {
		try {
			ret = fs.readFileSync(filename);
			ret = JSON.parse(ret);
		}
		catch(e) {
			ret = null;
			console.log(e);
		}
	}
	return ret;
};
