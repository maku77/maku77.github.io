---
title: "Python でハッシュ値 (MD5/SHA-1/SHA-256/SHA-512) を求める (`hashlib`)"
url: "p/gn4cn3s/"
date: "2023-06-09"
tags: ["Python"]
---

Python の [hashlib](https://docs.python.org/ja/3/library/hashlib.html) 標準ライブラリを使用すると、いろいろなタイプのハッシュ値を求めることができます。

バイトデータのハッシュ値を求める
----

あるバイトデータのハッシュ値を求めるには、__`hashlib.sha1`__ や __`hashlib.sha256`__ などのコンストラクタでハッシュオブジェクトを生成し、__`hexdigest`__ メソッドで 16 進数表記のハッシュ値を取得します。
次の例では、MD5、SHA-1、SHA-256 アルゴリズムを使って、`b"HelloWorld"` というバイトデータのハッシュ値を計算しています。

{{< code lang="python" title="バイトデータのハッシュ値を求める" >}}
import hashlib

print(hashlib.md5(b"HelloWorld").hexdigest())
print(hashlib.sha1(b"HelloWorld").hexdigest())
print(hashlib.sha256(b"HelloWorld").hexdigest())
{{< /code >}}

{{< code title="実行結果" >}}
68e109f0f40ca72a15e05cc22786f8e6
db8ac1c259eb89d4a131b253bacfca5f319d54f2
872e4e50ce9990d8b041330c47c9ddd11bec6b503ae9386a99da8584e9bb12c4
{{< /code >}}

ハッシュ計算に使用するバイトデータは、__`update`__ メソッドで追加していくことができます。

{{< code lang="python" hl_lines="2 3 8" >}}
hash = hashlib.sha1()
hash.update(b"Hello")
hash.update(b"World")
print(hash.hexdigest())  # => db8ac1c259eb89d4a131b253bacfca5f319d54f2

# 以下のようにバイトデータを結合してから update しても結果は同じ
hash = hashlib.sha1()
hash.update(b"Hello" + b"World")
print(hash.hexdigest())  # => db8ac1c259eb89d4a131b253bacfca5f319d54f2
{{< /code >}}


文字列のハッシュ値を求める
----

文字列のハッシュ値を求めるには、エンコーディング形式を指定して、どのようなバイトデータとして扱うかを明確にする必要があります。
次の例では、文字列の __`encode`__ メソッドで UTF-8 形式のバイトデータを取得しています。

{{< code lang="python" hl_lines="4" title="文字列のハッシュ値を求める" >}}
import hashlib

str = "HelloWorld"
bytes = str.encode(encoding="utf-8")

print(hashlib.md5(bytes).hexdigest())
print(hashlib.sha1(bytes).hexdigest())
print(hashlib.sha256(bytes).hexdigest())
{{< /code >}}

{{< code title="実行結果" >}}
68e109f0f40ca72a15e05cc22786f8e6
db8ac1c259eb89d4a131b253bacfca5f319d54f2
872e4e50ce9990d8b041330c47c9ddd11bec6b503ae9386a99da8584e9bb12c4
{{< /code >}}


ファイルのハッシュ値を求める
----

以下の `file_sha256` 関数は、指定したファイルの内容からハッシュ値を求めています。
ハッシュ値の計算にはバイトデータが必要なので、ファイルオープン時にバイトモード (__`"b"`__) を指定するのがポイントです。

```python
import hashlib

def file_sha256(filename: str) -> str:
    """
    ファイル内容の SHA-256 ハッシュ値を計算し、16 進数文字列として取得します。
    """
    with open(filename, mode="rb") as f:
        binary = f.read()
        return hashlib.sha256(binary).hexdigest()

        # Python 3.11 以降では次のようにも記述できます
        # return hashlib.file_digest(f, "sha256").hexdigest()

print(file_sha256("sample.py"))
```

{{< code title="実行結果" >}}
d727c62744e193aa6a0d0728e2dc78c0221bd77da2599dc6f86d17a46aa0bea4
{{< /code >}}


使用可能なアルゴリズムの一覧
----

### alogrithms_guaranteed （必ず使えるアルゴリズム）

すべてのプラットフォーム使用できることが保証されているアルゴリズムの一覧は、__`hashlib.algorithms_guaranteed`__ で参照できます。

```python
import hashlib

for name in sorted(hashlib.algorithms_guaranteed):
    print(name)
```

{{< code title="実行結果（Python 3.10 の場合）" >}}
blake2b
blake2s
md5
sha1
sha224
sha256
sha384
sha3_224
sha3_256
sha3_384
sha3_512
sha512
shake_128
shake_256
{{< /code >}}

ここに列挙された名前を使って、`hashlib.new("sha256")` のようにハッシュオブジェクトを生成することができます。
ただし、`hashlib.sha256()` のような、専用のファクトリー関数が定義されている場合はそちらを使った方が効率的です。

### algorithms_available （現在のシステムで使えるアルゴリズム）

現在のシステムで使用できることができるアルゴリズムの一覧は、__`hashlib.algorithms_available`__ で参照できます。
前述の `hashlib.algorithms_guaranteed` に含まれているアルゴリズムは、この一覧にも必ず含まれています。

```python
for name in sorted(hashlib.algorithms_available):
    print(name)
```

{{< code title="実行結果" >}}
blake2b
blake2s
md4
md5
md5-sha1
mdc2
ripemd160
sha1
sha224
sha256
sha384
sha3_224
sha3_256
sha3_384
sha3_512
sha512
sha512_224
sha512_256
shake_128
shake_256
sm3
whirlpool
{{< /code >}}

`hashlib.algorithms_available` にしか含まれていないアルゴリズムを抽出したい場合は、次のようにセット演算を行います（参考: [set オブジェクトで集合演算を行う](/p/h3jqpp9/)）。

```python
import hashlib

only_in_available = hashlib.algorithms_available - hashlib.algorithms_guaranteed
for name in sorted(only_in_available):
    print(name)
```

{{< code title="実行結果" >}}
md4
md5-sha1
mdc2
ripemd160
sha512_224
sha512_256
sm3
whirlpool
{{< /code >}}


関連リンク
----

- [HMAC-SHA256 コードを生成する｜まくろぐ](https://maku.blog/p/uqhbb5p/)

