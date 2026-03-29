---
title: "TradeStationメモ: 銘柄のファンダメンタルデータを取得する (GetFundData)"
url: "p/bw64daa/"
date: "2018-11-25"
tags: ["tradestation"]
aliases: /tradestation/info/get-fund-data.html
---

個別銘柄の決算日、営業利益、PER、PBR といったファンダメンタルデータを取得するには、**`GetFundData`** 系の API を使用します。
取得するデータの型（数値、文字列、真偽値）に応じて、以下の 3 種類の関数を使い分けます。

- `GetFundData` - **数値**のファンダメンタルデータを取得する
- `GetFundDataAsString` - **文字列**のファンダメンタルデータを取得する
- `GetFundDataAsBoolean` - **真偽値**のファンダメンタルデータを取得する

{{< code title="関数の定義" >}}
GetFundData(sFieldName, nDataPointsBack) → 数値
GetFundDataAsString(sFieldName, nDataPointsBack) → 文字列
GetFundDataAsBoolean(sFieldName, nDataPointsBack) → 真偽値
{{< /code >}}

第 1 パラメータ `sFieldName` には、取得したいファンダメンタルデータを表す文字列を指定します。
取得可能なフィールドの一覧は、以下の TradeStation ヘルプページを参照してください。

- 参考: [TradeStation - ファンダメンタルデータ - スナップショットフィールド](http://help.tradestation.com/09_05/Monex/jpn/tradestationhelp/Subsystems/elanalysis/fundamental/fundamental_data_-_jpeq.htm)

第 2 パラメータ `nDataPointsBack` には、何四半期前の情報を取得するかを指定します。
1 を指定すると、1 つ前の四半期の情報を取得できます。

下記の例では、個別銘柄の売上高や各種利益を取得しています。

{{< code title="実装例" >}}
var:
    double sales(0),
    double op(0),
    double rp(0),
    double np(0);

once begin
    sales = GetFundData("CR_SALES", 0);  // 売上高
    op = GetFundData("CR_OP", 0);  // 営業利益
    rp = GetFundData("CR_RP", 0);  // 経常利益
    np = GetFundData("CR_NP", 0);  // 純利益

    Print("CR_SALES(売上高)=", sales:0:0, " (100万円)");
    Print("CR_OP(営業利益)=", op:0:0, " (100万円)");
    Print("CR_RP(経常利益)=", rp:0:0, " (100万円)");
    Print("CR_NP(純利益)=", np:0:0, " (100万円)");
end;
{{< /code >}}

実行すると、印刷ログに次のように出力されます。

```
CR_SALES(売上高)=1650000 (100万円)
CR_OP(営業利益)=33259 (100万円)
CR_RP(経常利益)=34047 (100万円)
CR_NP(純利益)=24779 (100万円)
```

`CR_SALES` に似たフィールドとして `CE_SALES` などがありますが、こちらは実績売上高ではなく予想売上高を示します（それぞれ Current Result と Current Estimate の略と思われます）。
ドキュメントにはどちらも「売上高 (Sales volume)」とだけ記述されており、やや分かりにくいです。


エラー処理を追加する
----

直前の `GetFundData` 系の関数呼び出しがエラーになると、`GetLastFundDataError` 変数（予約語）の値が `0` (`fdrOK`) 以外になります。
この値を参照することで、ファンダメンタルデータを取得できなかった場合の例外処理を記述できます。

- [GetLastFundDataError 予約語](http://help.tradestation.com/09_05/Monex/jpn/tsdevhelp//Subsystems/elword/word/getlastfunddataerror_reserved_word_.htm)

```
var: op(0);

once begin
    op = GetFundData("CR_OP", 0);  // 営業利益(100万円)
    If GetLastFundDataError <> fdrOk then Print("Cannot get CR_OP");
    Print("CR_OP(営業利益)=", op:0:0, " (100万円)");
end;
```

あるいは、多値関数バージョンのファンダメンタルデータ取得関数を使用してエラー情報を受け取ることもできます。

| 基本バージョン | 多値関数バージョン |
| ---- | ---- |
| `GetFundData` | **`FundValue`** |
| `GetFundDataAsString` | **`FundString`** |
| `GetFundDataAsBoolean` | **`FundBoolean`** |

多値関数バージョンでは、第 3 パラメータが出力用パラメータとなっており、そこでエラーコードを受け取れます。

```
var: op(0), error(-1);

once begin
    op = FundValue("CR_OP", 0, error);  // 営業利益(100万円)
    If error <> fdrOk then Print("Error=", error:0:0);
    Print("CR_OP(営業利益)=", op:0:0, " (100万円)");
end;
```
