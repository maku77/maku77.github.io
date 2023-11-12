---
title: "OpenCV チートシート"
url: "p/dgkrqjo/"
date: "2023-11-10"
tags: ["OpenCV", "cheatsheet"]
draft: true
---

| カテゴリ | コード | 説明 |
| :--: | ---- | ---- |
| I/O | `cv2.imread('a.png')` | 画像ファイルの読み込み |
| I/O | `cv2.imwrite('a.png', img)` | PNG ファイルへの出力 |
| I/O | `cv2.imwrite('a.jpg', img)` | JPG ファイルへの出力 |
| I/O | `cv2.imwrite('a.jpg', img, [cv2.IMWRITE_JPEG_QUALITY, 50])` | JPG ファイルへの出力 + クオリティ指定（0〜100の範囲。デフォルトは 95)。 |
| 編集 | `cv2.threshold()` | [閾値により画素値を置換](/p/i26mc77/) |
| 編集 | `cv2.findContours()` | [二値画像の輪郭抽出](/p/ocef2kr/) |
| 編集 | `cv2.rectangle()` | 矩形の描画 |
| イベント | `cv2.waitKey(0)` | キー入力があるまで待機 |
| イベント | `cv2.destroyAllWindow()` | 全ウィンドウを破棄する |

