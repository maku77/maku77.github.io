---
title: Groovy スクリプトで Jenkins サーバを制御する
date: "2016-08-23"
---

Jenkins の API
----

Jenkins CLI の `groovy` コマンドを使用すると、Groovy スクリプトを使って Jenkins サーバの制御を行えるようになります。
下記の Jenkins API を自由に扱うことができるので、ほとんどどのような処理もスクリプトで自動化することができます。

- [Jenkins Javadoc](http://javadoc.jenkins-ci.org/)


Jenkins CLI で Groovy スクリプトを実行する
----

まずは、下記の記事を参考にして、Jenkins CLI コマンドを実行できるようにしておく必要があります。

- [Jenkins CLI を使ってコマンドラインから Jenkins を操作する](./cli.html)

Jenkins CLI が使用できるようになったら、`groovy` コマンドで実行したい Groovy スクリプトを指定すれば OK です。

```
$ java -jar jenkins-cli.jar -s http://localhost:8080 groovy sample.groovy
```

下記のサンプルスクリプトは、Jenkins のインストールされているディレクトリから再帰的にすべてのファイルを検索し、`config.xml` というファイルが見つかったときに、その絶対パスを表示しています。

#### sample.groovy

```groovy
root = jenkins.model.Jenkins.instance.getRootDir()
println "Seach files in ${root.getPath()} ..."

root.eachFileRecurse  { file ->
    if (file.getName() == 'config.xml') {
        println file.getPath()
    }
}
```

ちなみに、Groovy の `==` 演算子は `equals()` による比較として扱われるので、上記のように文字列比較も `==` 演算子で行えます。

#### 実行例

```
C:\> java -jar jenkins-cli.jar -s http://localhost:8080 groovy sample.groovy
Search files in C:\app\Jenkins ...
C:\app\Jenkins\config.xml
C:\app\Jenkins\jobs\test-job\config.xml
C:\app\Jenkins\nodes\mynode1\config.xml
C:\app\Jenkins\users\maku\config.xml
```


スクリプトコンソールから Groovy スクリプトを実行する
----

実は Jenkins CLI を使用しなくても、ブラウザ上で Groovy スクリプトを貼り付けて実行することもできます。
Jenkins のメニューから、**Jenkins の管理 → スクリプトコンソール** と辿って起動するか、下記のアドレスにアクセスすると、スクリプトコンソールの画面を表示できます。

* [http://localhost:8080/script](http://localhost:8080/script)

ここに、任意の Groovy スクリプトを貼り付けて実行できます。
スクリプトを実行するには、「実行」ボタンを押すか、**Ctrl + Enter** というキーボードショートカットを利用します。

