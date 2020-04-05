---
title: "Hugo のパーシャルテンプレートから値を返す（関数化）"
date: "2020-04-06"
---

パーシャルテンプレートを関数として使用する
----

[Hugo v.0.55](https://github.com/gohugoio/hugo/issues/5783) 以降のパーシャルテンプレートでは、テキストを出力する代わりに、値を `return` できるようになっています。

- [Returning a value from a Partial ｜ Hugo](https://gohugo.io/templates/partials/#returning-a-value-from-a-partial)

例えば、次のパーシャルテンプレートは、引数で受け取った値に 100 を足した値を返します。

#### layouts/partials/functions/my-add.html

```
{{ "{{" }} $ret := add 100 . }}
{{ "{{" }} return $ret }}
```

呼び出し側のテンプレートは次のように使用します。

#### layouts/_default/single.html

```
{{ "{{" }} $ret := partial "functions/my-add" 50 }}
計算結果: {{ "{{" }} $ret }}
```

実行結果は次のようになります。

```
計算結果: 150
```


リストを返す
----

パーシャルテンプレートの `return` では様々な型のオブジェクトを返すことができます。
例えば次の例では、`slice` により作成したリストを返し、呼び出し側でループ処理しています。

#### layouts/partials/functions/get-colors.html

```
{{ "{{" }} $ret := slice "Blue" "Red" "Yellow" }}
{{ "{{" }} $ret = $ret | append "Black" "White" }}
{{ "{{" }} return $ret }}
```

#### layouts/_default/single.html

```
<ul>
  {{ "{{" }}- range partial "functions/get-colors" . }}
    <li>{{ "{{" }} . }}
  {{ "{{" }}- end }}
</ul>
```

#### 実行結果

```html
<ul>
  <li>Blue
  <li>Red
  <li>Yellow
  <li>Black
  <li>White
</ul>
```

