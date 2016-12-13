---
title: NumPy と Matplotlib をインストールする
created: 2016-12-13
---

NumPy は行列などの数値計算ライブラリ、Matplotlib はグラフ描画のためのライブラリで、組み合わせて使用することの多いライブラリです。


PIP による NumPy と Matplotlib のインストール
----

Python 3.4 以降はパッケージ管理のための `pip` コマンドが標準で搭載されているのでこれを使って NumPy と Matplotlib をインストールするのがお手軽です。
まずは、pip コマンド自体を最新にアップグレードします。

```
$ pip install --upgrade pip
```

引き続き、NumPy と Matplotlib のインストールを行います。

```
$ pip install numpy
$ pip install matplotlib
```

これで、`import numpy`、`import matplotlib` とインポートできるようになります。


トラブルシューティング: tkinter モジュールが見つからない
----

`import matplotlib.pyplot` などを実行したときに、下記のように `tkinter` モジュールが足りないというエラーが出ることがあります。

```
ImportError: No module named 'tkinter'
```

`tkinter` は GUI ライブラリの Tk を Python から利用できるようにしたモジュールです。
Tk 自体はもはや時代遅れの GUI ライブラリですが、`matplotlib` ではまだ Tk を使用しているので、`tkinter` のインストールが必要です。
Linux の場合は、`python3-tk` パッケージを導入すれば、Python 内で `tkinter` モジュールをインポートできるようになります。

```
sudo apt-get install python3-tk
```

Windows の場合は、[Python のインストーラ](https://www.python.org/downloads/) を実行して、 "tcl/tk and IDLE" という項目にチェックをいれてインストールしてください。

