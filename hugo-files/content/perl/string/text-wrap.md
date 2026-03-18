---
title: "Perlメモ: テキストの折り返し（改行）処理を自動で行う (Text::Wrap)"
url: "p/ebjochg/"
date: "2008-04-30"
tags: ["perl"]
aliases: ["/perl/string/text-wrap.html"]
---

`Text::Wrap` モジュールを使用すると、長いテキストの折り返し処理などを自動で行うことができます。

{{< code lang="perl" title="Text::Wrap の使用例（50文字で折り返す）" >}}
use Text::Wrap;

# Sample text.
my $text = "hello world " x 20;

# wrap settings
$Text::Wrap::columns = 50;    # Wrap at 50 characters
my $initial_tab = ' ' x 4;    # Indent before first line
my $subsequent_tab = '';      # All other lines flush left

print wrap ($initial_tab, $subsequent_tab, $text);
{{< /code >}}

{{< code title="実行結果" >}}
    hello world hello world hello world hello
world hello world hello world hello world hello
world hello world hello world hello world hello
world hello world hello world hello world hello
world hello world hello world hello world hello
world
{{< /code >}}
