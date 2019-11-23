Fondue
======

Fondue is a generator-based sub framework for Spring Boot and MyBatis.

Fondue has 3 features;
 1. Generator for Model resources (using MyBatisGenerator)
   * Model class (Entity)
   * (Model)Example.java
   * (Model)Mapper.java
   * (Model)Mapper.xml
 2. Generator for CRUD resources
   * CrudController.java
   * Form.java (with Bean Validation)
   * CrudService.java
   * CrudServiceImpl.java
   * (Model)Dao.java (Optional)
   * list.html
   * detail.html
   * edit.html
 3. Sub framework for Spring Boot
   * Pagination (yet another one)
   * Conversion between Form and Model
   * Display information message by type
   * Minimum settings for running boot app

For more information, see examples and [wiki pages](https://github.com/argius/fondue/wiki).


Installation
------------

Fondue does not support installation so far.  
Download source codes from [the release page](https://github.com/argius/fondue/releases) and import into your IDE (with `gradle eclipse` command).


Get Started
-----------

It requires `gradle` version 4.10 or higher.  
If not installed gradle, use `./gradlew` instead.

## To run the app in examples

To run fondue-app1;

1. Run `gradle example:fondue-app1:dbMigrate` to prepare a database contains 2 tables.
2. Run `gradle example:fondue-app1:bootRun` to run fondue-app1 at fondue-root directory.


## To create a new app

1. Run `./add <app-name>` at fondue-root directory.
2. Run `gradle <app-name>:initApp` at your project.
3. Run `gradle <app-name>:dbMigrate` at your project.
4. Run `gradle <app-name>:genAll` (genModel and genCrud) at your project.
5. Run `gradle <app-name>:bootRun` to run your app.
6. Configure `MyBatisGeneratorConfig.xml` and `fondue-gen.yml`
7. Repeat from #4

To specify rootPkg when initializing app ('initApp'), use 'fondue.rootPkg' as env or system property.  
See the following snippet.

```
$ env fondue.rootPkg="net.argius.webapp.f1" ./gradlew test1:initApp
# or
$ ./gradlew test1:initApp -Dfondue.rootPkg="net.argius.webapp.f1"
```

### Command Line Examples

```
# pwd=fondue-root
$ ./add test1
$ ./gradlew test1:initApp
$ ./gradlew test1:dbMigrate
$ ./gradlew test1:genAll
$ ./gradlew test1:bootApp
```

```
# help command

$ ./gradlew examples:fondue-app1:showHelp
# or
$ ./gradlew -q tasks
```


License
-------

Apache 2.0 License.
