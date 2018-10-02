---
title: "テキストの折り返し（改行）処理を自動で行う (Text::Wrap)"
date: "2008-04-30"
---

`Text::Wrap` モジュールを使用すると、長いテキストの折り返し処理などを自動で行うことができます。

#### Text::Wrap の使用例（50文字で折り返す）

~~~ perl
use Text::Wrap;

# Sample text.
my $text = "hello world " x 20;

# wrap settings
$Text::Wrap::columns = 50;    # Wrap at 50 characters
my $initial_tab = ' ' x 4;    # Indent before first line
my $subsequent_tab = '';      # All other lines flush left

print wrap ($initial_tab, $subsequent_tab, $text);
~~~

#### 実行結果

~~~
    hello world hello world hello world hello
world hello world hello world hello world hello
world hello world hello world hello world hello
world hello world hello world hello world hello
world hello world hello world hello world hello
world
~~~

