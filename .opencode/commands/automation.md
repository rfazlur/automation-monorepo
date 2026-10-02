# /automation

Design or update automation tests with focused repository context.

## Required behavior
- Create or reuse a `task_id`.
- Use `.opencode/runtime/<task_id>/state.json`.
- Route by risk and complexity.
- Use artifact references rather than forwarding full prior outputs.
- Compact any oversized output before the next agent.
