# V5.1 Artifact-Based Agentic Architecture

The core design prevents context explosion by replacing transcript-to-transcript handoffs with small artifacts.

## Flow

User → Orchestrator → Router → Specialist → Artifact → Context Compactor → Next Specialist.

## New infrastructure agents

- `repo-scout`
- `context-compactor`
- `artifact-manager`

## Product agents

- `product-manager`
- `product-owner`
- `business-analyst`
- `ux-researcher`
- `product-analyst`
- `technical-product-manager`

## Commands

`/route`, `/product`, `/discovery`, `/feature`, `/bug`, `/qa`, `/automation`, `/review`, `/release`.
