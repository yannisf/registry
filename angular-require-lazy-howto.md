Howto with angular-require-lazy
===============================

1. Run `npm install`
	- Grunt-cli is needed; install it locally with `npm install grunt-cli` or globally using `npm install -g grunt-cli`
2. Start db, tomcat as normal; client-side development will use a connect server
3. Run `grunt server`
4. Go to http://localhost:8110/index.html

Running the built application with Tomcat
-----------------------------------------

1. `mvn clean`
2. `mvn package -Pgrunt`
3. `mvn tomcat7:run-war`
4. Go to http://localhost:8080/school/index-built.html

**What to note:** On the network tab of the browser's debugger, the Javascripts requested are concatenated
and requested only when needed; e.g. the `guardian/guardian-built.js` is requested only upon navigation to
a guardian-related view. Additionally, some Javascripts are requested with a `?v=1234...` request parameter,
a content-based hash used to bust any caches. This feature can be applied to the `bootstrap-built.js` and
`application-built.js`, with some extra effort.

The concatenated files are not minified; this helps debugging the built artifacts at this stage of
development. It can easilly be added with a small extra effort.
