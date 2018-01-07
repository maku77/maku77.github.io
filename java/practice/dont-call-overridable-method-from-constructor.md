---
title: コンストラクタからオーバライド可能なメソッドを呼び出さない
date: "2016-03-23"
---

コンストラクタの実装から、そのクラスのオーバライド可能なメソッドを呼び出すと、サブクラス化によって動作を破壊されることがあります。
このような呼び出しは、静的解析ツールの PMD で `ConstructorCallsOverridableMethod` 警告として検出されます。

例えば、下記は PMD のサイトに掲載されている（正しく動作しない）サンプルコードです。

```java
public class SeniorClass {
    public SeniorClass(){
        toString();  // may throw NullPointerException if overridden
    }
    public String toString(){
        return "IAmSeniorClass";
    }
}

public class JuniorClass extends SeniorClass {
    private String name;
    public JuniorClass(){
        super();  // Automatic call leads to NullPointerException
        name = "JuniorClass";
    }
    public String toString(){
        return name.toUpperCase();
    }
}
```

親クラスの `SeniorClass` は、オーバライド可能な `toString` メソッドを呼び出しているため、サブクラス化によってこの動作を破壊される可能性があります。
サブクラスの `JuniorClass` は `toString` をオーバライドして、`name` フィールドを参照しています。
一方のクラスだけを見ると何も問題ないように見えますが、`JuniorClass` のインスタンスを生成しようとすると、`SeniorClass` のコンストラクタ経由で `JuniorClass#toString()` が呼び出されることになるため、初期化されていない `JuniorClass` の `name` フィールドが参照されて `NullPointerException` が発生します。

