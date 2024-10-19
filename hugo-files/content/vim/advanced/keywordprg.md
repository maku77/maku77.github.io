---
title: "Vim でカーソル位置の単語に対して任意のコマンド（ヘルプなど）を実行する (keywordprg)"
url: "p/3hp29j9/"
date: "2007-11-21"
tags: ["vim"]
aliases: /vim/basic/keywordprg.html
---

Vim で任意のキーワードにカーソルを合わせた状態で **`SHIFT-K`** と入力すると、そのキーワードをパラメータにして、**`keywordprg`** オプションに設定されたコマンドが実行されます。
`keywordprg` の初期値は以下のようになっています。

{{< code lang="vim" title="keywordprg オプションのデフォルト値"  >}}
set keywordprg=man    " Unix の場合
set keywordprg=:help  " Windows の場合
{{< /code >}}

つまり、Unix の場合は、`SHIFT-K` と入力するだけでカーソル位置にあるキーワードの man ページを参照することができます。
man ページのセクション番号を指定するには、**`2 SHIFT-K`** のように、先にセクション番号を入力します。

どの文字をキーワードとみなすかは、**`iskeyword`** オプションに文字コードで設定します。
`iskeyword` のデフォルト値は下記のような感じになっています。

{{< code lang="vim" title="iskeyword オプションのデフォルト値" >}}
set iskeyword=@,48-57,_,128-167,224-235
{{< /code >}}

