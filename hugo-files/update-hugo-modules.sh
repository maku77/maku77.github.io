#!/bin/sh
go mod tidy  # これを入れておくと go.sum のエントリーが最新だけになっていい感じ
hugo mod get -u github.com/maku77/hugo-module-maku-common
