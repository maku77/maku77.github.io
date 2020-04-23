---
title: "現在計算中のバーが何本目か調べる (CurrentBar, BarNumber)"
date: "2018-11-07"
---

CurrentBar
----

**`CurrentBar`** は、現在計算中のバーが何本目であるかを示しています。
より正確には、`MaxBarsBack` 番目のバーから計算することになるので、例えば、`MaxBarsBack` の値が 15 の場合、チャート上の 16 番目のバーが `CurrentBar=1` となります。

- 参考: [インジケーターの計算が何本目のバーから始められるか (MaxBarsBack)](max-bars-back.html)

#### 実装例

~~~
Print("CurrentBar=", CurrentBar:0:0);
~~~

<div class="note">
<code>CurrentBar:0:0</code> の後ろの <code>:0:0</code> の部分は、小数点数以下を省略して表示するための桁数設定です。そもそも足の番号は整数でしかありえないのですが、トレードステーションの仕様として実数として扱われてしまうのでこのような指定が必要です。
</div>

#### 出力結果（印刷ログ）

~~~
CurrentBar=1
CurrentBar=2
CurrentBar=3
...
CurrentBar=83
CurrentBar=84
CurrentBar=85
~~~

`MaxBarsBack` が 15 のとき、上記のように表示された場合は、チャート上のバーは全部で 100 本 (=15+85) 存在することになります。


BarNumber
----

**`BarNumber`** も `CurrentBar` と同様に、バーの番号を取得するために用意されています。
`CurrentBar` が現在処理中の足の番号を取得する単純な予約ワードなのに対して、`BarNumber` はシリーズ関数として定義されており、n 本前の足の番号を参照することができるようになっています。

`BarNumber` と `CurrentBar[0]` はまったく同じ意味で、現在処理中のバーの番号を表します（`CurrentBar[0]` は `CurrentBar` と省略できます）。
`CurrentBar[1]` は、1 つ前のバーの番号を表します。

#### 実装例

~~~
Print("CurrentBar=", CurrentBar:0:0,
      ", BarNumber[0]=", BarNumber[0]:0:0,
      ", BarNumber[1]=", BarNumber[1]:0:0);
~~~

#### 出力結果（印刷ログ）

~~~
...
CurrentBar=40, BarNumber[0]=40, BarNumber[1]=39
CurrentBar=41, BarNumber[0]=41, BarNumber[1]=40
CurrentBar=42, BarNumber[0]=42, BarNumber[1]=41
...
~~~

`BarNumber[1]` は 1 本前のバーの番号なので、`CurrentBar` や `BarNumber[0]` と比べて値が 1 つ小さいことがわかると思います。
まぁ、`CurrentBar - 1` としても同じ値が得られるんですけどね。。。


（おまけ）バー番号をログのプレフィックスとして表示する
----

`Print` 関数でインジケーターのデバッグを行っているとき、`CurrentBar` によるバー番号をプレフィックスと表示したいことはよくあります。
そのようなときは、`CurrentBar:4:0` のように整数の位を 4 桁に固定して表示すると、ログ表示のガタつきを抑えられます。

次の例では、各足の終値を表示しています。

~~~
// インジケーター適用時にログをクリア
once ClearPrintLog;

// 各足の番号と終値をログ出力
Print(CurrentBar:4:0, ": Close = ", Close:0:0);
~~~

実行結果:

~~~
   1: Close = 774
   2: Close = 850
   3: Close = 905
   4: Close = 901
   5: Close = 930
   6: Close = 890
   7: Close = 884
   8: Close = 860
   9: Close = 850
  10: Close = 852
  11: Close = 892
  12: Close = 913
  13: Close = 910
  14: Close = 900
  15: Close = 917
~~~

