---
title: "JavaScriptメモ: HTML 要素のサイズを取得する"
url: "p/afa7mck/"
date: "2018-05-18"
tags: ["javascript"]
aliases: [/js/dom/elem-size.html]
---

{{< image src="img-001.svg" >}}

{{% private %}}
- {{< file src="elem-size.idraw" >}}
{{% /private %}}

要素のサイズを取得する API
----

ここでは、下記のようなテスト用の div 要素を使用します。

```html
<div id="box">あああああいいいいい</div>
```

この要素のサイズは、下記のようにして取得することができます。

{{< code lang="javascript" title="jQuery を使用した場合" >}}
const $e = $('#box');

// コンテンツ領域のみのサイズ
const w1 = $e.width();
const h1 = $e.height();

// padding を含むサイズ
const w2 = $e.innerWidth();
const h2 = $e.innerHeight();

// padding と border を含む幅
const w3 = $e.outerWidth();
const h3 = $e.outerHeight();

// padding と border と margin を含む幅
const w4 = $e.outerWidth(true);
const h4 = $e.outerHeight(true);
{{< /code >}}

jQuery を使わずに、プレーンな JavaScript (Vanilla JavaScript) でやろうとすると、若干わかりにくいコードになります。

{{< code lang="javascript" title="(Vanilla) JavaScript の場合" >}}
const e = document.getElementById('box');

// padding も border も含まないサイズ
const w1 = parseInt(window.getComputedStyle(e).width);
const h1 = parseInt(window.getComputedStyle(e).height);

// padding を含むサイズ
const w2 = e.clientWidth;
const h2 = e.clientHeight;

// padding と border を含む幅
const w3 = e.offsetWidth;
const h3 = e.offsetHeight;
{{< /code >}}

特に、padding を含まない content 領域のみのサイズを求めるときに使用している `window.getComputedStyle` 関数は、`50px` のような単位付きの文字列を返すので、これを `50` という数値に変換するために `parseInt` を使用しています。


要素のサイズを調べるデモ
----

下記のデモでは、テスト用の div 要素のサイズや padding、border を次のように指定し、それぞれのサイズがどのように取得されるかを調べています。

```css
#box {
  width: 50px;
  height: 50px;
  padding: 5px;
  margin: 10px;
  border: solid 10px red;
  background: lightcyan;
}
```

#### デモ（{{< file src="elem-size-demo.html" caption="別ウィンドウで開く" >}}）

<iframe class="xHtmlDemo" src="elem-size-demo.html"></iframe>

例えば、`$e.outerWidth()` は、コンテンツ分 (50px) + padding分 (10px) + border分 (20px) = 80px と計算されています。
