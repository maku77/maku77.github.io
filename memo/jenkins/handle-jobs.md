---
title: Groovy スクリプトで Jenkins 上のすべての Job を制御する
date: "2016-08-23"
---

すべてのジョブを列挙する
----

下記のサンプルコードは、Jenkins で定義されているすべてのジョブの情報を表示します。
スクリプトコンソールに張り付けて実行することができます。

#### sample.groovy

```groovy
jenkins.model.Jenkins.instance.items.each { job ->
    println "Name: ${job.name}"
    println "Class: ${job.class}"
    println "Root Dir: ${job.rootDir}"
    println "URL: ${job.url}"
    println "Absolute URL: ${job.absoluteUrl}"
    println "Description: ${job.description}"
    if (job.lastSuccessfulBuild != null) {
        println "Last successful time: ${job.lastSuccessfulBuild.timestamp.time}"
    }
    println '----'
}
```

ループ中に参照可能な Job オブジェクトの詳細に関しては、下記の API ドキュメントを参照してください。

* [hudson.model.Job クラス](http://javadoc.jenkins-ci.org/hudson/model/Job.html)

#### 実行結果

```
Name: MySampleJob1
Class: class hudson.model.FreeStyleProject
Root Dir: C:\app\Jenkins\jobs\MySampleJob1
URL: job/MySampleJob1/
Absolute URL: http://localhost:8080/job/MySampleJob1/
Description: despcription text
Last successful time: Tue Aug 23 16:34:04 JST 2016
----
Name: MySampleJob2
Class: class hudson.model.FreeStyleProject
Root Dir: C:\app\Jenkins\jobs\MySampleJob2
URL: job/MySampleJob2/
Absolute URL: http://localhost:8080/job/MySampleJob2/
Description: despcription text
----
```

ジョブの設定を変更する
----

各ジョブの setter 系メソッドを使用すれば、スクリプトからジョブの設定を変更することができます。
下記の例では、すべてのジョブの説明 (Description) をまとめて変更しています。

#### modify-description.groovy

```groovy
import java.util.Calendar
Calendar cal = Calendar.getInstance()

jenkins.model.Jenkins.instance.items.each { job ->
    job.setDescription("Updated at ${cal.getTime().toString()}")
    println "Name: ${job.name}"
    println "Description: ${job.description}"
    println '----'
}
```

#### 実行結果

```
Name: MySampleJob1
Description: Updated at Tue Aug 23 17:45:51 JST 2016
----
Name: MySampleJob2
Description: Updated at Tue Aug 23 17:45:51 JST 2016
----
```

すべてのジョブの名前を一括で変更してしまうこともできます（実行するときは慎重に）。

#### modify-job-names.groovy

```groovy
println "Renaming all jobs ..."

count = 1
jenkins.model.Jenkins.instance.items.each { job ->
  job.renameTo("SampleJob ${count++}")
}
```

