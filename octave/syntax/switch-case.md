---
title: "Octave の制御命令: switch ～ case"
date: "2017-03-27"
---

変数に格納された値の種類によって分岐処理を行うには、`switch` キーワードを使用します。

~~~ matlab
val = 2
switch val
    case 1
        disp('one')
    case 2
        disp('two')
    otherwise
        disp('other')
endswitch
~~~

下記のように、複数のパターンを１つの `case` でまとめて処理することもできます。

~~~ matlab
yesno = 'yes';

switch yesno
    case {'Yes' 'yes' 'YES' 'y' 'Y'}
        value = 1;
    case {'No' 'no' 'NO' 'n' 'N'}
        value = 0;
    otherwise
        error ('invalid value');
endswitch
~~~

逆に、下記のように、複数の `case` を連続して記述する方法はうまくいかないので注意してください（値が 1 のケースは何も処理しないという意味になってしまいます）。

~~~ matlab
switch val
    case 1  % 間違った書き方
    case 2
        disp('one or two')
    otherwise
        disp('other')
endswitch
~~~

