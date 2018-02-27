#!/usr/bin/env bash
#

set -euo pipefail

cd "${TMPDIR:-/tmp}"
git clone "https://github.com/typelevel/discipline.git"
( cd discipline && sbt +publishLocal)
rm -rf discipline

