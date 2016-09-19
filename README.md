# Catalysts

[![Build Status](https://api.travis-ci.org/typelevel/catalysts.png)](https://travis-ci.org/typelevel/catalysts)
[![Join the chat at
https://gitter.im/typelevel/sbt-catalysts](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/typelevel/sbt-catalysts?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Stories in Ready](https://badge.waffle.io/typelevel/sbt-catalysts.png?label=ready&title=Ready)](https://waffle.io/typelevel/sbt-catalysts)
[![codecov.io](http://codecov.io/github/typelevel/catalysts/coverage.svg?branch=master)](http://codecov.io/github/typelevel/catalysts?branch=master)

Catalysts is an intentionally small library of small moduless to help build and test platform independent Scala projects with SBT.

This interim version adds 2.12 support. It locally builds scoverage libraries, so to build his PR locally:

```bash
scripts/publishLocalScoverage.sh
scripts/publishLocalSbtScoverage.sh
sbt +clean coverageOn +test +clean coverageOff +test
```

### Maintainers

The current maintainers (people who can merge pull requests) are:

 * [adelbertc](https://github.com/adelbertc) Adelbert Chang
 * [ochrons](https://github.com/ochrons) Otto Chrons
 * [BennyHill](https://github.com/BennyHill) Alistair Johnson
 * [non](https://github.com/non) Erik Osheim
 * [milessabin](https://github.com/milessabin) Miles Sabin
 * [dwijnand](https://github.com/dwijnand) Dale Wijnand

We are currently following a practice of requiring at least two
sign-offs to merge PRs (and for large or contentious issues we may
wait for more). For typos or other small fixes to documentation we
relax this to a single sign-off.

### Contributing

Discussion around Catalysts is currently happening in the
[Gitter channel](https://gitter.im/typelevel/sbt-catalysts) as well as on Github
issue and PR pages. You can get an overview of who is working on what
via [Waffle.io](https://waffle.io/typelevel/sbt-catalysts).

Feel free to open an issue if you notice a bug, have an idea for a
feature, or have a question about the code. Pull requests are also
gladly accepted.

People are expected to follow the
[Typelevel Code of Conduct](http://typelevel.org/conduct.html) when
discussing Catalysts on the Github page, Gitter channel, or other
venues.

We hope that our community will be respectful, helpful, and kind. If
you find yourself embroiled in a situation that becomes heated, or
that fails to live up to our expectations, you should disengage and
contact one of the [project maintainers](#maintainers) in private. We
hope to avoid letting minor aggressions and misunderstandings escalate
into larger problems.

If you are being harassed, please contact one of [us](#maintainers)
immediately so that we can support you.

## License

Catalysts is licensed under the **[Apache License, Version 2.0][apache]** (the
"License"); you may not use this software except in compliance with the License.

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[apache]: https://www.apache.org/licenses/LICENSE-2.0
