---
title: "HTML の audio 要素で音声ファイルを再生する"
url: "p/hoffk3g/"
date: "2018-02-14"
lastmod: "2024-06-24"
tags: ["HTML"]
description: "HTML5 の audio タグを使用すると、mp3 や wav などの音声ファイルを簡単に再生することができます。"
aliases: /web/media/audio.html
---

HTML の __`audio`__ 要素を使用すると、Web ページ内に音声コンテンツを埋め込むことができます。

audio 要素の使用例
----

次の例では、音声ファイル (`coin.mp3`) を再生するためのコントールパネルを表示しています。

<div style="text-align: center">
  <audio src="./coin.wav" controls>
    <p><a src="./coin.wav">音声ファイルをダウンロード</a></p>
  </audio>
</div>

{{< code lang="html" title="HTML 記述例" >}}
<audio src="coin.mp3" controls>
  <p><a src="coin.mp3">音声ファイルをダウンロード</a></p>
</audio>
{{< /code >}}

`<audio> 〜 </audio>` のブロック内には、`audio` 要素がサポートされていない環境で表示されるメッセージを記述することができます。
ここでは、コントロールパネルを表示できないときに、音声ファイルをダウンロードするリンクを表示するようにしています。


audio 要素の属性
----

`audio` 要素には、次のような属性が用意されています。
（論理属性）(boolean attributes) と書かれているものは、値を指定せずに単純にその属性名だけを記述すれば有効になる属性です。

__`src`__
: 再生する音声ファイルの URL を指定します。
`source` 要素を使って音声ファイルを指定する場合は、`audio` 要素の `src` 属性は指定できません。

__`controls`__ （論理属性）
: 音声ファイルを再生するためのコントロールパネルを表示します。

__`autoplay`__ （論理属性）
: ページが読み込まれたときに、自動的に音声ファイルを再生します。

__`loop`__ （論理属性）
: ループ再生します。

__`muted`__ （論理属性）
: デフォルトの音量を 0 に設定します。

__`preload`__
: 音声ファイルのプリロード方法を指定します。

  - __`none`__: 事前読み込みしない。
  - __`metadata`__: 音声のメタデータのみ事前読み込みする。
  - __`auto`__: 事前読み込みする。

  指定しなかった場合の振る舞いは、ブラウザの実装に依存します。
  `preload` の指定は、ブラウザにプリロードのヒントを与えるだけで、必ずしも指定した通りに振る舞うとは限りません。
  例えば、`autoplay` 属性が指定されている場合は、音声ファイルをダウンロードしなければいけないことは明らかなので、`preload="none"` という指定は意味を持ちません。

__`crossorigin`__
: 元文書とは異なるオリジンからデータを取得する際の認証方法を指定します。
__`anonymous`__ または __`use-credentials`__ を指定します。


source 要素で複数の音声ファイルを指定する
----

`audio` 要素の `src` 属性の代わりに、次のように __`source`__ 要素を使って、種類の異なる音声ファイルを指定することができます。
音声ファイルの MIME タイプを `type` 属性で指定します。
ブラウザがサポートしている MIME タイプのうち、最初に見つかった音声ファイルが再生されます。

```html
<audio controls autoplay>
  <source src="file/sample.mp3" type="audio/mp3">
  <source src="file/sample.ogg" type="audio/ogg">
  <source src="file/sample.wav" type="audio/wav">
  <p>
    ご利用のブラウザは<a src="file/sample.mp3">音声ファイル</a>の再生に対応していません。
  </p>
</audio>
```

使用しているブラウザがどの MIME タイプもサポートしない場合は、最後の要素として指定したメッセージが表示されます。

