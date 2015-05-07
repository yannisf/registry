Build
=====

To build the application the client and server must be built individually.

Client
------
The client uses node.js, npm, bower and grunt for the build. In `package.json` exist the required dependencies for 
grunt. Node.js and npm must be installed and in the path. Grunt and bower must be installed afterwards. This can be achieved
like so:

`npm install -g grunt-cli`

`npm install -g bower`

Grunt manages the build. In order to fetch the build dependencies run this first:

`npm install`

The application dependencies are managed by bower. In `.bowerrc` the dependencies download location is defined and 
`bower.json` lists the application dependencies. Before building the application fetch the dependencies like so:

`bower install`

This command needs to be executed whenever the `bower.json` file is changed, typically when adding or updating a dependency.

Now you are in position to actually build the client. The build script is `Gruntfile.js` and its tasks may be invoked like so:

`grunt task`

There are two tasks that concerns us: **default** and **compile**.

The **default** tasks build the client for use during development, while the **compile** prepares a production build.
These tasks maty be invoked respectively like so:
 
 `grunt`
 
 `grunt compile`

Server
-----
For the server build, maven is used. The build uses two profiles **dev** and **prod**, the first one being the default.
During development the maven command to execute is:

`mvn clean tomcat7:run-war -Dmaven.test.skip`

This will launch the application within an embedded tomcat. It uses a local datasource. It is required that the user
has already built the client:

`grunt`

When preparing a production release the command to use is:

`mvn clean package -Dmaven.test.skip -Pprod`

This command will build a production package to deploy on tomcat. Alternatively, after parametrise accordingly certain
 system variables and have configured tomcat manage, using the **deploy** profile as well the application may be deployed
 during the build. This build requires a JNDI datasource. It is required that the user has already built the client:
 
 `grunt compile`

Notes
-----
To add a build dependency in `package.json`, e.g. `less` use the following command:
`npm install grunt-contrib-less --save-dev`


