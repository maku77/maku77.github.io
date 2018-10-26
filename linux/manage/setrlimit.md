---
title: "Linux システムコールを使用して core dump を吐かないようにする (setrlimit)"
date: "2011-10-11"
---

Linux プロセスが異常終了した場合に吐き出される core dump ファイルはセキュリティホールになり得ます。
core dump ファイルを出力しないようにするには、各プロセスで以下のように Linux システムコールの `setrlimit` を実行します。

~~~ cpp
// #include <sys/time.h>
// #include <sys/resource.h>

rlimit rl;
rl.rlim_cur = 0;  // soft limit
rl.rlim_max = 0;  // hard limit
if (setrlimit(RLIMIT_CORE, &no_core) != 0) {
    // setrlimit failed! exit process
}
~~~

このように実行しておけば、仮に root を奪取されて `ulimit -c` コマンドを実行されても core dump を吐かなくなります。

参考: [IPA - coreファイルから情報が漏れる](https://www.ipa.go.jp/security/awareness/vendor/programmingv1/b07_06.html)

