Production datasource
=====================

Mode 1 (
When deploying into production, and JNDI datasource is required. For tomcat installations make sure to satisfy the following:

1. Add the PostgreSQL JDBC driver into Tomcat lib.
2. Edit Tomcat `server.xml` to include the following in the `GlobalNamingResources` section:
        
        <Resource name="RegistryDatasource" auth="Container" type="javax.sql.DataSource" 
        		driverClassName="org.postgresql.Driver" url="jdbc:postgresql://localhost:5432/registry" 
        		username="registry" password="registry" maxActive="10" maxIdle="5" maxWait="10000" 
        		testOnBorrow="true" validationQuery="select 1" validationInterval="360000"/>
