---
title: "NumPy / pandas / Matplotlib の概要とインストール"
url: "p/qs6iv8j/"
date: "2016-12-13"
tags: ["NumPy", "pandas", "Matplotlib"]
aliases: /python/numpy/install.html
---

NumPy / pandas / Matplotlib とは
----

NumPy、pandas、Matplotlib などのライブラリは組み合わせて使用することの多いライブラリで、それぞれ下記のような特徴を持っています。

NumPy - 数値計算ライブラリ ([numpy.org](https://numpy.org))
: C で実装された高速な多次元配列である __`ndarray`__（通称 NumPy 配列）、及び関連メソッドを扱うことができます。
Python のみで行列を表現しようとすると、配列の配列（要素ごとに可変長）を作ることになりますが、NumPy 配列ではn x m の固定サイズ、単一タイプのデータとして扱うことで非常に高速な計算処理を行えるようになっています。

pandas -- 数値解析ライブラリ ([pandas.pydata.org](https://pandas.pydata.org))
: R 言語のような数値解析を行うことができるライブラリです。
データファイルの読み込み、加工、集計、可視化までを総合的に行うことができます。
データのプロットには内部で Matplotlib を使用しており、`pd.DataFrame.plot()` がラッパーとして提供されています。

Matplotlib -- グラフ描画ライブラリ ([matplotlib.org](https://matplotlib.org))
: 単独でグラフを描画するために使用できるライブラリですが、pandas による計算結果を描画するために内部で使用されています。


NumPy / pandas / Matplotlib のインストール
----

Python 3.4 以降はパッケージ管理のための `pip` コマンドが標準で搭載されているので、これを使って NumPy と pandas と Matplotlib をインストールするのがお手軽です。
まずは、`pip` コマンド自体を最新にアップグレードします。

```console
$ pip install --upgrade pip
```

引き続き、NumPy と Matplotlib のインストールを行います。

```console
$ pip install numpy
$ pip install pandas
$ pip install matplotlib
```

これで、`import numpy`、`import pandas`、`import matplotlib` といった感じでインポートできるようになります。


慣例になっている別名
----

各ライブラリを `import` するときは、下記のように略称 (__`plt`__, __`np`__, __`pd`__) を定義して使用するのが慣例ととなっています。

```python
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
```

トラブルシューティング: tkinter モジュールが見つからない
----

`import matplotlib.pyplot` などを実行したときに、下記のように `tkinter` モジュールが足りないというエラーが出ることがあります。

~~~
ImportError: No module named 'tkinter'
~~~

`tkinter` は GUI ライブラリの Tk を Python から利用できるようにしたモジュールです。
Tk 自体はもはや時代遅れの GUI ライブラリですが、`matplotlib` ではまだ Tk を使用しているので、`tkinter` のインストールが必要です。
Linux の場合は、`python3-tk` パッケージを導入すれば、Python 内で `tkinter` モジュールをインポートできるようになります。

```console
$ sudo apt-get install python3-tk
```

Windows の場合は、[Python のインストーラ](https://www.python.org/downloads/) を実行して、 "tcl/tk and IDLE" という項目にチェックをいれてインストールしてください。

