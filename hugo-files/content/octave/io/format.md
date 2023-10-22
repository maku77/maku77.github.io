---
title: "Octave で出力する桁数を増やす (format)"
url: "p/fism9pp/"
date: "2017-03-24"
tags: ["Octave"]
aliases: /octave/format.html
---

Octave の内部では倍精度の浮動小数点数（10 進数で 15 桁程度の精度）で計算が行われていますが、画面出力時のデフォルト桁数は 5 桁に設定されています。

```matlab
>> pi
pi = 3.1416
```

表示桁数を増やすには、下記のように __`format`__ 関数を使用します。

{{< code lang="matlab" title="表示桁数を増やす" >}}
>> format long
>> pi
ans =  3.14159265358979
{{< /code >}}

上記のように、パラメーターとして __`long`__ を指定すると、倍精度実数の表現限界である 15 桁程度の数値を表示してくれるようになります。
元に戻したい場合は、パラメータなしで `format` 関数を実行します。

{{< code lang="matlab" title="表示桁数を元に戻す" >}}
>> format
>> pi
ans = 3.1415
{{< /code >}}

`format` 関数で指定できるパラメータの詳細は、次のようにヘルプで確認できます。

```matlab
>> help format
```

