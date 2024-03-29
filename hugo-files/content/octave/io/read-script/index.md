---
title: "Octave でファイルに記述したプログラムを実行する（スクリプトファイル）"
url: "p/mgjgq8m/"
date: "2017-03-27"
tags: ["Octave"]
aliases: /octave/io/read-script.html
---

スクリプトファイルと実行方法
----

Octave では、カレントディレクトリに __`スクリプト名.m`__ という名前でスクリプトファイルを作成しておくと、下記のようにしてそのスクリプトを実行することができます。

{{< code lang="matlab" title="スクリプト名.m の呼び出し" >}}
>> スクリプト名
{{< /code >}}

ただし、スクリプトファイルが置かれたディレクトリで実行しなければいけません。
カレントディレクトリを移動するには、__`cd`__ コマンドを実行します。
次のようにしてスクリプトを格納したディレクトリに移動しましょう。

```matlab
>> cd D:    % D ドライブのルートへ移動
```

カレントディレクトリがどこになっているかを確認するには、__`pwd`__ コマンドを実行します。

```matlab
>> pwd
ans = C:\Users\maku
```


スクリプトの実行例
----

ここでは、下記のようなスクリプトファイルを用意してみます。

{{< code lang="matlab" title="myscript.m" >}}
[X, Y] = meshgrid(-8:.5:8);
R = sqrt(X.^2 + Y.^2) + eps;
Z = sin(R) ./ R;
figure
mesh(X, Y, Z)
{{< /code >}}

このファイルが置いてあるディレクトリに移動して、拡張子 `.m` を除いた部分の名前を入力すると、スクリプトを実行できます。

```matlab
>> cd somewhere
>> myscript
```

次のようなグラフが表示されれば成功です。

{{< image src="img-001.png" title="myscript.m によるグラフ描画" >}}

