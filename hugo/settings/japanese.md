---
title: "日本語を正しく扱えるようにしてページサマリーが長くなるのを防ぐ"
date: "2017-08-29"
---

Hugo の記事一覧ページでは、記事の先頭部分を特定の単語数 (70 words) だけサマリー表示するようになっています。
ただし、このワードカウントはデフォルトでは英語などの単語数をベースに計算されているので、日本語などでは正しくカウントされずに、長大なサマリーが表示されてしまいます。

日本語の文字数を正しく数えて、短いサマリーを表示できるようにするには、設定ファイルで下記のように `hasCJKLanguage` を `true` に設定します。

#### config.toml

~~~
hasCJKLanguage = true
~~~

#### config.yaml

~~~
hasCJKLanguage: true
~~~

これで、Hugo 内部の `.Summary` や `.WordCount` が正しく動作するようになり、短いサマリー文章が表示されるようになります。

各ページごとに設定を行いたい場合は、下記のように front matter ヘッダで、`isCJKLanguage` を `true` に設定します。

~~~
---
title: "記事のタイトル"
date: 2017-08-29
isCJKLanguage: true
---

記事の本文
~~~

設定ファイルでは `hasCJKLanguage` を設定し、front matter では `isCJKLanguage` を設定することに注意してください。

