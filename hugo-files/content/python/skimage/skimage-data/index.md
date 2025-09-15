---
title: "scikit-image の data パッケージに含まれるテスト画像を使用する"
url: "p/nz8zoxu/"
date: "2023-11-10"
tags: ["scikit-image"]
---

scikit-image の data パッケージ
----

scikit-image の __`data`__ パッケージには、画像処理のテストに使えるテスト画像データが含まれています。

画像データを読み込むには、画像ごとに用意された関数を呼び出します。
次の例では、__`data.astronaut()`__ 関数で宇宙飛行士の画像データを取得しています。
画像データは NumPy 配列データ (`ndarray`) として返されます。

```python
from skimage import data

# 画像データを読み込む
img = data.astronaut()

# 画像データの形式を調べる
print(type(img))  #=> <class 'numpy.ndarray'>
print(img.shape)  #=> (512, 512, 3)
```

この画像データのサイズは 512x512 で、カラー画像（3 チャネル）であることが分かります。


読み込んだ画像データを表示する
----

`matplotlib` パッケージを使えば、画像データ（NumPy 配列）を画面上に出力することができます。

{{< image src="img-001.png" title="skimage.data.astronaut() の画像データ" >}}

```python
from matplotlib import pyplot as plt
from skimage import data

# 画像データを読み込む
img = data.astronaut()

# matplotlib で画面に表示
fig, ax = plt.subplots(figsize=(4, 4), dpi=72)
ax.imshow(img)
plt.show()

# （別の書き方）
# plt.figure(figsize=(4, 4), dpi=72)
# plt.imshow(img)
# plt.show()
```


他の画像データ
----

scikit-image の `data` パッケージは他にもいくつかの画像データを提供しています。
下記は汎用的に使える画像データの一覧です（詳しくは[こちらの公式ドキュメント](https://scikit-image.org/docs/stable/auto_examples/data/index.html)）。

| イメージ | データ取得関数 | shape | 色 |
| :--: | ---- | ---- | ---- |
| {{< image src="img-thumb-astronaut.png" >}} | `data.astronaut()` | `(512, 512, 3)` | カラー |
| {{< image src="img-thumb-binary_blobs.png" >}} | `data.binary_blobs()` | `(512, 512)` | グレースケール |
| {{< image src="img-thumb-brick.png" >}} | `data.brick()` | `(512, 512)` | グレースケール |
| {{< image src="img-thumb-colorwheel.png" >}} | `data.colorwheel()` | `(370, 371, 3)` | カラー |
| {{< image src="img-thumb-camera.png" >}} | `data.camera()` | `(512, 512)` | グレースケール |
| {{< image src="img-thumb-cat.png" >}} | `data.cat()` | `(300, 451, 3)` | カラー |
| {{< image src="img-thumb-checkerboard.png" >}} | `data.checkerboard()` | `(200, 200)` | グレースケール |
| {{< image src="img-thumb-clock.png" >}} | `data.clock()` | `(300, 400)` | グレースケール |
| {{< image src="img-thumb-coffee.png" >}} | `data.coffee()` | `(400, 600, 3)` | カラー |
| {{< image src="img-thumb-coins.png" >}} | `data.coins()` | `(303, 384)` | グレースケール |
| {{< image src="img-thumb-grass.png" >}} | `data.grass()` | `(512, 512)` | グレースケール |
| {{< image src="img-thumb-gravel.png" >}} | `data.gravel()` | `(512, 512)` | グレースケール |
| {{< image src="img-thumb-horse.png" >}} | `data.horse()` | `(328, 400)` | グレースケール |
| {{< image src="img-thumb-logo.png" >}} | `data.logo()` | `(500, 500, 4)` | カラー |
| {{< image src="img-thumb-page.png" >}} | `data.page()` | `(191, 384)` | グレースケール |
| {{< image src="img-thumb-text.png" >}} | `data.text()` | `(172, 448)` | グレースケール |
| {{< image src="img-thumb-rocket.png" >}} | `data.rocket()` | `(427, 640, 3)` | カラー |

{{% accordion title="（おまけ）上記の一覧を出力するコード" %}}
```python
import matplotlib.pyplot as plt
from skimage import data

images = ('astronaut', 'binary_blobs', 'brick', 'colorwheel',
    'camera', 'cat', 'checkerboard', 'clock', 'coffee', 'coins',
    'grass', 'gravel', 'horse', 'logo', 'page', 'text', 'rocket')

for name in images:
    # 画像データの読み込み
    caller = getattr(data, name)  # 関数名から関数を取得
    img = caller()

    # 出力
    print("{} {}".format(name, img.shape))
    plt.figure(figsize=(3, 0.8))
    plt.axis('off')
    cmap = plt.cm.gray if img.ndim == 2 else None
    plt.imshow(img, cmap=cmap)
    plt.show()
```
{{% /accordion %}}

