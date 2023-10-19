---
title: "Matplotlib の出力設定チートシート"
url: "p/ghch694/"
date: "2023-10-19"
tags: ["Matplotlib", "チートシート"]
draft: true
---

Matplotlib の出力設定のまとめです。


プロットサイズ／解像度／背景色
----

| コード | 説明 |
| ---- | ---- |
| `plt.figure(figsize=(8, 6))` | プロットのサイズ |
| `plt.figure(dpi=100)` | DPI (ドット数/インチ) |
| `plt.gca().set_facecolor("#e5e5e5")` | プロット領域の背景色 |
| `fig, ax = plt.subplots(2, 2)` | サブプロットの設定 |
| `plt.style.use("ggplot")` | プロットスタイルを変更 |
| `plt.style.use("default")` | プロットスタイルをデフォルトに戻す |


タイトル
----

| コード | 説明 |
| ---- | ---- |
| `plt.title("タイトル", pad=20)` | タイトルの位置調整 |


ラベルと凡例
----

| コード | 説明 |
| ---- | ---- |
| `plt.xlabel("X軸ラベル")` | ラベルを表示する |
| `plt.xlabel("X軸ラベル", labelpad=10)` | ラベルの位置調整 |
| `plt.legend()` | 凡例を描画 |
| `plt.legend(loc="upper right")` | 凡例を描画＋位置指定 |


軸
----

| コード | 説明 |
| ---- | ---- |
| `plt.xlim(0, 10)` | X軸の範囲設定 |
| `plt.xticks([0, 5, 10], ["Low", "Mid", "High"])` | X軸の目盛りの設定 |
| `plt.xticks(rotation=45)` | X軸のラベルの回転 |


グリッド線
----

| コード | 説明 |
| ---- | ---- |
| `plt.grid(True)` | グリッド線の表示 |
| `plt.grid(linestyle="--", linewidth=0.5)` | グリッド線のスタイル |

線とマーカー
----

| コード | 説明 |
| ---- | ---- |
| `plt.plot(x, y, linewidth=2)` | 線の太さ |
| `plt.plot(x, y, linestyle="--")` | 線のスタイル |
| `plt.scatter(x, y, marker="o")` | マーカーの種類 |
| `plt.scatter(x, y, s=50)` | マーカーサイズ |


フォント
----

| コード | 説明 |
| ---- | ---- |
| `plt.rcParams["font.size"] = 14` | 全体のフォントサイズ（タイトル、ラベル、凡例など） |
| `plt.rcParams["font.family"] = "sans-serif"` | フォントの種類 |
| `plt.title("タイトル", fontsize=16)` | タイトルのフォントサイズ |
| `plt.xlabel("X軸ラベル", fontsize=12)` | X軸ラベルのフォントサイズ |
| `plt.xticks(fontsize=10)` | X軸の目盛りのフォントサイズ |
| `plt.legend(fontsize=10)` | 凡例のフォントサイズ |


テキストと注釈
----

| コード | 説明 |
| ---- | ---- |
| `plt.text(2, 5, "テキスト")` | プロット上にテキスト表示 |
| `plt.annotate("注釈", xy=(2, 5), xytext=(3, 8), arrowprops=dict(arrowstyle="->"))` | プロットに注釈を追加 |


カラーマップとカラーバー
----

| コード | 説明 |
| ---- | ---- |
| `plt.set_cmap("coolwarm")` | カラーマップの設定 |
| `plt.colorbar()` | カラーバーの表示 |


ヒストグラム
----

| コード | 説明 |
| ---- | ---- |
| `plt.hist(data, bins=20)` | ヒストグラムのビン数 |
| `plt.hist(data, alpha=0.5)` | ヒストグラムの透明度 |

