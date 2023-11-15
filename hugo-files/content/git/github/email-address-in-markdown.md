---
title: "GitHub の Markdown でメールアドレスへのリンクを記述する"
url: "p/xikzhtr/"
date: "2016-02-04"
tags: ["GitHub"]
aliases: /git/github/email-address-in-markdown.html
---

Markdown ファイルの中で、E メールアドレスのリンクを記述するには下記のようにします。

{{< code lang="md" title="Markdown ファイル抜粋" >}}
[表示名](<mailto:xxx@example.com>)
{{< /code >}}

上記のように `<` と `>` で囲んだメールアドレスは、出力される HTML の中では、下記のように実体参照による表現に変換されるため、スパムメールの対策にもなります。

{{< code lang="html" title="変換後の HTML" >}}
<a href="&#109;&#097;&#105;&#108;&#116;&#111;:&#120;&#120;&#120;&#064;&#101;&#120;&#097;&#109;&#112;&#108;&#101;&#046;&#099;&#111;&#109;">表示名</a>
{{< /code >}}

実際の表示結果は次のような感じになります。

[表示名](<mailto:xxx@example.com>)

