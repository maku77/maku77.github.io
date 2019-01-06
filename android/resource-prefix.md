---
title: "リソース名に正しくプレフィックスが付いているか確認する"
date: "2015-11-30"
---

マルチプロジェクト構成な Android プロジェクトでは、サブプロジェクトによるリソース名の重複を意識しなければいけません。
リソース名の重複を防ぐには、例えば、サブプロジェクト内で使用するリソース名にプレフィックス名を付けたりします。

`build.gradle` で下記のように **resourcePrefix** を指定しておくと、リソース名のプレフィックスが正しく付けられているかチェックしてくれるようになります。

```groovy
android {
  resourcePrefix 'mylib_'
}
```

指定したプレフィックスが付いていないリソース名が見つかると、Android Lint による警告が発生します。
この設定は、Android Studio 0.5.8 以降で使用可能です。

* [Android Tools - New Build System](http://tools.android.com/tech-docs/new-build-system)

