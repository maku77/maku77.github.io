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
| `$ pip install torch` | PyTorch のインストール |
| `torch.__version__` | PyTorch のバージョン |
| `torch.cuda.is_available()` | CUDA が有効 or 無効 |
| `torch.manual_seed(seed)` | [乱数シードの固定](#seed) |

### 乱数シードの固定 {#seed}

機械学習などで乱数を使っている場合は、各種乱数ライブラリ用のシードを固定することで、毎回同じ結果を得ることができます。
PyTorch 用のランダムシード設定関数は `torch.manual_seed(seed)` ですが、下記のように各種ライブラリ用のランダムシードをまとめて設定してしまうのが慣例となっています。

```python
import torch
import random
import numpy as np

def set_random_seeds(seed):
    """各種ライブラリのランダムシードを設定します。"""

    random.seed(seed)  # Python のランダムシード
    np.random.seed(seed)  # NumPy のランダムシード
    torch.manual_seed(seed)  # PyTorch のランダムシード
    if torch.cuda.is_available():
        torch.cuda.manual_seed_all(seed)  # CuDNN (CUDA Deep Neural Network) のランダムシード

# 使用例
set_random_seeds(1234)
print(torch.rand(3))  # 必ず [0.0290, 0.4019, 0.2598] になる
```


テンソルの情報
----

| コード | 説明 |
| ---- | ---- |
| `t.shape`<br>`t.size()` | [テンソルの形状（各次元の要素数のタプル）](#shape) |
| `t.shape[n]`<br>`t.size()[n]` | [テンソルの n 次元目の要総数](#shape) |
| `t.dtype` | テンソルのデータ型 |
| `t.storage_offset()` | 最初の要素が配置されたストレージのインデックス |
| `t.stride()` | 各次元の次の要素を得る（各次元のインデックスを 1 つ進める）ために必要なストレージ内での移動量 |
| `t.is_contiguous()` | ストレージ内で連続して配置されているか |
| `id(t1.storage()) == id(t2.storage())` | 2 つのテンソルが同じストレージを共有しているか調べる |

### テンソルの形状を調べる {#shape}

```python
t = torch.rand(2, 3)

print(t.shape)     # torch.Size([2, 3])
print(t.shape[0])  # 2
print(t.shape[1])  # 3
```


テンソルの作成
----

| コード | 説明 |
| ---- | ---- |
| `t = torch.tensor([1.0, 2.0, 3.0])` | Python のリストから 1 次元テンソルを作成 |
| `t = torch.tensor([1.0, 2.0, 3.0], [4.0, 5.0, 6.0]])` | Python のリストから 2 次元テンソルを作成（2行3列） |
| `t = torch.zeros(3)` | すべての要素が 0 の 1 次元テンソルを作成 |
| `t = torch.zeros(3, dtype=torch.int32)` | すべての要素が 0 の 1 次元テンソルを作成（データタイプを指定） |
| `t = torch.zeros(2, 3)` | すべての要素が 0 の 2 次元テンソルを作成（2行3列） |
| `t = torch.ones(3)` | すべての要素が 1 の 1 次元テンソルを作成 |
| `t = torch.ones(2, 3)` | すべての要素が 1 の 2 次元テンソルを作成（2行3列） |
| `t = torch.arange(0, 5)` | の連番（1〜4）の 1 次元テンソルを作成 |
| `t = torch.rand(3)` | ランダム値（0.0〜1.0）の 1 次元テンソルを作成 |
| `t = torch.rand(2, 3)` | ランダム値（0.0〜1.0）の 2 次元テンソルを作成（2行3列） |
| `t = torch.randn(3)` | 正規分布（平均0、分散1）のランダム値の 1 次元テンソルを作成 |
| `t = torch.randn(2, 3)` | 正規分布（平均0、分散1）のランダム値の 2 次元テンソルを作成（2行3列） |
| `t = torch.stack(t1, t2, t3)` | [複数のテンソルを結合（スタックするので一次元増える）](#stack) |


### 複数のテンソルを結合する {#stack}

__`torch.tensor()`__ 関数を使用すると、複数のテンソルを新しい次元で結合できます。

```python
import torch

t1 = torch.tensor([1, 2, 3])
t2 = torch.tensor([4, 5, 6])

print(torch.stack((t1, t2)))
# tensor([[1, 2, 3],
#         [4, 5, 6]])
```

`dim=1` オプション（デフォルトは `dim=0`）を指定することで、結合する方向を切り替えることができます（Python の `zip()` 関数のようなイメージ）。

```python
print(torch.stack((t1, t2), dim=1))
# tensor([[1, 4],
#         [2, 5],
#         [3, 6]])
```


テンソルの変換
----

| コード | 説明 |
| ---- | ---- |
| `t2 = t.double()`<br>`t2 = t.to(torch.double)`<br>`t2 = t.to(dtype=torch.double)` | [データ型の変換](#data-type-conversion) |
| `t_gpu = t.cuda()`<br>`t_gpu = t.to(device="cuda")` | [CPU 上のテンソルを GPU にコピー](#to-cuda) |
| `t = t_gpu.cpu()`<br>`t = t_gpu.to(device="cpu")` | GPU 上のテンソルを CPU にコピー |
| `t2 = t.view(3, 2)` | テンソルの変形（3 行 2 列にする） |
| `t2 = t.view(-1)` | テンソルの変形（1 次元にする） |
| `t2 = t.view(-1, 8, 8)` | テンソルの変形（1 次元目のサイズを自動計算） |
| `t = torch.from_numpy(np_a)` | NumPy 配列 → テンソル（メモリは共有） |
| `np_a = t.numpy()` | テンソル → NumPy 配列（メモリは共有） |
| `s = t.storage()` | テンソルのストレージを参照（型は `TypedStorage`） |
| `t2 = t.clone()` | テンソルのコピー（Storage のメモリ領域のコピー） |
| `t2 = t.t()`<br>`t2 = t.transpose(0, 1)` | [転置行列](#t) |

### データ型の変換 {#data-type-conversion}

テンソルの `double()` メソッドや `int()` メソッドを使うと、データ型を変換することができます。
次の例では、`torch.rand()` で作成したランダムなテンソルを変換しています。

```python
import torch

torch.manual_seed(1234)

t = torch.rand(3)  # デフォルトでは torch.float32 型になる
t2 = t.double()    # torch.float64 型への変換
t3 = t.int()       # torch.int32 型への変換

print(t.dtype)   # torch.float32
print(t2.dtype)  # torch.float64
print(t3.dtype)  # torch.int32

print(t)   # tensor([0.0290, 0.4019, 0.2598])
print(t2)  # tensor([0.0290, 0.4019, 0.2598], dtype=torch.float64)
print(t3)  # tensor([0, 0, 0], dtype=torch.int32)
```

`torch.double` 型は `torch.float64` 型のエイリアスです（`float64()` という関数はないようです）。

### GPU 上にテンソルを置く {#to-cuda}

CPU 上に配置されたテンソルデータを GPU 上にコピーするには、__`t.cuda()`__ メソッドを使用します。
他の変換（データ型など）も行う場合は、`t.to(device="cuda")` を使うと便利です。

{{< code lang="python" hl_lines="4" >}}
import torch

t = torch.tensor([1, 2])
t_gpu = t.cuda()  # t.to(device="cuda")

# GPU 上に保持されているかを確認
print(t_gpu)
print(t_gpu.device)
{{< /code >}}

{{< code title="実行結果" >}}
tensor([1., 2.], device='cuda:0')
cuda:0
{{< /code >}}

あるいは、テンソルの生成時に `device="cuda"` オプションを指定します。

```python
t_gpu = torch.tensor([1, 2], device="cuda")
```

複数の GPU がある場合は、インデックスで指定することができます（デフォルトは 0）。

```python
t_gpu = t.cuda(0)
t_gpu = t.to(device="cuda:0")
```

### 転置行列 {#t}

__`t.t()`__ メソッドで転置行列を生成できます。
下記の例では、2 行 3 列のテンソルを転置して 3 行 2 列のテンソルを生成しています。

```python
import torch

t = torch.tensor([[1, 2, 3], [4, 5, 6]], dtype=torch.int64)  # 2 行 3 列のテンソル
print(t)
print(t.t())
```

{{< code title="実行結果" >}}
tensor([[1, 2, 3],
        [4, 5, 6]])
tensor([[1, 4],
        [2, 5],
        [3, 6]])
{{< /code >}}

__`t.tranpose()`__ メソッドを使うと、任意の次元を入れ替えることができます。
2 次元テンソルであれば、下記の結果は `t.t()` と同じになります。

```python
t2 = t.transpose(0, 1)  # t.t() と同じ
```


部分的なデータ（行や列）の抽出
----

| コード | 説明 |
| ---- | ---- |
| `t[row_index]` | 1 行の抽出 |
| `t[start:end]` | 複数行の抽出 |
| `t[:, col_index]` | 1 列の抽出 |
| `t[:, [col1_index, col2_index, col3_index]]` | 複数列の抽出 |
| `t[0][0].item()` | Tensor 型ではないスカラー値を取得 |


シリアライズ・デシリアライズ
----

| コード | 説明 |
| ---- | ---- |
| `torch.save(t, "./data.t")` | [テンソルデータをファイルに保存](#save) |
| `t = torch.load("./data.t")` | [ファイルからテンソルデータをロード](#load) |
| `torch.save(model.state_dict(), "./model.pth")` | 学習済みモデル（パラメーター）の保存 |
| `model.load_state_dict(torch.load("./model.pth"))` | 学習済みモデル（パラメーター）のロード |

### ファイルへのテンソルの保存 {#save}

{{< code lang="python" title="data.t ファイルに保存" hl_lines="2" >}}
t = torch.rand(2, 3)  # 適当なテンソルを生成
torch.save(t, "./data.t")  # ファイルに保存

# あるいは次のようにしても OK
with open("./datat", "wb") as f:
    troch.save(t, f)
{{< /code >}}

### ファイルからテンソルをロード {#load}

{{< code lang="python" title="data.t ファイルをロード" hl_lines="1" >}}
t = torch.load("./data.t")

# あるいは次のようにしても OK
with open("./data.t", "rb") as f:
    t = torch.load(f)
{{< /code >}}


演算、その他
----

| コード | 説明 |
| ---- | ---- |
| `t2 = t.abs()` / `t.abs_()` | 各要素を絶対値にする |
| `t2 = t.sqrt()` / `t.sqrt_()` | 各要素を平方根にする |
| `t.mean(dtype=torch.float64)` | 平均値 |
| `t.std()` | 標準偏差 |
| `t.var()` | 不偏分散（≠ 分散） |
| `t.zero_()` | すべての要素を 0 にする |
| `t2 = t.contiguous()` | 連続配置されたテンソルを取得（ストレージの再配置） |

`t.abs_()` のように末尾にアンダースコア (`_`) の付いたメソッドは、__インプレース操作__ を表しており、自分自身のテンソルの内容を変更します。


メモ
----

- ストレージ (`torch.Storage`) インスタンス
  - 数値を一次元配列の形で保持するメモリチャンクで、PyTorch の `Tensor` は、この `Storage` インスタンスを指し示すビューです。`Tensor` インスタンス経由でアクセスすることで、多次元のデータ形状を表現することができるようになっています。

