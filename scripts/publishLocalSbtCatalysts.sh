#!/usr/bin/env bash
#

set -euo pipefail

cd "${TMPDIR:-/tmp}"
git clone -b topic/2.13-M3 "https://github.com/BennyHill/sbt-catalysts.git"
( cd sbt-catalysts && sbt -sbt-version 0.13.6 publishLocal)
rm -rf  sbt-catalysts

