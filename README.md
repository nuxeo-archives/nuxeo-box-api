#Nuxeo Box API
---
## General information and motivation

The **Nuxeo** addon _nuxeo-box-api_ is an implementation of [Box](http://www.box.com) api on top of Nuxeo repository. It transforms the Nuxeo repository into a Box compliant storage backend. Use cases of such an approach are:
- light integration on continuous integration chain for your Box development
- on-premise setup of Box stored content
- extension of feature scope on your project (workflow, conversions, ...)

### Getting Started
<!-- - [Install a Nuxeo server](http://www.nuxeo.com/en/downloads)  -->
- Install a [Nuxeo 5.9.3-SNAPSHOT server](http://community.nuxeo.com/static/snapshots/)
  - Note: while this addon is not yet released,
     - it is only available in development version: 1.0.0-SNAPSHOT
     - it requires [Connect registration](https://connect.nuxeo.com/nuxeo/site/connect/trial/form) for access to the Marketplace DEV channel.

- Install nuxeo-box-api from command line with [Nuxeo Control Panel](http://doc.nuxeo.com/x/FwNc):
  - Linux/Mac:
    - `chmod +x bin/*ctl bin/*.sh`
    - `./bin/nuxeoctl mp-init`
    - `./bin/nuxeoctl mp-install nuxeo-box-api`
    - `./bin/nuxeoctl start`
  - Windows:
    - `bin\nuxeoctl.bat mp-init`
    - `bin\nuxeoctl.bat mp-install nuxeo-box-api`
    - `bin\nuxeoctl.bat start`

- Install nuxeo-box-api from [Nuxeo Marketplace](http://marketplace.nuxeo.com/):
  - Browse [nuxeo-box-api Marketplace Package](https://connect.nuxeo.com/nuxeo/site/marketplace/package/nuxeo-box-api)

- Login
  - username: Administrator
  - password: Administrator
  
#####Usage examples:

Folders:

- GET http://localhost:8080/nuxeo/box/**2.0/folders/{folder_id}**
- GET https://api.box.com/**2.0/folders/{folder id}**

Files:

- GET http://localhost:8080/nuxeo/box/**2.0/files/{file_id}**
- GET https://api.box.com/**2.0/files/{file_id}**


The complete documentation of the Box API can be found [here](https://developers.box.com/docs/).
  

###REST API Compatibility Matrix

Features | Box | Nuxeo
------------ | ------------- | ------------
[**Folders**](https://developers.box.com/docs/#folders)| Yes | Yes
[**Files**](https://developers.box.com/docs/#files)| Yes | Yes
[**Comments**](https://developers.box.com/docs/#comments)| Yes | Yes
[**Collaborations**](https://developers.box.com/docs/#collaborations)| Yes | Yes
[**Search**](https://developers.box.com/docs/#search)| Yes | Yes
[**Events**](https://developers.box.com/docs/#events)| Yes | Not yet
[**Shared Items**](https://developers.box.com/docs/#shared-items)| Yes | Not yet
[**Users**](https://developers.box.com/docs/#users)| Yes | Not yet
[**Groups**](https://developers.box.com/docs/#groups)| Yes | Not yet
[**Tasks**](https://developers.box.com/docs/#tasks)| Yes | Not yet

###Report & Contribute

We are glad to welcome new developers on this initiative, and even simple usage feedback is great.
- Ask your questions or report issues on [Nuxeo Answers](http://answers.nuxeo.com)
- [Nuxeo JIRA Issue Management](https://jira.nuxeo.com/browse/NXP)
- See [Contributing to Nuxeo](http://doc.nuxeo.com/x/VIZH)

##Sample usage

###Java Box SDK usage with Nuxeo Box API

The [Box Java SDK 2.2.2](https://github.com/box/box-java-sdk-v2) can be used to browse Nuxeo repositories.

Maven artifact:

    <dependency>
      <groupId>com.box</groupId>
      <artifactId>box-java-sdk</artifactId>
      <version>2.2.2</version>
    </dependency>

    <repositories>
     <repository>
      <id>public</id>
      <url>http://maven.nuxeo.org/nexus/content/groups/public</url>
      <releases>
        <enabled>true</enabled>
      </releases>
      <snapshots>
        <enabled>false</enabled>
      </snapshots>
     </repository>
     <repository>
      <id>public-snapshot</id>
      <url>http://maven.nuxeo.org/nexus/content/groups/public-snapshot</url>
      <releases>
        <enabled>false</enabled>
      </releases>
      <snapshots>
        <updatePolicy>always</updatePolicy>
        <enabled>true</enabled>
      </snapshots>
     </repository>
    </repositories>


Initialize the client with the following lines to make it point to your Nuxeo server:

        import com.box.boxjavalibv2.BoxClient;
        import com.box.boxjavalibv2.BoxConfig;
        
        static final String PROTOCOL = "http";
        static final String SERVER_URL = "localhost:8080";

        BoxConfig boxConfig = BoxConfig.getInstance();
        
        boxConfig.setAuthUrlScheme(PROTOCOL); // Default value is "https"
        boxConfig.setOAuthUrlAuthority(SERVER_URL);
        boxConfig.setOAuthApiUrlPath("/nuxeo");

        boxConfig.setApiUrlScheme(PROTOCOL); // Default value is "https"
        boxConfig.setApiUrlAuthority(SERVER_URL);
        boxConfig.setApiUrlPath("/nuxeo/box/2.0");

        boxConfig.setUploadUrlScheme(PROTOCOL); // Default value is "https"
        boxConfig.setUploadUrlAuthority("/nuxeo");
        boxConfig.setUploadUrlPath("/nuxeo/site/box/2.0");
        

        
###Box/Nuxeo hybrid client application sample

[This sample application](https://github.com/nuxeo/nuxeo-box-angular-sample) browses Nuxeo and Box repositories with the same [REST API](https://developers.box.com/docs/). Only the base URL should be updated.

Examples:

- Same endpoints (to fetch a folderish document definition) such as:
  - GET http://server/nuxeo/box/**2.0/folders/{folder_id}**
  - GET https://api.box.com/**2.0/folders/{folder id}**
- Same JSON payload response such as:
  - <http://developers.box.com/docs/#folders-folder-object>

##About
###Nuxeo

[Nuxeo](http://www.nuxeo.com) provides a modular, extensible Java-based [open source software platform for enterprise content management](http://www.nuxeo.com/en/products/content-management-platform), and packaged applications for [document management](http://www.nuxeo.com/en/products/document-management), [digital asset management](http://www.nuxeo.com/en/products/digital-asset-management), [social collaboration](http://www.nuxeo.com/en/products/social-collaboration) and [case management](http://www.nuxeo.com/en/products/case-management).

Designed by developers for developers, the Nuxeo platform offers a modern architecture, a powerful plug-in model and extensive packaging capabilities for building content applications.

More information on: <http://www.nuxeo.com/> 

###Box
Box documentation and account:

- <https://app.box.com/signup/personal/> -> Box Signup
- <https://developers.box.com/docs/> -> API Documentation
