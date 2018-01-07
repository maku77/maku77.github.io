---
title: 拡張子に関連付けられたアプリケーションでファイルを開く (os.system)
date: "2009-01-05"
---

`os.system()` に任意のコマンドを渡すと、その OS のコマンドライン端末からコマンドを実行したのと同様の効果を得られます。
下記のようにすれば、.txt ファイルを関連付けられたアプリケーションで開くことができます。

#### Windows の場合

```python
import os
os.system('sample.txt')
```

#### Mac OSX の場合

```python
import os
os.system('open sample.txt')
```

