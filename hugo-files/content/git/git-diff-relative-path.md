---
title: "Gitメモ: Git / git diff の出力で相対パスを使うようにする (git diff --relative)"
url: "p/w266n3s/"
date: "2011-02-09"
tags: ["git"]
aliases: [/git/git-diff-relative-path.html]
---

`git diff` の出力に含まれるファイルのパスは、デフォルトでは .git ディレクトリのあるディレクトリからのパスになります。

{{< code lang="console" title="git diff のデフォルトの振る舞い" >}}
$ git diff system/core/init/init.c
diff --git a/android/system/core/init/init.c b/android/system/core/init/init.c
index eb5db89..98878f2 100644
--- a/android/system/core/init/init.c
+++ b/android/system/core/init/init.c
@@ -1385,7 +1385,11 @@ int main(int argc, char **argv)
...
{{< /code >}}

カレントディレクトリからの相対パスで出力したい場合は、オプションに **`--relative`** を指定します。

{{< code lang="console" title="相対パスで表示" >}}
$ git diff --relative system/core/init/init.c
diff --git a/system/core/init/init.c b/system/core/init/init.c
index eb5db89..98878f2 100644
--- a/system/core/init/init.c
+++ b/system/core/init/init.c
@@ -1385,7 +1385,11 @@ int main(int argc, char **argv)
...
{{< /code >}}

さらに、`a/` や `b/` のプレフィックスを付けないようにするには、オプション **`--no-prefix`** を指定します。

{{< code lang="console" title="プレフィックスを省略" >}}
$ git diff --relative --no-prefix system/core/init/init.c
diff --git system/core/init/init.c system/core/init/init.c
index eb5db89..98878f2 100644
--- system/core/init/init.c
+++ system/core/init/init.c
@@ -1385,7 +1385,11 @@ int main(int argc, char **argv)
...
{{< /code >}}

