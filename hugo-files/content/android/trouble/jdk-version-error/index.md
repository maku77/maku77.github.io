---
title: "Androidトラブルシューティング: JDK のバージョンが原因でビルドが失敗する場合"
url: "p/jhsiowu/"
date: "2025-10-10"
tags: ["android"]
---

Android プロジェクト内で特定の JDK バージョンによるビルドが要求されている場合、Android Studio 内の JBR (JetBrains Runtime) ではバージョンが合わず、ビルドが失敗することがあります。
そのような場合は、次のように JDK を追加することで解決できます。

1. **`Ctrl + Shift + A`** で "Find Action" を開く
2. **`Download JDK...`** を選択
3. 下のような画面が表示されるので JDK の Version などを指定してダウンロード

{{< image src="img-001.png" title="JDK の追加ダウンロード" >}}

{{< code title="エラーメッセージの例" >}}
A problem occurred configuring project ':app'.
> Failed to calculate the value of task ':app:compileDebugJavaWithJavac' property 'javaCompiler'.
   > Cannot find a Java installation on your machine matching this tasks requirements: {languageVersion=17, vendor=any, implementation=vendor-specific} for WINDOWS on x86_64.
      > No locally installed toolchains match and toolchain download repositories have not been configured.

* Try:
> Learn more about toolchain auto-detection at https://docs.gradle.org/8.8/userguide/toolchains.html#sec:auto_detection.
> Learn more about toolchain repositories at https://docs.gradle.org/8.8/userguide/toolchains.html#sub:download_repositories.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.
> Get more help at https://help.gradle.org.

* Exception is:
org.gradle.api.ProjectConfigurationException: A problem occurred configuring project ':app'.
	at org.gradle.configuration.project.LifecycleProjectEvaluator.wrapException(LifecycleProjectEvaluator.java:84)
	at org.gradle.configuration.project.LifecycleProjectEvaluator.addConfigurationFailure(LifecycleProjectEvaluator.java:77)
	at org.gradle.configuration.project.LifecycleProjectEvaluator.access$500(LifecycleProjectEvaluator.java:55)
Caused by: org.gradle.jvm.toolchain.internal.ToolchainDownloadFailedException: No locally installed toolchains match and toolchain download repositories have not been configured.
	at org.gradle.jvm.toolchain.internal.install.DefaultJavaToolchainProvisioningService.tryInstall(DefaultJavaToolchainProvisioningService.java:118)
	at org.gradle.jvm.toolchain.internal.JavaToolchainQueryService.downloadToolchain(JavaToolchainQueryService.java:178)
	... 243 more
{{< /code >}}

