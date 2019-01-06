---
title: "Python スクリプトの中で Python のバージョンを確認する (sys.version)"
date: "2011-01-28"
---

`sys` パッケージを使用すると、Python 処理系のバージョンをいろいろな形式で取得することができます。

```python
>>> import sys
>>> sys.version
'3.1.1 (r311:74483, Aug 17 2009, 17:02:12) [MSC v.1500 32 bit (Intel)]'

>>> sys.version_info
sys.version_info(major=3, minor=1, micro=1, releaselevel='final', serial=0)

>>> sys.hexversion
50397680

>>> hex(sys.hexversion)
'0x30101f0'
```

#### 例: Python 2.4 以上を使っていない場合にプログラムの実行を中止する

```python
import sys

if sys.hexversion < 0x02040000:
    print >> sys.stderr, "Python 2.4 or newer is required."
    sys.exit(1)
```

