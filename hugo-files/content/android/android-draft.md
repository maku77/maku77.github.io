---
title: "(DRAFT) Android メモ"
url: "p/judvdtb/"
date: "2022-11-08"
tags: ["Android"]
draft: true
---

ビルドバリアントとは
----

ビルドバリアント __(Build variant)__ は、ビルドタイプ __(Build type)__ とプロダクトフレーバー __(Product flavor)__ の組み合わせで表現され、Android アプリをビルドするときの構成を示します。
ビルドバリアントごとに異なる APK ファイルが生成されます。

```
Build variant  =    Build type    x  Product flavor1  x   Product flavor2
                  (debug/release)    (free/paid/...)     (prod/stg/dev/...)
```

プロダクトフレーバーは、__Flavor dimension__ という単位でグルーピングすることができます。
例えば、`tier` という Flavor dimension を定義して、その中に有料版・無料版を表す `free` / `paid` といったフレーバーを定義します。
アプリをビルドするときは、各 dimension ごとにひとつのフレーバーを選択することで、ビルドバリアントを構成します。
例えば、次のような感じでビルドタイプおよびフレーバーを選択します。

- Build type: `debug`
- Product flavor1 (tier): `free`
- Product flavor2 (env): `stg`

このビルドバリアント名は、それぞれの単語をつなげて、`debugFreeStg` になります。

