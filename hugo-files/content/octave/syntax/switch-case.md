---
title: "Octave の制御命令: switch ～ case"
url: "p/r9h53gn/"
date: "2017-03-27"
tags: ["Octave"]
aliases: /octave/syntax/switch-case.html
---

変数に格納された値の種類によって分岐処理を行うには、次のように __`switch`__ と __`case`__ で分岐させます。

```matlab
val = 2
switch val
    case 1
        disp('one')
    case 2
        disp('two')
    otherwise
        disp('other')
endswitch
```

下記のように、複数のパターンを１つの `case` でまとめて処理することもできます。

```matlab
yesno = 'yes';

switch yesno
    case {'Yes' 'yes' 'YES' 'y' 'Y'}
        value = 1;
    case {'No' 'no' 'NO' 'n' 'N'}
        value = 0;
    otherwise
        error ('invalid value');
endswitch
```

逆に、下記のように、複数の `case` を連続して記述する方法はうまくいかないので注意してください（値が 1 のケースは何も処理しないという意味になってしまいます）。

{{< code lang="matlab" title="間違った書き方" >}}
switch val
    case 1  % 間違った書き方
    case 2
        disp('one or two')
    otherwise
        disp('other')
endswitch
{{< /code >}}

