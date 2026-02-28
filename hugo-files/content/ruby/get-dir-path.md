---
title: "Rubyメモ: 様々なディレクトリのパスを取得する／パスを加工する"
url: "p/vynqmh5/"
date: "2010-05-07"
tags: ["ruby"]
aliases: ["/ruby/get-dir-path.html"]
---

ディレクトリパスを取得するためのメソッド
====

| 取得したいディレクトリ | メソッド |
| ---------------------- | -------- |
| カレントディレクトリ | `Dir.pwd`<br>`Dir.getwd` |
| ホームディレクトリ | `Dir.home`<br>`ENV['HOME']` |


パス文字列を加工するためのメソッド
====

| 操作 | メソッド |
| ---- | -------- |
| パスの結合 | `File.join(path1, path2)` |
| パス → ディレクトリ名 | `File.dirname(path)` |
| パス → ファイル名     | `File.basename(path)` |
| パス → ファイル名<br>（指定したサフィックスを除いたもの） | `File.basename(path, '.rb')` |
| パス → 拡張子名 | `File.extname(path)` |
| 相対パス → 絶対パス | `File.expand_path(filename)` ※1 |

※1 … スクリプト実行時のカレントディレクトリを単純にプレフィックスとして付加するだけなので、ディレクトリを再帰処理するような場合は、`File.expand_path` は正しく動作しません。このような場合は、`File.join(dir_path, filename)` のように自力で結合すれば OK です。

