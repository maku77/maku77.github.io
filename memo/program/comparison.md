---
title: "スクリプト言語の文法比較など"
date: "2010-05-07"
---

制御構文
----

|      | Bash | PHP  | Python | Ruby | Perl |
| ---- | ---- | ---- | ------ | ---- | ---- |
| if-else | [Bash](/linux/syntax/if.html) | [PHP](/php/syntax/if.html) | [Python](/python/syntax/if.html)  |  |  |
| switch-case |  |  | [Python](/python/syntax/switch.html) |  |  |
| ループ |  | [PHP](/php/syntax/loop.html) | `for x in a:` | [Ruby](/ruby/syntax-loop.html) |  |
| クラス |  | [PHP](/php/syntax/class.html) | [Python](/python/syntax/class.html) |  |  |
| 文字列リテラル |  |  |  [Python](/python/numstr/string-literal.html) |  |  |
| ヒアドキュメント | | [PHP](/php/syntax/here-document.html) | [Python](/python/syntax/here-document.html) | [Ruby](/ruby/here-document.html) | [Perl](/perl/string/here-document.html) |
| 正規表現 |  |  | [Python](/python/numstr/search-string.html) |  |  |


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
| 文字列の長さを調べる | `len(s)` | `s.length` | `length($s)` |
| 末尾の改行を削除する | `s = s.rstrip('\r\n')` | `s.chomp!` | `chomp($s)` |
| 文字列を逆順にする | `s[::-1]` | `s.reverse` | `reverse($s)` |
| デリミタ文字で結合する | `','.join(arr)` | `arr.join(',')` | `join("delim", LIST...)` |
| デリミタ文字で分割する | `s.split(',')` | `s.split(',')` | `split(/PATTERN/, $s)` |
| １文字ずつに分割する | `tuple(s)` | `s.chars` | `split(//, $s)` |
| 部分文字列を取得する | `s[start:end]` | `s[start..end]` | `substr($s, start, length)` |
| 文字列検索（前方） | `s.find('pattern')` | `s.index('pattern')` | `index($s, "pattern")` |
| 文字列検索（後方） | `s.rfind('pattern')` | `s.rindex('pattern')` | `rindex($s, "pattern")` |
| 文字列を置換する | `s.replace('from', 'to')` | `s.gsub('from', 'to')` | `$s =~ s/from/to/g` |


リスト／配列の操作
----

|      | Python | Ruby | Perl |
| ---- | ------ | ---- | ---- |
| 末尾に追加する | `lst.append(x)` |    | `push(@arr, x)` |
| 先頭に追加する | `lst.insert(0, x)` |    | `unshift(@arr, x)` |
| 末尾から取り出す | `x = lst.pop()` |    | `pop(@arr)` |
| 先頭から取り出す | `x = lst.pop(0)` |    | `shift(@arr)` |
| 指定した位置の要素を削除 | `del lst[2]` | | `splice(@arr, offset, length)` |
| 昇順ソートする | `lst.sort()` | | `sort [EXPR] @arr` |
| 降順ソートする | `lst.sort(reverse=True)` | | `sort { $b <=> $a } @nums` |
| 逆順にする | `lst.reverse()` | | `reverse(@arr)` |
| EXPR を満たす要素を取り出す | | | `grep(expr, @arr)` |
| 各要素に EXPR を適用する | | | `map(EXPR, @arr)` |


ハッシュ／連想配列の操作

|      | Python | Ruby | Perl |
| ---- | ------ | ---- | ---- |
| キーの数を取得する | | | `scalar(keys %HASH)` |
| キーのリストを取得する | | | `@arr = keys %HASH` |
| 値のリストを取得する | | | `@arr = values %HASH` |
| キーが存在するか確認する | | | `exists $HASH{'key'}` |
| キーのエントリを削除する | | | `delete $HASH{'key'}` |
| ハッシュ全体を削除する | | | `undef %HASH` |

