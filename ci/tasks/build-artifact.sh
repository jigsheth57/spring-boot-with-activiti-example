#!/usr/bin/env bash
set -e
version=`cat version/number`

cd git-repo

mvn clean versions:set -DnewVersion=$version
mvn package -DskipTests=true -Djava.version=1.7

mv target/*.jar ../build-artifact
ls -laF ../build-artifact
