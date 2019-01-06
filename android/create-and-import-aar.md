---
title: "AAR 形式のファイルを作成する/使用する"
date: "2015-11-16"
---

AAR とは
----

Android Studio では、従来の JAR 形式のライブラリファイルに加え、[AAR (Android Archive) 形式のライブラリ](http://tools.android.com/tech-docs/new-build-system/aar-format) を作成することができます。

JAR ライブラリとは違い、AAR ライブラリは Android 固有のアセット、リソース、`AndroidManifest.xml` などを含めることができます。
AAR ライブラリには `AndroidManifest.xml` を含めることができるので、`uses-library` 宣言をライブラリ内で完結させるといったことが可能になります。


AAR ライブラリを作成する
----

### 通常のアプリプロジェクトの下に AAR ライブラリ用のサブプロジェクトを作成する方法

現状の Android Studio (version 1.5) では、AAR ライブラリ専用の新規プロジェクトを作成することができません（後述の方法で、ごにょごにょすれば AAR 専用のプロジェクトとして扱うことはできます）。
なので、通常はアプリケーションプロジェクトの下に、サブプロジェクトとして AAR ライブラリのプロジェクトを作成することになります。
AAR ライブラリを作成する場合は、その使用例などを示すサンプルプロジェクトなども用意する必要がありますから、この構成で作成しておいても特に問題ないでしょう。

まずは下記のように親プロジェクトとなるアプリプロジェクトを作成します。
Android Phone 用の UI ライブラリを作るつもりであれば、それを前提とした構成で作成しておけばよいでしょう。

1. メニューから `File` => `New` => `New Project...` を選択

次に、そのサブプロジェクトとして、AAR ライブラリ作成用のプロジェクトを作成します。

1. メニューから `File` => `New` => `New Module...` を選択
2. `Android Library` を選択し、**Module name** に AAR ライブラリのファイル名、**Package name** にライブラリの Java パッケージ名を入力

AAR ライブラリ用のサブプロジェクトが作成されたら、そこに Java のクラスやリソース、`AndroidManifest.xml` などを追加していきます。
最後に、メニューから `Build` => `Make Module` としてビルドすれば、下記のディレクトリに AAR ライブラリが生成されます。

```
<AARサブプロジェクト名>/build/outputs/aar
```

#### AAR ライブラリ専用のプロジェクトを作成する方法

Android Studio 1.5 の時点では、ウィザードから AAR ライブラリ専用のプロジェクトを作成することができません。
どうしても作りたいのであれば、まずは空のアプリプロジェクトとして作成しておき、それをライブラリプロジェクトに書き換えるという方法で実現できます。

1. メニューから `File` => `New` => `New Project...` を選択
2. UI 構成の選択画面で、`Add No Activity` を選択

空のアプリプロジェクトを作成したら、アプリモジュール内の `build.gradle` を下記のように変更します。

* `apply plugin:` の後ろを、`com.android.application` から `com.android.library` に変更
* `defaultConfig` のコンフィギュレーションブロックの `applicationId` を削除

あとは、メニューから `Build` => `Make module` とすれば、AAR ファイルが生成されます。


AAR ライブラリを使用する
----

配布された AAR ライブラリを、別のアプリプロジェクトから使用するには以下のようにします。

1. PC 内のどこかに AAR ファイルを置いておく（下記の手順でアプリプロジェクトにコピーされるので、アプリプロジェクト以下のディレクトリに AAR ファイルを置いておく必要はありません）
2. メニューから `File` => `New` => `New Module...` を選択（`Import Module...` ではないことに注意）
3. `Import .JAR/.AAR Package` を選択し、上記の AAR ファイルを指定

上記の手順を行うと、AAR ライブラリだけを含んだサブプロジェクト（ここでは `mylib-release` とします）が生成されます。
プロジェクトの `settings.gradle` には、自動的に下記のようにサブプロジェクトへの参照情報が追記されます。

#### settings.gradle

```groovy
include ':app', ':mylib-release'
```

あとは、このライブラリを使用したい `app` プロジェクトから依存関係を設定すれば OK です。

#### app/build.gradle

```groovy
dependencies {
    compile project(':mylib-release')
    ...
}
```

これで、AAR ライブラリで定義した Java のクラスファイルなどを参照できるようになります。

