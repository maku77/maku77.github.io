---
title: "Hugo で使用していないテンプレートやショートコードを見つける (hugo --printUnusedTemplates)"
url: "p/st6fpz9/"
date: "2023-04-25"
tags: ["Hugo"]
---

Hugo で四苦八苦しながらテンプレートを開発していると、いつのまにか使われていないテンプレート（`.html` ファイル）が量産されていたりします。
Hugo サイトのビルド時に、未使用のテンプレートを検出するには、次のように __`--printUnusedTemplates`__ オプションを付けて実行します。

```console
$ hugo --printUnusedTemplates
Start building sites …
...(省略)...
WARN 2023/04/25 00:27:35 Template _default/section.backup.html is unused, source file /Users/maku/mysite/themes/maku/layouts/_default/section.backup.html
WARN 2023/04/25 00:27:35 Template partials/debug/link-path.html is unused, source file /Users/maku/mysite/themes/maku/layouts/partials/debug/link-path.html
WARN 2023/04/25 00:27:35 Template partials/pager.html is unused, source file /Users/maku/mysite/themes/maku/layouts/partials/pager.html
WARN 2023/04/25 00:27:35 Template partials/top-menu.back.html is unused, source file /Users/maku/mysite/themes/maku/layouts/partials/top-menu.back.html
WARN 2023/04/25 00:27:35 Template shortcodes/anchor.html is unused, source file /Users/maku/mysite/themes/maku/layouts/shortcodes/anchor.html
WARN 2023/04/25 00:27:35 Template shortcodes/mermaid.html is unused, source file /Users/maku/mysite/themes/maku/layouts/shortcodes/mermaid.html
WARN 2023/04/25 00:27:35 Template shortcodes/script.html is unused, source file /Users/maku/mysite/themes/maku/layouts/shortcodes/script.html
WARN 2023/04/25 00:27:35 Template shortcodes/workings-and-drafts.html is unused, source file /Users/maku/mysite/themes/maku/layouts/shortcodes/workings-and-drafts.html
Total in 239 ms
```

上記の例では、8 つのテンプレートやショートコードが使われていないことが分かります。
内容を確認して、問題なければ削除してしまいましょう。

