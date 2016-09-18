#!/usr/bin/env bash
#

set -euo pipefail

cd "${TMPDIR:-/tmp}"
git clone -b topic/2-12-0-RC1 "https://github.com/BennyHill/tut.git"
( cd tut && sbt -sbt-version 0.13.13-M1 plugin/compile plugin/publishLocal ++2.10.6    core/compile core/publishLocal ++2.11.8 core/compile core/publishLocal ++2.12.0-RC1 core/compile core/publishLocal )
rm -rf  tut
