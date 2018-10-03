---
title: "Perl で使用可能なテストモジュール"
date: "2008-07-08"
---

Perl ディストリビューション 5.8 以降では、デフォルトで、

- `Test::Simple`
- `Test::More`

などのテストモジュールを使用できるようになっています。

下記は、`perldoc Test::Simple` で読むことのできるマニュアルのサンプルコードの抜粋です。

~~~ perl
use Test::Simple tests => 5;
use Film;  # What you're testing.

my $btaste = Film->new({ Title    => 'Bad Taste',
                         Director => 'Peter Jackson',
                         Rating   => 'R',
                         NumExplodingSheep => 1
                       });

ok( defined($btaste) && ref $btaste eq 'Film', 'new() works' );
ok( $btaste->Title      eq 'Bad Taste',     'Title() get'    );
ok( $btaste->Director   eq 'Peter Jackson', 'Director() get' );
ok( $btaste->Rating     eq 'R',             'Rating() get'   );
ok( $btaste->NumExplodingSheep == 1,        'NumExplodingSheep() get' );
~~~

