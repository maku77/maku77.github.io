---
title: "入力エリアの入力候補を表示する（autocomplete 属性）"
date: "2017-12-12"
description: "input 要素の autocomplete 属性を使用すると、テキスト入力時に自動補完できるようになります。"
---

自動保管の候補リストは、[datalist 要素](https://developer.mozilla.org/ja/docs/Web/HTML/Element/datalist)で用意しておきます。
`datalist` 要素の `id` 属性に設定した値を、`input` 要素の `list` 属性で指定することで、自動補完の候補が結び付けられます。


#### HTML サンプル

~~~ html
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
~~~

### デモ

<iframe class="maku-htmlDemo" src="autocomplete-demo.html"></iframe>
<a target="_blank" href="autocomplete-demo.html">デモページを開く</a>
