---
title: "Java でカスタムアノテーションを作成する"
date: "2015-10-05"
---

Java 5.0 Tiger では、標準のアノテーションとして `@Override`, `@Deprecated`, `@SuppressWarnings` などが定義されました。
このようなアノテーションは、自由に定義することができます。

簡単なマーカーアノテーション
====

アノテーションを定義するには、`@interface` というキーワードを使用します。
下記はシンプルなアノテーションの例で、単純にマーカーとしての役割を果たします。

#### NonNull.java（アノテーションの定義例）

```java
package com.example.annotation;

/**
 * Denotes that a parameter, field or method return value can never be null.
 */
public @interface NonNull {
}
```

上記のように定義したアノテーションは、標準のアノテーションと同様に使用することができます。

#### Hello.java（アノテーションの使用例）
```java
package com.example;

import com.example.annotation.NonNull;

public class Hello {
    @NonNull
    private static Hello getInstance() {
        return new Hello();
    }
}
```


アノテーションにメンバを追加する
====

基本
----
アノテーションには、任意の数のメンバを持たせることができます。
アノテーションのメンバは、ちょっと特殊な構文で下記のように定義します。

```java
package com.example.annotation;

public @interface ReviewedBy {
    String reviewer();
    String date();
}
```

アノテーションを使用するときは、キー＆バリューの形でパラメータを指定します。

```java
package com.example;
import com.example.annotation.ReviewedBy;

@ReviewedBy(reviewer = "maku", date = "2015-10-05")
public class Hello {
    // ...
```


デフォルト値の指定
----
アノテーションのメンバには、デフォルト値を指定しておくこともできます。

```java
public @interface ReviewedBy {
    String reviewer() default "maku";
    String date();
}
```

デフォルト値が指定されている場合は、アノテーションを使用するときに、そのパラメータを省略することができます。

```java
@ReviewedBy(date = "2015-10-05")
public class Hello {
    // ...
```


配列メンバ
----

アノテーションのメンバとして配列を持たせることもできます。

```java
public @interface Author {
    String[] authors();
}
```

配列メンバを持つアノテーションを使用するときは、下記のように `{ }` を使ってパラメータを渡します。

```java
@Author(authors = {"maku", "moja"})
public class Sample {
    // ...
```

単一要素をパラメータとして渡す場合は、`{ }` を省略することができます。

```java
@Author(authors = "maku")
public class Sample {
    // ...
```


パラメータとして Enum 値を渡せるようにする
----

アノテーションの定義の中で、パラメータに使用する Enum 値を定義しておくことができます。

```java
package com.example.annotation;

public @interface NeedReview {
    public enum Severity {
        HIGH, MIDDLE, LOW
    };

    String note();

    Severity severity() default Severity.MIDDLE;
}
```

定義した Enum 値は、通常の Java コードと同じ感覚で使用することができます。

```java
package com.example;

import com.example.annotation.NeedReview;

@NeedReview(
        note = "This code is dangerous!",
        severity = NeedReview.Severity.HIGH)
public class Sample {
    // ...
```

value フィールド
----

アノテーションのメンバ名として value という名前のフィールドをひとつだけ持つように定義しておくと、アノテーションを使用するときに、そのフィールド名を省略してパラメータを渡せるようになります。

```java
public @interface Author {
    String[] value();
}
```

```java
@Author({"maku", "moja"})
public class Sample {
    // ...
```

メタアノテーション
====

アノテーションを定義するときに付加できるアノテーション（メタアノテーション）が用意されており、これを使用すると、アノテーションの特性や使い方を制御することができます。

* **@Target** -- どのタイプの要素（クラスやメソッドなど）に対してアノテーションを付加できるかを制限できる。
* **@Retention** -- アノテーション情報をどの段階まで保持するかを制御できる。
* **@Documented** -- これを付けておくと、そのアノテーションは Javadoc API ドキュメントの出力にも表示されるようになる。
* **@Inherited** -- アノテーションを付けたクラスを継承すると、サブクラスもそのアノテーションを付けられたかのように振る舞うようになる。

@Target メタアノテーション
----

独自のアノテーションを定義する際に、**@Target** メタアノテーションを指定しておくと、どのようなタイプの要素に対して、そのアノテーションを付加できるのかを制御することができます。
タイプは **java.lang.annotation.ElementType** で定義されており、下記のようなものを指定することができます。

* ElementType.ANNOTATION_TYPE -- Annotation type declaration
* ElementType.CONSTRUCTOR -- Constructor declaration
* ElementType.FIELD -- Field declaration (includes enum constants)
* ElementType.LOCAL_VARIABLE -- Local variable declaration
* ElementType.METHOD -- Method declaration
* ElementType.PACKAGE -- Package declaration
* ElementType.PARAMETER -- Formal parameter declaration
* ElementType.TYPE -- Class, interface (including annotation type), or enum declaration
* ElementType.TYPE_PARAMETER -- Type parameter declaration
* ElementType.TYPE_USE -- Use of a type

例えば、メソッドとパラメータ、フィールドにだけ付加できる `NonNull` アノテーションを定義するには、下記のようにします。

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
public @interface NonNull {}
```


@Retention メタアノテーション
----

独自のアノテーションを定義する際に、**@Retention** メタアノテーションを指定しておくと、そのアノテーション情報をどの段階まで保持するかを制御できます。
段階は、**java.lang.annotation.RetentionPolicy** で定義されており、下記のような値を指定することができます。

* **RetentionPolicy.SOURCE** - The marked annotation is retained only in the source level and is ignored by the compiler.
* **RetentionPolicy.CLASS** - The marked annotation is retained by the compiler at compile time, but is ignored by the Java Virtual Machine (JVM).
* **RetentionPolicy.RUNTIME** - The marked annotation is retained by the JVM so it can be used by the runtime environment.

RetentionPolicy として **SOURCE** を指定すると、コンパイル時にアノテーション情報は破棄されます。
**CLASS（デフォルト）**を指定すると、クラスファイルにアノテーション情報は含まれますが、実行時に VM で無視されます。
**RUNTIME** を指定すると、実行時に VM から参照することができます。

例えば、下記のように RetentionPolicy として RUNTIME を指定しておくと、

```java
@Retention(RetentionPolicy.RUNTIME)
public @interface Author {
    String[] value();
}
```

Java プログラムの中から、下記のようにアノテーション情報にアクセスすることができるようになります。

```java
@Author("maku")
public class Sample {
    public static void main(String[] args) {
        for (Annotation anno : Sample.class.getAnnotations()) {
            System.out.println(anno);  // @com.example.annotation.Author(value=[maku])
        }
    }
}
```


@Documented アノテーション
----

デフォルトでは、独自アノテーションを付けたクラスの Javadoc API ドキュメントを出力しても、そこにはアノテーション情報は表示されません。
アノテーション情報を API ドキュメントに含めるには、**@Documented** メタアノテーションを付加した状態で、独自アノテーションを定義する必要があります。

```java
@Documented
public @interface ToDo {
    String[] value();
}
```

