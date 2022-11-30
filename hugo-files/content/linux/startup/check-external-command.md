---
title: "シェルスクリプト: ある外部コマンドが使用できるかチェックする"
url: "p/xi5sjju/"
date: "2010-06-13"
tags: ["Linux"]
aliases: /linux/startup/check-external-command.html
---

シェルスクリプトの中で特定の外部コマンドを使用する必要がある場合、先頭部分でその外部コマンドが使用できる状態かどうかを調べておくと親切です。
例えば、次のようにすれば、`ruby` コマンドが使用できるかを確認できます。

{{< code lang="bash" title="sample.sh" >}}
if type -P ruby > /dev/null; then
  echo 'ruby コマンドを使用できます'
else
  echo 'ruby コマンドが見つかりません'
fi
{{< /code >}}

{{% note title="type -P コマンド" %}}
`type -P ruby` コマンドは、パスの通ったディレクトリから `ruby` を検索してそのパスを取得するためのものですが、ここでは `ruby` コマンドが使用できる環境になっているかを確認するために利用しています。
単純に実行すると、検索結果のパスが出力されてしまうので、`null` デバイスへリダイレクトして出力を抑制しています。
{{% /note %}}

特定のコマンドが使用できない場合にスクリプトを終了させるには、以下のように記述すればよいでしょう。
条件式を __`!`__ で反転させていることに注意してください。

```bash
if ! type -P ruby > /dev/null; then
   echo 'This script requires Ruby. Please install Ruby first and try again.' >&2
   exit -1
fi

# 処理を継続
```

