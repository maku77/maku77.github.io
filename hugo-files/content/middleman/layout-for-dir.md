---
title: "Middlemanメモ: ディレクトリ単位でデフォルトレイアウトを設定する"
url: "p/y8kr7kv/"
date: "2015-12-07"
tags: ["middleman"]
aliases: /middleman/layout-for-dir.html
---

各ページの YAML Frontmatter でレイアウトファイルの指定を行うことができますが、ページ数が増えてくると、毎回 `layout` パラメータを記述するのが面倒になってきます。
そのような場合は、プロジェクトのルートにある `config.rb` の中で、使用するレイアウトファイルをまとめて指定することができます。

{{< code lang="ruby" title="config.rb（一部抜粋）" >}}
page "/sample/*", :layout => "sample"
{{< /code >}}

上記のように設定しておくと、`source/sample` ディレクトリ以下のコンテンツに対して、`sample` レイアウトが適用されるようになります。
下記のように個別のページに対するレイアウト指定も行えます。

{{< code lang="ruby" title="config.rb（一部抜粋）" >}}
page "/sample/index.html", :layout => "index"
{{< /code >}}

第一パラメータは、生成された後のファイル名で指定することに注意してください。
