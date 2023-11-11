---
title: "OpenCV: 画像を閾値で二値化（白黒画像化）する (cv2.threshold)"
url: "p/i26mc77/"
date: "2023-11-10"
tags: ["OpenCV"]
draft: true
---

{{% private %}}
- https://docs.opencv.org/5.x/d7/d4d/tutorial_py_thresholding.html
{{% /private %}}

cv2.threshold 関数
----

OpenCV の __`cv2.threshold()`__ 関数を使用すると、ある閾値を基準にして画素値を置き換えることができます。
多くの場合、グレースケール画像を 0（黒）と 255（白）の白黒画像 (binary image) にするときに使われます。

```python
cv2.threshold(src, thresh, maxval, type) -> retval, dst
```

- 引数:
  1. `src` ... 入力画像。グレースケールのデータでなければいけません。
  2. `thresh` ... 閾値となる画素値です。
  3. `maxval` ... 閾値を超えた画素値はこの値に置き換えられます。
  4. `type` ... どのように画素値を置き換えるかを指定します。
     | type | 閾値以下の画素 | 閾値超えの画素 | 生成される色 |
     | ---- | :--: | :--: | ---- |
     | `cv2.THRESH_BINARY` | 0 | maxval | 白黒（2 値） |
     | `cv2.THRESH_BINARY_INV` | maxval | 0 | 白黒（2 値） |
     | `cv2.THRESH_TRUNC` | 元の値 | thresh | グレースケール |
     | `cv2.THRESH_TOZERO` | 0 | 元の値 | グレースケール |
     | `cv2.THRESH_TOZERO_INV` | 元の値 | 0 | グレースケール |

     閾値を自動設定したいときは、下記を組み合わせて指定します。この場合、第 2 引数 (`thresh`) の値は無視されます。
     - `cv2.THRESH_OTSU` ... 大津の手法により閾値を自動選択します。
     - `cv2.THRESH_TRIANGLE` ... Triangle アルゴリズムにより閾値を自動選択します。
- 戻り値
  1. `retval` ... 実際に使用された閾値。`type` に `cv2.THRESH_OTSU` などを指定した場合は閾値が自動設定されるので、その値を取得したい時に使います。
  2. `dst` ... 変換後の画像データ (thresholded image)。NumPy 配列として返されます。


使用例
----

入力画像として、下記のカラー画像を使用します。

{{< image src="img-ghost-girl.jpg" title="元の画像ファイル (ghost-girl.jpg)" >}}

### 閾値を明示的に指定する方法

下記は、`cv2.THRESH_BINARY` を指定した単純な 2 値化の例で、画素値の閾値 (`thresh`) として 200 を指定しています。
閾値以下の画素値は 0 になり、閾値を超える画素値は `maxval` で指定されている 255 になります。

{{< code lang="python" title="cv2.THRESH_BINARY による 2 値化" >}}
import cv2
import matplotlib.pyplot as plt

# 画像ファイルをグレースケールで読み込む
img_gray = cv2.imread('ghost-girl.jpg', cv2.IMREAD_GRAYSCALE)

# 白黒にする（200 以下の画素値を 0 に置換、200 超えの画素値を 255 に置換）
_, img_bin = cv2.threshold(img_gray, 200, 255, cv2.THRESH_BINARY)

# 結果表示
plt.figure(figsize=(3, 3))
plt.imshow(img_bin, cmap='gray')
plt.show()
{{< /code >}}

{{< image src="img-ghost-girl-bin.png" title="閾値=200 で白黒化（2 値化）された画像" >}}

### 閾値を自動設定する方法

画素値のヒストグラムをもとに閾値を自動設定したい場合は、第 4 引数 (`type`) に __`cv2.THRESH_OTSU`__ や __`cv2.THRESH_TRIANGLE`__ を指定します。
この場合、第 2 引数 (`thresh`) は使われないので、適当な値を指定しておけば OK です（下記では 0 を指定しています）。
実際に使用された閾値は、1 つ目の戻り値（下記例では `ret`）として返されます。
ちなみに、このサンプル画像の場合は、閾値は 128 になりました。

{{< code lang="python" title="大津の手法による閾値の自動設定" >}}
ret, img_bin = cv2.threshold(img_gray, 0, 255, cv2.THRESH_OTSU)
{{< /code >}}

{{< image src="img-ghost-girl-otsu.png" title="大津の手法による閾値の自動決定" >}}

閾値を自動設定した場合、その処理方法はデフォルトで `cv2.THRESH_BINARY (0)` と同様になりますが、他の処理方法を組み合わせて指定することもできます。
次の例では、`cv2.THRESH_TRIANGLE` で閾値を自動設定しつつ、`cv2.THRESH_TOZERO`（閾値以下を 0 に置換するだけ）で処理しています。
この場合、第 2 引数 (`thread`) も第 3 引数 (`maxval`) も必要ないので、両方とも 0 を指定しておけば OK です。

{{< code lang="python" title="閾値の自動設定しつつ、処理方法を指定" >}}
_, img_th = cv2.threshold(img_gray, 0, 0, cv2.THRESH_TRIANGLE | cv2.THRESH_TOZERO)
{{< /code >}}

{{< image src="img-ghost-girl-tozero.png" title="閾値を自動決定しつつ THRESH_TOZERO 処理" >}}
