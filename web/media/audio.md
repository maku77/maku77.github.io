---
title: "audio タグを使って音声ファイルを再生する"
date: "2018-02-14"
description: "HTML5 の audio タグを使用すると、mp3 や wav などの音声ファイルを簡単に再生することができます。"
---

audio 要素の使用例
----

#### HTML 記述例

~~~ html
<audio src="coin.mp3" controls>
  <p><a src="coin.mp3">音声ファイルをダウンロード</a></p>
</audio>
~~~

#### デモ

<div style="text-align:center">
  <audio src="file/coin.wav" controls>
    <p><a src="file/coin.wav">音声ファイルをダウンロード</a></p>
  </audio>
</div>

`<audio> 〜 </audio>` の中には、audio 要素がサポートされていない環境で表示されるメッセージを記述することができます。
上記の例では、音声ファイルをダウンロードするリンクを表示するようにしています。


audio 要素の属性
----

audio 要素には、次の属性が用意されています。

src
: 再生する音声ファイルの URL を指定します。source 要素を使って音声ファイルを指定する場合は、audio 要素の src 属性は指定できません。

controls（論理属性）
: 音声ファイルを再生するためのコントロールパネルを表示します。

autoplay（論理属性）
: ページが読み込まれたときに、自動的に音声ファイルを再生します。

loop（論理属性）
: ループ再生します。

muted（論理属性）
: デフォルトの音量を 0 に設定します。

preload
: 音声ファイルのプリロード方法を指定します。指定しなかった場合の振る舞いは、ブラウザの実装に依存します。preload の指定は、ブラウザにプリロードのヒントを与えるだけで、必ずしも指定した通りに振る舞うとは限りません。例えば、autoplay 属性が指定されている場合は、音声ファイルをダウンロードしなければいけないことは明らかなので、`preload="none"` という指定は意味を持ちません。
- "none": 事前読み込みしない。
- "metadata": 音声のメタデータのみ事前読み込みする。
- "auto": 事前読み込みする。

crossorigin
: 元文書とは異なるオリジンからデータを取得する際の認証方法を指定します。"anonymous" または "use-credentials" を指定します。


source 要素で複数の音声ファイルを指定する
----

audio 要素の src 属性の代わりに、次のように source 要素を使って、種類の異なる音声ファイルを指定することができます。
type 属性で指定した MIME タイプのうち、現在使用しているブラウザが最初にサポートするファイルが再生されることになります。

~~~ html
<audio controls autoplay>
  <source src="file/sample.mp3" type="audio/mp3">
  <source src="file/sample.ogg" type="audio/ogg">
  <source src="file/sample.wav" type="audio/wav">
  <p>
    ※ご利用のブラウザは、
    <a src="file/sample.mp3">音声ファイル</a>の再生に対応していません。
  </p>
</audio>
~~~

使用しているブラウザが、どの MIME タイプもサポートしない場合は、最後の要素に指定したメッセージが表示されます。

