#!/bin/bash
find . -name '*.html' -not -path '*/vendor/*' | xargs grep 'http:'

