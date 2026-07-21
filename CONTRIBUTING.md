# Contributing

Thank you for contributing to this English-default Slimefun build.

## Development setup

This project requires Java 25 and uses the included Gradle wrapper.

```bash
./gradlew spotlessApply
./gradlew build
```

The repository includes an `.editorconfig`; configure your editor to follow it.
Use four spaces for Java indentation.

## Branches and commits

Create changes on a feature branch. Commit messages should follow
[Conventional Commits](https://www.conventionalcommits.org/), for example:

```text
fix(items): correct an item tooltip
feat(storage): add a database option
trans(locale): update a locale file
```

Supported commit types include `feat`, `fix`, `docs`, `style`, `refactor`,
`ci`, `chore`, `perf`, `build`, `test`, `revert`, and `trans`.

## Code style and checks

Before opening a pull request, run:

```bash
./gradlew spotlessApply spotlessCheck test build --no-daemon
```

Do not compress code merely to reduce line count. Prefer readable code and keep
translation text in the appropriate locale resource whenever the localization
API supports that context.

## Pull requests

Pull requests may contain bug fixes, new functionality, API improvements, or
translation updates. Explain the behavior change and link any related issue
using `Fixes #123` or `Resolves #123` when applicable.
