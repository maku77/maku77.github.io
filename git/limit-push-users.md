---
title: 特定のユーザのみ git push できるように制限する
date: "2010-08-26"
---

git push できるユーザをホワイトリストで管理する
====

指定したユーザにだけ `git push` を許可するには、`git push` 時にサーバサイドで実行される `.git/hooks/update` というスクリプトを利用します。
このスクリプトが `0` 以外の値を返して終了するとき、ユーザの `push` が失敗することを利用してスクリプトを作成します。

以下のサンプルスクリプトでは、中央リポジトリの `.git/hooks/authorized_users` に記述されたユーザにのみ push を許可しています。
`authorized_users` ファイルの中身はこのような感じです。
コメントも記述できます。
ユーザ名が 1 つも存在しない場合は、すべてのユーザの push を許可します。

#### .git/hooks/authorized_users

```bash
# Authorized Users
user1  # Jack
user2  # Smith
user3  # Justine
```

中央リポジトリの `.git/hooks/update` スクリプトの内容は以下のような感じで記述します。

#### .git/hooks/update

```bash
#!/bin/sh

# Branch name the user is about to push to.
branch_name="$1"

# Fullpath of hooks dir.
self_dir=$(cd $(dirname $0); pwd)

# 1 if there is no entries in the authorized_users.
no_entry=1

# Search user names.
while read line
do
    # Remove comment.
    line=${line%%#*}

    # Remove trailing spaces.
    line=$(echo $line | sed -e 's/ *$//')

    if [ -z "$line" ]; then
        continue
    fi
    if [ "$USER" = "$line" ]; then
        # The user can push to the branch.
        exit 0
    fi
    no_entry=0
done < "$self_dir/authorized_users"

if [ $no_entry = 1 ]; then
    # No restriction.
    # The user can push to the branch.
    exit 0
fi

echo "----------------------------------------------"
echo "[$branch_name] is in submit control."
echo "You have no access to the branch."
echo "Please wait until the submit control finishes."
echo "----------------------------------------------"

# The user cannot push to the branch.
exit 1
```


特定のブランチだけサブミットコントロールをかける
====

さらに、ブランチ毎に push 権限をコントロールできるようにする update スクリプトの例です。
このスクリプトでは、ユーザのアクセス制限を以下のように設定ファイル (`authorized_users`) でかけることができます。

例: Submit コントロールをかけない場合
----

#### .git/hooks/authorized_users
```bash
# authorized_users の中身を空にする（コメントは記述してよい）
```

例: master ブランチは user1, user2, user3 にだけ push を許可する
----

#### .git/hooks/authorized_users
```bash
[refs/heads/master]
user1
user2
user3
```

例: master ブランチは全員 push 不可
----

#### .git/hooks/authorized_users
```bash
[refs/heads/master]
# ブランチ名だけ上記のように指定し、ユーザ名を記述しない。
```

例: master ブランチは全員 push 不可、maint ブランチは user1 だけ push 許可。
----

#### .git/hooks/authorized_users
```bash
[refs/heads/master]

[refs/heads/maint]
user1
```

update スクリプトは下記のような感じで作成できます。

#### .git/hooks/update
```bash
#!/bin/sh
#
# Git's update hook for submit control [2010-08-27]
#

# User setting file
readonly AUTH_USERS_FILE='authorized_users'

# Branch name the user is about to push to.
target_branch="$1"

# Fullpath of hooks dir.
self_dir=$(cd $(dirname $0); pwd)

# 1 if the target branch name can be found in the $AUTH_USERS_FILE.
is_restricted=0

# Parenthetic section which represents a branch name in the $AUTH_USERS_FILE.
current_section=''

# Search user names.
while read line
do
    # Remove comment.
    line=${line%%#*}

    # Remove trailing spaces.
    line=$(echo $line | sed -e 's/ *$//')

    # Empty line.
    if [ -z "$line" ]; then
        continue
    fi

    # Parse section name. e.g. 'refs/heads/master'.
    section=$(echo $line | sed -n -e 's/\[\(.\+\)\]/\1/p')
    if [ -n "$section" ]; then
        if [ "$section" = "$target_branch" ]; then
            is_restricted=1
        fi
        current_section="$section"
        continue
    fi

    # Check if the user is listed.
    if [ "$current_section" = "$target_branch" ]; then
        if [ "$USER" = "$line" ]; then
            # The user can push to the branch.
            exit 0
        fi
    fi
done < "$self_dir/$AUTH_USERS_FILE"

if [ $is_restricted = 0 ]; then
    # No restriction. The user can push to the branch.
    exit 0
fi

echo ""
echo "----------------------------------------------"
echo "[$target_branch] is in submit control."
echo "You do not have access to this branch."
echo "Please wait until the submit control finishes."
echo "----------------------------------------------"
echo ""

# The user cannot push to the branch.
exit 1
```


#### デフォルトの .git/hooks/authorized_users
```bash
#
# Authorized users
#
# e.x) Allow user1, user2, user3 to push to the master branch.
#
# [refs/heads/master]
# user1
# user2
# user3
#
# If there is no restriction, empty this file.
```

