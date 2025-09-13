---
title: "Gradle のマルチプロジェクトで依存関係を扱う (dependencies, compile)"
url: "p/hg9n9wd/"
date: "2015-08-04"
tags: ["gradle"]
aliases: ["/gradle/multi-project-dependency.html"]
---

マルチプロジェクトビルドにおいて、別のサブプロジェクトが生成するクラスへの依存があることを示したい場合は、**`dependencies`** ブロックで依存先のプロジェクトを指定します。
例えば、下記のように、2 つのサブプロジェクト (`myapp` と `mylib`) で構成されているとします。

{{< code title="プロジェクト構成" >}}
root/
  +-- build.gradle
  +-- settings.gradle
  +-- myapp/
  |     +-- src/main/java/Main.java
  +-- mylib/
        +-- src/main/java/MyLib.java
{{< /code >}}

{{< code lang="groovy" title="settings.gradle" >}}
include 'myapp'
include 'mylib'
{{< /code >}}

プロジェクト `myapp` は、プロジェクト `mylib` が提供するクラスを参照して実装されています。

{{< code lang="java" title="myapp/src/main/java/Main.java" >}}
public class Main {
    public static void main(String... args) {
        System.out.println(MyLib.add(100, 200));
    }
}
{{< /code >}}

{{< code lang="java" title="mylib/src/main/java/MyLib.java" >}}
public class MyLib {
    public static int add(int a, int b) {
        return a + b;
    }
}
{{< /code >}}

このように Java のビルドにプロジェクト間の依存関係がある場合は、**`dependencies`** ブロック内の **`compile`** コンフィギュレーションに対して依存先のプロジェクトを指定します。

{{< code lang="groovy" title="build.gradle" >}}
project(':myapp') {
    apply plugin: 'java'
    dependencies {
        compile project(':mylib')
    }
}

project(':mylib') {
    apply plugin: 'java'
}
{{< /code >}}

これで、自動的に `myapp` プロジェクトから `mylib` プロジェクトへのクラスパスが設定され、ビルドが通るようになります。

```console
$ gradle -q build
```

ちなみに、上記のようにすべてのサブプロジェクト内で同じコンフィギュレーションを行う部分がある場合は、**`subprojects`** メソッドに、共通処理のクロージャとして渡しておくことができます。
ルートプロジェクトを含む共通処理を記述したい場合は、`subprojects` の代わりに **`allprojects`** を使用します。

{{< code lang="groovy" title="build.gradle（subprojects で共通処理をまとめる例）" >}}
subprojects {
    apply plugin: 'java'
}

project(':myapp') {
    dependencies {
        compile project(':mylib')
    }
}

project(':mylib') {
}
{{< /code >}}

