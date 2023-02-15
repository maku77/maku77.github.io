---
title: "Hugo で日本語を正しく扱えるようにしてページサマリーが長くなるのを防ぐ (hasCJKLanguage, isCJKLanguage)"
url: "p/rqcwgyj/"
date: "2017-08-29"
tags: ["Hugo"]
aliases: /hugo/settings/japanese.html
---

Hugo の記事一覧ページでは、記事の先頭部分を特定の単語数 (70 words) だけサマリー表示するようになっています。
ただし、このワードカウントはデフォルトでは英語などの単語数をベースに計算されているので、日本語などでは正しくカウントされずに、長大なサマリーが表示されてしまいます。

日本語の文字数を正しく数えて、短いサマリーを表示できるようにするには、設定ファイルで下記のように __`hasCJKLanguage`__ を `true` に設定します。

{{< code lang="toml" title="config.toml の場合" >}}
hasCJKLanguage = true
{{< /code >}}

{{< code lang="mayml" title="config.yaml の場合" >}}
hasCJKLanguage: true
{{< /code >}}

これで、Hugo 内部の __`.Summary`__ や __`.WordCount`__ が日本語で正しく動作するようになり、短いサマリー文章が表示されるようになります。

各ページごとに設定を行いたい場合は、下記のようにフロントマターで、__`isCJKLanguage`__ を `true` に設定します。

```yaml
---
title: "記事のタイトル"
date: "2017-08-29"
isCJKLanguage: true
---

記事の本文
```

設定ファイルでは `hasCJKLanguage` を設定し、Markdown ファイルのフロントマターでは `isCJKLanguage` を設定することに注意してください。

