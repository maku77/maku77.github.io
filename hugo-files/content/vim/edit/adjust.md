---
title: "Vim でテキストを中央寄せ／左寄せ／右寄せする"
url: "p/sh79ewb/"
date: "2008-01-15"
tags: ["vim"]
aliases: ["/vim/edit/adjust.html"]
---

テキストの中央／左／右寄せ
----

指定した範囲のテキストをセンタリングするには、ex コマンドの `:center` を使用します。
同様に、右寄せは `:right`、左寄せは `:left` で実行できます。

```
:[range] center [width]
:[range] right [width]
:[range] left [margin]
```

テキスト幅を示す `width` オプションを省略した場合は、`textwidth` の値が使用されます。
`textwidth` が設定されていない場合は、デフォルトで 80 が使用されます。

`:left` の場合だけは、左端からのマージンを指定できます。


{{< code lang="vim" title="例: テキスト全体をセンタリング（テキスト幅は 80 とする）" >}}
:% center 80
{{< /code >}}

{{< code lang="vim" title="例: 1 行目から 5 行目までを右寄せ（テキスト幅は 80 とする）" >}}
:1,5 right 80
{{< /code >}}

{{< code lang="vim" title="ビジュアルモードで選択した範囲を左寄せ（左端からのマージン 5 文字分）" >}}
:`<,`> left 5
{{< /code >}}


テキストの両端揃え
----

Vim に付属している `justify.vim` マクロパッケージを使用すると、テキストの両端揃えを実行できます。
`justify.vim` は以下のようにロードします。

```vim
:source $VIMRUNTIME/macros/justify.vim
```

ビジュアルモードで範囲選択し、以下のコマンドを入力すると、両端揃えが実行されます。

```
_j
```

