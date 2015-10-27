---
title: Gradle によるマルチプロジェクトの基本
created: 2015-07-27
---

Gradle では複数のサブプロジェクトを作成し、それらを連携させてビルドすることができます。
サブプロジェクトの構成は、**settings.gradle** ファイルで記述します。
例えば、下記の設定では、2 つのサブプロジェクト (`subproject1`, `subproject2`) から構成されることを示しています（ここでサブプロジェクトを `include` する順番は、タスクの実行順序には何も影響を与えません）。

#### settings.gradle
```groovy
rootProject.name = 'rootproject'
include 'subproject1'
include 'subproject2'
```

Gradle にデフォルトで用意されている **projects** タスクを実行することで、現在のプロジェクト構成を調べることができます。

```bash
$ gradle -q projects
------------------------------------------------------------
Root project
------------------------------------------------------------

Root project 'rootproject'
+--- Project ':subproject1'
\--- Project ':subproject2'
...
```

サブプロジェクトごとに build.gradle を持つ構成
----

settings.gradle の `include` メソッドで指定したサブプロジェクト名は、サブプロジェクトを格納するディレクトリ名に対応しています。
サブプロジェクトの各ディレクトリには、そのプロジェクト用のビルドスクリプト (`build.gradle`) を格納しておくことができます。

#### マルチプロジェクトのディレクトリ構成
```
root/
  +-- build.gradle
  +-- settings.gradle
  +-- subprojects1/
  |    +-- build.gradle
  +-- subprojects2/
       +-- build.gradle
```

#### build.gradle（ルート）
```groovy
task hello << { println 'Hello' }
```
#### subprojects1/build.gradle
```groovy
task hello1 << { println 'Hello1' }
```

#### subprojects2/build.gradle
```groovy
task hello2 << { println 'Hello2' }
```

settings.gradle ファイルに記述されたサブプロジェクトは Initialization フェーズで認識され、それぞれのディレクトリ内のビルドスクリプトが読み込まれるようになります。
下記のようにすると、サブプロジェクト内で定義されたタスクもすべて認識されていることが分かります。

```bash
$ gradle -q tasks --all
...
Other tasks
-----------
hello
subproject1:hello1
subproject2:hello2
```

`gradle` コマンドを実行するとき、settings.gradle ファイルは上位のディレクトリに向かって検索してくれるため、サブプロジェクトのディレクトリにいる場合でも各プロジェクト内のタスクを参照することができます。
ルートプロジェクトのタスクや、別のサブプロジェクト内のタスクを実行したい場合は、下記のように `:` を使った指定をすることによって、どのプロジェクトのタスクなのかを明示的に示す必要があります。

```bash
$ cd subproject1
$ gradle -q :hello    # ルートプロジェクトのタスクを実行
Hello
$ gradle -q :subproject2:hello2    # 別のサブプロジェクトのタスクを実行
Hello2
```

1 つの build.gradle ファイルでマルチプロジェクトを構成する
----
これまでの例では、各サブプロジェクトのディレクトリに build.gradle スクリプトを格納していましたが、ルートプロジェクトの build.gradle の中で、サブプロジェクトのビルド内容を記述してしまうこともできます。
サブプロジェクトのビルド内容は、`project` メソッドに渡すクロージャ内で定義します。

#### build.gradle
```
task hello << { println 'Hello' }

project(':subproject1') {
    task('hello1') << { println 'Hello1' }
}

project(':subproject2') {
    task('hello2') << { println 'Hello2' }
}
```

このような 1 ファイルでの構成をした場合に、サブプロジェクト内にも build.gradle スクリプトが存在する場合、両方が実行されます（よって、同じ名前のタスクを定義したりするとエラーになります）。


