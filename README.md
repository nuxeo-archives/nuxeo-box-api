#Nuxeo Box API

The **Nuxeo** addon _nuxeo-box-api_ is an implementation of [Box](http://www.box.com) api on top of Nuxeo repository. It transforms the Nuxeo repository into a Box compliant storage backend. Use cases of such an approach are:
- light integration on continuous integration chain for your Box development
- on-premise setup of Box stored content


# Getting Started

- [Download a Nuxeo server](http://www.nuxeo.com/en/downloads) (the zip version)

- Unzip it

- Install nuxeo-box-api plugin from command line
  - Linux/Mac:
    - `NUXEO_HOME/bin/nuxeoctl mp-init`
    - `NUXEO_HOME/bin/nuxeoctl mp-install nuxeo-box-api`
    - `NUXEO_HOME/bin/nuxeoctl start`
  - Windows:
    - `NUXEO_HOME\bin\nuxeoctl.bat mp-init`
    - `NUXEO_HOME\bin\nuxeoctl.bat mp-install nuxeo-box-api`
    - `NUXEO_HOME\bin\nuxeoctl.bat start`

- From your browser, go to `http://localhost:8080/nuxeo`

- Follow Nuxeo Wizard by clicking 'Next' buttons, re-start once completed

- Check Nuxeo correctly re-started `http://localhost:8080/nuxeo`
  - username: Administrator
  - password: Administrator

- You can now use the Box api against this running Nuxeo server.


Note: Your machine needs internet access. If you have a proxy setting, skip the mp-init and mp-install steps at first, just do nuxeoctl start and run the wizzard where you will be asked your proxy settings.

##API Usage Examples:

###Folders:

- GET http://localhost:8080/nuxeo/box/**2.0/folders/{folder_id}**

looks like

- GET https://api.box.com/**2.0/folders/{folder id}**

###Files:

- GET http://localhost:8080/nuxeo/box/**2.0/files/{file_id}**

looks like

- GET https://api.box.com/**2.0/files/{file_id}**


The complete documentation of the Box API can be found [here](https://developers.box.com/docs/).

Only the base URL `https://api.box.com` must be replaced by `http://localhost:8080/nuxeo/box` for instance.


##REST API Compatibility Matrix

Features | Box | Nuxeo
------------ | ------------- | ------------
[**Folders**](https://developers.box.com/docs/#folders)| Yes | Yes
[**Files**](https://developers.box.com/docs/#files)| Yes | Yes
[**Comments**](https://developers.box.com/docs/#comments)| Yes | Yes
[**Collaborations**](https://developers.box.com/docs/#collaborations)| Yes | Yes
[**Search**](https://developers.box.com/docs/#search)| Yes | Yes
[**Events**](https://developers.box.com/docs/#events)| Yes | On Demand
[**Shared Items**](https://developers.box.com/docs/#shared-items)| Yes | On Demand
[**Users**](https://developers.box.com/docs/#users)| Yes | On Demand
[**Groups**](https://developers.box.com/docs/#groups)| Yes | On Demand
[**Tasks**](https://developers.box.com/docs/#tasks)| Yes | On Demand

#Sample usage

##Java Box SDK usage with Nuxeo Box API

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



##Box/Nuxeo hybrid client application sample

[This sample application](https://github.com/nuxeo/nuxeo-box-angular-sample) browses Nuxeo and Box repositories with the same [REST API](https://developers.box.com/docs/). Only the base URL should be updated.

Examples:

- Same endpoints (to fetch a folderish document definition) such as:
  - GET http://server/nuxeo/box/**2.0/folders/{folder_id}**
  - GET https://api.box.com/**2.0/folders/{folder id}**
- Same JSON payload response such as:
  - <http://developers.box.com/docs/#folders-folder-object>


# Building

    mvn clean install

## Deploying

Install [the Nuxeo Box API Marketplace Package](https://connect.nuxeo.com/nuxeo/site/marketplace/package/nuxeo-box-api).
Or manually copy the built artifacts into `$NUXEO_HOME/templates/custom/bundles/` and activate the "custom" template.

## QA results

[![Build Status](https://qa.nuxeo.org/jenkins/buildStatus/icon?job=addons_nuxeo-box-api-master)](https://qa.nuxeo.org/jenkins/job/addons_nuxeo-box-api-master/)

##Report & Contribute

We are glad to welcome new developers on this initiative, and even simple usage feedback is great.
- Ask your questions on [Nuxeo Answers](http://answers.nuxeo.com)
- Report issues on this GitHub repository (see [issues link](http://github.com/nuxeo/nuxeo-box-api/issues) on the right)
- Contribute: Send pull requests!

#About
##Nuxeo

Nuxeo dramatically improves how content-based applications are built, managed and deployed, making customers more agile, innovative and successful. Nuxeo provides a next generation, enterprise ready platform for building traditional and cutting-edge content oriented applications. Combining a powerful application development environment with SaaS-based tools and a modular architecture, the Nuxeo Platform and Products provide clear business value to some of the most recognizable brands including Verizon, Electronic Arts, Netflix, Sharp, FICO, the U.S. Navy, and Boeing. Nuxeo is headquartered in New York and Paris. More information is available at www.nuxeo.com.

##Box
Box documentation and account:

- <https://app.box.com/signup/personal/> -> Box Signup
- <https://developers.box.com/docs/> -> API Documentation
