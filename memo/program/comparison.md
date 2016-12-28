---
title: スクリプト言語の文法比較など
created: 2010-05-07
---

制御構文
----

|      | Bash | PHP  | Python | Ruby | Perl |
| ---- | ---- | ---- | ------ | ---- | ---- |
| if-else | [Bash](/shell/syntax/if.html) | [PHP](/php/syntax/if.html) | [Python](/python/syntax/if.html)  |  |  |
| switch-case |  |  | [Python](/python/syntax/switch.html) |  |  |
| ループ |  | [PHP](/php/syntax/loop.html) | `for x in a:` | [Ruby](/ruby/syntax-loop.html) |  |
| クラス |  | [PHP](/php/syntax/class.html) | [Python](/python/syntax/class.html) |  |  |
| 文字列リテラル |  |  |  [Python](/python/syntax/string-literal.html) |  |  |
| ヒアドキュメント | | [PHP](/php/syntax/here-document.html) | [Python](/python/syntax/here-document.html) | [Ruby](/ruby/here-document.html) | [Perl](/perl/string/here-document.html) |
| 正規表現 |  |  | [Python](/python/regexp.html) |  |  |


入出力
----

|      | Python | Ruby | Perl |
| ---- | ------ | ---- | ---- |
| 標準出力（改行あり） | `print('Hello')` | `puts "Hello"`  | `print "Hello\n"` |
| 標準出力（改行なし） | `print('Hello', end='')` | `print "Hello"` | `print "Hello"`  |
| 標準エラー | `print('Hello', file=sys.stderr)`  | `STDERR.puts "Hello"`  | `print STDERR "Hello\n"` |
| 標準入力 | `line = input('name: ')` | `line = gets` | `line = <STDIN>` |
| ファイルオープン | `with open('fname') as f:` | `f = open("fname")` |    |


特殊変数／オブジェクト
----

|      | Python | Ruby | Perl |
| ---- | ------ | ---- | ---- |
| 標準入力 | `sys.stdin` | `STDIN` (`$stdin)` | `STDIN` |
| 標準出力 | `sys.stdout` | `STDOUT` (`$stdout)` | `STDOUT`  |
| 標準エラー | `sys.stderr` | `STDERR` (`$stderr)` | `STDERR`  |
| 真 | `True`（および下記以外） | `true`（および下記以外） | 下記以外 |
| 偽 | `False`、`None`、0 を示す数値、空文字列、空リスト、空タプル、空辞書 | `false`, `nil` | `0`、`"0"`、空文字列、未定義値 |
| コマンドライン引数| `sys.argv` | `ARGV` (`$*`) | `@ARGV` |
| フィルタ用仮想ハンドル| | `ARGF` (`$<`) | `<>` |


パラメータ
----

|      | Python | Ruby | Perl |
| ---- | ------ | ---- | ---- |
| スクリプト名 | `sys.argv[0]` | `$0` | `$ARGV[0]` |
| 第１パラメータ | `sys.argv[1]` | `$1` | `$ARGV[1]` |


文字列の操作
----

|      | Python | Ruby | Perl |
| ---- | ------ | ---- | ---- |
|末尾の改行削除| `s = s.rstrip('\r\n')` | `s.chomp!` | `chomp($s)` |


リストの操作
----

|      | Python | Ruby | Perl |
| ---- | ------ | ---- | ---- |
|末尾に追加      | `lst.append(x)` |    ||
|先頭に追加      | `lst.insert(0, x)` |    ||
|末尾から取り出す| `x = lst.pop()` |    ||
|先頭から取り出す| `x = lst.pop(0)` |    ||
| 指定した位置の要素を削除 | `del lst[2]` | | |
|昇順ソートする| `lst.sort()` |||
|降順ソートする| `lst.sort(reverse=True)` |||
|逆順にする| `lst.reverse()` |||

