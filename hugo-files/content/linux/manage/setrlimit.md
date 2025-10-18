---
title: "Linuxメモ: Linux システムコールを使用して core dump を吐かないようにする (setrlimit)"
url: "p/afze7gn/"
date: "2011-10-11"
tags: ["linux"]
aliases: ["/linux/manage/setrlimit.html"]
---

Linux プロセスが異常終了した場合に吐き出される core dump ファイルはセキュリティホールになり得ます。
core dump ファイルを出力しないようにするには、各プロセスで以下のように Linux システムコールの **`setrlimit`** を実行します。

{{< code lang="cpp" title="core dump を吐かないようにする (C++)" >}}
// #include <sys/time.h>
// #include <sys/resource.h>

rlimit rl;
rl.rlim_cur = 0;  // soft limit
rl.rlim_max = 0;  // hard limit
if (setrlimit(RLIMIT_CORE, &rl) != 0) {
    // setrlimit failed! exit process
}
{{< /code >}}

このように実行しておけば、仮に root を奪取されて `ulimit -c` コマンドを実行されても core dump を吐かなくなります。

