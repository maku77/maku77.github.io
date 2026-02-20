---
title: "Middlemanメモ: 現在のページがディレクトリインデックスであるか調べる"
url: "p/7398heb/"
date: "2017-08-20"
tags: ["middleman"]
aliases: /middleman/directory-index.html
---

現在のページが、ディレクトリインデックスであるかどうかを調べるには、`Resource` オブジェクトの `directory_index?` メソッドを使用します。
現在のページの `Resource` オブジェクトは、あらかじめ定義されている `current_page` という変数で参照できます。

{{< code lang="erb" title="カレントページがディレクトリインデックスかどうか調べて出力する" >}}
<% is_index = current_page.directory_index? %>

is_index = <%= is_index %>
{{< /code >}}

ディレクトリインデックスというのは、`index.html` や、ディレクトリ名と同じ名前のページ（例えば、`aaa` ディレクトリが存在するのであれば、同じ階層にある `aaa.html`）のことを言います。

単純に現在のページが `index.html` かどうかを調べたいのであれば、下記のようにしたほうがよいでしょう。

{{< code lang="ruby" title="index.html であることの確認方法 (1) url の末尾が '/' であることをチェック" >}}
if current_page.url.end_with?('/')
  # ...
end
{{< /code >}}

{{< code lang="ruby" title="index.html であることの確認方法 (2) ファイル名でチェック" >}}
if File.basename(current_page.path) == 'index.html'
  # ...
end
{{< /code >}}
