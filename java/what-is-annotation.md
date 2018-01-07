---
title: アノテーションとは
date: "2006-02-06"
---

Java 1.5 Tiger で、メソッドなどに不可情報を与える仕組みとして **Annotation** が導入されました。
デフォルトで使用できる標準アノテーションとしては下記のものが定義されています。

* Override (java.lang.Override)
  * そのメソッドが、スーパークラスのメソッドをオーバライドしていることを示す。
* Deprecated (java.lang.Deprecated)
  * そのメソッドやフィールドの使用が非推奨であることを示す。
* SuppressWarnings (java.lang.SuppressWarnings)
  * コンパイラ警告を抑制する。

アノテーション型を使用するには、クラスと同様に `import` が必要ですが、上記の標準アノテーションは `java.lang` パッケージに属しているため、`import` せずに使用することができます。
独自のカスタムアノテーションを作成することもできます。


Override アノテーション
====
あるメソッドに `@Override` を付加することで、スーパークラスのメソッドをオーバライドしているという意思をコンパイラに伝えます。
スペルミスなどでオーバライドできていないなどのミスを、コンパイル時に防ぐことができます。

```java
@Override
public String toString() { ... }  // OK

@Override
public String toStrin() { ... }  // コンパイルエラー（オーバライド対象のメソッドがない）
```

`Override` アノテーションは、オーバライドされるスーパークラス側のメソッドには記述しません。
Java では、オーバライドされることを前提としたメソッドは `abstract` キーワードで示すことができます。


Deprecated アノテーション
====
`Deprecated` アノテーションをメソッドやフィールドに付加すると、それらの使用が非推奨であることをコンパイラに知らせることができます。

Javadoc にも `@deprecated` タグがありますが、こちらは主にドキュメントの記述に使われる印です。
一方で `Deprecated` アノテーションは、コンパイル時に非推奨要素の使用を使っているかを検出して警告します。
`Deprecated` アノテーションを使用するケースでは、同時に Javadoc の `@deprecated` タグを使い、代替手段を説明するようにしましょう。

下記は `String` クラスの非推奨コンストラクタの例です。

```java
/**
 * Converts the byte array to a string, setting the high byte of every
 * {@code char} to the specified value.
 * ...
 * @deprecated Use {@link #String(byte[])} or {@link #String(byte[], String)} instead.
 */
@Deprecated
public String(byte[] data, int high) { ... }
```


SuppressWarnings アノテーション
====

`SuppressWarnings` アノテーションを付加すると、コンパイラによる警告を抑制することができます。

下記のメソッド `useDeprecatedMethod` は、Deprecated なメソッドを呼び出しているので、デフォルトではコンパイラによる警告が発生しますが、`SuppressWarnings` アノテーションによりこの警告を抑制しています。

```
@SuppressWarnings("deprecation")
private void useDeprecatedMethod() {
    obj.deprecatedMethod();
}
```

Checkstyle や PMD などの静的解析ツールによる警告を抑制することもできるようになっています。
下記は、PMD による警告の一部を抑制する例です。

```java
@SuppressWarnings("PMD.UnusedLocalVariable")
private void hoge() {
    int unusedVar = 1;
    ...
}
```

基本的にはコンパイラ警告を抑制するべきではありませんが、何らかの都合（プロジェクト内の政治的理由など）で、まっとうな理由がある場合は抑制してもよいでしょう。
それにより、本当に修正したほうがよい箇所の警告に気付きやすくなります。
警告を抑制した場合は、なぜ抑制する必要があったのかをコメントで記述しておきましょう。

