---
title: "Perlメモ: 一時的にカレントパッケージを変更する"
url: "p/8hrsi9o/"
date: "2008-04-30"
tags: ["perl"]
aliases: ["/perl/syntax/change-current-package.html"]
---

パッケージのスコープは、次の `package` ディレクトリが出てくるまで、あるいはファイルの最後までで、デフォルトのパッケージは `main` パッケージとなっています。

ただし、ブロック内で `package` 宣言をした場合は、そのブロック内だけがパッケージのスコープになります。

```perl
package Foo;  # ここから先は Foo パッケージ
...
{
    package Bar;  # ここから先は Bar パッケージ
    ...
}
# パッケージ Foo に戻る
```

