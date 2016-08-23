#!/usr/bin/env bash
set -e

cd git-repo

mvn clean test -Djava.version=1.7
