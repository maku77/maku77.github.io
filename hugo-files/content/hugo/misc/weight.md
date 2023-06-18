---
title: "Hugo で特定の記事を常にリスト上方に表示する（weight プロパティ）"
url: "p/utmg42x/"
date: "2017-08-28"
tags: ["Hugo"]
aliases: /hugo/basic/weight.html
---

Hugo で記事（コンテンツ）の一覧を表示しようとすると、通常は記事の作成日時順に表示されます。
一覧ページで特定の記事を最初に表示したい場合は、記事のフロントマターの __`weight`__ プロパティで、記事の重要度（整数値）を設定します。

{{< code lang="yaml" title="記事のヘッダ (Front matter) の記述例" >}}
---
title: "重要な記事"
date: "2017-08-28"
weight: 1
---

...本文...
{{< /code >}}

`weight` プロパティに設定した値は、小さいほど重要度が高いと認識されます。
つまり、`weight` 値を 1 にすると、重要な記事として上の方に表示されます。
2 はその次、3 はさらにその次、というように、昇順に表示されます。
0 は特別で、`weight` 値を指定していないのと同じ意味になります。

`weight` 値には、実はマイナスの値も設定することができるため、-99999 といった値を設定しておくと、1 や 2 と設定したものよりも上位に表示されます（優先度がものすごく高いと見なされる）。

`weight` 値の設定されていない（あるいは `weight: 0` に設定した）通常の記事は、どのような `weight` 値を持つ記事よりも優先度が低いと見なされませます（後ろに表示されます）。
