---
title: "あるメソッドが存在するか調べる"
date: "2008-06-27"
---

すべてのクラスの基底クラスである `UNIVERSAL` パッケージに用意されている `can()` メソッドを使用すると、あるクラス（あるはそのオブジェクト）にあるメソッドが定義されているかを調べることができます。

```perl
if (UNIVERSAL::can($obj, "greet")) {
    print "This obj can greet().\n";
}
```

`$obj` が bless されていることが保証されるのであれば、次のようにメソッドとして呼び出せます。

```perl
if ($obj->can("greet")) {
    ...
}
```

`UNIVERSAL` パッケージに定義されているメソッドを引数にして `UNIVERSAL::can` を呼び出すと、必ず真を返します。

```perl
if ($obj->can("can")) {  # 必ず真
    ...
}
```

