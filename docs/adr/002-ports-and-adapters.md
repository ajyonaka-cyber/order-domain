# ADR-002: Use Ports and Adapters

## Status

Accepted

## Context

The Order domain needs to work with external concerns such as
persistence, notifications, and time.

Directly connecting the domain to databases, messaging systems, or
frameworks would make the business logic dependent on infrastructure.

## Decision

The application will define interfaces for external concerns.

The project uses:

- `OrderRepository` for persistence
- `NotificationPort` for publishing domain events
- `TimeProvider` for obtaining the current time

In-memory implementations are used for testing.

The domain remains focused on business rules while external
implementations can be replaced independently.

## Consequences

### Positive

- Business logic remains independent of infrastructure.
- Testing is easier because in-memory implementations can be used.
- External technologies can be changed without changing the core
  business rules.
- Dependencies are explicit through interfaces.

### Negative

- More classes and interfaces are required.
- A real application will need additional infrastructure adapters.