---
title: "OpenCV: 画像内の物体の輪郭を検出する (cv2.findContours)"
url: "p/ocef2kr/"
date: "2023-11-12"
tags: ["OpenCV"]
draft: true
---

{{% private %}}
- [cv2.findContours()](https://docs.opencv.org/5.x/d3/dc0/group__imgproc__shape.html#gadf1ad6a0b82947fa1fe3c3d497f260e0)
{{% /private %}}

cv2.findContours() 関数
----

OpenCV の __`findContours()`__ 関数を使用すると、二値画像内の物体の輪郭を抽出することができます。
二値画像以外のデータを渡すこともできますが、画素値は 0 とそれ以外（1 以上）の値でしか区別しないので、実質的に二値画像（白黒画像）のデータを渡すことになります。

```python
cv2.findContours(image, mode, method) -> contours, hierarchy
```

- 引数:
  - `image` ... 入力画像（8bit、1 チャネル）。二値化（0 or 1 以上）されたデータである必要があります。
  - `mode` ... 輪郭の検索モード
    - `cv2.RETR_EXTERNAL` ... 全景の外側の輪郭だけを抽出します。入れ子になった物体の輪郭は抽出しません。(retrieves only the extreme outer contours. It sets hierarchy[i][2]=hierarchy[i][3]=-1 for all the contours.)
    - `cv2.RETR_LIST` ... retrieves all of the contours without establishing any hierarchical relationships.
    - `cv2.RETR_CCOMP` ... retrieves all of the contours and organizes them into a two-level hierarchy. At the top level, there are external boundaries of the components. At the second level, there are boundaries of the holes. If there is another contour inside a hole of a connected component, it is still put at the top level.
    - `cv2.RETR_TREE` ... retrieves all of the contours and reconstructs a full hierarchy of nested contours.
    - `cv2.RETR_FLOODFILL`
  - `method` ... 輪郭の近似方法
    - `cv2.CHAIN_APPROX_NONE` ... stores absolutely all the contour points. That is, any 2 subsequent points (x1,y1) and (x2,y2) of the contour will be either horizontal, vertical or diagonal neighbors, that is, max(abs(x1-x2),abs(y2-y1))==1.
    - `cv2.CHAIN_APPROX_SIMPLE` ... compresses horizontal, vertical, and diagonal segments and leaves only their end points. For example, an up-right rectangular contour is encoded with 4 points.
    - `cv2.CHAIN_APPROX_TC89_L1` ... applies one of the flavors of the Teh-Chin chain approximation algorithm
    - `cv2.CHAIN_APPROX_TC89_KCOS` ... applies one of the flavors of the Teh-Chin chain approximation algorithm
- 戻り値:
  - `contours` ... 抽出した輪郭のリスト。各輪郭は頂点座標の NumPy 配列として表現されます。
  - `hierarchy` ... 階層構造のリスト。

