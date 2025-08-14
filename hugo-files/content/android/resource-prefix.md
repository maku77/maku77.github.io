---
title: "Android Gradleメモ: リソース名に正しくプレフィックスが付いているか確認する (resourcePrefix)"
url: "p/4zogpen/"
date: "2015-11-30"
tags: ["android", "gradle"]
aliases: [/android/resource-prefix.html]
---

マルチプロジェクト構成な Android プロジェクトでは、サブプロジェクトによるリソース名の重複を意識しなければいけません。
リソース名の重複を防ぐには、例えば、サブプロジェクト内で使用するリソース名にプレフィックス名を付けたりします。

`build.gradle` で下記のように **`resourcePrefix`** を指定しておくと、リソース名のプレフィックスが正しく付けられているかチェックしてくれるようになります。
この設定は、Android Studio 0.5.8 以降で使用可能です。

```groovy
android {
  resourcePrefix 'mylib_'
}
```

指定したプレフィックスが付いていないリソース名が見つかると、Android Lint による警告が発生します。

