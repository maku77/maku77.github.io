---
title: "Octave の制御命令: if ～ else"
created: 2017-03-27
---

Octave (MATLAB) の if ～ else の構文は以下のようになっています。

~~~ matlab
if (COND)
    ...
elseif (COND)
    ...
else
    ...
end
~~~

条件式では下記のような比較演算子が使用できます。
特に、a と b が等しくないことを表す記号が `~=` であることに注意してください。

| `a == b` | a と b が等しい |
| `a ~= b` | a と b が等しくない |
| `a < b` | a が b より小さい |
| `a > b` | a が b より大きい |
| `a <= b` | a が b 以下 |
| `a >= b` | a が b 以上 |
| `a` | a が 0 以外 |

また、論理演算子を組み合わせて使用できます。

| `COND1 || COND2` | COND1 あるいは COND2 が真ならば真 |
| `COND1 && COND2` | COND1 と COND2 両方が真ならば真 |
| `!COND1` | 真偽値の反転 |

### 例: x の値によって分岐

~~~ matlab
x = 2
if (x == 1)
    disp('one')
elseif (x == 2)
    disp('two')
else
    disp('other')
end
~~~

