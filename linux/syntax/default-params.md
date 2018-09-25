---
title: "関数のデフォルト引数を定義する"
date: "2012-12-09"
---

以下の関数では、パラメータを何も指定しないで呼び出すと、`par1` ローカル変数に `ABC` が入ります。
パラメータを指定すると、そちらが使用されます。

#### param-test.sh

```bash
function param-test() {
  local par1=${1:-ABC};
  echo "param = $par1"
}
```

#### 実行例

```
$ . param-test.sh
$ param-test
param = ABC

$ param-test 10000
param = 10000
```

