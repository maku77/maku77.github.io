---
title: Gradle タスクの一覧を表示する (tasks)
date: "2014-06-17"
---

実行可能なタスクの一覧は `tasks` タスクを走らせることで確認できます。

```sh
$ gradle -q tasks

------------------------------------------------------------
All tasks runnable from root project
------------------------------------------------------------

Build Setup tasks
-----------------
init - Initializes a new Gradle build. [incubating]
wrapper - Generates Gradle wrapper files. [incubating]

Help tasks
----------
dependencies - Displays all dependencies declared in root project 'gradle'.
dependencyInsight - Displays the insight into a specific dependency in root project 'gradle'.
help - Displays a help message
projects - Displays the sub-projects of root project 'gradle'.
properties - Displays the properties of root project 'gradle'.
tasks - Displays the tasks runnable from root project 'gradle'.

To see all tasks and more detail, run with --all.
```

もう少し詳細に表示したい場合は、`--all` オプションを付けて `tasks` タスクを実行します。
下記の例は、`apply plugin: 'java'` したときのタスク一覧を確認しています。

```sh
$ gradle -q tasks --all

------------------------------------------------------------
All tasks runnable from root project
------------------------------------------------------------

Build tasks
-----------
assemble - Assembles the outputs of this project. [jar]
build - Assembles and tests this project. [assemble, check]
buildDependents - Assembles and tests this project and all projects that depend on it. [build]
buildNeeded - Assembles and tests this project and all projects it depends on. [build]
classes - Assembles classes 'main'.
    compileJava - Compiles Java source 'main:java'.
    processResources - Processes resources 'main:resources'.
clean - Deletes the build directory.
jar - Assembles a jar archive containing the main classes. [classes]
testClasses - Assembles classes 'test'. [classes]
    compileTestJava - Compiles Java source 'test:java'.
    processTestResources - Processes resources 'test:resources'.

Build Setup tasks
-----------------
init - Initializes a new Gradle build. [incubating]
wrapper - Generates Gradle wrapper files. [incubating]

Documentation tasks
-------------------
javadoc - Generates Javadoc API documentation for the main source code. [classes]

Help tasks
----------
dependencies - Displays all dependencies declared in root project 'gradle'.
dependencyInsight - Displays the insight into a specific dependency in root project 'gradle'.
help - Displays a help message
projects - Displays the sub-projects of root project 'gradle'.
properties - Displays the properties of root project 'gradle'.
tasks - Displays the tasks runnable from root project 'gradle'.

Verification tasks
------------------
check - Runs all checks. [test]
test - Runs the unit tests. [classes, testClasses]

Rules
-----
Pattern: build<ConfigurationName>: Assembles the artifacts of a configuration.
Pattern: upload<ConfigurationName>: Assembles and uploads the artifacts belonging to a configuration.
Pattern: clean<TaskName>: Cleans the output files of a task.
```


