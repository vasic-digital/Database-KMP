# Database-KMP Agent Guidelines

## Testing

Tests in `src/commonTest/`. Run with `./gradlew desktopTest`.

Test files:
- `DatabaseTest.kt` - All entity types, enums, computed properties, permissions

## Rules

- Never remove or disable tests
- All changes must pass existing tests
- Entity types must remain immutable data classes
- Timestamps use Long (epoch millis), not Instant
- NetworkStorageDatabase interface methods must return Result<T>
- with*() methods must return new copies
