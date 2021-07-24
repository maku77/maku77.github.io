---
title: "コマンドラインで JSON ファイルを整形する (json.tool)"
date: "2014-11-28"
lastmod: "2021-07-24"
---

Python 2.6 以降には json モジュールが標準搭載されていて、これはコマンドラインからも便利に利用することができます。
下記は、[json.tool モジュール](https://docs.python.org/ja/3/library/json.html#module-json.tool) を使って JSON 形式のテキスト出力を、きれいに整形しなおして出力する例です。

```sh
$ python -mjson.tool input.json

# あるいは

$ cat input.json | python -mjson.tool
```

ここでは、次のような入力ファイル (`input.json`) を使って試してみます。
スペースはわざとぐちゃぐちゃに入れています。

```json
{"ccc"  : 300, "aaa":100,  "bbb":200, "data": [1,2,3]}
```

#### 実行例

```
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
```

Python 3.7 以降で、キーの順序は入力ファイルのものが保持されるようになりました（内部的に辞書オブジェクト (`dict`) のキー挿入順序が保持されるようになりました）。
キー順序をアルファベット順にソートして出力したいときは、__`--sort-keys`__ オプションを指定します。

```
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
```

バージョン 3.9 以降では、インデントサイズの調整などもできるようになっています (`--indent`)。

