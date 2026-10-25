<div align="center">
  <img src="https://amaquet.newfoundcodes.com/amaquet.png" width="128" />
  <h1>Amaquet Java SDK</h1>
  <p>
    <a href="https://github.com/newfoundcodes/amaquet-java-sdk/actions"><img src="https://github.com/newfoundcodes/amaquet-java-sdk/actions/workflows/ci.yml/badge.svg" alt="Build Status"></a>
  </p>
</div>

Native Java 17+ client for a **running Amaquet server**. It supports Amaquet's own 24-byte-header TCP/TLS protocol v1 and its separate HTTP administration interface. The data plane is **not Redis RESP** and is **not an HTTP API**.

## Coverage matrix

| Feature | Java implementation |
|---|---|
| Native opcodes | All 6: `COMMAND`, `HELLO`, `AUTH`, `SUBSCRIBE`, `UNSUBSCRIBE`, `CANCEL` |
| Native top-level commands | All 23 documented names |
| Server wire types | All 91, generated named handles |
| Type-specific methods | All 242 documented `OP` methods |
| Administration HTTP API | All 33 method/path combinations |
| Network | TCP and TLS using JDK streams and JSSE |
| Authentication | Native API key; admin Bearer or `X-Amaquet-Admin-Token`; admin session cookie |
| Identity | Signed Ed25519 HELLO verification, optional public-key/fingerprint pinning |
| Concurrency | Thread-safe writes, response correlation by request ID, out-of-order replies, `CompletableFuture` API |
| Cancellation | Explicit `CANCEL` and best-effort `CANCEL` on command timeout |
| Pub/Sub | Server channel subscription and bounded best-effort local event queue |
| Binary | Staged begin/chunk/commit/abort and ranged reads, streaming uploads/downloads |
| Pool | Fixed-size round-robin connection pool; explicit connection replacement |
| JSON | Built-in bounded parser, exact integers (`BigInteger`) and decimals (`BigDecimal`) |
| Test assets | 91 type examples, 42 workflow examples, 91 JSON reference scenarios, JUnit suite, 13 fuzz targets, TLS test, optional live-server test |

No third-party **runtime** dependencies. The Maven test phase requires JUnit 5 as a test-only dependency.

The documented interface inventory is mapped and tested with controlled servers. Live type scenarios require a dedicated Amaquet test instance; they are opt-in and are not run by the default test commands.

## Requirements

- JDK 17 or later (recommended JDK 21).
- A running Amaquet server with its native port (often `13378`).
- An optional separate HTTP admin endpoint (often `13379`).
- An API key if your server requires native authentication.

Build and install with Maven:

```bash
mvn clean test package
mvn install
```

Add to a consuming Maven project's dependencies after installation:

```xml
<dependency>
  <groupId>com.newfoundcodes</groupId>
  <artifactId>amaquet</artifactId>
  <version>0.1.0</version>
</dependency>
```

## Connect to Amaquet

```java
import com.newfoundcodes.amaquet.*;
import java.util.*;
import java.math.BigInteger;
import java.time.Duration;

class App {
    public static void main(String[] args) {
        Client.Options options = new Client.Options()
            .apiKey(System.getenv("AMAQUET_API_KEY"))
            .timeout(Duration.ofSeconds(10))
            .maxInFlight(1024);

        try (Client db = Client.connect("amaquet://127.0.0.1:13378", options)) {
            System.out.println(db.serverInfo());
            System.out.println(db.ping());
            db.set("sample:count", "integer", 42);
            System.out.println(db.get("sample:count"));
            db.delete("sample:count");
        }
    }
}
```

The `Client` constructor establishes TCP/TLS, sends a nonce-based `HELLO`, verifies any supplied Ed25519 signature, and sends `AUTH` when credentials are supplied. If the server requests authentication and no API key is present, connection fails. The SDK does not store API keys in log messages.

A `amaquets://` URI uses TLS with trusted certificate and hostname verification. To pin a server's signed identity:

```java
Client.Options options = new Client.Options()
    .apiKey(System.getenv("AMAQUET_API_KEY"))
    .fingerprint("ed25519:YOUR_SERVER_SHA256_FINGERPRINT")
    .requireIdentity(true);
try (Client db = Client.connect("amaquets://db.example.com:13378", options)) {
    System.out.println(db.ping());
}
```

