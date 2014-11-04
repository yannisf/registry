// required
var baseUrl = '.';
// Almond suggests it is best not to use the `var require = ...` configuration.
// Since it is convenient I keep ot so, but for the built application I keep the
// original config in rcfg, delete require, then call `require.config()` after
// almond is loaded.
// If not for this workaround, the `config.map` option does not work.
var rcfg = require;
delete require;
