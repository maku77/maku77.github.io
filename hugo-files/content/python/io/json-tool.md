---
title: "Python のワンライナーコマンドで JSON ファイルを整形する (json.tool)"
url: "p/an8o6m4/"
date: "2014-11-28"
lastmod: "2021-07-24"
aliases: /python/io/json-tool.html
---

json.tool の基本
----

Python 2.6 以降には `json` モジュールが標準搭載されていて、これはコマンドラインからも便利に利用することができます。
下記は、[json.tool モジュール](https://docs.python.org/ja/3/library/json.html#module-json.tool) を使って JSON 形式のテキスト出力を、きれいに整形しなおして出力するワンライナーの例です。

```console
$ python -mjson.tool input.json

# あるいは

$ cat input.json | python -mjson.tool
```

ここでは、次のような JSON ファイルを使って試してみます。
スペースはわざとぐちゃぐちゃに入れています。

{{< code lang="json" title="input.json（入力ファイル）" >}}
{"ccc"  : 300, "aaa":100,  "bbb":200, "data": [1,2,3]}
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ python -mjson.tool input.json
{
    "ccc": 300,
    "aaa": 100,
    "bbb": 200,
    "data": [
        1,
        2,
        3
    ]
}
{{< /code >}}


キー名でソートして出力する (--sort-keys)
----

Python 3.7 以降で、キーの順序は入力ファイルのものが保持されるようになりました（内部的に辞書オブジェクト (`dict`) のキー挿入順序が保持されるようになりました）。
キー順序をアルファベット順にソートして出力したいときは、__`--sort-keys`__ オプションを指定します。

{{< code lang="console" title="キー順にソートする" >}}
$ python -mjson.tool --sort-keys input.json
{
    "aaa": 100,
    "bbb": 200,
    "ccc": 300,
    "data": [
        1,
        2,
        3
    ]
}
{{< /code >}}


インデントサイズを指定する (--indent=N)
----

バージョン 3.9 以降では、__`--indent`__ オプションで、インデントサイズの調整などもできるようになっています。

{{< code lang="console" title="インデントサイズを 2 にする" >}}
$ python -mjson.tool --indent=2 input.json
{
  "ccc": 300,
  "aaa": 100,
  "bbb": 200,
  "data": [
    1,
    2,
    3
  ]
}
{{< /code >}}


インデントや改行なしで出力する (--compact)
----

余計なスペースや改行を取り除いて、1 行でコンパクトに出力するには、__`--compact`__ オプションを使用します。
プログラムへの入力にしか使わない JSON データは、このように圧縮されていると効率的です。

{{< code lang="console" title="コンパクトに出力する" >}}
$ python -mjson.tool --compact input.json
{"ccc":300,"aaa":100,"bbb":200,"data":[1,2,3]}
{{< /code >}}

