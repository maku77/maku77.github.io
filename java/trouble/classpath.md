---
title: "CLASSPATH 関連のエラーに対処する"
date: "2005-06-27"
---

java.lang.NoClassDefFoundError: XXX/class
----

#### エラーの内容

~~~
C:\test> java Hello.class
Exception in thread "main" java.lang.NoClassDefFoundError: Hello/class
~~~

#### 正しい実行方法

~~~
C:\test> java Hello
~~~

#### 原因

クラスファイルの指定で `.class` という拡張子を付けているのが原因です。
`java` コマンドの仕様では、パッケージ名を含むクラス名を指定することにより、クラスパスの通ったところからそのクラスを検索するようになっています。

`java Hello.class` という指定をすると、`Hello` というパッケージに入っている `class` という名前のクラスを検索してしまいます。
例えば、カレントディレクトリがクラスパスに入っている場合、`Hello` ディレクトリの下の `class.class` というファイルを探しにいってしまいます。
だから、`Hello/class` というクラスが見つからないとエラーが出ています。


JAR ライブラリ指定時のエラー
----

JAR ファイル形式のライブラリを利用している時に、`-cp` オプションで指定した JAR ファイル内のクラスが見つからないケースです。

#### エラーの内容

~~~
C:\temp> java Hello -cp .;commons-lang-2.1.jar
NoClassDefFoundError: org/apache/commons/lang/builder/ReflectionToStringBuilder
        at Hello.main(Hello.java:7)
~~~

#### 正しい実行方法

~~~
C:\temp> java -cp .;commons-lang-2.1.jar Hello
~~~

#### 原因

`-cp` オプションを指定する場所が間違っています。
`java` コマンドの書式は、

~~~
java [option] class [args]
~~~

なので、`-cp` オプションはクラス名よりも前に指定する必要があります。

~~~
java Hello -cp .;commons-lang-2.1.jar
~~~

としてしまうと、`java` コマンドではなく、`Hello` というプログラムの引数に `.;commons-lang-2.1.jar` が渡されてしまいます。

