---
title: SuprressWarnings アノテーションで Checkstyle の警告を抑制する
created: 2015-12-21
---

下記のように Checkstyle の警告を `@SuppressWarnings` アノテーションで抑制できるようにするには、Checkstyle で使用するルールファイルにそれ用の設定を記述しておく必要があります。

```java
@SuppressWarnings("checkstyle:parameternumber")
public MyClass(int a, int b, int c, int d, int e, int f, int g, int h) {
    ...
}
```

#### checkstyle-rules.xml

```xml
...
<module name="Checker">
  <module name="SuppressWarningsFilter" />
  <module name="TreeWalker">
    ...
    <module name="SuppressWarningsHolder" />
  </module>
</module>
```

`SuppressWarningsFilter` と `SuppressWarningsHolder` を配置する階層に注意してください。

