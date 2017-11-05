---
title: "SNS ボタンをきれいに並べる"
created: 2017-11-02
description: "Facebook のいいねボタンや、Twitter のツイートボタンなどの SNS 系のボタンは、デフォルトで用意されているボタンのサイズが異なるため、そのまま並べると見た目が悪くなってしまいます。ここでは、同じサイズで SNS 系のボタンをきれいに並べる方法を紹介します。"
---


各 SNS ボタンのガイドライン
----

各 SNS サービスごとに、ブランドカラーが決まっています。
SNS シェア用のボタンを設置するときは、ガイドラインにそった色を使って作成するようにしましょう。

- [Twitter のガイドライン](https://about.twitter.com/content/dam/about-twitter/company/brand-resources/en_us/Twitter_Brand_Guidelines_V2.0.pdf)
  - カラー: `#1da1f2`<span style="background:#1da1f2">　</span>
- [Facebook のガイドライン](https://en.facebookbrand.com/)
  - カラー: `#3b5998`<span style="background:#3b5998">　</span>
- [Google のガイドライン](https://developers.google.com/identity/branding-guidelines?hl=ja)
  - カラー: `#dd4b39`<span style="background:#dd4b39">　</span>
- Pocket のガイドライン
  - カラー: `#ee4056`<span style="background:#ee4056">　</span>
- [Line のガイドライン](https://line.me/en/logo)
  - カラー: `#1dcd00`<span style="background:#1dcd00">　</span>
- [Instagram のガイドライン](https://www.instagram-brand.com/)
  - カラー: `#3f729b`<span style="background:#3f729b">　</span>
- [Pinterest のガイドライン](https://business.pinterest.com/en/brand-guidelines)
  - カラー: `#cb2027`<span style="background:#cb2027">　</span>
- [LinkedIn のガイドライン](https://brand.linkedin.com/downloads)
  - カラー: `#0e76a8`<span style="background:#0e76a8">　</span>


テキストだけのボタンを配置する
----

<iframe class="maku-htmlDemo" style="height:100px" src="sns-buttons-demo1.html"></iframe>
<a target="_blank" href="sns-buttons-demo1.html">デモページを開く</a>

まずは、テキスト表示だけの SNS ボタンを表示するサンプルです。
シンプルなのでとても動作が軽いです。
それでも、マウスを重ねた時のハイライト表示や、ボタンを押した時のアニメーションはしっかりと表現できています。

#### HTML

~~~ html
<ul class="snsButtons">
  <li class="snsButtons_twitter">ツイート
  <li class="snsButtons_facebook">シェア
  <li class="snsButtons_pocket">Pocket
  <li class="snsButtons_google">Google+
</ul>
~~~

#### CSS

~~~ css
/* SNS ボタンのグループ */
.snsButtons {
  margin: 5px;
  padding: 0px;
  text-align: center;
}
.snsButtons a {
  text-decoration: none;
}
/* 各 SNS ボタンのボックス */
.snsButtons li {
  display: inline-block;  /* 水平に並べる */
  list-style-type: none;  /* 先頭のポッチを消す */
  margin: 2px;
  padding: 6px 10px;
  width: 100px;
  color: white;
  border-radius: 4px;
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.29);
}
/* マウスカーソルを重ねたとき */
.snsButtons li:hover {
  opacity: 0.7;
}
/* クリックしたとき */
.snsButtons li:active {
  box-shadow: inset 0 0 2px rgba(128, 128, 128, 0.1);
  transform: translateY(2px);
}
/* 各 SNS のブランドカラー */
.snsButtons_twitter  { background: #1da1f2; }
.snsButtons_facebook { background: #3b5998; }
.snsButtons_pocket   { background: #ee4056; }
.snsButtons_google   { background: #dd4b39; }
~~~


アイコン付きのボタンを配置する
----

<iframe class="maku-htmlDemo" style="height:100px" src="sns-buttons-demo2.html"></iframe>
<a target="_blank" href="sns-buttons-demo2.html">デモページを開く</a>

[Font Awesome](http://fontawesome.io/) で提供されているフォントには、SNS ボタン用のグリフが含まれています。
上記では、これを使って SNS ボタンにアイコンを追加しています。

Font Awesome を使用するには、例えば、HTML の head 要素の中で下記のように読み込みます（最新の URL は [BootstrapCDN のサイト](https://www.bootstrapcdn.com/fontawesome/)で確認してください）。

#### HTML 抜粋

~~~ html
<head>
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
~~~

あとは、Font Awesome で定義されている `fa-twitter` などのクラスを使用すれば、SNS アイコンを表示することができます。

#### HTML 抜粋

~~~ html
<ul class="snsButtons">
  <li class="snsButtons_twitter"><i class="fa fa-twitter"></i><span class="snsButtons_label">ツイート</span>
  <li class="snsButtons_facebook"><i class="fa fa-facebook-square"></i><span class="snsButtons_label">シェア</span>
  <li class="snsButtons_pocket"><i class="fa fa-get-pocket"></i><span class="snsButtons_label">Pocket</span>
  <li class="snsButtons_google"><i class="fa fa-google-plus"></i><span class="snsButtons_label">Google</span>
</ul>
~~~

#### CSS

~~~ css
/* SNS ボタンのグループ */
.snsButtons {
  margin: 5px;
  padding: 0px;
  text-align: center;
}
.snsButtons a {
  text-decoration: none;
}
/* 各 SNS ボタンのボックス */
.snsButtons li {
  display: inline-block;  /* 水平に並べる */
  list-style-type: none;  /* 先頭のポッチを消す */
  margin: 2px;
  padding: 6px 0px;
  color: white;
  border-radius: 4px;
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.29);
}
/* マウスカーソルを重ねたとき */
.snsButtons li:hover {
  opacity: 0.7;
}
/* クリックしたとき */
.snsButtons li:active {
  box-shadow: inset 0 0 2px rgba(128, 128, 128, 0.1);
  transform: translateY(2px);
}
/* アイコン部分 */
.snsButtons i {
  font-size: 18pt;
  min-width: 40px;
  margin: 0px;
  padding: 0px 0px 0px 8px;
  vertical-align: bottom;
}
/* テキスト部分 */
.snsButtons_label {
  min-width: 80px;
  display: inline-block;
  padding: 0px 8px 0px 0px;
  text-align: center;
  font-size: 12pt;
}
/* 各 SNS のブランドカラー */
.snsButtons_twitter  { background: #1da1f2; }
.snsButtons_facebook { background: #3b5998; }
.snsButtons_pocket   { background: #ee4056; }
.snsButtons_google   { background: #dd4b39; }
~~~


各 SNS ボタンを機能させる
----

### Twitter のツイートボタン

Twitter ボタン用のコードは [https://publish.twitter.com/](https://publish.twitter.com/) から取得することができます。

~~~ html
<a href="https://twitter.com/share?ref_src=twsrc%5Etfw" class="twitter-share-button" data-show-count="false">Tweet</a><script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>
~~~

上記のコードをそのまま使用すると、デフォルトのボタンが表示されます。
自分で用意したボタンを使用するには、下記のようなコードを使用します。

~~~ html
<a rel="nofollow" href="http://twitter.com/share?text=【ツイート文】&url=【URL】&hashtags=【ハッシュタグ】"
    onclick="window.open(encodeURI(decodeURI(this.href)), 'tweet_window',
    'width=600, height=470, toolbar=no, scrollbars=yes, resizable=yes'); return false;">
  <!-- ここに表示する要素を配置 -->
  <img src="twitter.png">
</a>
~~~

検索エンジンが辿らないようにするために、一応 `rel="nofollow"` 属性を入れています。
【ツイート文】の所には、ページのタイトル（title 要素の値）を設定しておけばよいでしょう。


### Facebook のシェアボタン

Facebook のシェアボタンの設置方法は、[facebook for developers のソーシャルプラグインのページ](https://developers.facebook.com/docs/plugins/share-button/?locale=ja_JP)に詳しく記述されています。

自分で用意したボタンを使用するには、下記のようなコードを使用します。

~~~ html
<a rel="nofollow" href="http://www.facebook.com/share.php?u=【URL】"
    onclick="window.open(encodeURI(decodeURI(this.href)), 'facebook_window',
    'width=600, height=470, toolbar=no, scrollbars=yes, resizable=yes'); return false;">
  <!-- ここに表示する要素を配置 -->
  <img src="facebook.png">
</a>
~~~

Facebook は、ページの情報を Open Graph tags から読み取るので、HTML の head 要素に下記のような記述をしておく必要があります。

~~~ html
<head>
  <meta charset="UTF-8">
  <title>ページタイトル</title>

  <!-- You can use Open Graph tags to customize link previews.
       Learn more: https://developers.facebook.com/docs/sharing/webmasters -->
  <meta property="og:url"         content="http://example.com/sample.html" />
  <meta property="og:type"        content="website" />
  <meta property="og:site_name"   content="サイト名" />
  <meta property="og:title"       content="ページタイトル" />
  <meta property="og:description" content="説明文" />
  <meta property="og:image"       content="http://example.com/site-image.png" />
  <meta property="fb:app_id"      content="111122223333444" />
</head>
~~~


### Pocket の保存ボタン

Pocket ボタン用のコードは [https://getpocket.com/publisher/button](https://getpocket.com/publisher/button) から取得することができます。

~~~ html
<a data-pocket-label="pocket" data-pocket-count="vertical" class="pocket-btn" data-lang="en"></a>
<script type="text/javascript">!function(d,i){if(!d.getElementById(i)){var j=d.createElement("script");j.id=i;j.src="https://widgets.getpocket.com/v1/j/btn.js?v=1";var w=d.getElementById(i);d.body.appendChild(j);}}(document,"pocket-btn-js");</script>
~~~

上記のコードをそのまま使用すると、デフォルトのボタンが表示されます。
自分で用意したボタンを使用するには、下記のようなコードを使用します。

~~~ html
<a rel="nofollow" href="http://getpocket.com/edit?url=【URL】"
    onclick="window.open(encodeURI(decodeURI(this.href)), 'pocket_window',
    'width=600, height=470, toolbar=no, scrollbars=yes, resizable=yes'); return false;">
  <!-- ここに表示する要素を配置 -->
  <img src="pocket.png">
</a>
~~~


### Google＋ の共有ボタン

Google＋ ボタン用のコードは、[Google+ Platform for Web](https://developers.google.com/+/web/share/?hl=ja) から取得することができます。
自分で用意したボタンを使用するには、下記のようなコードを使用します。

~~~ html
<a rel="nofollow" href="https://plus.google.com/share?url=【URL】"
    onclick="window.open(encodeURI(decodeURI(this.href)), 'google_window',
    'width=400, height=470, toolbar=no, scrollbars=yes, resizable=yes'); return false;">
  <!-- ここに表示する要素を配置 -->
  <img src="google.png">
</a>
~~~

<div class="note">
href 属性の値に指定する【URL】の部分は、URL エンコードした値を渡すことに注意してください。
</div>

