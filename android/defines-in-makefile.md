---
title: android/build/core 以下の *.mk ファイルで定義されている関数 (define) のリスト
created: 2011-04-08
---

下記のスクリプトを `android/build/core` 以下で実行すると、Android の Makefile (.mk) 用に定義されている関数を一覧で取得できます。
Android が定義している関数はハイフンを単語区切りにしているみたいです。

#### list_defines.sh
```bash
#!/bin/bash
find -name '*.mk' -o -name 'Makefile' | xargs grep '^define ' | sed 's/^\(.\+\):\(.\+\)/\2  ...  \1/' | sort | uniq
```

#### 使用例
```
$ cd android/build/core
$ ./list_defines.sh

define add-assets-to-package  ...  ./definitions.mk
define _add-clean-step  ...  ./cleanbuild.mk
define add-clean-step  ...  ./cleanbuild.mk
define add-dependency  ...  ./definitions.mk
define add-dex-to-package  ...  ./definitions.mk
define add-java-resources-to-package  ...  ./definitions.mk
define add-jni-shared-libs-to-package  ...  ./definitions.mk
define add-prebuilt-file  ...  ./definitions.mk
define add-prebuilt-files  ...  ./definitions.mk
define add-radio-file  ...  ./definitions.mk
define add-radio-file-internal  ...  ./definitions.mk
define add-required-deps  ...  ./main.mk
define align-package  ...  ./definitions.mk
define all-c-files-under  ...  ./definitions.mk
define all-Iaidl-files-under  ...  ./definitions.mk
define all-java-files-under  ...  ./definitions.mk
define all-makefiles-under  ...  ./definitions.mk
define all-named-subdir-makefiles  ...  ./definitions.mk
define all-proto-files-under  ...  ./definitions.mk
define all-subdir-c-files  ...  ./definitions.mk
define all-subdir-html-files  ...  ./definitions.mk
define all-subdir-Iaidl-files  ...  ./definitions.mk
define all-subdir-java-files  ...  ./definitions.mk
define all-subdir-makefiles  ...  ./definitions.mk
define append-path  ...  ./definitions.mk
define assert-max-file-size  ...  ./definitions.mk
define assert-max-image-size  ...  ./definitions.mk
define auto-prebuilt-boilerplate  ...  ./multi_prebuilt.mk
define build-systemimage-target  ...  ./Makefile
define build-systemtarball-target  ...  ./Makefile
define build-userdataimage-target  ...  ./Makefile
define build-userdatatarball-target  ...  ./Makefile
define check-all-products  ...  ./product.mk
define check-api  ...  ./tasks/apicheck.mk
define clear-var-list  ...  ./node_fns.mk
define collapse-pairs  ...  ./definitions.mk
define combine-notice-files  ...  ./Makefile
define copy-file-to-new-target  ...  ./definitions.mk
define copy-file-to-new-target-with-cp  ...  ./definitions.mk
define copy-file-to-target  ...  ./definitions.mk
define copy-file-to-target-strip-comments  ...  ./definitions.mk
define copy-file-to-target-with-cp-and-permissions  ...  ./definitions.mk
define copy-file-to-target-with-cp  ...  ./definitions.mk
define copy-file-to-target-with-permissions  ...  ./definitions.mk
define copy-file-to-target-with-zipalign  ...  ./definitions.mk
define copy-one-dist-file  ...  ./distdir.mk
define copy-one-file  ...  ./definitions.mk
define copy-one-header  ...  ./definitions.mk
define copy-testcase-apk  ...  ./tasks/cts.mk
define copy-var-list  ...  ./node_fns.mk
define create-empty-package  ...  ./definitions.mk
define create-java-proto-rule  ...  ./definitions.mk
define create-resource-java-files  ...  ./definitions.mk
define default-locale-language  ...  ./Makefile
define default-locale  ...  ./Makefile
define default-locale-region  ...  ./Makefile
define dist-for-goals  ...  ./distdir.mk
define doc-timestamp-for  ...  ./definitions.mk
define dump-device  ...  ./device.mk
define dump-devices  ...  ./device.mk
define dump-module-variables  ...  ./definitions.mk
define dump-product  ...  ./product.mk
define dump-products  ...  ./product.mk
define dump-words-to-file  ...  ./definitions.mk
define emit-line  ...  ./definitions.mk
define _expand-inherited-values  ...  ./node_fns.mk
define extract-and-include-whole-static-libs  ...  ./definitions.mk
define _find-android-products-files  ...  ./product.mk
define find-copy-subdir-files  ...  ./product_config.mk
define find-other-html-files  ...  ./definitions.mk
define find-other-java-files  ...  ./definitions.mk
define find-parent-file  ...  ./definitions.mk
define find-subdir-assets  ...  ./definitions.mk
define find-subdir-files  ...  ./definitions.mk
define find-subdir-subdir-files  ...  ./definitions.mk
define first-makefiles-under  ...  ./definitions.mk
define generate-core-test-description  ...  ./tasks/cts.mk
define get-all-product-makefiles  ...  ./product.mk
define get-file-size  ...  ./combo/darwin-x86.mk
define get-file-size  ...  ./combo/linux-x86.mk
define get-file-size  ...  ./combo/windows-x86.mk
define get-inherited-nodes  ...  ./node_fns.mk
define _get-package-overrides  ...  ./definitions.mk
define get-package-overrides  ...  ./definitions.mk
define get-tagged-modules  ...  ./definitions.mk
define image-size-from-data-size  ...  ./definitions.mk
define import-devices  ...  ./device.mk
define _import-node  ...  ./node_fns.mk
define _import-nodes-inner  ...  ./node_fns.mk
define import-nodes  ...  ./node_fns.mk
define import-products  ...  ./product.mk
define include-path-for  ...  ./pathmap.mk
define include-prebuilt  ...  ./definitions.mk
define inherit-device  ...  ./device.mk
define inherit-product  ...  ./product.mk
define insert-liblog  ...  ./binary.mk
define install-dex-debug  ...  ./definitions.mk
define intermediates-dir-for  ...  ./definitions.mk
define is-c-identifier  ...  ./product_config.mk
define _java-lib-classes.jar  ...  ./definitions.mk
define _java-lib-dep  ...  ./definitions.mk
define java-lib-deps  ...  ./definitions.mk
define _java-lib-dir  ...  ./definitions.mk
define java-lib-files  ...  ./definitions.mk
define _java-lib-full-classes.jar  ...  ./definitions.mk
define _java-lib-full-dep  ...  ./definitions.mk
define local-intermediates-dir  ...  ./definitions.mk
define module-built-files  ...  ./definitions.mk
define module-installed-files  ...  ./definitions.mk
define module-names-for-tag-list  ...  ./definitions.mk
define modules-for-tag-list  ...  ./definitions.mk
define module-stubs-files  ...  ./definitions.mk
define move-var-list  ...  ./node_fns.mk
define my-dir  ...  ./definitions.mk
define normalize-host-libraries  ...  ./definitions.mk
define normalize-libraries  ...  ./definitions.mk
define normalize-path-list  ...  ./definitions.mk
define normalize-target-libraries  ...  ./definitions.mk
define obfuscate-jar  ...  ./definitions.mk
define package_files-copy-root  ...  ./Makefile
define prepare-doc-source-list  ...  ./droiddoc.mk
define pretty  ...  ./definitions.mk
define print-vars  ...  ./definitions.mk
define _resolve-short-device-name  ...  ./device.mk
define resolve-short-device-name  ...  ./device.mk
define _resolve-short-product-name  ...  ./product.mk
define resolve-short-product-name  ...  ./product.mk
define select-android-config-h  ...  ./config.mk
define should-install-to-system  ...  ./main.mk
define sign-package  ...  ./definitions.mk
define sign-package-full  ...  ./definitions.mk
define tranform-host-m-to-o  ...  ./definitions.mk
define transform-aidl-to-java  ...  ./definitions.mk
define transform-classes.jar-to-dex  ...  ./definitions.mk
define transform-classes.jar-to-emma  ...  ./definitions.mk
define transform-c-or-s-to-o-no-deps  ...  ./definitions.mk
define transform-cpp-to-o  ...  ./definitions.mk
define transform-c-to-o  ...  ./definitions.mk
define transform-c-to-o-no-deps  ...  ./definitions.mk
define transform-d-to-p  ...  ./definitions.mk
define transform-generated-source  ...  ./definitions.mk
define transform-host-c-or-s-to-o-no-deps  ...  ./definitions.mk
define transform-host-cpp-to-o  ...  ./definitions.mk
define transform-host-c-to-o  ...  ./definitions.mk
define transform-host-c-to-o-no-deps  ...  ./definitions.mk
define transform-host-java-to-package  ...  ./definitions.mk
define transform-host-m-to-o-no-deps  ...  ./definitions.mk
define transform-host-o-to-executable  ...  ./definitions.mk
define transform-host-o-to-executable-inner  ...  ./combo/darwin-x86.mk
define transform-host-o-to-executable-inner  ...  ./definitions.mk
define transform-host-o-to-package  ...  ./definitions.mk
define transform-host-o-to-shared-lib  ...  ./definitions.mk
define transform-host-o-to-shared-lib-inner  ...  ./combo/darwin-x86.mk
define transform-host-o-to-shared-lib-inner  ...  ./definitions.mk
define transform-host-o-to-static-lib  ...  ./definitions.mk
define transform-host-ranlib-copy-hack  ...  ./definitions.mk
define transform-host-s-to-o  ...  ./definitions.mk
define transform-host-s-to-o-no-deps  ...  ./definitions.mk
define transform-java-to-classes.jar  ...  ./definitions.mk
define transform-l-to-cpp  ...  ./definitions.mk
define transform-m-to-o  ...  ./definitions.mk
define transform-m-to-o-no-deps  ...  ./definitions.mk
define transform-o-to-executable  ...  ./definitions.mk
define transform-o-to-executable-inner  ...  ./combo/darwin-x86.mk
define transform-o-to-executable-inner  ...  ./combo/linux-arm.mk
define transform-o-to-executable-inner  ...  ./combo/target_linux-fishtank.mk
define transform-o-to-executable-inner  ...  ./combo/target_linux-x86.mk
define transform-o-to-executable-inner  ...  ./definitions.mk
define transform-o-to-package  ...  ./definitions.mk
define transform-o-to-shared-lib  ...  ./definitions.mk
define transform-o-to-shared-lib-inner  ...  ./combo/darwin-x86.mk
define transform-o-to-shared-lib-inner  ...  ./combo/linux-arm.mk
define transform-o-to-shared-lib-inner  ...  ./combo/target_linux-fishtank.mk
define transform-o-to-shared-lib-inner  ...  ./combo/target_linux-x86.mk
define transform-o-to-shared-lib-inner  ...  ./definitions.mk
define transform-o-to-static-executable  ...  ./definitions.mk
define transform-o-to-static-executable-inner  ...  ./combo/darwin-x86.mk
define transform-o-to-static-executable-inner  ...  ./combo/linux-arm.mk
define transform-o-to-static-executable-inner  ...  ./combo/target_linux-fishtank.mk
define transform-o-to-static-executable-inner  ...  ./combo/target_linux-x86.mk
define transform-o-to-static-executable-inner  ...  ./definitions.mk
define transform-o-to-static-lib  ...  ./definitions.mk
define transform-prebuilt-to-target  ...  ./definitions.mk
define transform-prebuilt-to-target-strip-comments  ...  ./definitions.mk
define transform-prebuilt-to-target-with-zipalign  ...  ./definitions.mk
define transform-proto-to-cpp  ...  ./definitions.mk
define transform-ranlib-copy-hack  ...  ./definitions.mk
define transform-s-to-o  ...  ./definitions.mk
define transform-s-to-o-no-deps  ...  ./definitions.mk
define transform-to-prelinked  ...  ./definitions.mk
define transform-to-stripped  ...  ./definitions.mk
define transform-variables  ...  ./definitions.mk
define transform-y-to-cpp  ...  ./definitions.mk
define true-or-empty  ...  ./definitions.mk
define uniq-word  ...  ./node_fns.mk
define unzip-jar-files  ...  ./definitions.mk
define word-color  ...  ./definitions.mk
```

