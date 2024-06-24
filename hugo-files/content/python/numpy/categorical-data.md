---
title: "Python データ分析: カテゴリデータを扱う"
url: "p/vopvucs/"
date: "2024-06-16"
tags: ["python"]
---

カテゴリデータとは
----

数値ではなく、カテゴリ名やラベルで表現されるデータをカテゴリデータと呼びます。
カテゴリデータには、本質的に順序関係を持たない名義カテゴリ (nominal category) と、純情関係のある順序カテゴリ (ordinal category) があります。

- 名義カテゴリ (nominal category)
  - 順序のないカテゴリ
  - 例: `りんご`、`バナナ`、`オレンジ`
  - 例: `赤`、`青`、`黄`、`緑`
- 順序カテゴリ (ordinal category)
  - 順序のあるカテゴリ
  - 例: `Low`、`Middle`、`High`
  - 例: `S`、`M`、`L`、`XL`

ロジスティック回帰やディープラーニングなどのアルゴリズムでは、入力データとして数値を想定しているため、カテゴリデータは数値に変換して扱う必要があります。


名義カテゴリを数値化する（ワンホットエンコーディング）
----

本質的に順序を持たない名義カテゴリ特徴量（色など）を扱うには、特徴量の個々のクラスに対する 2 値特徴量 (0 or 1) を作成します。
この手法は、機械学習では __ワンホットエンコーディング（one-hot encoding)__、統計では __ダミー変数化 (dymmying)__ と呼ばれています。

- [scikit-learn の LabelBinarizer を使う方法](#label-binarizer)
- [pandas の get_dummies を使う方法](#get_dummies)

### scikit-learn の LabelBinarizer を使う方法 {#label-binarizer}

acikit-learn の [preprocessing.LabelBinarizer](https://scikit-learn.org/stable/modules/generated/sklearn.preprocessing.LabelBinarizer.html) クラスは、シンプルなワンホットエンコード機能を提供します。
次の例では、色を表す名義カテゴリ特徴量を one-hot ベクトル化しています。

```python
import numpy as np
import pandas as pd
from sklearn.preprocessing import LabelBinarizer

feature = np.array(["red", "blue", "yellow", "yellow", "blue"])
binarizer = LabelBinarizer()
feature2 = binarizer.fit_transform(feature)
print(feature2)  # [[0 1 0] [1 0 0] [0 0 1] [0 0 1] [1 0 0]]
```

各列がどの色を表現しているかのクラス情報は __`LabelBinarizer#classes_`__ で参照できます。

```python
print(binarizer.classes_)  # ['blue' 'red' 'yellow']
```

one-hot ベクトル化されたデータを元の名義カテゴリデータに戻すには、__`LabelBinarizer#inverse_transform()`__ を使用します。

```python
feature2 = binarizer.fit_transform(feature)
feature3 = binarizer.inverse_transform(feature2)
print(feature3)  # ['red' 'blue' 'yellow' 'yellow' 'blue']
```

### pandas の get_dummies を使う方法 {#get_dummies}

pandas の DataFrame を使っている場合は、__`pd.get_dummies()`__ 関数を使うと簡単にワンホットエンコードできます。
`pd.get_dummies()` 関数に DataFrame を渡すと、すべてのカテゴリ特徴量カラム（通常は文字列を含むカラム）をワンホットエンコードしてくれます。
次の例では、`col1` カラムを `col1_AAA`、`col1_BBB`、`col1_CCC` という 3 つのカラムに変換し、`col2` カラムを `col2_X`、`col2_Y` という 2 つのカラムに変換しています。
これらのカラムのことを __ダミー特徴量 (dummy feature)__ と呼びます。

```python
df = pd.DataFrame({
    "col1": ["AAA", "BBB", "CCC", "BBB", "AAA"],
    "col2": ["X", "Y", np.nan, "X", np.nan]
})
df2 = pd.get_dummies(df)
print(df2)
```

{{< code title="実行結果" >}}
   col1_AAA  col1_BBB  col1_CCC  col2_X  col2_Y
0      True     False     False    True   False
1     False      True     False   False    True
2     False     False      True   False   False
3     False      True     False    True   False
4      True     False     False   False   False
{{< /code >}}

ワンホットエンコードするカラムを明示的に指定する場合は、__`columns=["col1", "col2"]`__ のように指定してください。

欠損値 (NaN) のカラムも生成するには、__`dummy_na=True`__ パラメーターを追加します。

```python
>>> pd.get_dummies(df, dummy_na=True)
   col1_AAA  col1_BBB  col1_CCC  col1_nan  col2_X  col2_Y  col2_nan
0      True     False     False     False    True   False     False
1     False      True     False     False   False    True     False
2     False     False      True     False   False   False      True
3     False      True     False     False    True   False     False
4      True     False     False     False   False   False      True
```

デフォルトでは上記のように `bool` 型のカラムに変換されますが、__`dtype=int`__ を指定することで、`int` 型 (0 or 1) のカラムに変換することができます。

```python
>>> pd.get_dummies(df, dtype=int)
   col1_AAA  col1_BBB  col1_CCC  col2_X  col2_Y
0         1         0         0       1       0
1         0         1         0       0       1
2         0         0         1       0       0
3         0         1         0       1       0
4         1         0         0       0       0
```

ワンホットエンコードを行う場合、線形依存（多重共線性: multicollinearity）を避けるために __作成された特徴量の 1 つをひとつを捨てる__ ことが推奨されれています（参考: [Dummy Variable Trap in Regression Models](https://www.algosome.com/articles/dummy-variable-trap-regression.html)）。
この処理は `pd.get_dummies()` 関数の __`drop_first`__ フラグを `True` にセットすることで、簡単に実行できます。
次の実行例では、`col1_AAA` カラムと `col2_X` カラムが削除されています。

```python
>>> pd.get_dummies(df, drop_first=True)
   col1_BBB  col1_CCC  col2_Y
0     False     False   False
1      True     False    True
2     False      True   False
3      True     False   False
4     False     False   False
```

順序カテゴリを数値化する
----

順序カテゴリ特徴量は、サイズ (S/M/L) や難易度 (easy/normal/hard) のように、本質的に順序付けが可能なカテゴリ特徴量です。
これらは 1、2、3 のような大小関係のあるデータに置き換えることで、機械学習モデルなどのインプットとして扱えるようになります。

もっともシンプルなやり方は、各クラスのどの数値に対応づけるかを自分で定義して置き換える方法です。
pandas の `Series` インスタンスの __`replace()`__ メソッド（あるいは `map()` メソッド）を使って値を置換できます。

```python
import pandas as pd

# サンプルデータ
df = pd.DataFrame({
    "Level": ["Easy", "Normal", "Hard", "Hard", "Easy"]
})

# 順序特徴量のマッピング定義
level_mapping = { "Easy": 1, "Normal": 2, "Hard": 3 }

# マッピング定義に従って変換
df["Level"] = df["Level"].replace(level_mapping)
print(df)
```

{{< code title="実行結果" >}}
   Level
0      1
1      2
2      3
3      3
4      1
{{< /code >}}

逆変換をして元のラベルに戻すには次のようにします。

```python
# 逆変換用のマッピング定義
inv_level_mapping = { val: key for key, val in level_mapping.items() }
df["Level"] = df["Level"].replace(inv_level_mapping)
```

scikit-learn の `OrdinalEncoder` を使う方法もありますが、逆にややこしかったりします。

