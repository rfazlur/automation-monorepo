# /product

Run product triage/discovery. Produce a product brief and route only the necessary product agents.

## Required behavior
- Create or reuse a `task_id`.
- Use `.opencode/runtime/<task_id>/state.json`.
- Route by risk and complexity.
- Use artifact references rather than forwarding full prior outputs.
- Compact any oversized output before the next agent.
