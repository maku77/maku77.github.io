---
title: Git でコミットログを grep する
created: 2010-12-09
---

`git log` コマンドを使用するときに、`--grep` オプションを使用すると、指定したパターンが変更内容に含まれるコミットだけを表示することができます。

```
$ git log --grep=<RegularExpression>
```

