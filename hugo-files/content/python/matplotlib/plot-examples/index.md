---
title: "Matplotlib によるプロットの例（散布図）"
url: "p/4xyn2kv/"
date: "2024-02-09"
tags: ["Matplotlib"]
---

Python の Matplotlib によるプロットの例です。
すぐに忘れてしまうのでメモメモ( ..)φ

散布図 (scatter diagram)
----

### 2 つの特徴量を X/Y 座標値としてプロット

{{< image src="img-scatter.svg" title="散布図の例" >}}

{{< accordion title="テスト用のサンプルデータ（アヤメの分類データ）" >}}
{{< code lang="python" >}}
def load_data():
    from sklearn import datasets
    iris = datasets.load_iris()

    # 各サンプルの特徴量には下記の 4 つが含まれている
    #   - sepal length (cm)
    #   - sepal width (cm)
    #   - petal length (cm)
    #   - petal width (cm)
    features = iris.data

    # 各サンプルの正解ラベルとして下記のいずれかが含まれている
    #   - 0: setosa
    #   - 1: versicolor
    #   - 2: virginica
    targets = iris.target

    return features, targets
{{< /code >}}
{{< /accordion >}}

{{< code lang="python" hl_lines="8" >}}
import matplotlib.pyplot as plt
features, targets = load_data()

# 2 つの特徴量を X/Y 座標値とした散布図を描画
fig, ax = plt.subplots(figsize=(6, 4), layout="tight")
ax.set_xlabel("speal length (cm)")
ax.set_ylabel("sepal width (cm)")
ax.scatter(x=features[:, 0], y=features[:, 1])

fig.savefig("img-scatter.svg")
plt.show()
{{< /code >}}

Matplotlib で散布図を描画するには、__`scatter`__ 関数を使用します。
散布図のどの座標に点を打つかは、__`x`__、__`y`__ 引数に、X 軸と Y 軸の座標値リストを渡すことで指示します。
ここでは、`features[:, 0]` で「speal length (cm)」のリスト、`features[:, 1]` で「speal width (cm)」のリストを取り出して渡しています。

### 各点に意味を持たせる

{{< image src="img-scatter-2.svg" title="散布図の例 (2)">}}

`scatter` 関数に X/Y 座標値だけを渡すと、前述の例のように、すべての点が同じ記号で表示されます。
__`c`__ 引数で、各点の値のリストを渡すことで、異なる色で点を打つことができます。

{{< code lang="python" hl_lines="8 9" >}}
import matplotlib.pyplot as plt
features, targets = load_data()

# 2 つの特徴量を X/Y 座標値とした散布図を描画
fig, ax = plt.subplots(figsize=(6, 4), layout="tight")
ax.set_xlabel("speal length (cm)")
ax.set_ylabel("sepal width (cm)")
scatter = ax.scatter(x=features[:, 0], y=features[:, 1], c=targets)
ax.legend(*scatter.legend_elements())  # 各点の値の凡例を表示

fig.savefig("img-scatter-2.svg")
plt.show()
{{< /code >}}

ちなみに、`c` 引数で渡している `targets` 変数には、次のような正解ラベル (0, 1, 2) が含まれています。

```python
[0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 2 2 2 2 2 2 2 2 2 2 2
 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2
 2 2]
```

