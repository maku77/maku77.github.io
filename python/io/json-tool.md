---
title: "コマンドラインで JSON 形式のテキストファイルを整形する (json.tool)"
date: "2014-11-28"
---

Python 2.6 以降には json モジュールが標準搭載されていて、これはコマンドラインからも便利に利用することができます。
下記は、**`json.tool`** モジュールを使って JSON 形式のテキスト出力を、きれいに整形しなおして出力する例です。

```sh
$ cat json.txt > python -mjson.tool
```

