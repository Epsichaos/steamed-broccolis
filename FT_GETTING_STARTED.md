GETTING STARTED WITH FT TOOLS
=============================

1. Resources
2. Create your own project
3. Build your project
4. Issues

Resources
---------

1. JAVA

You will need to install JAVA SDK (version >= 8)

 If JAVA is already installed, or if you have multiple Java version, make sure you use the correct one:
 ```
  java -version
  javac -version
 ```

You can change your java/javac version with the following command:

```
  sudo update -alternatives --config java
```

If your JAVA version is not recognized, you can install the sdk you previously downloaded with:

```
  sudo update -alternatives --install <path_to_java_executable_e.g._/usr/bin/java> <name_of_your_sdk> <path_to_your_new_sdk> <priority>
```

2. MongoDB

Install MongoDB with the command line.
```
  [Debian]
  apt-get install mongodb-org
  [OSX]
  brew install mongo
```
MongoDB Cheats:

```
  [Run mongo shell]
  mongo
  [Show db]
  show databases
  [Connect to db]
  use db_name
  [List `tables`]
  show collections
  [insert|remove|update]
  db_name.collection_name.insert({...})
  db_name.collection_name.findOne()
```

NB: On OSX, you don't have the `service` command to stop/start/restart and handle your services, but you can use `brew services` - which is nice:

```
  brew services [start|stop|restart] <service_name>
```

3. Play Framework

FT team is developing the software using IntelliJ IDEA. You won't need to install or do anything about Play, as the IDE allows you to create automatically a Play project, without doing anything else.

4. Google Web Toolkit

Download the GWT library from the official website and extract it.

5. IntelliJ IDEA - Ultimate version

Download and install IntelliJ IDEA. You won't be able to create Play or GWT projects with the community (free) version. So just download the Ultimate version, which is free for one month.

Create your project
-------------------

FT project is split into 2 parts:

- client project: GWT project creating the interface of the Single Page Application (SPA)
- server project: Play server handling routes and HTTP requests

The first thing to do is create 2 different projects, one for the server and one for the client. We'll need to make this 2 projects work together.

### 1. Play server

#### 1.1 Create the project on [IntelliJ]

* File > New > Project
* Select JAVA on the left Panel, then PLAY 2.X on the right panel
* Enter project name & location

#### 1.2 Run the server [IntelliJ]

* Run > Run...
* Edit configurations
* Create new configuration (`+`)
* Play 2 App
* Enter the name and select the module to build (e.g. your project)

#### 1.3 Server structure

The server is organized under the following structure:

```
.
├── app
│   ├── controllers
│   └── views
├── build.sbt
├── conf
│   ├── application.conf
│   └── routes
├── project
│   ├── build.properties
│   ├── plugins.sbt
│   ├── project
│   └── target
├── public
│   ├── images
│   ├── javascripts
│   └── stylesheets
├── src
├── target
│   ├── resolution-cache
│   └── streams
└── test
    ├── ApplicationTest.java
    └── IntegrationTest.java
```

* `app/`: Application directory which contains the Models, Views and Controllers of the server. The App will be developed here.
* `build.sbt`: Building file containing the dependencies.
* `conf/`: Contains the application & the routes of the server.
* `project/`: Project configuration.
* `public/`: Statics assets (stylesheets, images, javascript, ...).
* `target/`: Folder containing the compiled sources.
* `test/`: Tests.

#### 1.4 Routes

The routes are defined in `conf/routes`. You can easily define routes according the following syntax:

```
# Home page
GET    /                            controllers.Application.index
POST   /example                     controllers.ExampleController.postExample()
GET    /example/:id                 controllers.ExampleController.getExample(id: String)
```

In the route file, you define which function of which controller should be called after an HTTP request on a specific route. You also have an example above of the use of parameters in the routes.

#### 1.5 Controllers



#### 1.6 Using Jongo plugin for MongoDB



### 2. GWT project

#### 2.1 Create the project [IntelliJ]

#### 2.2 Run the client [IntelliJ]

#### 2.3 GWT Structure
