---
title: "CSS で画像と埋め込み動画のサイズを画面内に収める (max-width)"
url: "p/3tip9yp/"
date: "2016-05-01"
tags: ["CSS"]
aliases: /web/responsive/image-width.html
---

画像の最大幅を設定する (max-width)
----

画像や埋め込み動画を表示するときに、そのままのサイズで表示すると、画面サイズを縮めた場合に、画面内に収まらなくなってしまいます。
次のように __`max-width`__ を指定しておくと、ブラウザのウィンドウサイズを変更したときや、モバイル端末のような小さな画面サイズで表示したときも、ちゃんと画面内に収まるように表示されます。

```css
img, embed, iframe, object {
  max-width: 100%;
}
```

<center>
  <iframe width="470" height="180" src="./demo1.html" style="resize: both; overflow: auto;"></iframe>
  <div>デモ（<a target="_blank" href="./demo1.html">別ページで開く</a>）</div>
</center>

上記のフレームをブラウザだと思ってサイズを縮めてみてください。
表示領域のサイズ変更に連動して画像サイズも縮小されて表示されるはずです。


画面幅に応じて画像を拡大表示する
----

逆に、画面（ブラウザ）のサイズを広げた場合は、もともとの画像のサイズ以上の大きさでは表示されません。
画面（ブラウザ）のサイズを広げたときに、そのサイズに連動して拡大して表示したいのであれば、`max-width` プロパティではなく、単純に __`width`__ プロパティに `100%` 指定します。

```css
img {
  width: 100%;  /* 横幅いっぱいに画像を広げて表示 */
}
```

横幅いっぱいにまで広げてしまうとさすがに大きすぎるけれど、ある程度まで（例えば、横幅の 80% くらいまで）は広げたいという場合は、__`min-width`__ を指定することで、「少なくともこのサイズまでは拡大して表示する」、という意味になります。
下記の例ではパーセンテージで横幅を指定していますが、`px` 単位でも指定することができます。

```css
img {
    min-width: 80%;
    max-width: 100%;

    /* 下記は中央寄せ設定 */
    display: block;
    margin-left: auto;
    margin-right: auto;
}
```

<center>
  <iframe width="470" height="180" src="./demo2.html" style="resize: both; overflow: auto;"></iframe>
  <div>デモ（<a target="_blank" href="./demo2.html">別ページで開く</a>）</div>
</center>

