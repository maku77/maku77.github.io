---
title: "コマンドライン引数を取得する"
date: "2013-10-20"
---

コマンドライン引数の数、値を取得する
====

コマンドライン引数で与えられた値は、`sys.argv` でリストの形で参照することができます。
インデックス 0 の位置 (`sys.argv[0]`) には、python コマンドで指定したスクリプト自身の名前が格納されています。

#### sample.py
```python
import sys

print(sys.argv)
print(len(sys.argv))
```

#### 実行例
```
$ python sample.py aaa bbb ccc
['sample.py', 'aaa', 'bbb', 'ccc']
4
```

`sys.argv` の先頭には、指定したスクリプト自身の名前が入っているので、パラメータ数は少なくとも 1 以上になることに注意してください。

下記は、コマンドライン引数を 1 つも指定せずにスクリプトを実行した場合に usage メッセージを表示して終了するサンプルです。

#### sample.py
```python
import sys
import os

if len(sys.argv) < 2:
    print('Usage: python %s <host>' % os.path.basename(sys.argv[0]))
    sys.exit(1)
```

#### 実行例
```
$ python sample.py
Usage: python sample.py <host>
```


スクリプト名を除いたパラメータを取得する
====

`sys.argv[0]` にはスクリプト名が含まれています。
純粋にパラメータだけを取り出したい場合は、以下のようにインデックス 1 以降を取り出してしまうと分かりやすくなります。

```python
import sys

args = sys.argv[1:]
for x in args:
    print(x)
```


ハッカー流のコマンドライン引数の処理方法
====

下記は、高校生ハッカーが主人公のドラマ『ブラッディ・マンデイ』の中で、主人公であるファルコンがやってた方法です（＊_＊;

強引に `sys.argv` をスライスしてしまって、正しい数のパラメータを指定されていない場合に、`ValueError` 例外を発生させてしまうやり方です。なるほどねぇ。。

#### Python 2 の場合（ファルコン流）
```python
#!/usr/bin/env python
import sys

try: host,frm,to=sys.argv[1:4]
except ValueError:
    print "Usage: %s <host> <from> <to>" % (sys.argv[0])
    sys.exit(1)
```

#### Python 3 の場合
```python
#!/usr/bin/env python
import sys

try: host,frm,to=sys.argv[1:4]
except ValueError:
    print("Usage: {0} <host> <from> <to>".format(sys.argv[0]))
    sys.exit(1)
```

