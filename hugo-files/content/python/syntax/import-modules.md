---
title: "Python でモジュールを import する方法のまとめ"
url: "p/zdxfvct/"
date: "2009-11-19"
lastmod: "2023-05-30"
tags: ["Python"]
changes:
  - 2023-05-30: 記事を洗練
aliases: /python/import-modules.html
---

Python でモジュールをインポートする際に使用される __`import`__ 構文は、複数の指定方法があるため、初めはやや混乱するかもしれません。
以下に、`import` のさまざまな指定方法をまとめておきます。
Python のモジュールとパッケージの基本については下記の記事を参考にしてください。

- [Python でモジュールやパッケージを作成する](/p/n4n5m3i/)


モジュール単位でのインポート
----

まずは基本から。
モジュール名でインポートする方法です。
この方法でモジュールをインポートした場合は、内部の関数などを参照するときは、モジュール名をプレフィックスとして付けます。

{{< code lang="python" title="mymod モジュールをインポートする" >}}
import mymod
mymod.hello()
{{< /code >}}

パッケージ内のモジュールをインポートするときは、次のいずれかの方法で記述しますが、モジュール内のメンバーを参照するときのプレフィックスが変わってきます。

{{< code lang="python" title="mypkg パッケージ内の mymod モジュールをインポートする" >}}
# 方法1
import mypkg.mymod
mypkg.mymod.hello()

# 方法2
from mypkg import mymod
mymod.hello()
{{< /code >}}

2 階層以上のパッケージになっても同様です。

```python
# 方法1
import mypkg.sub.mymod
mypkg.sub.mymod.hello()

# 方法2
from mypkg.sub import mymod
mymod.hello()
```


特定のメンバーのみをインポート
----

__`from モジュール名 import 関数名`__ という構文で、モジュール内の特定の関数だけインポートすれば、モジュール名を指定せずにその関数を直接呼び出せるようになります。

{{< code lang="python" title="mymod モジュールの hello 関数のみをインポートする" >}}
from mymod import hello
hello()
{{< /code >}}

パッケージ内のモジュールに関しても同様です。

{{< code lang="python" title="mypkg パッケージの mymod モジュールの hello 関数のみをインポートする" >}}
from mypkg.mymod import hello
hello()
{{< /code >}}

ワイルドカード (__`*`__) を使って、モジュール内のすべてのメンバーをインポートできます（ただし、アンダースコア (`_`) で始まるメンバーはインポートされません）。

{{< code lang="python" title="mymod モジュールのすべてのメンバーをインポートする" >}}
from mymod import *
hello()
{{< /code >}}


別名を付けてインポート (as)
----

インポートしたモジュールや関数の名前が長すぎると感じたときは、__`as`__ キーワードを使用して別名を付けることができます。
別名は、名前のコンフリクトを防ぐためにも使用することがあります。

{{< code lang="python" title="ElementTree モジュールを ET という別名でインポート" >}}
import xml.etree.ElementTree as ET

tree = ET.parse('books.xml')
{{< /code >}}


いろいろなインポートのパターン
----

最後にいろいろなインポートの例を載せておきます。
全部理解できれば、たぶんインポートマスターです。

```python
# モジュール mod をインポート
import mod

# モジュール mod をインポート（hoge で参照）
import mod as hoge

# モジュール mod1 と mod2 をインポート
import mod1, mod2

# モジュール mod 内の関数 func をインポート
from mod import func

# モジュール mod 内の関数 func1 と func2 をインポート
from mod import func1, func2

# モジュール mod 内の全メンバーをインポート
from mod import *

# パッケージ pkg 内のモジュール mod をインポート（参照時のプレフィックスは pkg.mod）
import pkg.mod

# パッケージ pkg 内のサブパッケージ sub 内のモジュール mod をインポート（参照時のプレフィックスは pkg.sub.mod）
import pkg.sub.mod

# パッケージ pkg 内のモジュール mod をインポート（参照時のプレフィックスは mod）
from pkg import mod

# パッケージ pkg 内のモジュール mod をインポート（参照時のプレフィックスは hoge）
from pkg import mod as hoge

# パッケージ pkg 内のモジュール mod 内の関数 func をインポート
from pkg.mod import func

# 相対パスで同じ階層のモジュール mod をインポート
from . import mod

# 相対パスで同じ階層のモジュール mod 内の関数 func をインポート
from .mod import func

# 相対パスで 1 つ上のパッケージ階層のモジュール mod をインポート
from .. import mod

# 相対パスで 1 つ上のパッケージ階層のモジュール mod 内の関数 func をインポート
from ..mod import func
```

