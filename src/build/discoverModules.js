var path = require("path"), fs = require("fs");

function discoverModules() {
	var modulesDir = path.normalize(path.join(__dirname, "..", "main", "webapp")),
		filenames = fs.readdirSync(modulesDir),
		stat, i, res = [];
	for( i=0; i < filenames.length; i++ ) {
		try {
			stat = fs.statSync(path.join(modulesDir, filenames[i], filenames[i] + ".metadata.json"));
			if( stat != null && stat.isFile() ) {
				res.push(filenames[i] + "/" + filenames[i]);
			}
		}
		catch(ignore) {
			// IGNORE
		}
	}
	return res;
//	return ["app/modules/index","app/modules/categories","app/modules/expenses"];
}

module.exports = discoverModules;
