# Database-KMP

Kotlin Multiplatform network storage database interfaces and entity types.

## About

Database-KMP provides the `NetworkStorageDatabase` interface and all entity types needed for network storage metadata persistence. Implement the interface with any storage backend (SQLite, Room, in-memory, etc.).

## Quick Start

```kotlin
val storage = NetworkStorage(
    id = "s1",
    name = "My WebDAV",
    type = StorageType.WEBDAV,
    location = "https://dav.example.com"
)

// Implement NetworkStorageDatabase for your backend
class MyDatabase : NetworkStorageDatabase { ... }
```

## Links

- [GitHub](https://github.com/vasic-digital/Database-KMP)
- [GitLab](https://gitlab.com/vasic-digital/database-kmp)
- [User Guide](../user-guide.md)
- [API Reference](../api-reference.md)
- [Architecture](../architecture.md)
