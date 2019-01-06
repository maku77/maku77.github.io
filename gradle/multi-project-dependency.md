---
title: "Gradle のマルチプロジェクトで依存関係を扱う"
date: "2015-08-04"
---

マルチプロジェクトビルドにおいて、別のサブプロジェクトが生成するクラスへの依存があることを示したい場合は、**dependencies** ブロックで依存先のプロジェクトを指定します。
例えば、下記のように、2 つのサブプロジェクト (myapp, mylib) で構成されているとします。

#### プロジェクト構成
```
root/
  +-- build.gradle
  +-- settings.gradle
  +-- myapp/
  |     +-- src/main/java/Main.java
  +-- mylib/
        +-- src/main/java/MyLib.java
```

#### settings.gradle
```groovy
include 'myapp'
include 'mylib'
```

プロジェクト myapp は、プロジェクト mylib が提供するクラスを参照して実装されています。

#### myapp/src/main/java/Main.java
```java
public class Main {
    public static void main(String... args) {
        System.out.println(MyLib.add(100, 200));
    }
}
```

#### mylib/src/main/java/MyLib.java
```java
public class MyLib {
    public static int add(int a, int b) {
        return a + b;
    }
}
```

このように Java のビルドにプロジェクト間の依存関係がある場合は、**dependencies** ブロック内の **compile** コンフィギュレーションに対して、依存先のプロジェクトを指定します。

#### build.gradle
```groovy
project(':myapp') {
    apply plugin: 'java'
    dependencies {
        compile project(':mylib')
    }
}

project(':mylib') {
    apply plugin: 'java'
}
```

これで、自動的に myapp プロジェクトから mylib プロジェクトへのクラスパスが設定され、ビルドが通るようになります。

```bash
$ gradle -q build
```

ちなみに、上記のようにすべてのサブプロジェクト内で同じコンフィギュレーションを行う部分がある場合は、**subprojects** メソッドに、共通処理のクロージャとして渡しておくことができます（ルートプロジェクトを含む共通処理を記述したい場合は、subprojects の代わりに **allprojects** を使用します）。


#### build.gradle（subprojects で共通処理を括りだした）
```groovy
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
```
