---
title: Windows で pydoc コマンドを使用できるようにする
created: 2016-12-08
---

`pydoc` コマンドを使用すると、下記のように Python の API ドキュメントを手軽に参照できます。

```
$ pydoc list.sort
Help on method_descriptor in list:

list.sort = sort(...)
    L.sort(key=None, reverse=False) -> None -- stable sort *IN PLACE*
```

Windows では Python をインストールしただけでは `pydoc` コマンドは使用できませんが、実装自体は `Lib/pydoc.py` というスクリプトとしてインストールされます。
下記のようなバッチファイルを `python.exe` と同じディレクトリに作成しておけば、Windows でも `pydoc` コマンドを使用できるようになります。

#### pydoc.cmd

```cmd
@python %~dp0\Lib\pydoc.py %*
```

#### 実行例

```
C:\> pydoc dict.update
Help on method_descriptor in dict:

dict.update = update(...)
    D.update([E, ]**F) -> None.  Update D from dict/iterable E and F.
    If E is present and has a .keys() method, then does:  for k in E: D[k] = E[k]
    If E is present and lacks a .keys() method, then does:  for k, v in E: D[k] = v
    In either case, this is followed by: for k in F:  D[k] = F[k]
```

