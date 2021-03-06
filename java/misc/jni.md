---
title: "JNI (Java Native Interface) の基本"
date: "2010-06-01"
---

JNI によるネイティブコード呼び出しのステップ
----

Java から JNI でネイティブなライブラリを呼び出すまでのステップは、以下のような感じになります。

1. C の関数を呼び出すための `native` キーワードを含んだ Java のコードを作成する。
2. Java コードをコンパイルしてクラスファイルを作成する (`javac *.java`)。
3. できたクラスファイルから C のヘッダファイルを自動生成する (`javah -jni`)。
4. ヘッダファイルに記述されている関数を新しい CPP ファイル内で実装する。
5. CPP コードをビルドして dll (so) ファイルを作成する。
6. クライアントとなる Java の main コードを作成する。


ネイティブコードを呼び出すための Java コードを実装
----

#### MyLib.java

~~~ java
public class MyLib {
    static {
        // Load the following library.
        //   Windows: MyLib.dll
        //   Linux: libMyLib.so
        System.loadLibrary("MyLib");
    }

    // Receive string from native.
    public native String getMessage();

    // Send string from java.
    public native void showMessage(String msg);
}
~~~

`native` キーワードを付けて宣言したメソッドを、後ほど C++ で実装します。
この時点で DLL (so) は存在しませんが、コンパイルは通るので、コンパイルしてクラスファイルを生成しておきます。

~~~
C:\> javac MyLib.java
~~~


ネイティブコード実装用のヘッダファイルを自動生成する
----

まず、JDK に含まれている `javah` コマンドで、Java のクラスファイルから、C のヘッダファイルを自動生成します。
Java のコード内で、`native` キーワードを付けて宣言したメソッドが抽出されて、C のヘッダファイルが生成されます。

~~~
C:\> javah -jni MyLib
~~~

`com.example.MyLib` のようなパッケージ階層がある場合は、`com` ディレクトリの上のディレクトリから、以下のように実行します。

~~~
C:\> javac -jni com.example.MyLib
~~~

`javah` コマンドのオプション `-jni` はデフォルトで指定したとみなされるので省略しても大丈夫です。
生成されるファイルは以下のような感じになります。

#### MyLib.h

~~~ cpp
/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class MyLib */

#ifndef _Included_MyLib
#define _Included_MyLib
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     MyLib
 * Method:    getMessage
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_MyLib_getMessage
  (JNIEnv *, jobject);

/*
 * Class:     MyLib
 * Method:    showMessage
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_MyLib_showMessage
  (JNIEnv *, jobject, jstring);

#ifdef __cplusplus
}
#endif
#endif
~~~


ネイティブコードを実装する
----

新規に C++ ファイルを作成し、自動生成されたヘッダの関数を実装します。
ここではサンプルとして、Java 側に文字列を返す関数と、Java 側から文字列を受け取る関数を実装しています。
おまけに Windows 上でビルドしているとして、Windows API の `MessageBox` 関数を呼び出しています。

この中の実装は、別のライブラリの関数呼び出しだけにしてしまって、JNI のレイヤと純粋なネイティブライブラリのレイヤを分けてもいいかもしれません。

#### MyLib.cpp

~~~ cpp
#include <windows.h>
#include "MyLib.h"

JNIEXPORT jstring JNICALL Java_MyLib_getMessage(JNIEnv *env, jobject obj) {
    return env->NewStringUTF("Message from C++");
}

JNIEXPORT void JNICALL Java_MyLib_showMessage(JNIEnv *env, jobject obj, jstring jMsg) {
    const char *msg = env->GetStringUTFChars(jMsg, 0);
    ::MessageBox(NULL, msg, "Message", MB_OK);
    env->ReleaseStringUTFChars(jMsg, msg);
}
~~~

ここでは、MinGW の `g++` コマンドを使って DLL を作成します。

`javah` で作成したヘッダファイルがインクルードしている `jni.h` と `jni_md.h` は、JDK の `include`、`include/win32` ディレクトリにそれぞれ入っているので、次のようにインクルードパスの指定（`-I` オプション）が必要です。

~~~
C:> set JDK_HOME="C:\Program Files\Java\jdk1.6.0_19"
C:> g++ -shared MyLib.cpp -o MyLib.dll
        -Wl,--add-stdcall-alias
        -I%JDK_HOME%\include -I%JDK_HOME%\include\win32
~~~

下記のヘッダファイルを MinGW の `include` ディレクトリ直下にまるごとコピーしちゃうのが実は一番お手軽だったりします。

- `%JDK_HOME%\include\*.h`
- `%JDK_HOME%\include\win32\*.h`

オプションとして `--Wl,--add-stdcall-alias` を付けないと、Java から呼び出したときに、以下のようなエラーになるので注意してください。

~~~
Exception in thread "main" java.lang.UnsatisfiedLinkError: MyLib.showMessage(Ljava/lang/String;)V
        at MyLib.showMessage(Native Method)
        at MyLibTest.main(MyLibTest.java:4)
~~~


クライアントとなる Java コードを作成する
----

あとは、Java のプログラムからメソッドを呼び出すだけです。

#### MyLibTest.java

~~~ java
public class MyLibTest {
    public static void main(String[] args) {
        MyLib lib = new MyLib();
        System.out.println(lib.getMessage());
        lib.showMessage("Message from Java");
    }
}
~~~

#### ビルド＆実行

~~~
C:\> javac MyLibTest.java
C:\> java MyLibTest
~~~

標準出力に、`Message from C++` と表示され、メッセージボックスで `Message from Java` と表示されれば成功です。

