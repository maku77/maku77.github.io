---
title: "Groovy スクリプトで Jenkins 上のすべてのスレーブを制御する"
date: "2016-08-23"
---

下記のサンプルでは、現在マスターサーバに登録されている Jenkins スレーブをループで処理しています。
ループ中は、**Slave** オブジェクトの情報を扱うことができます。

* [hudson.model.Slave クラス](http://javadoc.jenkins-ci.org/hudson/model/Slave.html)


スレーブの登録情報を列挙する
----

#### slave-info.groovy

```groovy
for (slave in jenkins.model.Jenkins.instance.slaves) {
  println "Slave name: ${slave.name}"
  println "Slave class: ${slave.class}"
  println "Slave node description: ${slave.nodeDescription}"
  println "Slave root path: ${slave.rootPath}"
  println "Slave label:  ${slave.labelString}"
  println "Slave num executors: ${slave.numExecutors}"
  println '----'
}
```

#### 実行結果

```
Slave name: mynode1
Slave class: class hudson.slaves.DumbSlave
Slave node description: xxx
Slave URL: xxx
Slave label: xxx
Slave num executors: 1
---
...
```

スレーブとなっている PC がオンラインかどうか確認する
----

#### slave-check-online.groovy

```groovy
import jenkins.model.Jenkins

Jenkins.instance.slaves.each {
  boolean isOn = it.getComputer().isOnline();
  println it.name + (isOn ? ": ON" : ": OFF")
}
```

#### 実行結果

```
mynode1: ON
mynode2: OFF
mynode3: OFF
```

