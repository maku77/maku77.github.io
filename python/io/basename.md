---
title: パスからファイル名だけを取り出す (basename)
created: 2012-06-04
---

`os.path.basename` を使用して、パスからベースネーム（ファイル名の部分）を抜き出すことができます。

```python
import os.path
path = os.path.basename('/foo/bar/file.txt')    #=> 'file.txt'
```

最後のスラッシュ（あるいはバックスラッシュ）より後ろの文字列を取得していると考えればよいです。
Windows のドライブ名などもちゃんと削除してくれます。

```
>>> os.path.basename('C:\\')
''
>>> os.path.basename('C:\\file.txt')
'file.txt'
>>> os.path.basename('C:\\dir\\file.txt')
'file.txt'
>>> os.path.basename('file.txt')
'file.txt'
>>> os.path.basename('aaa/file.txt')
'file.txt'
>>> os.path.basename('/aaa/file.txt')
'file.txt'
>>> os.path.basename('/aaa/bbb ccc ddd')
'bbb ccc ddd'
>>> os.path.basename('/aaa/bbb/ccc/')
''
```

`__file__` と組み合わせて使用すれば、**実行中のスクリプトのファイル名だけを取り出す**ことができます。

```python
import os.path
myfile = os.path.basename(__file__)    #=> 'sample.py'
```

