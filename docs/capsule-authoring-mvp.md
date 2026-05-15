# YAML Capsule Authoring MVP

## Overview
Capsules are the fundamental unit of configuration in Syntra. This MVP defines the YAML schema for capsule authoring.

## Capsule Schema v0.2

```yaml
# Required fields
apiVersion: syntra/v0.2
kind: Capsule
metadata:
  name: my-capsule
  version: 1.0.0
  description: "A brief description"
  author: "your-name"
  tags:
    - utility
    - automation

# Capsule configuration
spec:
  # Input parameters
  inputs:
    - name: target_url
      type: string
      required: true
      description: "URL to process"
    - name: max_retries
      type: integer
      default: 3
      description: "Maximum retry attempts"

  # Execution steps
  steps:
    - name: fetch
      action: http.get
      params:
        url: "{{inputs.target_url}}"
        timeout: 30s
    
    - name: transform
      action: data.extract
      params:
        source: "{{steps.fetch.output}}"
        format: json
        path: ".data.items[]"
    
    - name: output
      action: data.format
      params:
        format: markdown
        template: "results.md.j2"

  # Output specification
  outputs:
    - name: result
      type: string
      from: "{{steps.output.result}}"

  # Error handling
  onError:
    retry:
      max: "{{inputs.max_retries}}"
      delay: 5s
    fallback:
      action: notify
      params:
        channel: "#alerts"
        message: "Capsule {{metadata.name}} failed"
```

## Validation Rules
1. `apiVersion` must match supported versions
2. `metadata.name` must be lowercase, hyphens only
3. All required inputs must have values
4. Steps must reference valid actions
5. Output references must resolve

## MVP Scope
- [x] YAML schema definition
- [x] Input/output specification
- [ ] Runtime validation
- [ ] Action library
- [ ] Step execution engine
