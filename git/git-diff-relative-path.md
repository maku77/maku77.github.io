---
title: Git / git diff の出力で相対パスを使うようにする
date: "2011-02-09"
---

`git diff` の出力に含まれるファイルのパスは、デフォルトでは .git ディレクトリのあるディレクトリからのパスになります。

#### git diff（オプションなし）

```bash
$ git diff system/core/init/init.c
diff --git a/android/system/core/init/init.c b/android/system/core/init/init.c
index eb5db89..98878f2 100644
--- a/android/system/core/init/init.c
+++ b/android/system/core/init/init.c
@@ -1385,7 +1385,11 @@ int main(int argc, char **argv)
...
```

カレントディレクトリからの相対パスで出力したい場合は、オプションに `--relative` を指定します。

#### git diff --relative
```bash
$ git diff --relative system/core/init/init.c
diff --git a/system/core/init/init.c b/system/core/init/init.c
index eb5db89..98878f2 100644
--- a/system/core/init/init.c
+++ b/system/core/init/init.c
@@ -1385,7 +1385,11 @@ int main(int argc, char **argv)
...
```

さらに、`a/` や `b/` のプレフィックスを付けないようにするには、オプション `--no-prefix` を指定します。

#### git diff --relative --no-prefix
```bash
$ git diff --relative --no-prefix system/core/init/init.c
diff --git system/core/init/init.c system/core/init/init.c
index eb5db89..98878f2 100644
--- system/core/init/init.c
+++ system/core/init/init.c
@@ -1385,7 +1385,11 @@ int main(int argc, char **argv)
...
```

