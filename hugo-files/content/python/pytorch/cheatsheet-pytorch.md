---
title: "PyTorch チートシート"
url: "p/c8rd5tg/"
date: "2023-12-03"
tags: ["pytorch"]
draft: true
---

環境
----

| コード | 説明 |
| ---- | ---- |
| `torch.__version__` | PyTorch のバージョン |
| `torch.cuda.is_available()` | CUDA が有効 or 無効 |

テンソルの作成
----

| コード | 説明 |
| ---- | ---- |
| `t = torch.zeros(3)` | すべての要素が 0 の 1 次元テンソルを作成 |
| `t = torch.zeros(3, dtype=torch.int32)` | すべての要素が 0 の 1 次元テンソルを作成（データタイプを指定） |
| `t = torch.zeros(2, 3)` | すべての要素が 0 の 2 次元テンソルを作成（2行3列） |
| `t = torch.ones(3)` | すべての要素が 1 の 1 次元テンソルを作成 |
| `t = torch.ones(2, 3)` | すべての要素が 1 の 2 次元テンソルを作成（2行3列） |
| `t = torch.tensor([1.0, 2.0, 3.0])` | Python のリストから 1 次元テンソルを作成 |
| `t = torch.tensor([1.0, 2.0, 3.0], [4.0, 5.0, 6.0]])` | Python のリストから 2 次元テンソルを作成（2行3列） |
| `t = torch.rand(3)` | ランダム値（0.0〜1.0）の 1 次元テンソルを作成 |
| `t = torch.rand(2, 3)` | ランダム値（0.0〜1.0）の 2 次元テンソルを作成（2行3列） |
| `t = torch.randn(3)` | 正規分布（平均0、分散1）のランダム値の 1 次元テンソルを作成 |
| `t = torch.randn(2, 3)` | 正規分布（平均0、分散1）のランダム値の 2 次元テンソルを作成（2行3列） |


メモ
----

- `torch.Storage` インスタンス
  - 数値を一次元配列の形で保持するメモリチャンクで、PyTorch の `Tensor` は、この `Storage` インスタンスを指し示すビューです。`Tensor` インスタンス経由でアクセスすることで、多次元のデータ形状を表現することができるようになっています。
