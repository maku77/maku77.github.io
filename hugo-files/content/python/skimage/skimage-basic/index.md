---
title: "Python の scikit-image で画像処理"
url: "p/apkiihk/"
date: "2023-11-08"
tags: ["Python", "scikit-image"]
---

scikit-image とは
----

[scikit-image](https://scikit-image.org/) は Pytyon 用の画像処理パッケージで、画像の入出力、変換、表示まで 1 パッケージで容易に行うことができます。
読み込んだ画像データは NumPy 配列として保持されるため、NumPy 配列の各種メソッドを使用して画素値を直接処理することができます。

高速な画像処理が可能なライブラリとして OpenCV などもありますが、scikit-image は OpenCV よりも学習が容易です。
科学的な画像処理には scikit-image、リアルタイムでのコンピュータービジョンタスクには OpenCV、と使い分けるとよさそうです。


scikit-image のインストール
----

{{% private %}}
- https://scikit-image.org/docs/stable/api/skimage.html
{{% /private %}}

`scikit-image` パッケージは `pip install` コマンドで簡単にインストールできます（最新バージョンへの更新を兼ねるには `-U` オプションを付けます）。
依存する `numpy`、`scipy`、`pillow` などのパッケージもまとめてインストールされます。
できれば、[venv 環境](/p/wozpogm/) にインストールすることをお勧めします。

{{< code lang="console" title="scikit-image のインストール" >}}
$ python -m pip install -U scikit-image
{{< /code >}}

画面上に画像を表示する関数 (`io.imshow()`) を使用する場合は、`matplotlib` パッケージもインストールしておく必要があります。

{{< code lang="console" title="matplotlib のインストール" >}}
$ python -m pip install -U matplotlib
{{< /code >}}

Python コードからインポートするパッケージの名前は `scikit-image` ではなく、__`skimage`__ なので注意してください。
`skimage` の機能は、次のようにサブパッケージとして分けられています。

- __`io`__ ... 画像の読み書きや、画面への表示
- __`transform`__ ... 回転などの画像変換処理
- __`color`__ ... 色空間の変換
- __`data`__ ... テスト用の画像データ
  - `img = data.astronaut()` のようにテスト画像データで簡単にお試しできます。


画像ファイルを読み込む
----

{{< image src="cat-64x64.jpg" title="画像ファイル (cat-64x64.jpg)" >}}

scikit-image で画像ファイルを読み込むには、__`io.imread()`__ 関数を使用します。
読み込んだ画像データは、NumPy 配列 (`numpy.ndarray`) として保持されます。
次の例では、64x64 サイズのカラー画像を読み込んでいます。

```python
from skimage import io

img = io.imread("cat-64x64.jpg")
print(type(img))  # => <class 'numpy.ndarray'>
print(img.shape)  # => (64, 64, 3)
```

NumPy 配列の値を出力してみると、各画素値 (R, G, B) の値が格納されていることを確認できます。

```python
print(img)
```

{{< code lang="python" title="実行結果" >}}
[[[215 238 232]
  [216 236 234]
  [219 234 237]
  ...
  [119  80  81]
  [124  88  92]
  [133  98 102]]
  ...
{{< /code >}}

NumPy 配列は 3 次元ベクトルの形で、`[行, 列, チャネル]` という構成になっているので、例えば次のようにして画素値を取り出すことができます。

```python
print(img[0, 0])     # => [215 238 232] （左上の 1 ピクセルの RGB 値）
print(img[0, 0, :])  # => 同上
print(img[0, 0, 0])  # => 215 （左上 の 1 ピクセルの R 値）
print(img[0, 0, 1])  # => 238 （左上 の 1 ピクセルの G 値）
print(img[0, 0, 2])  # => 232 （左上 の 1 ピクセルの B 値）
print(img[0, :, 0])  # => [215 216 219 ... 133] （1 行目のすべての列のピクセルの R 値）
print(img[:, 0, 0])  # => [215 219 227 ... 146] （1 列目のすべての行のピクセルの R 値）
```

`io.imread()` で画像を読み込むときに、__`as_gray=True`__ オプションを指定すると、グレースケール化された NumPy 配列データを取得することができます。
この場合、RGB の 3 チャネルではなく、1 チャネルのデータになるので、NumPy 配列は 2 次元データになります。

```python
img = io.imread("cat-64x64.jpg", as_gray=True)
print(type(img))  # => <class 'numpy.ndarray'>
print(img.shape)  # => (64, 64)
print(img[0, 0])  # => 0.9124701960784314（左上の画素値）
```


画像を表示する
----

NumPy 配列として保持している画像データを、画面上に表示するには __`im.imshow()`__ 関数を使用します。
Jupyter Notebook などの GUI 環境を使用していない場合は、続けて __`im.show()`__ を実行して、各種 OS (Windows/macOS/Linux) のウィンドウを表示する必要があります。

```python
from skimage import io

img = io.imread("cat-64x64.jpg")
io.imshow(img)
io.show()
```

{{< image w="320" src="img-001.png" title="io.imshow() による画像表示" >}}

画像を表示するだけであれば、実は次のように直接ファイル名を指定するだけで表示できます。

```python
io.imshow("cat-64x64.jpg")
io.show()
```

次のように明示的に `matplotlib.pyplot` モジュールをインポートして使う方法もあります。
`matplotlib` で細かい出力制御をしたいときは、こちらの方法を使うとよいです。

```python
from matplotlib import pyplot as plt
from skimage import io

img = io.imread("cat-64x64.jpg")
plt.imshow(img)
plt.show()
```


RGB の各成分を取り出す
----

{{< image src="img-002.png" title="RGB 各成分の強度をグレースケールで表示" >}}

画像データ（NumPy 配列）の 3 次元目には、各画素の RGB 成分が含まれています。
次のように配列スライスを使って、各チャネルの成分を抽出することができます。

{{< code lang="python" title="RGB の各チャネルを抽出" hl_lines="5-7" >}}
import matplotlib.pyplot as plt
from skimage import io

img = io.imread("cat-64x64.jpg")
img_r = img[:, :, 0]  # 全ピクセルの R 成分
img_g = img[:, :, 1]  # 全ピクセルの G 成分
img_b = img[:, :, 2]  # 全ピクセルの B 成分

# 画像の表示
fig, ax = plt.subplots(1, 4, figsize=(8, 2), dpi=72, layout="tight")
ax[0].set_title("RGB")
ax[0].imshow(img)
ax[1].set_title("R")
ax[1].imshow(img_r, cmap="gray")
ax[2].set_title("G")
ax[2].imshow(img_g, cmap="gray")
ax[3].set_title("B")
ax[3].imshow(img_b, cmap="gray")
for a in ax:
    a.set_axis_off()  # 軸を表示しない
plt.show()
{{< /code >}}

上の例では、`imshow()` 関数の __`cmap`__ でカラーマップを `gray` に指定したので、画素値 0 が黒、画素値 255 が白になるように表示されています。
カラーマップを変更することで、各成分の強度を異なる色表現で表示することができます（参考: [Choosing Colormaps in Matplotlib](https://matplotlib.org/stable/users/explain/colors/colormaps.html)）。
例えば、`Grays`（あるいは `Greys`）を使うと、画素値 0 が白、画素値 255 が黒となるようなグレースケールで表示されます。

{{< image src="img-003.png" title="カラーマップに Grays を指定した場合" >}}

```python
ax[1].imshow(img_r, cmap="Grays")
ax[2].imshow(img_g, cmap="Grays")
ax[3].imshow(img_b, cmap="Grays")
```

カラーマップとして `Reds`、`Greens`、`Blues` を指定すると、各チャネルの強度を直感的な色で表示できます。

{{< image src="img-004.png" title="カラーマップに Reds/Greens/Blues を指定した場合" >}}

```python
ax[1].imshow(img_r, cmap="Reds")
ax[2].imshow(img_g, cmap="Greens")
ax[3].imshow(img_b, cmap="Blues")
```

