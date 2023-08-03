---
title: "Matplotlib の Axes、Figure、pyplot の関係を理解する"
url: "p/59ruo5m/"
date: "2023-08-04"
tags: ["Matplotlib", "Python"]
---

Figure と Axes
----

Matplotlib の基本的な描画要素として、__`Figure`__ と __`Axes`__ があり、`Figure` の中に複数の `Axes` を配置できるようになっています。
次のプロット画像には、1 つの `Figure` の中に、2 つの `Axes` を配置しています。

{{< image src="img-001.svg" title="Figure と Axes の関係" >}}

{{< accordion title="上記画像のコード" >}}
{{< code lang="python" >}}
import matplotlib.pyplot as plt

x = [0, 1, 2, 3]
y = [0, 8, 2, 10]

# Figure の作成
fig = plt.figure(facecolor="lightskyblue", layout="tight", dpi=72, figsize=(6, 3))
fig.suptitle("Figure")

# Axes によるプロット
ax = fig.subplots(1, 2)
ax[0].set_title("Axes 1", loc="left", fontstyle="oblique", fontsize="medium")
ax[0].plot(x, y)
ax[1].set_title("Axes 2", loc="left", fontstyle="oblique", fontsize="medium")
ax[1].plot(x, y)

fig.savefig("img-001.svg")
{{< /code >}}
{{< /accordion >}}

`Figure` オブジェクトの [subfigures](https://matplotlib.org/stable/api/figure_api.html#matplotlib.figure.Figure.subfigures) メソッドや [add_subfigure](https://matplotlib.org/stable/api/figure_api.html#matplotlib.figure.Figure.add_subfigure) メソッドを使うと、`Figure` の入れ子構造を作ることができます。

{{% private %}}
- [Figure subfigures](https://matplotlib.org/stable/gallery/subplots_axes_and_figures/subfigures.html)
{{% /private %}}


Axes インターフェイスと pyplot インターフェイス
----

Matplotlib を使ったプロット方法には、__明示的 Axes インターフェイス__ を使う方法と、__暗黙的 pyplot インターフェイス__ を使う方法があります。

### 明示的 Axes インターフェイス (explicit Axes interface)

最初に `Figure` や `Axes` オブジェクトの参照を取得し、それらのメソッドを明示的に呼び出して各種パラメーターを設定していく方法です。
オブジェクト指向インターフェイスと呼ばれています。
各オブジェクトを別々に制御できるため、柔軟性が高いです。

```python
import matplotlib.pyplot as plt

x = [0, 1, 2, 3]
y = [0, 8, 2, 10]

fig = plt.figure()   # Figure を作成
ax = fig.subplots()  # Figure 内に Axes を作成
ax.plot(x, y)        # Axes へのプロット

# 次のように Figure と Axes をまとめて取得することも可能
# fig, ax = plt.subplots()
# ax.plot(x, y)

plt.show()
```

`Figure#subplots()` メソッドあるいは `pyplot.subplots()` 関数の引数として、プロット領域の分割数（行数と列数）を渡すと、リストの形で `Axes` オブジェクトを取得することができます。
次のように、プロット先をインデックスで切り替えることができます。

```python
fig, ax = plt.subplots(1, 2)  # 1 行 2 列に分割されたプロット領域 (Axes) を作成
ax[0].plot(x, y)  # 1 つ目の Axes にプロット
ax[1].plot(x, y)  # 2 つ目の Axes にプロット
```

### 暗黙的 pyplot インターフェイス (implicit pyplot interface)

`pyplot` モジュールが提供する関数を連続して呼び出すことで、内部で描画情報を積み上げていく方法です。
コードとしては、`plt.hogehoge()` のような関数をたくさん呼び出すことになります。
内部的には `Axes` オブジェクトのプロット関連メソッドが呼び出されているわけですが、それらの呼び出しは隠蔽されるため、簡単な内容であれば、短いコードで記述することができます。

```python
import matplotlib.pyplot as plt

x = [0, 1, 2, 3]
y = [0, 8, 2, 10]

plt.plot(x, y)  # 内部で保持されている Axes にプロットする

plt.show()
```

ただし、分割されたチャートを描画するようなケースでは、前述の明示的な `Axes` を使った方が分かりやすいです。
`pyplot` インターフェイスを使う方法だと、内部的に現在どの `Axes` を操作しているのかを意識したコーディングを行う必要があります。

```python
plt.subplot(1, 2, 1)  # 1 行 2 列に分割した描画先の 1 つ目の Axes を選択
plt.plot(x, y)        # 1 つ目の Axes にプロット

plt.subplot(1, 2, 2)  # 1 行 2 列に分割した描画先の 2 つ目の Axes を選択
plt.plot(x, y)        # 2 つ目の Axes にプロット
```

