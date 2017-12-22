---
title: "セクションに属するページの一覧を表示する (.Pages)"
created: 2017-12-22
---

リストテンプレートやセクションテンプレートの中で、`.Pages` を参照すると、そのセクション（やタクソノミー）に含まれるページの一覧を `Page` オブジェクトの配列として取得することができます。
`.Data` は `.Data.Pages` のエイリアスです。
`.Pages` をシングルページレイアウト内で参照すると、サイズ 0 の配列が返されます。
基本的にはリストテンプレート（`layouts/index.html` や `layouts/_default/list.html`、`layouts/_default/section.html` など）から参照するとよいでしょう。

#### layouts/_default/list.html

~~~ html
<h3>セクションに含まれるページの一覧</h3>
<ul>
  {{ "{{" }} range .Pages }}
    <li><a href="{{ "{{" }} .RelPermalink }}">{{ "{{" }} .Title }}</a>
  {{ "{{" }} end }}
</ul>
~~~

上記のようなリストテンプレートは、タグの一覧ページ（タクソノミーリスト）としても同様に使用することができます。

