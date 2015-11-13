---
title: Javadoc でアノテーションのサンプルコードを記述するときの＠の扱い
created: 2015-10-07
---

Javadoc でサンプルコードを記述するときは、下記のように `<pre>{@code` と `}</pre>` で囲んだ領域にコードを記述するのが一般的です。

```java
/**
 * ...
 * <pre>{@code
 *    int hoge = obj.getHoge();
 * }</pre>
 * ...
 */
```

ですが、アノテーションの使い方をサンプルコードで示そうとすると、`@YourAnnnotation` といった、アットマーク `@` を含んだテキストを含めなければいけません。
Javadoc 形式のコメントの中に `@` を含めてしまうと、Javadoc のタグとみなされて警告が出たり、うまく API ドキュメントが生成されなかったりします。

Java 1.5 (Tiger) で導入された [Target アノテーション](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/Target.html) の Javadoc コメントでは、下記のようにサンプルコードが記述されています。

```java
/**
 * ...
 *
 * <p>For example, this {@code @Target} meta-annotation indicates that the
 * declared type is itself a meta-annotation type.  It can only be used on
 * annotation type declarations:
 * <pre>
 *    &#064;Target(ElementType.ANNOTATION_TYPE)
 *    public &#064;interface MetaAnnotationType {
 *        ...
 *    }
 * </pre>
 * ...
 */
```

アットマーク `@` は、`&#064;` で表現し、特殊な意味を持たないように配慮しています。
どう考えてもスマートな書き方ではないのですが、今のところ、このように書くしかなさそうです。
誰か javadoc 直してくれないかなぁ。。。

