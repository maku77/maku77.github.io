---
title: "Pythonメモ: wxPython 関連コラム"
url: "p/y3bknpi/"
date: "2007-03-23"
tags: ["python"]
aliases: /python/wxpython/column.html
---

Python 標準搭載の Tkinter ではダメなわけ
----

- Tk に依存してるためウィジェットの見た目が時代遅れ。ネイティブなウィジェットでないため見た目に統一感がなくダサい。
- 今ではもうスタンダードになっているウィジェットすら用意されていない（ツリーコントロール、タブ付きウィンドウ、各種ダイアログなど）。
- コーディングスタイルがダサい。wxPython のイベントシステムはカッコいい。

そもそも Python の UI ツールキットとして Tkinter を標準搭載したのは、その時点でまだ wxPython が存在していなかったからというのが大きいと思います。
将来的には wxPython が標準になってくれるとよいねー。


wxWidget の wx とは？
----

wxPython のベースとなっている wxWidget プロジェクトは、1992 年に Julian Smart によってスタートされました。
共通のプレフィックスとなっている **wx** には、もともと下記のような意味が含まれています。

- w は Windows の **W**
- x は Unix X server の **X**


wxPython のコーディングスタイル
----

Python のメソッド名は一般的に、

```python
lower_case_separeted_by_underscores
lowerCaseInterCap
```

のようなスタイルが使用されますが、wxPython のメソッド名は wxWidget のスタイルが採用されているため、

```python
UpperCaseInterCap
```

といったスタイルで記述されています。
wxPython を使って作成するアプリケーションでは、スタイルを統一するために後者の形式を使うことが推奨されています。


wxPython の旧パッケージ名について
----

wxPython の古いパッケージ構成では、トップレベルの `wxPython` パッケージのサブパッケージとして `wx` パッケージが用意されていたため、次のようなインポート記述を行っていました。

```python
from wxPython import wx
app = wx.wxApp()
```

各クラスには `wx` というプレフィックスが付いていたため、実際にクラスを使用する時は `wx.wxApp` というように、２回 `wx` というテキストをタイピングする必要がありました。
クラス名の前にプレフィックスが付いているのは、

```python
from wxPython.wx import *
app = wxApp()
```

というようにすべてのシンボルをインポートしたときに名前の衝突を防ぐためでしたが、そもそもこのようなインポート方法は名前衝突の根本的解決にならず、Python のコーディングスタイルとして推奨されていません。
新しい wxPython では、`wx` がトップレベルのパッケージとなり、クラス名のプレフィックスもなくなったので、シンプルに記述できるようになりました。

```python
import wx
app = wx.App()
```

- 参考文献: wxPython in Action


wxPython のサブパッケージを import する前に必ず wx パッケージを import する
----

wxPython の `wx` パッケージをインポートすると、wxPython 関連モジュールの初期化処理が行われます。
この処理は、`wx` パッケージのサブパッケージにも必要な処理なので、サブパッケージをインポートする前に、必ず `wx` パッケージをインポートしておく必要があります。

```python
import wx  # サブパッケージの前に wx パッケージをインポートする！
from wx import xrc
from wx import html
```


トップレベルのサブウィジェットとして wx.Panel を配置する理由
---

- パネルごとにクラスを作成しておけば、そのパネルを再利用しやすい。
- `wx.Panel` は適切なデフォルト背景色を表示してくれる。
- Enter キーを入力したときに選択されるデフォルトのウィジェットを設定できる。
- Tab キーを入力したときに、ウィジェット間のフォーカス移動が行える。

