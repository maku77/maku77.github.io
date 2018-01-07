---
title: D-Bus Java で Session bus に接続してメソッドを呼び出す
date: "2012-05-15"
---

D-Bus Java の `CreateInterface` コマンドを使用すると、API インタフェースを定義した XML ファイル (introspection data) から、Java のインタフェースを作成することができます。

ここでは、以下のような XML ファイルからインタフェースを生成します（セッションバスなどで公開されているオブジェクトに接続して生成することもできます）。

#### interface.xml

```xml
<?xml version="1.0"?>
<node name="/com/example/MyApp">
    <interface name="com.example.MyApp.Calc">
        <method name="Add">
            <arg type="i" name="val1" direction="in" />
            <arg type="i" name="val2" direction="in" />
            <arg type="i" name="ret_val" direction="out" />
        </method>
        <method name="Subtract">
            <arg type="i" name="val1" direction="in" />
            <arg type="i" name="val2" direction="in" />
            <arg type="i" name="ret_val" direction="out" />
        </method>
    </interface>
</node>
```

#### 生成

```
$ CreateInterface interface.xml > Calc.java
```

以下のようなファイルが生成されます。シンプル！

#### Calc.java

```java
/* File: com/example/MyApp/Calc.java */
package com.example.MyApp;
import org.freedesktop.dbus.DBusInterface;

public interface Calc extends DBusInterface {
    public int Add(int val1, int val2);
    public int Subtract(int val1, int val2);
}
```

次に、これを使ってクライアントのコードを記述します。
GLib bindings を使った場合のコードと比べると、かなりシンプルです。

```
$ mkdir -p com/example/MyApp
$ mv Calc.java com/example/MyApp
```

#### Main.java

```java
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import com.example.MyApp.Calc;

public class Main {
    public static void main(String[] args) {
        DBusConnection conn = null;
        Calc calc = null;

        try {
            conn = DBusConnection.getConnection(DBusConnection.SESSION);
            calc = (Calc) conn.getRemoteObject(
                    "com.example.MyApp",  // Bus name
                    "/com/example/MyApp",  // Object path
                    Calc.class);  // Interface
        } catch (DBusException ex) {
            System.err.println("Failed to obtain a remote object.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        try {
            int a = calc.Add(100, 200);
            int b = calc.Subtract(100, 200);
            System.out.println(a);
            System.out.println(b);
        } catch (RuntimeException ex) {
            System.err.println("Failed to invoke a remote method.");
            System.err.println(ex.getMessage());
        }

        conn.disconnect();  // これがないとプログラム終了しない
    }
}
```

#### 実行結果

```
300
-100
```

#### ソースコード一式

* [dbus-java-session-bus.zip](./dbus-java-session-bus.zip)

