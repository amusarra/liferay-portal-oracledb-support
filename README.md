# Welcome to Oracle DB support for Liferay CE 7.0 GA1

Those who follow Liferay is aware of the fact that the Community Edition version 7 of Liferay, were eliminated quite a bit of components App Server, Database & Clustering Support. For more detail information you can read the blog post by [Bryan Cheung]( https://www.liferay.com/it/web/bryan.cheung/blog/-/blogs/liferay-portal-7-ce-app-server-database-clustering-support) published on April 7, 2016.

The Liferay 7 CE GA1 no more support OOTB:
* Application Server: Oracle WebLogic, IBM WebSphere
* Clustering
* MultiVM Cache
* Oracle Database, Microsoft SQL Server, IBM DB2, Sybase DB

This sample project demonstrates how to add support to the Oracle database. Liferay has performed refactorting the code so that it is possible and easy to add support for databases no longer supported OOTB.

## 1. Introduction
To extend support to other databases, Liferay has decided to refactory code to use Java [*SPI (Service Provider Interface)*](https://docs.oracle.com/javase/tutorial/sound/SPI-intro.html). SPI is the mechanism that allows you to extend / change the behavior within a system without changing the source. It includes interfaces, classes or methods that the user extends or implements in order to obtain a certain functionality.

In short we must:
* Implement the SPI interface [com.liferay.portal.kernel.dao.db.DBFactory](https://github.com/liferay/liferay-portal/blob/2960360870ae69360861a720136e082a06c5548f/portal-kernel/src/com/liferay/portal/kernel/dao/db/DBFactory.java). Implementation class inside this project is **OracleDBFactory.java**
* Implement the abstract class [com.liferay.portal.dao.db.BaseDB](https://github.com/liferay/liferay-portal/blob/master/portal-impl/src/com/liferay/portal/dao/db/BaseDB.java) for Oracle DB. Implementation class inside this project is **OracleDB.java**


## 2. Build project
The driver that adds support for Oracle database is a jar (**liferay-portal-oracledb-support-${version}.jar**) which then will be installed in ROOT/WEB-INF/lib (for apache tomcat).

To generate the driver for Oracle database just follow the instructions below.

```
$ git clone https://github.com/amusarra/liferay-portal-oracledb-support.git
$ mvn package
```

the build process create the jar inside the (maven) target directory:

```
liferay-portal-oracledb-support-1.0-SNAPSHOT.jar
```

## 3. Install Liferay CE 7 on Oracle Database

1. Download [Liferay CE 7 GA1 Tomcat Bundle](https://sourceforge.net/projects/lportal/files/Liferay%20Portal/7.0.0%20GA1/liferay-portal-tomcat-7.0-ce-ga1-20160331161017956.zip/download) from sourceforge
2. Extract the Liferay bundle
3. Copy the jar **liferay-portal-oracledb-support-${version}.jar** in $LIFERAY_HOME/$TOMCAT_HOME/webapps/ROOT/WEB-INF/lib
4. Download and install [Oracle JDBC driver (ojdbc6.jar)](http://www.oracle.com/technetwork/apps-tech/jdbc-112010-090769.html) in $LIFERAY_HOME/$TOMCAT_HOME/lib/ext
5. Configure **portal-ext.properties** with jdbc connection configuration
6. Launch the Liferay Portal via $LIFERAY_HOME/$TOMCAT_HOME/bin/startup.sh
7. See the log $LIFERAY_HOME/$TOMCAT_HOME/logs/catalina.out for monitoring activities

Below you can see the portal-ext.properties

```
##
## Admin Portlet
##
    #
    # Configure email notification settings.
    #
    admin.email.from.name=Joe Bloggs
    admin.email.from.address=test@liferay.com

##
## JDBC
##

    #
    # Oracle
    #
    jdbc.default.driverClassName=oracle.jdbc.OracleDriver
    jdbc.default.password=liferay12345
    jdbc.default.url=jdbc:oracle:thin:@oracledb.vm.local:1521:xe
    jdbc.default.username=liferayce7

##
## Liferay Home
##
    #
    # Specify the Liferay home directory.
    #
    liferay.home=/opt/liferay-portal-7.0-ce-ga1-node-1

##
## Setup Wizard
##
    #
    # Set this property to true if the Setup Wizard should be displayed the
    # first time the portal is started.
    #
    setup.wizard.enabled=false
```
