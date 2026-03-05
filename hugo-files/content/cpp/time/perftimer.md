---
title: "C++メモ: プログラムの実行時間を計測するクラス (PerfTimer) を作成する"
url: "p/opiv99h/"
date: "2011-06-28"
tags: ["cpp"]
aliases: /cpp/time/perftimer.html
---

下記は、`gettimeofday` を使用した、パフォーマンス計測用クラス `PerfTimer` の実装例です。

{{< code lang="cpp" title="PerfTimer.h" >}}
#ifndef PERF_TIMER_H
#define PERF_TIMER_H

#include <sys/time.h>

class PerfTimer {
private:
    timeval startTime;

public:
    bool start() {
        return gettimeofday(&startTime, NULL) == 0;
    }

    const timeval getDelta() {
        timeval curr;
        timeval delta;
        gettimeofday(&curr, NULL);
        timersub(&curr, &startTime, &delta);

        return delta;
    }
};

#endif // PERF_TIMER_H
{{< /code >}}

{{< code lang="cpp" title="使用方法" >}}
PerfTimer timer;

timer.start();
// ...計測したい処理...
timeval delta = timer.getDelta();

printf("delta = %ld.%ld\n", delta.tv_sec, delta.tv_usec / 1000);
{{< /code >}}

