---
title: "ローカル環境での開発中だけアフィリエイト広告を非表示にする"
date: "2017-08-21"
---

ローカル環境で Middleman サーバを立ち上げている場合（開発環境での作業中の場合）は、`development?` 変数の値が `true` に設定されます。
これを利用して、ローカルでの作業中だけアフィリエイト広告の表示を行わないようにすることができます。

#### 例: 開発中は includes/_adsense.erb の内容を出力しない

~~~ erb
<% if not development? %>
  <%= partial 'includes/adsense' %>
<% end %>
~~~

