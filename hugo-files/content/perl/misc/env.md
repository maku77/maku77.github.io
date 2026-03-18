---
title: "Perlメモ: 環境変数を参照する (%ENV)"
url: "p/4j5sfnp/"
date: "2008-04-05"
tags: ["perl"]
aliases: ["/perl/misc/env.html"]
---

現在の環境変数は **`%ENV`** ハッシュを参照することで取得できます。

```perl
while (my ($key, $val) = each %ENV) {
    print "$key = $val\n";
}
```

`%ENV` ハッシュの内容を変更することで、環境変数の内容を変更することができます。

{{< code lang="perl" title="環境変数 PATH にパスを追加する" >}}
$ENV{'PATH'} = "/home/kate/bin:$ENV{'PATH'}";
{{< /code >}}

{{< code lang="perl" title="環境変数 FLAGS を削除する" >}}
delete $ENV{'FLAGS'};  # 環境変数の削除
{{< /code >}}

環境変数は子プロセスに引き継がれるので、次のように `system` 関数で別のプログラムを呼び出す前に、環境変数をあらかじめ設定しておくことができます。

```perl
$ENV{'CXX'} = 'g++';
my $result = system 'make';  # make ユーティリティを呼び出す
```

子プロセスで環境変数を変更しても、親プロセスには影響しません。
つまり、Perl スクリプトの中で環境変数を設定しても、呼び出し元のシェルプロセスには影響しないということです。
