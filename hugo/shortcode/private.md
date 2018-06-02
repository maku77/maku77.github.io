---
title: "ローカルサーバで動作させているとき（開発時）のみ内容を出力する private ショートコードを作成する"
date: "2018-04-02"
lastmod: "2018-06-02"
description: "自分の作業用備忘録としてはメモとして残したいけれど、インターネット上には公開したくない内容に関しては、Hugo をローカルサーバとして動作させているときだけ出力するようなショートコードを作成しておくと便利です。"
---

private ショートコードを作成する
----

そのショートコードが、Hugo のローカルサーバ上 (hugo server) で使用されたかを判別するには、[Site 変数](https://gohugo.io/variables/site/)の `.Site.IsServer` を参照します。

#### layouts/shortcodes/private.html

~~~
{{ "{{" }} if .Site.IsServer }}
  ローカルサーバで動作しています。
{{ "{{" }} end }}
~~~

あとは、`.Inner` 変数で、渡されたテキストを参照することができるので、たとえば次のように実装すればよいでしょう。

#### layouts/shortcodes/private.html

~~~
{{ "{{" }} if .Site.IsServer }}
  <div class="private">{{ "{{" }} .Inner }}</div>
{{ "{{" }} end }}
~~~

ここでは、出力する div 要素に `private` というクラスを割り当てています。
下記は `private` クラスに適用するスタイルシートの例です（赤系の色で強調表示するようにしています）。

~~~ css
.private {
  background: #fee;
  color: #f11;
  padding: 0.5em;
  border: solid 1px #f11;
}
.private::before {
  content: "Private メモ";
  display: block;
  font-weight: bolder;
}
~~~


使用方法
----

作成した private ショートコードは、記事ファイルの中で次のように利用します（ショートコードなので、テンプレートファイルの中では使えないことに注意してください）。

#### content/sample.md

~~~
---
title: "サンプルタイトル"
---

{{ "{{" }}< private >}}
  これは非公開なメモだよ。
{{ "{{" }}< /private >}}
~~~

次のように、内部のテキストで Markdown 構文を使用することもできます。

~~~
{{ "{{" }}% private %}}
これは非公開なメモだよ。

- Markdown の構文も使えるよ
- Markdown の構文も使えるよ
- Markdown の構文も使えるよ
{{ "{{" }}% /private %}}
~~~


（おまけ）.Site.IsServer が使えなかった頃
----

`.Site.IsServer` 変数は [Hugo v.0.38 で追加](https://github.com/gohugoio/hugo/pull/4541)されました。
v.0.37 以前のバージョンでは、ローカルサーバで Web ページをホスティングしているかどうかを、`.Site.BaseURL` の値が `http://localhost` で始まっているかどうかで判断していました。

#### layouts/shortcodes/private.html

~~~
{{ "{{" }} if hasPrefix .Site.BaseURL "http://localhost" }}
  ローカルサーバで動作しています。
{{ "{{" }} end }}
~~~

