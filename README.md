![travis ci](https://travis-ci.org/amusarra/liferay-portal-oracledb-support.svg?branch=master)

# Welcome to Oracle DB support for Liferay CE 7.0 GA1

Those who follow Liferay is aware of the fact that the Community Edition version 7 of Liferay, were eliminated quite a bit of components App Server, Database & Clustering Support. For more detail information you can read the blog post by [Bryan Cheung]( https://www.liferay.com/it/web/bryan.cheung/blog/-/blogs/liferay-portal-7-ce-app-server-database-clustering-support) published on April 7, 2016.

The Liferay 7 CE GA1 no more support OOTB (Out Of The Box):
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

In the figure below shows the complete class diagram for OracleDB.

![Class Diagram for OracleDB](http://www.dontesta.it/blog/wp-content/uploads/2014/02/OracleDB.png)

## 2. Build project
**Requirements for build project**
1. Sun/Oracle JDK 1.7
2. Maven 3.x (for build project)

The driver that adds support for Oracle database is a jar (**liferay-portal-oracledb-support-${version}.jar**) which then will be installed in ROOT/WEB-INF/lib (for apache tomcat).

To generate the driver for Oracle database just follow the instructions below.

You can download the binary jar [liferay-portal-oracledb-support-1.0-SNAPSHOT.jar](https://github.com/amusarra/liferay-portal-oracledb-support/releases/download/v1.0/liferay-portal-oracledb-support-1.0-SNAPSHOT.jar), by doing so you can avoid doing the build.

```
$ git clone https://github.com/amusarra/liferay-portal-oracledb-support.git
$ mvn package
```

the build process create the jar inside the (maven) target directory:

```
liferay-portal-oracledb-support-1.0-SNAPSHOT.jar
```

## 3. Install Liferay CE 7 on Oracle Database

To install Liferay on Oracle you must have previously configured a schema for Liferay on an Oracle instance (including Oracle XE 11gR2).

I have used an Oracle XE 11gR2 installation on CentOS 6.2 created on the fly on VirtualBox.

The parameters of my Oracle instance are:
* Username: liferayce7
* Password: liferay12345
* FQDN: oracledb.vm.local (IP: 192.168.56.101)
* TCP/IP Port: 1521
* SID: XE

For the installation of Liferay follow the following steps:
1. Download [Liferay CE 7 GA1 Tomcat Bundle](https://sourceforge.net/projects/lportal/files/Liferay%20Portal/7.0.0%20GA1/liferay-portal-tomcat-7.0-ce-ga1-20160331161017956.zip/download) from sourceforge
2. Extract the Liferay bundle (in my case $LIFERAY_HOME is /opt/liferay-portal-7.0-ce-ga1-node-1)
3. Copy the jar **liferay-portal-oracledb-support-${version}.jar** in $LIFERAY_HOME/$TOMCAT_HOME/webapps/ROOT/WEB-INF/lib
4. Download and install [Oracle JDBC driver (ojdbc6.jar)](http://www.oracle.com/technetwork/apps-tech/jdbc-112010-090769.html) in $LIFERAY_HOME/$TOMCAT_HOME/lib/ext
5. Create the **portal-ext.properties** in $LIFERAY_HOME with the content as the file below. ***You should modify the JDBC connection parameters to the your db and the value of liferay.home***
6. Launch the Liferay Portal through the command $LIFERAY_HOME/$TOMCAT_HOME/bin/startup.sh
7. See the Liferay activities via the log file $LIFERAY_HOME/$TOMCAT_HOME/logs/catalina.out

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
    jdbc.default.username=liferayce7
    jdbc.default.password=liferay12345
    jdbc.default.url=jdbc:oracle:thin:@oracledb.vm.local:1521:xe

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

You can see the my complete [catalina.out](https://gist.github.com/amusarra/7f7d5c2f4d73e88f55f092d4d1853ff9) log file and following some screenshots of DB.

![Liferay CE 7 GA1 on Oracle ](http://www.dontesta.it/blog/wp-content/uploads/2014/02/Liferay7CEOnOracleDataBase.png)

![Liferay CE 7 GA1 on Oracle ](http://www.dontesta.it/blog/wp-content/uploads/2014/02/Liferay7CEOnOracleDataBase_1.png)
