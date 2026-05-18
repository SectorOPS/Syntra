// Copyright 2024 SectorOPS. Apache-2.0.
package com.sectorops.syntra;

/**
 * Request body for a {@code POST /feedback} call.
 *
 * <p>Reward must be in the continuous range {@code [-1.0, 1.0]} as declared in
 * the capsule YAML. See {@link com.sectorops.syntra.retry.RetryClient} for the
 * recommended reward formula.
 */
public record FeedbackRequest(String decisionId, double reward) {}
