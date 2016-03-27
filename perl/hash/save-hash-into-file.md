---
title: ハッシュをファイルに保存する（DBMハッシュ）
created: 2008-03-06
---

**DBM ハッシュ**の機能を使うと、ハッシュの内容をファイル（DBM ファイル）に保存することができます。
DBM ハッシュは通常のハッシュと同じように操作することができます。

```perl
# DBM ファイルの open （なければ新規作成）
dbmopen(my %DATA, "data", 0644)
    or die "Failed to create DBM file: $!";

# データ追加
$DATA{'key'} = 'value';

# DBM ファイルの close
dbmclose(%DATA);
```

DBM ハッシュを使用する場合、以下のことに気をつける必要があります。

- DBM ハッシュの変数名は、ファイルに結び付けられていることを示すために大文字にする慣習がある。
- DBM ファイル名には拡張子を付けない。通常 `.dir` や `.pag` という拡張子を持つ 2 つのファイルが生成されます。
- システムによっては DBM ファイルのサイズに制限があることもある。

次のようにすれば DBM ファイルからエントリを削除できます。

```perl
delete $DATA{'key'};
```

キー、値の列挙も通常のハッシュと同様に `while` や `each` で行えます。

```perl
while (my($key, $val) = each(%DATA)) {
    print "$key => $val\n";
}
```

