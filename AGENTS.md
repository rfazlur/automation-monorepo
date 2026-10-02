# Master Automation — V5.1 Agentic Product Engineering

## Core rules

1. Use artifact-based handoffs. Never pass the full prior agent conversation to another agent.
2. Every workflow run has a `task_id` and a runtime directory under `.opencode/runtime/<task_id>/`.
3. Prefer small, structured JSON artifacts over prose transcripts.
4. Agents must read only the artifacts and repository files relevant to their current task.
5. Use `repo-scout` before broad repository exploration.
6. Use `context-compactor` before handing a large result to another agent.
7. Do not include secrets, `.env`, credentials, tokens, or private keys in artifacts.
8. Never run destructive commands unless explicitly approved by the user.
9. Never `git push` automatically.
10. QA evidence must identify command, scope, result, and failure classification.
11. Release-gate may block release when required evidence is missing or critical checks fail.
12. If context approaches the configured budget, summarize and persist an artifact rather than continuing to grow the prompt.

## Artifact contract

Artifacts should contain:
- `schema_version`
- `task_id`
- `created_at`
- `producer`
- `status`
- `data`

Large logs belong in files; artifacts should contain concise summaries and references.

## Product lifecycle

Request → product triage → requirements → UX/business analysis → technical plan → implementation → QA → review → release gate.

Not every request requires every phase. The router chooses the minimum viable set of agents.
