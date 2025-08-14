---
title: "Android Nativeメモ: ServiceManager に登録されたサービスを列挙する"
url: "p/qn635m9/"
date: "2011-05-09"
tags: ["android"]
aliases: [/android/list-services.html]
---

下記は、デバイス上の ServiceManager に登録されたサービスを列挙するためのプログラムです。
Android の make でビルドすると、**`ListServices`** という実行ファイルが生成されます。

{{< code lang="cpp" title="main.cpp" >}}
#undef LOG_TAG
#define LOG_TAG "ListServices"

#include <binder/IServiceManager.h>
#include <utils/Log.h>
#include <utils/RefBase.h>
#include <utils/Vector.h>
#include <utils/String16.h>
#include <utils/String8.h>
using namespace android;

static int compareServiceName(const String16* lhs, const String16* rhs) {
    return *lhs > *rhs;
}

int main() {
    sp<IServiceManager> sm = defaultServiceManager();
    Vector<String16> services = sm->listServices();
    size_t n = services.size();
    LOGI("%d services have found", n);
    services.sort(compareServiceName);
    for (size_t i = 0; i < n; ++i) {
        LOGI("[%2d] %s", i + 1, String8(services[i]).string());
    }
}
{{< /code >}}

{{< code lang="makefile" title="ListServices/Android.mk" >}}
LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := ListServices
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := main.cpp
LOCAL_SHARED_LIBRARIES := libcutils

include $(BUILD_EXECUTABLE)
{{< /code >}}

`m` コマンドなどで `ListServices` をビルドしたら、デバイスに転送してシェル上で実行します。
下記のように、ServiceManager に登録されたサービスが列挙されます。

```
# /system/bin/ListServices
04-28 16:38:47.108  1566  1566 I ListServices: 65 services have found
04-28 16:38:47.112  1566  1566 I ListServices: [ 1] SurfaceFlinger
04-28 16:38:47.112  1566  1566 I ListServices: [ 2] accessibility
04-28 16:38:47.112  1566  1566 I ListServices: [ 3] account
04-28 16:38:47.112  1566  1566 I ListServices: [ 4] activity
04-28 16:38:47.112  1566  1566 I ListServices: [ 5] alarm
...
```

