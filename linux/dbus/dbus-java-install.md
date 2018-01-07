---
title: D-Bus Java をインストールする
date: "2012-05-11"
---

（Ubuntu 12.04 で確認）

必要なファイルのダウンロード
----

以下から、`dbus-java-2.7.tar.gz` などをダウンロードします。

- [http://www.freedesktop.org/wiki/Software/DBusBindings](http://www.freedesktop.org/wiki/Software/DBusBindings)

INSTALL ドキュメントを見ると、他にも Metthew さんのライブラリが必要のようなので、以下から、`libmatthew-java-0.8.tar.gz` などをダウンロードします。

- [http://www.matthew.ath.cx/projects/java/](http://www.matthew.ath.cx/projects/java/)


ライブラリ (*.jar *.so) ファイルのビルド
----

#### libmatthew-java のビルド

```
$ tar xzvf libmatthew-java-0.8.tar.gz
$ cd libmatthew-java-0.8
$ make
```

`jni.h` が見つからないと言われたら、`Makefile` を編集して、`jni.h` のディレクトリにパスを通してから `make` を実行します。

```makefile
INCLUDE+= ....... -I/usr/lib/jvm/java-7-openjdk-amd64/include
```

`make install` まで実行すると、システムに jar ファイルと so ファイルがインストールされるのですが、ここでは、Linux 環境の汚染を防ぐために、`make install` は行わないことにします（プロジェクトごとに jar ファイルのクラスパスを指定するようにします）。


dbus-java のビルド
----

`dbus-java-2.7` のビルドには、JDK の version 1.6 が必要ぽいです（`dbus-java` のコードが Java の Generics 構文などに対応できてない）。
JDK 1.6 がインストールされていない場合は先にインストールします。

#### Ubuntu での例

```
$ sudo apt-get install openjdk-6-jdk
$ sudo update-java-alternatives -s java-1.6.0-openjdk-amd64
```

また、`msgfmt` コマンドも必要なので、`gettext` パッケージをインストールします。

```
$ sudo apt-get install gettext
```

`dbus-java` のビルドには、`libmatthew-java` の jar ライブラリ群が必要なので、カレントディレクトリにコピーします。

```
$ cp ../libmatthew-java-0.8/debug-disable-1.1.jar ./debug-disable.jar
$ cp ../libmatthew-java-0.8/hexdump-0.2.jar ./hexdump.jar
$ cp ../libmatthew-java-0.8/unix-0.5.jar ./unix.jar
```

#### ビルド

```
$ JAVAUNIXJARDIR=. make bin
```

エラーは色々出ますが、何とか `libdbus-java-2.7.jar` は生成できます。
システムに jar ファイルや `CreateInterface` などのコマンドをインストールしてしまうには、`sudo make install` を実行します。


使ってみる
----

D-Bus Java を使ったサンプルコードのビルド、実行を試してみます。

#### Main.java

```java
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Connect to the system bus");
        DBusConnection conn = null;
        try {
            conn = DBusConnection.getConnection(DBusConnection.SYSTEM);
        } catch (DBusException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        System.out.println("Disconnect");
        conn.disconnect();
    }
}
```

ビルドや実行のためには、`libdbus-java` 関連の jar ファイルや so ファイルが必要です。

```
$ cp ../dbus-java-2.7/libdbus-java-2.7.jar .
$ cp ../libmatthew-java-0.8/debug-disable-1.1.jar .
$ cp ../libmatthew-java-0.8/debug-enable-1.1.jar .
$ cp ../libmatthew-java-0.8/hexdump-0.2.jar .
$ cp ../libmatthew-java-0.8/unix-0.5.jar .
$ cp ../libmatthew-java-0.8/libunix-java.so .
```

#### サンプルプログラムのビルド

```
$ javac -cp libdbus-java-2.7.jar Main.java
```

#### サンプルプログラムの実行

```
$ CP=./debug-disable-1.1.jar:./debug-enable-1.1.jar:./hexdump-0.2.jar:libdbus-java-2.7.jar:./unix-0.5.jar:.
$ java -cp $CP -Djava.library.path=. Main
```

何もエラーがなく実行されれば成功です。

