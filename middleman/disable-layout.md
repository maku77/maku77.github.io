---
title: レイアウト機能を無効にする
created: 2015-12-07
---

ERB 形式などのテンプレートファイルから HTML が生成されるとき、YAML Frontmatter などでレイアウトの指定がない場合は、デフォルトで `source/layouts/layout.erb` というレイアウトファイルが使用されるようになっています。
どのレイアウトも適用したくない場合（つまり、HTML 文書全体を ERB ファイルで構築したい場合）は、下記のようにレイアウト指定で `false` を指定します。

#### source/index.html.erb

```erb
---
layout: false
---

<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="UTF-8">
    <title>Hello</title>
  </head>
  <body>
    <h1>Hello</h1>
  </body>
</html>
```

