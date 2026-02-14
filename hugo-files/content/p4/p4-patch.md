---
title: "Perforceメモ: p4-patch：変更されたファイルの diff からパッチファイルを作る／パッチファイルを適用する"
url: "p/wbdr5m4/"
date: "2012-09-28"
tags: ["perforce"]
aliases: ["/p4/p4-patch.html"]
---

## 使い方

{{< code lang="console" title="(1) パッチファイルを作成する（独自形式）" >}}
$ ./p4-patch-create.py > test.p4patch
{{< /code >}}

※注: このスクリプトでは、`p4 diff` コマンドの出力を使用しています。`p4 diff` で出力されない、新規ファイルなどの内容は出力されないことに注意してください。

{{< code lang="console" title="(2) パッチファイルを適用する" >}}
$ ./p4-patch-apply.py < test.p4patch
{{< /code >}}

ここでは、手順 (1) で作成したパッチファイルを適用しています。
`p4 edit` コマンドも自動的に実行します。


## スクリプト

{{< code lang="python" title="p4-patch-create.py" >}}
#!/usr/bin/env python
#######################################################
# Perforce tool
# Create p4patch file using p4 diff command.
#
# Usage:
#   $ ./p4-patch-create.py > test.p4patch
#
#######################################################
import subprocess

def do_command(command):
    return subprocess.check_output(command, shell=True)

def get_p4path(path):
    return do_command('p4 where ' + path).split()[0]

### First value of returned tuple means that it should be written.
def convert_path(line):
    if line.startswith('--- '):
        return (False, )
    if line.startswith('+++ '):
        line =  '## ' + get_p4path(line.split()[1])
    return (True, line)

if __name__ == '__main__':
    for line in do_command('p4 diff -du').split('\n'):
        ret = convert_path(line)
        if (ret[0]):
            print(ret[1])
{{< /code >}}

{{< code lang="python" title="p4-patch-apply.py" >}}
#!/usr/bin/env python
#######################################################
# Perforce tool
# Apply p4patch file to your local files.
#
# Usage:
#   $ ./p4-patch-apply.py < test.p4patch
#
#######################################################
import subprocess
import sys

def do_p4edit(path):
    subprocess.call('p4 edit ' + path, shell=True)

def get_local_path(path):
    output = subprocess.check_output('p4 where ' + path, shell=True)
    return output.split()[2]

def apply_patch(patch_text):
    p = subprocess.Popen('patch -p0 -N', shell=True, stdin=subprocess.PIPE)
    p.stdin.write(patch_text)
    p.stdin.close()

### If the first value of returned value is True,
### it means that the second value is a local file path.
### Otherwise, the second value is the same as the passed parameter.
def convert_path(line):
    if line.startswith('## '):
        local_path = get_local_path(line.split()[1])
        return (True, local_path)
    return (False, line)

if __name__ == '__main__':
    patch_text = ''
    for line in sys.stdin:
        line = line.rstrip('\r\n')
        ret = convert_path(line)
        if (ret[0]):
            patch_text += '--- ' + ret[1] + '\n'
            patch_text += '+++ ' + ret[1] + '\n'
            do_p4edit(ret[1])
        else:
            patch_text += ret[1] + '\n'

    print('')
    apply_patch(patch_text)
{{< /code >}}


## 解説

結論から言うと、**p4 diff** コマンドでは、**GNU patch** コマンドが扱える形式のパッチファイルを作ることはできません。
仮に近いものが作れたとしても、次のような理由により、他のユーザーはそのパッチファイルを適用することができません。

* `p4 diff` コマンドには unified 形式の diff を出力する `-du` オプションがあるが、ファイルパスが Perforce の独自形式のパスになっている。
* 各ユーザは P4 クライアント設定により、サーバ上のファイルを任意のローカルパスにマッピングできるので、全ユーザが共通して使用できるファイルパスが存在しない。

そこで、ここでは、以下のようなアプローチで解決しています。

* パッチファイルを作成するときは独自スクリプトを使い、パッチファイル内のファイルパスには、P4 サーバ上のファイルパス（P4 独自のパス形式）を出力するようにする。このパッチファイルの拡張子を `.p4patch` とします。
* パッチファイル (`.p4patch`) を適用するときは、独自のスクリプトを使い、パッチファイル内のファイルパスを、そのクライアントのローカルファイルパスに書き換えてから適用する。

作成するパッチファイル内には、以下のように Perforce のパス形式を含めるようにします。
`##` で始まっている行が、p4patch ファイル独自の Perforce パスを示している行です。

{{< code lang="patch" title="sample.p4patch（独自の patch 形式）" >}}
## //Project/dir1/dir2/sample.txt
@@ -1,1 +1,1 @@
- AAA
+ BBB
{{< /code >}}

そして、パッチファイルを適用するときに、`##` で始まる Perforce パスを、そのユーザのローカルパスに変換してから適用します。

{{< code lang="patch" title="sample.patch（通常の patch 形式）" >}}
--- /home/jack/p4work/Project/dir1/dir2/sample.txt
+++ /home/jack/p4work/Project/dir1/dir2/sample.txt
@@ -1,1 +1,1 @@
- AAA
+ BBB
{{< /code >}}
