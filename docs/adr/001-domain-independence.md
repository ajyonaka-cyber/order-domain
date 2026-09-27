# ADR-001: Keep the Domain Independent

## Status

Accepted

## Context

The Order domain contains the business rules for creating, confirming,
paying, and cancelling orders.

The domain should not depend on external technologies such as Spring,
JPA, HTTP, or database adapters.

This keeps the business logic independent and easier to test.

## Decision

The domain will use plain Java classes and interfaces.

The domain will not contain:

- Spring annotations
- JPA annotations
- HTTP-specific code
- Database-specific code

External concerns will be connected through interfaces and adapters.

For example, `OrderRepository` defines the persistence contract, while
`InMemoryOrderRepository` provides an implementation for testing.

## Consequences

### Positive

- Domain logic can be tested without a database.
- The business rules remain independent of frameworks.
- Infrastructure can be changed without changing the core domain.
- In-memory adapters can be used for testing.

### Negative

- Additional interfaces and adapter classes are required.
- A real application will need infrastructure implementations later.