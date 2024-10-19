---
title: "Vim で大文字と小文字を区別しないで検索する (ignorecase)"
url: "p/mfcba2q/"
date: "2010-05-18"
tags: ["vim"]
aliases: /vim/advanced/ignorecase.html
---

Vim で `/` や `:vimgrep` による検索を行うときに、大文字・小文字を区別するかどうかの設定は下記のように行います（デフォルトでは大文字・小文字を区別します）。

{{< code lang="vim" >}}
:set ignorecase      "大文字と小文字を区別しない （ic と省略可能）
:set noignorecase    "大文字と小文字を区別する （noic と省略可能）
{{< /code >}}

この設定にかかわらず、検索時に大文字・小文字を区別するかを指定するには、検索パターンの中に __`\c`__ や __`\C`__ を含めるようにします。

{{< code lang="vim" title="検索時に大文字・小文字を区別を指定" >}}
/\cfoo    " 大文字、小文字を区別せずに foo を検索
/\Cfoo    " 大文字、小文字を区別して foo を検索
{{< /code >}}

検索パターン中の `\c` と `\C` は、先頭で指定する必要はなく、パターン内のどこにあっても構いません。

検索に関する設定方法の詳細は下記を参照してください。

- 参考: [Vim の検索に関する設定 (`ignorecase`, `smartcase`, `wrapscan`, `hlsearch`, `incsearch`)](/p/v4cuc9g/)

