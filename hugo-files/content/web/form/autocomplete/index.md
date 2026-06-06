---
title: "HTML/CSSメモ: 入力エリアの入力候補を表示する（autocomplete 属性、datalist 要素）"
url: "p/kom5g5p/"
date: "2017-12-12"
description: "input 要素の autocomplete 属性を使用すると、テキスト入力時に自動補完できるようになります。"
tags: ["html"]
aliases: /web/form/autocomplete.html
---

自動補完の候補リストは、[datalist 要素](https://developer.mozilla.org/ja/docs/Web/HTML/Element/datalist)で用意しておきます。
`datalist` 要素の `id` 属性に設定した値を、`input` 要素の `list` 属性で指定することで、自動補完の候補が結び付けられます。

{{< code lang="html" title="HTML サンプル" >}}
<form>
  <label>感想:
    <input list="candidate" autocomplete="on">
  </label>
  <datalist id="candidate">
    <option value="Good">
    <option value="Not too bad">
    <option value="Bad">
  </datalist>
</form>
{{< /code >}}

### デモ

<iframe class="xHtmlDemo" src="autocomplete-demo.html"></iframe>
<div>（{{< file src="autocomplete-demo.html" caption="デモページを開く" >}}）</div>
