---
title: "scikit-image チートシート"
url: "p/bwemnew/"
date: "2024-02-13"
tags: ["scikit-image"]
draft: true
---

<table>
  <tr>
    <th>コード</th><th>説明</th>
  </tr>
  <tr>
    <td>
{{< code lang="python" >}}
io.imread("cat.png")
{{< /code >}}
    </td>
    <td>画像を読み込む（3 次元 NumPy 配列になる）</td>
  </tr>
  <tr>
    <td>
{{< code lang="python" >}}
io.imread("cat.png", as_gray=True)
{{< /code >}}
    </td>
    <td>グレースケールで画像を読み込む</td>
  </tr>
  <tr>
    <td>
{{< code lang="python" >}}
img_r = img[:, :, 0]
img_g = img[:, :, 1]
img_b = img[:, :, 2]
{{< /code >}}
    </td>
    <td>RGB の情報を抽出する（img.shape は (64, 64, 3) のような RGB 画像データとする）</td>
  </tr>
  <tr>
    <td>
{{< code lang="python" >}}
img_new = transform.resize(img,
    output_shape=(100, 50),
    mode="reflect",
    anti_aliasing=True)
{{< /code >}}
    </td>
    <td>画像サイズの変更</td>
  </tr>
  <tr>
    <td>
{{< code lang="python" >}}
img_new = img[0:50, 20:30]
{{< /code >}}
    </td>
    <td>切り出し（クリッピング）</td>
  </tr>
  <tr>
    <td>
{{< code lang="python" >}}
tr = transform.AffineTransform(translation=(10, 20))
img_new = transform.warp(img, tr)
{{< /code >}}
    </td>
    <td>平行移動（左上へ (10, 20) 移動）</td>
  </tr>
  <tr>
    <td>
{{< code lang="python" >}}
img_new = img[::-1, :]
{{< /code >}}
    </td>
    <td>上下反転</td>
  </tr>
  <tr>
    <td>
{{< code lang="python" >}}
img_new = img[:, ::-1]
{{< /code >}}
    </td>
    <td>左右反転</td>
  </tr>
  <tr>
    <td>
{{< code lang="python" >}}
img_new = transform.rotate(img, angle=45)
{{< /code >}}
    </td>
    <td>回転</td>
  </tr>
</table>

