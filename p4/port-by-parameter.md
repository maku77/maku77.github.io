---
title: p4 コマンドの引数で接続先や使用するクライアント名を指定する
created: 2015-10-15
layout: p4
---

p4 のオプションにより接続情報を指定する
====

`p4` コマンドによる接続先のサーバは `P4PORT` 変数、使用するクライアントは `P4CLIENT` 変数で指定しておくのが一般的な使い方ですが、`p4` コマンド実行時のオプションで指定することも可能です。
一時的に接続先を変えたい場合や、スクリプト内で `p4` コマンドを実行したい場合などに便利です。

#### 書式

```
$ p4 -p <port> -u <user> -P <pass> <command>
```

#### 具体例

```
$ p4 -p p4.example.com:2666 -u maku -P pass files //...
```


ヘルプ
====
ちなみにこのようなパラメータ (options) として、他にどのようなものを指定できるかは、`p4 help usage` で確認することができます。

```
$ p4 help usage

    Perforce client usage:

    p4 [options] command [arg ...]
    p4 -V

        options:
            -b batchsize -c client -C charset -d dir -H host -G -L language
            -p port -P pass -s -Q charset -u user -x file -z tag

        The -b batchsize specifies a batch size (number of arguments) to
        use when processing a command from a file '-x' (default 128).

        The -c flag specifies the client name, overriding the value of
        $P4CLIENT in the environment and the default (the hostname).

        The -C flag specifies the client's character set, overriding the
        value of $P4CHARSET in the environment.  See 'p4 help charset'
        for more information.

        The -d flag specifies the current directory, overriding the value of
        $PWD in the environment and the default (the current directory).

        The -G flag formats all output (and batch input for form commands
        with -i) as marshalled Python dictionary objects.

        The -H flag specifies the host name, overriding the value of
        $P4HOST in the environment and the default (the hostname).

        The -L flag specifies the language for text messages from the
        server, overriding the value of $P4LANGUAGE in the environment.
        Setting this option only works if the administrator has loaded
        support for non-English messages into the server database.

        The -p flag specifies the server's listen address, overriding the
        value of $P4PORT in the environment and the default (perforce:1666).

        The -P flag specifies the password, overriding the value of
        $P4PASSWD in the environment.

        The -Q flag specifies the client's command character set,
        overriding the value of $P4COMMANDCHARSET in the environment.
        See 'p4 help charset' for more information.

        The -s flag causes the p4 command line client program to prefix
        each line of output with a tag (error, warning, info, text, exit)
        to make it easier to use for scripting.

        The -u flag specifies the user name, overriding the value of
        $P4USER, $USER, and $USERNAME in the environment.

        The -x flag instructs p4 to read arguments, one per line, from the
        specified file.  If you specify '-', standard input is read.

        The -V flag displays the version of the p4 client command and exits.

        The -z tag option returns output of reporting commands in the
        format returned by 'p4 fstat'.
```

