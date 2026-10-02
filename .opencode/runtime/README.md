# Runtime Artifact Store

Each workflow should create:

`.opencode/runtime/<task-id>/`

Recommended structure:

```text
state.json
intake.json
routing.json
product/
requirements/
ux/
architecture/
development/
qa/
review/
release/
```

Do not commit runtime artifacts by default if they contain sensitive or ephemeral data.
