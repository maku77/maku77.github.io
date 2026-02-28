---
title: "Rubyメモ: 文字列配列から正規表現に一致する要素を検索する (grep)"
url: "p/ynufrhd/"
date: "2011-11-04"
tags: ["ruby"]
aliases: ["/ruby/array/grep.html"]
---

文字列配列を grep する
----

下記の例では、文字列配列の中から、特定の文字を含むものを抽出しています。

{{< code lang="ruby" title="例: 文字列配列から ab という文字列を含む要素を抽出" >}}
arr = %w{ aaa abc bbb bab cca ccc dab ddd }
newArr = arr.grep(/ab/)    # => ["abc", "bab", "dab"]
{{< /code >}}

テキストファイルを grep する
----

テキストファイルを文字列配列として読み込めば、同様に grep できます。


{{< code lang="ruby" title="例: input.txt というファイルから ABC という文字列を含む行を抽出" >}}
lines = File.readlines('input.txt').grep(/ABC/)
lines = lines.map {|x| x.chomp }  # 各行の末尾の改行文字を削除する
print lines    # => ["ABC123", "dsdfasABCsdf", "sFOAIABCAF"]
{{< /code >}}
