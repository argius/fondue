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
   * list.html
   * detail.html
   * edit.html
 3. Sub framework for Spring Boot
   * Pagination
   * Conversion between Form and Model
   * Display information message by type

For more information, see examples and wiki.


Installation
------------

Fondue does not support installation so far.
Download source codes and import into your IDE.


Get Started
-----------

1. Prepare a database

  See examples/fondue-app1/config/ddl-sql.txt


2. Create `MyBatisGeneratorConfig.xml` and run `genModel`

3. Create `fondue-gen.yml` and run `genCrud`

4. Run the project as Spring Boot app


To see help of Generator, run following commands.

```
$ cd examples/fondue-app1
$ ./gradlew help
```

If you want to create another new app, try  following operations.
 (If you want to use gradle wrapper, use `$(fondue-root)/gradlew` or copy gradle files from sample app)
 
1. Run `./add <project-path>` at `$(fondue-root)` directory, and change the directory. (for instance, `cd $(fondue-root); ./add applications/your-app; cd applications/your-app`)

2. Run `gradle initApp` at your project.

3. Run `gradle genAll` (genModel and genCrud) at your project.

4. Run `gradle bootRun` to run your app.


License
-------

Apache 2.0 License.
