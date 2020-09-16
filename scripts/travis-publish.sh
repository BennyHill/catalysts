#!/bin/bash

# Example setting to use at command line for testing:
# export TRAVIS_SCALA_VERSION=2.10.5;export TRAVIS_PULL_REQUEST="false";export TRAVIS_BRANCH="master"

export publish_cmd="publishLocal"

if [[ $TRAVIS_PULL_REQUEST == "false" && $TRAVIS_BRANCH == "master" && $(cat version.sbt) =~ "-SNAPSHOT" ]]; then
  export publish_cmd="publish gitSnapshots publish"
  #if [[ $TRAVIS_SCALA_VERSION = "2.11.7" ]]; then
  #  export publish_cmd="docs/makeSite  $publish_cmd ghpagesPushSite"
  #fi
fi

sbt_cmd="sbt ++$TRAVIS_SCALA_VERSION"

codecov_flag_jvm=`echo $TRAVIS_SCALA_VERSION | tr '[:upper:]' '[:lower:]' | tr  '.' '_' | tr '-' '_'`_jvm

#coverage="$sbt_cmd coverage validateJVM coverageReport && bash <(curl -s https://codecov.io/bash)"
coverage="$sbt_cmd coverage validateJVM coverageReport && bash <(curl -s https://codecov.io/bash) -F $codecov_flag_jvm "

#scala_js="$sbt_cmd macrosJS/compile coreJS/compile lawsJS/compile && $sbt_cmd testsJS/test && $sbt_cmd freeJS/test && $sbt_cmd stateJS/test"
#scala_jvm="$sbt_cmd validateJVM"

#run_cmd="$coverage && $scala_js && $scala_jvm $publish_cmd"

run_cmd="$coverage && $sbt_cmd clean validate $publish_cmd"
eval $run_cmd
