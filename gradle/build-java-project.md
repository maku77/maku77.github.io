---
title: Gradle で Java プロジェクトをビルドする
created: 2014-06-08
---

* http://gradle.monochromeroad.com/docs/userguide/java_plugin.html

Java プラグインを適用すると、Java のプロジェクトをビルドするためのタスクが定義されます。

#### build.gradle
```groovy
apply plugin: 'java'
```

Java プラグインはデフォルトで、以下のようなディレクトリ構造でソースコードが格納されていることを期待して動作します。

```
src/main/java              Java ソースコード
src/main/resources         リソースファイル（JAR に入るリソース）

src/test/java              ユニットテスト用の Java ソースコード
src/test/resources         ユニットテスト用のリソースファイル

src/<sourceSet>/java       特定のソースセット用の Java ソース
src/<sourceSet>/resources  特定のソースセット用のリソースファイル
```

例えば、`com.example.Main` クラスのソースコードは下記のように格納します。

```
src/main/java/com/example/Main.java
```