Do not confuse TLS certificate verification with Amaquet's separate signed HELLO identity. When using a plain `amaquet://` TCP connection without identity verification, there is no transport encryption; protect it with a trusted private network or switch to TLS.

## Exact integers, typed values and snapshots

```java
try (Client db = Client.connect(uri, new Client.Options().apiKey(key))) {
    db.set("big", "unsigned_integer", new BigInteger("18446744073709551615"));
    Object snapshot = db.get("big");
    System.out.println(snapshot);

    TypedHandles.AtomicCounter counter = db.typed().generated().atomicCounter("stats:requests");
    counter.create(Json.map("initial", 0));
    counter.add(Json.map("delta", 1));
    System.out.println(counter.load());
    counter.deleteKey();
}
```

`GET` is a top-level command that returns a stored type and snapshot. A generated `GET` operation (for example, an array's `get(Map)` method) runs **`OP GET`**, not top-level `GET`. For operations that share method names with Java's core language, use the documented accessor: `booleanType`, `floatType`, or `nullType`; a barrier provides `wait(Map)` or `waitOperation()` to avoid the final `Object.wait()` overload.

`Json.parse` returns JSON integral values as `BigInteger` and non-integral values as `BigDecimal`. Use `String` representations for Amaquet's `decimal`, `big_decimal`, and `big_integer` wire types when those types require decimal string input. Do not convert money or unsigned 64-bit amounts to `double`.

The client does not guess the argument fields for any type. Each generated method accepts the server's exact documented argument map. See the complete type method catalog below and `examples/scenarios`.

## Concurrent requests and cancellation

```java
Client.Request one = db.sendCommand("GET", Json.map("key", "a"));
Client.Request two = db.sendCommand("GET", Json.map("key", "b"));
System.out.println(db.await(two));
System.out.println(db.await(one));

// Native COMMAND futures:
db.asyncCommand("INFO", Map.of()).thenAccept(System.out::println);
```

The read loop matches responses by 64-bit request ID, not arrival order. Use `thenAcceptAsync` or another asynchronous continuation for expensive callbacks: synchronous future callbacks can execute on the socket reader thread. `await(request, duration)` limits the wait and performs a best-effort native `CANCEL` for a timed-out command. The **server may have committed a mutation before cancellation**. The SDK does not retry writes automatically and does not claim exactly-once effects. The default number of pending commands is limited to 4,096 and configurable with `maxInFlight`.

## Pub/Sub

```java
try (Subscription sub = db.subscribe("event-bus", "updates", 16, 64)) {
    System.out.println(sub.next(Duration.ofSeconds(10)));
    System.out.println("Locally dropped events: " + sub.dropped());
}
```

Subscription events are best effort. Events may be lost at the server or SDK when consumers are slow. An explicit `unsubscribe` releases the subscription, and closing the client removes all subscriptions.

## Chunked blob transfers

```java
byte[] bytes = java.nio.file.Files.readAllBytes(java.nio.file.Path.of("payload.bin"));
db.uploadBlob("payload", bytes);
byte[] copy = db.downloadBlobBytes("payload", 32 * 1024 * 1024);

java.nio.file.Path inputPath = java.nio.file.Path.of("large.bin");
long fileSize = java.nio.file.Files.size(inputPath);
try (var input = java.nio.file.Files.newInputStream(inputPath)) {
    db.uploadBlob("large-payload", input, fileSize, 1 << 20, false);
}
try (var out = java.nio.file.Files.newOutputStream(java.nio.file.Path.of("large-copy.bin"))) {
    db.downloadBlob("large-payload", out, 1 << 20, 1L << 30);
}
```

The streaming uploader consumes exactly the declared byte count; it does not wait for a source to reach EOF. Do not modify a blob during streaming reads. The downloader checks total size and version for every range.

## HTTP administration

```java
AdminClient admin = new AdminClient("https://db.example.com:13379", System.getenv("AMAQUET_ADMIN_TOKEN"));
System.out.println(admin.health());
System.out.println(admin.apiKeys());
System.out.println(admin.dataKeys("", "", 100));
```

The admin API is distinct from the native protocol and has its own authentication and permissions. Its complete 33-route catalog is included below.

## Running examples

After running `mvn package`, compile the examples against the built library:

```bash
find examples -name '*.java' | sort > target/example-sources.txt
javac --release 17 -cp target/classes -d target/examples @target/example-sources.txt
java -cp target/classes:target/examples Workflow01Connect
java -cp target/classes:target/examples Workflow03ExactIntegers
java -cp target/classes:target/examples ExampleAtomicCounter
```

Configure `AMAQUET_URL`, `AMAQUET_API_KEY`, `AMAQUET_ADMIN_URL`, and `AMAQUET_ADMIN_TOKEN`. Type examples create the documented type by default. Set `AMAQUET_RUN_OPERATIONS=true` to run its documented operations, which can mutate or block. Use a dedicated test server.

## Native protocol and security

Amaquet v1 uses a fixed 24-byte, big-endian header. Payloads are JSON and the
data plane is not Redis RESP or HTTP.

| Offset | Size | Field | Description |
|---:|---:|---|---|
| 0 | 4 | Magic | ASCII `AMQT` |
| 4 | 1 | Version | Protocol version `1` |
| 5 | 1 | Kind | Request, response, or event |
| 6 | 2 | Flags | Unsigned 16-bit flags |
| 8 | 2 | Opcode | `COMMAND`, `HELLO`, `AUTH`, `SUBSCRIBE`, `UNSUBSCRIBE`, or `CANCEL` |
| 10 | 2 | Reserved | Must be zero |
| 12 | 8 | Request ID | Unsigned big-endian request identifier |
| 20 | 4 | Payload length | Unsigned length, capped at 64 MiB |

Responses use `{ "ok": true, "result": value }` or `{ "ok": false, "error": { "code": "...", "message": "..." } }`. Malformed frames, unknown versions, opcode mismatches, invalid reserved bytes, unexpected frames, and oversized payloads close the connection.

The client uses a background reader and correlates responses by request ID, so
commands may complete out of order. Writes are serialized, requests are bounded
by `maxInFlight`, and subscriptions use bounded non-blocking queues. Queue
overflow is reported by `Subscription.dropped()`; event delivery is best effort.

TLS uses JSSE certificate-chain and hostname verification. Amaquet also signs
the `HELLO` challenge with Ed25519; this identity is independent of the TLS
certificate and can be required or pinned with a public key or fingerprint.
Native API keys should be supplied with `Client.Options.apiKey()`. URI
credentials are rejected by default. HTTP administration credentials are
separate from native credentials, redirects are not followed, and response
bodies are bounded before parsing.

The SDK does not automatically retry reads or writes. A timeout or cancellation
does not undo a mutation that may already have reached the server.

### Failure types

- `AmaquetException.Protocol`: malformed frames, unexpected responses, or identity verification errors.
- `AmaquetException.Request`: a server response with `ok: false`.
- `AmaquetException.Timeout`: a command exceeded its deadline; a mutation may already be committed.
- `AmaquetException.Transport`: socket or stream I/O failure.
- `AmaquetException.Http`: a non-2xx administration response with status and body details.
- `IllegalArgumentException`: invalid configuration, unsafe paths, malformed JSON, or invalid local arguments.

## HTTP administration route catalog

The administration server is a separate HTTP(S) endpoint. `AdminClient` uses
JDK `HttpClient`, disables redirects, bounds response bodies, and supports
Bearer tokens, `X-Amaquet-Admin-Token`, and the `amaquet_session` cookie.

```java
AdminClient admin = new AdminClient("https://db.example.com:13379",
    System.getenv("AMAQUET_ADMIN_TOKEN"));
System.out.println(admin.health());
System.out.println(admin.audit(100));
```

| Method | Path | Java method |
|---|---|---|
| `DELETE` | `/api/api-keys/{id}` | `revokeApiKey(id)` |
| `DELETE` | `/api/data/key` | `deleteDataKey(key)` |
| `DELETE` | `/api/identity/mfa` | `disableMfa(code)` |
| `DELETE` | `/api/members/{id}` | `deleteMember(id)` |
| `DELETE` | `/api/session` | `deleteSession()` |
| `GET` | `/api/api-keys` | `apiKeys()` |
| `GET` | `/api/audit` | `audit(limit)` |
| `GET` | `/api/auth/check` | `checkAuth()` |
| `GET` | `/api/data/key` | `dataKey(key)` |
| `GET` | `/api/data/keys` | `dataKeys(cursor,prefix,count)` |
| `GET` | `/api/health` | `health()` |
| `GET` | `/api/members` | `members()` |
| `GET` | `/api/organization` | `organization()` |
| `GET` | `/api/overview` | `overview()` |
| `GET` | `/api/rbac` | `rbac()` |
| `GET` | `/api/ready` | `ready()` |
| `GET` | `/api/system` | `system()` |
| `GET` | `/metrics` | `metrics()` |
| `GET` | `/openapi.json` | `openapi()` |
| `PATCH` | `/api/members/{id}` | `patchMember(id,role,status)` |
| `POST` | `/api/api-keys` | `createApiKey(name,role,expiresAt)` |
| `POST` | `/api/data/command` | `dataCommand(command,args)` |
| `POST` | `/api/identity/accept-invite` | `acceptInvite(invite)` |
| `POST` | `/api/identity/mfa` | `beginMfa()` |
| `POST` | `/api/members` | `addMember(name,email,role)` |
| `POST` | `/api/members/{id}/invite` | `inviteMember(id,hours)` |
| `POST` | `/api/persistence/checkpoint` | `checkpoint(name)` |
| `POST` | `/api/persistence/compact` | `compact()` |
| `POST` | `/api/session` | `createSession(token,mfaCode)` |
| `PUT` | `/api/identity/mfa` | `confirmMfa(code)` |
| `PUT` | `/api/organization` | `updateOrganization(name,slug)` |
| `PUT` | `/api/rbac` | `updateRbac(config)` |
| `PUT` | `/api/system` | `updateSystem(config)` |

There are exactly 33 documented method/path combinations. Route parameters are
encoded as single path segments; traversal and unsafe inputs are rejected.
Successful session creation stores the session cookie for later requests.
Non-2xx responses throw `AmaquetException.Http`; empty successful bodies return
`null`, `/metrics` returns text, and JSON routes return parsed Java values.

## Complete type operation catalog

Typed access is `client.typed().generated().atomicCounter(key)`, or
`client.typed().forType("atomic_counter", key)`. Construction and snapshots
use top-level `CREATE`, `SET`, and `GET`; every operation in this table maps to
native `OP`. Methods accept the server's exact `Map<String,Object>` arguments.

| Wire type | Java accessor | Generated `OP` methods |
|---|---|---|
| `adjacency_set` | `adjacencySet(key)` | `add`, `neighbors`, `remove` |
| `aggregated_series` | `aggregatedSeries(key)` | `aggregate`, `range` |
| `array` | `array(key)` | `append`, `get`, `len`, `set`, `values` |
| `atomic_counter` | `atomicCounter(key)` | `add`, `cas`, `load` |
| `barrier` | `barrier(key)` | `wait` |
| `big_decimal` | `bigDecimal(key)` | `add`, `cmp`, `sub` |
| `big_integer` | `bigInteger(key)` | `add` |
| `binary_string` | `binaryString(key)` | top-level methods |
| `bit_field` | `bitField(key)` | `set`, `get` |
| `bit_set` | `bitSet(key)` | `set`, `getbit`, `count` |
| `bitmap` | `bitmap(key)` | `set`, `getbit`, `count` |
| `blocking_queue` | `blockingQueue(key)` | `enqueue`, `dequeue`, `close`, `len` |
| `bloom_filter` | `bloomFilter(key)` | `add`, `contains` |
| `boolean` | `booleanType(key)` | `toggle` |
| `bounding_box` | `boundingBox(key)` | `contains` |
| `btree_index` | `btreeIndex(key)` | `insert`, `search`, `delete`, `range` |
| `cbor` | `cbor(key)` | `get` |
| `cidr_network` | `cidrNetwork(key)` | `contains` |
| `consumer_group` | `consumerGroup(key)` | `snapshot` |
| `count_min_sketch` | `countMinSketch(key)` | `add`, `estimate` |
| `counter_series` | `counterSeries(key)` | `add`, `last`, `aggregate`, `len`, `range` |
| `counting_bloom_filter` | `countingBloomFilter(key)` | `add`, `contains`, `remove` |
| `cuckoo_filter` | `cuckooFilter(key)` | `add`, `contains`, `remove` |
| `decimal` | `decimal(key)` | `add`, `cmp`, `sub` |
| `delayed_queue` | `delayedQueue(key)` | `enqueue`, `popReady`, `len` |
| `dense_vector` | `denseVector(key)` | `distance` |
| `deque` | `deque(key)` | `pushBack`, `pushFront`, `len`, `popFront`, `popBack`, `values` |
| `duration` | `duration(key)` | `add` |
| `event_log` | `eventLog(key)` | `append`, `len`, `read` |
| `fifo_queue` | `fifoQueue(key)` | `enqueue`, `peek`, `dequeue`, `len` |
| `flat_vector_index` | `flatVectorIndex(key)` | `add`, `search`, `remove`, `len` |
| `float` | `floatType(key)` | `add` |
| `full_text_document` | `fullTextDocument(key)` | `addText`, `addTag`, `addNumeric`, `snapshot`, `get` |
| `gauge_series` | `gaugeSeries(key)` | `set`, `last`, `range`, `aggregate`, `len` |
| `geo_point` | `geoPoint(key)` | `distance` |
| `geo_spatial_index` | `geoSpatialIndex(key)` | `add`, `radius`, `remove`, `box`, `polygon` |
| `geospatial_secondary_index` | `geospatialSecondaryIndex(key)` | `add`, `radius`, `remove`, `box`, `polygon` |
| `hash_index` | `hashIndex(key)` | `add`, `lookup`, `remove` |
| `hashmap` | `hashmap(key)` | `set`, `get`, `delete`, `keys` |
| `histogram` | `histogram(key)` | `observe`, `snapshot` |
| `hnsw_index` | `hnswIndex(key)` | `add`, `search`, `remove` |
| `hyperloglog` | `hyperloglog(key)` | `add`, `count` |
| `integer` | `integer(key)` | `add` |
| `inverted_index` | `invertedIndex(key)` | `index`, `search`, `remove` |
| `json` | `json(key)` | `set`, `get` |
| `lease` | `lease(key)` | `acquire`, `held`, `release`, `renew` |
| `lifo_stack` | `lifoStack(key)` | `push`, `peek`, `pop`, `len` |
| `list` | `list(key)` | `pushBack`, `pushFront`, `len`, `range`, `popFront`, `popBack` |
| `lock` | `lock(key)` | `tryLock`, `unlock`, `renew` |
| `matrix` | `matrix(key)` | `get`, `set`, `len` |
| `messagepack` | `messagepack(key)` | `get` |
| `multimap` | `multimap(key)` | `add`, `get`, `remove` |
| `multiset` | `multiset(key)` | `add`, `count`, `remove` |
| `null` | `nullType(key)` | top-level methods |
| `numeric_field` | `numericField(key)` | `set`, `get` |
| `ordered_map` | `orderedMap(key)` | `set`, `get`, `delete`, `entries` |
| `ordered_set` | `orderedSet(key)` | `add`, `values`, `remove` |
| `persistent_topic` | `persistentTopic(key)` | `publish`, `read` |
| `polygon` | `polygon(key)` | `contains` |
| `priority_queue` | `priorityQueue(key)` | `enqueue`, `len`, `dequeue` |
| `pubsub` | `pubsub(key)` | `publish`, `stats` |
| `quantized_vector` | `quantizedVector(key)` | `dequantize` |
| `radix_tree` | `radixTree(key)` | `insert`, `prefix`, `exact` |
| `rate_limit_bucket` | `rateLimitBucket(key)` | `allow`, `remaining` |
| `record` | `record(key)` | `set`, `get` |
| `reliable_queue` | `reliableQueue(key)` | `enqueue`, `claim`, `ack`, `nack`, `stats` |
| `ring_buffer` | `ringBuffer(key)` | `push`, `len`, `values` |
| `roaring_bitmap` | `roaringBitmap(key)` | `add`, `contains`, `cardinality`, `remove`, `values` |
| `secondary_index` | `secondaryIndex(key)` | `add`, `lookup` |
| `semaphore` | `semaphore(key)` | `tryAcquire`, `stats`, `release`, `acquire` |
| `set` | `set(key)` | `add`, `contains`, `remove`, `members`, `len` |
| `sorted_set` | `sortedSet(key)` | `add`, `score`, `remove`, `range` |
| `sparse_vector` | `sparseVector(key)` | top-level methods |
| `stream` | `stream(key)` | `add`, `range`, `groupCreate`, `groupRead`, `ack`, `pending`, `len` |
| `symbol` | `symbol(key)` | top-level methods |
| `t_digest` | `tDigest(key)` | `add`, `quantile` |
| `tag_field` | `tagField(key)` | `set`, `get` |
| `tensor` | `tensor(key)` | `get`, `set`, `len` |
| `text_field` | `textField(key)` | `set`, `get` |
| `timeseries` | `timeseries(key)` | `add`, `len`, `range`, `last`, `aggregate` |
| `timeseries_labels` | `timeseriesLabels(key)` | `set`, `get` |
| `timestamp` | `timestamp(key)` | top-level methods |
| `top_k` | `topK(key)` | `add`, `items` |
| `trie` | `trie(key)` | `insert`, `prefix`, `exact` |
| `tuple` | `tuple(key)` | `len`, `get`, `values` |
| `unsigned_integer` | `unsignedInteger(key)` | `add` |
| `url` | `url(key)` | `host`, `scheme` |
| `utf8_string` | `utf8String(key)` | top-level methods |
| `uuid` | `uuid(key)` | top-level methods |
| `vector_metadata` | `vectorMetadata(key)` | `set`, `get`, `delete` |
| `vector_set` | `vectorSet(key)` | `add`, `search`, `remove`, `len` |

There are 91 registered types and 242 documented type-specific operations.
Operation names are transmitted uppercase and unchanged. Java's reserved names
use `booleanType`, `floatType`, and `nullType`; the barrier handle provides
`wait(Map)` and `waitOperation()` because zero-argument `wait()` belongs to
`Object`. The 91 `Example<Type>.java` programs and JSON scenarios contain the
documented construction payloads and operation arguments.

## Testing and live verification

The SDK has Maven/JUnit 5 tests backed by controlled local peers. The default
test suite does not contact an external server.

```bash
mvn clean test package
```

The regression suite covers request-ID multiplexing, all 242 generated
operations, 91 construction payloads, 33 administration routes, exact-number
JSON, signed Ed25519 `HELLO`, cancellation, Pub/Sub drops, binary transfer,
pooling, error conversion, and malformed frames. The fuzz harness has 13
deterministic targets for JSON, UTF-8, frame parsing, endpoint parsing,
administration paths, expiration options, Base64 identity fields, and precise
numeric values. This is deterministic mutation testing, not coverage-guided
JVM fuzzing.

For TLS or fuzz smoke tests, compile the test classes and invoke their Java
entry points directly:

```bash
mvn test-compile
java -cp target/classes:target/test-classes com.newfoundcodes.amaquet.TlsTest
java -cp target/classes:target/test-classes com.newfoundcodes.amaquet.FuzzTargets 2000
```

For live verification, use a dedicated disposable Amaquet deployment and invoke
the opt-in test directly:

```bash
export AMAQUET_TEST_URL=amaquet://127.0.0.1:13378
export AMAQUET_TEST_API_KEY='your-test-api-key'
mvn test-compile
java -cp target/classes:target/test-classes com.newfoundcodes.amaquet.LiveTest
```

The live suite checks `TYPES`, constructs all 91 types under unique keys,
reads each snapshot, removes the test keys, and performs a binary round trip.
Set `AMAQUET_TEST_ALL_OPS=1` only after reviewing operation prerequisites and
side effects; some operations block or mutate state. An optional
`AMAQUET_TEST_IDENTITY_FINGERPRINT` pins the server identity.

Mock tests do not prove every server-side type mutation. Applications should
also test permissions, backpressure, network partitions, restarts, persistence,
and real TLS certificates in their deployment environment.

## License

Amaquet Java SDK is licensed under the MIT License. See [LICENSE](LICENSE).
