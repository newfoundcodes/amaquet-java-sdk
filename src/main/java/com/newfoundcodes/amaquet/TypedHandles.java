package com.newfoundcodes.amaquet;

import java.util.*;

/** All 91 Amaquet wire types and their 242 documented type-specific OP methods. */
/**
 * Performs the corresponding documented Amaquet operation.
 * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
 */
public final class TypedHandles {
  private final Client client;

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param client Existing client connected to the Amaquet endpoint.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public TypedHandles(Client client) {
    this.client = client;
  }

  /** Immutable map of wire types to their supported native operation names. */
  public static final Map<String, java.util.List<String>> CATALOG;

  static {
    Map<String, java.util.List<String>> c = new LinkedHashMap<>();
    c.put("adjacency_set", java.util.List.of("ADD", "NEIGHBORS", "REMOVE"));
    c.put("aggregated_series", java.util.List.of("AGGREGATE", "RANGE"));
    c.put("array", java.util.List.of("APPEND", "GET", "LEN", "SET", "VALUES"));
    c.put("atomic_counter", java.util.List.of("ADD", "CAS", "LOAD"));
    c.put("barrier", java.util.List.of("WAIT"));
    c.put("big_decimal", java.util.List.of("ADD", "CMP", "SUB"));
    c.put("big_integer", java.util.List.of("ADD"));
    c.put("binary_string", java.util.List.of());
    c.put("bit_field", java.util.List.of("SET", "GET"));
    c.put("bit_set", java.util.List.of("SET", "GETBIT", "COUNT"));
    c.put("bitmap", java.util.List.of("SET", "GETBIT", "COUNT"));
    c.put("blocking_queue", java.util.List.of("ENQUEUE", "DEQUEUE", "CLOSE", "LEN"));
    c.put("bloom_filter", java.util.List.of("ADD", "CONTAINS"));
    c.put("boolean", java.util.List.of("TOGGLE"));
    c.put("bounding_box", java.util.List.of("CONTAINS"));
    c.put("btree_index", java.util.List.of("INSERT", "SEARCH", "DELETE", "RANGE"));
    c.put("cbor", java.util.List.of("GET"));
    c.put("cidr_network", java.util.List.of("CONTAINS"));
    c.put("consumer_group", java.util.List.of("SNAPSHOT"));
    c.put("count_min_sketch", java.util.List.of("ADD", "ESTIMATE"));
    c.put("counter_series", java.util.List.of("ADD", "LAST", "AGGREGATE", "LEN", "RANGE"));
    c.put("counting_bloom_filter", java.util.List.of("ADD", "CONTAINS", "REMOVE"));
    c.put("cuckoo_filter", java.util.List.of("ADD", "CONTAINS", "REMOVE"));
    c.put("decimal", java.util.List.of("ADD", "CMP", "SUB"));
    c.put("delayed_queue", java.util.List.of("ENQUEUE", "POP_READY", "LEN"));
    c.put("dense_vector", java.util.List.of("DISTANCE"));
    c.put(
        "deque",
        java.util.List.of("PUSH_BACK", "PUSH_FRONT", "LEN", "POP_FRONT", "POP_BACK", "VALUES"));
    c.put("duration", java.util.List.of("ADD"));
    c.put("event_log", java.util.List.of("APPEND", "LEN", "READ"));
    c.put("fifo_queue", java.util.List.of("ENQUEUE", "PEEK", "DEQUEUE", "LEN"));
    c.put("flat_vector_index", java.util.List.of("ADD", "SEARCH", "REMOVE", "LEN"));
    c.put("float", java.util.List.of("ADD"));
    c.put(
        "full_text_document",
        java.util.List.of("ADD_TEXT", "ADD_TAG", "ADD_NUMERIC", "SNAPSHOT", "GET"));
    c.put("gauge_series", java.util.List.of("SET", "LAST", "RANGE", "AGGREGATE", "LEN"));
    c.put("geo_point", java.util.List.of("DISTANCE"));
    c.put("geo_spatial_index", java.util.List.of("ADD", "RADIUS", "REMOVE", "BOX", "POLYGON"));
    c.put(
        "geospatial_secondary_index",
        java.util.List.of("ADD", "RADIUS", "REMOVE", "BOX", "POLYGON"));
    c.put("hash_index", java.util.List.of("ADD", "LOOKUP", "REMOVE"));
    c.put("hashmap", java.util.List.of("SET", "GET", "DELETE", "KEYS"));
    c.put("histogram", java.util.List.of("OBSERVE", "SNAPSHOT"));
    c.put("hnsw_index", java.util.List.of("ADD", "SEARCH", "REMOVE"));
    c.put("hyperloglog", java.util.List.of("ADD", "COUNT"));
    c.put("integer", java.util.List.of("ADD"));
    c.put("inverted_index", java.util.List.of("INDEX", "SEARCH", "REMOVE"));
    c.put("json", java.util.List.of("SET", "GET"));
    c.put("lease", java.util.List.of("ACQUIRE", "HELD", "RELEASE", "RENEW"));
    c.put("lifo_stack", java.util.List.of("PUSH", "PEEK", "POP", "LEN"));
    c.put(
        "list",
        java.util.List.of("PUSH_BACK", "PUSH_FRONT", "LEN", "RANGE", "POP_FRONT", "POP_BACK"));
    c.put("lock", java.util.List.of("TRY_LOCK", "UNLOCK", "RENEW"));
    c.put("matrix", java.util.List.of("GET", "SET", "LEN"));
    c.put("messagepack", java.util.List.of("GET"));
    c.put("multimap", java.util.List.of("ADD", "GET", "REMOVE"));
    c.put("multiset", java.util.List.of("ADD", "COUNT", "REMOVE"));
    c.put("null", java.util.List.of());
    c.put("numeric_field", java.util.List.of("SET", "GET"));
    c.put("ordered_map", java.util.List.of("SET", "GET", "DELETE", "ENTRIES"));
    c.put("ordered_set", java.util.List.of("ADD", "VALUES", "REMOVE"));
    c.put("persistent_topic", java.util.List.of("PUBLISH", "READ"));
    c.put("polygon", java.util.List.of("CONTAINS"));
    c.put("priority_queue", java.util.List.of("ENQUEUE", "LEN", "DEQUEUE"));
    c.put("pubsub", java.util.List.of("PUBLISH", "STATS"));
    c.put("quantized_vector", java.util.List.of("DEQUANTIZE"));
    c.put("radix_tree", java.util.List.of("INSERT", "PREFIX", "EXACT"));
    c.put("rate_limit_bucket", java.util.List.of("ALLOW", "REMAINING"));
    c.put("record", java.util.List.of("SET", "GET"));
    c.put("reliable_queue", java.util.List.of("ENQUEUE", "CLAIM", "ACK", "NACK", "STATS"));
    c.put("ring_buffer", java.util.List.of("PUSH", "LEN", "VALUES"));
    c.put(
        "roaring_bitmap", java.util.List.of("ADD", "CONTAINS", "CARDINALITY", "REMOVE", "VALUES"));
    c.put("secondary_index", java.util.List.of("ADD", "LOOKUP"));
    c.put("semaphore", java.util.List.of("TRY_ACQUIRE", "STATS", "RELEASE", "ACQUIRE"));
    c.put("set", java.util.List.of("ADD", "CONTAINS", "REMOVE", "MEMBERS", "LEN"));
    c.put("sorted_set", java.util.List.of("ADD", "SCORE", "REMOVE", "RANGE"));
    c.put("sparse_vector", java.util.List.of());
    c.put(
        "stream",
        java.util.List.of("ADD", "RANGE", "GROUP_CREATE", "GROUP_READ", "ACK", "PENDING", "LEN"));
    c.put("symbol", java.util.List.of());
    c.put("t_digest", java.util.List.of("ADD", "QUANTILE"));
    c.put("tag_field", java.util.List.of("SET", "GET"));
    c.put("tensor", java.util.List.of("GET", "SET", "LEN"));
    c.put("text_field", java.util.List.of("SET", "GET"));
    c.put("timeseries", java.util.List.of("ADD", "LEN", "RANGE", "LAST", "AGGREGATE"));
    c.put("timeseries_labels", java.util.List.of("SET", "GET"));
    c.put("timestamp", java.util.List.of());
    c.put("top_k", java.util.List.of("ADD", "ITEMS"));
    c.put("trie", java.util.List.of("INSERT", "PREFIX", "EXACT"));
    c.put("tuple", java.util.List.of("LEN", "GET", "VALUES"));
    c.put("unsigned_integer", java.util.List.of("ADD"));
    c.put("url", java.util.List.of("HOST", "SCHEME"));
    c.put("utf8_string", java.util.List.of());
    c.put("uuid", java.util.List.of());
    c.put("vector_metadata", java.util.List.of("SET", "GET", "DELETE"));
    c.put("vector_set", java.util.List.of("ADD", "SEARCH", "REMOVE", "LEN"));

    CATALOG = Collections.unmodifiableMap(c);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public AdjacencySet adjacencySet(String key) {
    return new AdjacencySet(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class AdjacencySet extends TypeHandle {
    /**
     * send native OP ADD to the existing `adjacency_set` key.
     * The server applies `adjacency_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`, `weight`.
     * Reference argument example: {"from":"a","to":"b","weight":1.5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public AdjacencySet(Client client, String key) {
      super(client, key, "adjacency_set");
    }

    /** Native OP ADD for the adjacency_set wire type. */
    /**
     * send native OP ADD to the existing `adjacency_set` key.
     * The server applies `adjacency_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`, `weight`.
     * Reference argument example: {"from":"a","to":"b","weight":1.5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP NEIGHBORS to the existing `adjacency_set` key.
     * The server applies `adjacency_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`.
     * Reference argument example: {"from":"a"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP NEIGHBORS for the adjacency_set wire type. */
    /**
     * send native OP NEIGHBORS to the existing `adjacency_set` key.
     * The server applies `adjacency_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`.
     * Reference argument example: {"from":"a"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object neighbors(Map<String, Object> args) {
      return op("NEIGHBORS", args);
    }

    /**
     * send native OP REMOVE to the existing `adjacency_set` key.
     * The server applies `adjacency_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`.
     * Reference argument example: {"from":"a","to":"b"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object neighbors() {
      return neighbors(Map.of());
    }

    /** Native OP REMOVE for the adjacency_set wire type. */
    /**
     * send native OP REMOVE to the existing `adjacency_set` key.
     * The server applies `adjacency_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`.
     * Reference argument example: {"from":"a","to":"b"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove(Map<String, Object> args) {
      return op("REMOVE", args);
    }

    /**
     * send native OP REMOVE to the existing `adjacency_set` key.
     * The server applies `adjacency_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`.
     * Reference argument example: {"from":"a","to":"b"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove() {
      return remove(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public AggregatedSeries aggregatedSeries(String key) {
    return new AggregatedSeries(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class AggregatedSeries extends TypeHandle {
    /**
     * send native OP AGGREGATE to the existing `aggregated_series` key.
     * The server applies `aggregated_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `source_key`, `from`, `to`, `bucket_ms`, `aggregation`.
     * Reference argument example: {"source_key":"example:timeseries:source","from":0,"to":3000,"bucket_ms":1000,"aggregation":"avg"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public AggregatedSeries(Client client, String key) {
      super(client, key, "aggregated_series");
    }

    /** Native OP AGGREGATE for the aggregated_series wire type. */
    /**
     * send native OP AGGREGATE to the existing `aggregated_series` key.
     * The server applies `aggregated_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `source_key`, `from`, `to`, `bucket_ms`, `aggregation`.
     * Reference argument example: {"source_key":"example:timeseries:source","from":0,"to":3000,"bucket_ms":1000,"aggregation":"avg"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object aggregate(Map<String, Object> args) {
      return op("AGGREGATE", args);
    }

    /**
     * send native OP RANGE to the existing `aggregated_series` key.
     * The server applies `aggregated_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `source_key`, `from`, `to`, `bucket_ms`, `aggregation`.
     * Reference argument example: {"source_key":"example:timeseries:source","from":0,"to":3000,"bucket_ms":1000,"aggregation":"avg"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object aggregate() {
      return aggregate(Map.of());
    }

    /** Native OP RANGE for the aggregated_series wire type. */
    /**
     * send native OP RANGE to the existing `aggregated_series` key.
     * The server applies `aggregated_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `source_key`, `from`, `to`, `bucket_ms`, `aggregation`.
     * Reference argument example: {"source_key":"example:timeseries:source","from":0,"to":3000,"bucket_ms":1000,"aggregation":"avg"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object range(Map<String, Object> args) {
      return op("RANGE", args);
    }

    /**
     * send native OP RANGE to the existing `aggregated_series` key.
     * The server applies `aggregated_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `source_key`, `from`, `to`, `bucket_ms`, `aggregation`.
     * Reference argument example: {"source_key":"example:timeseries:source","from":0,"to":3000,"bucket_ms":1000,"aggregation":"avg"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object range() {
      return range(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Array array(String key) {
    return new Array(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Array extends TypeHandle {
    /**
     * send native OP APPEND to the existing `array` key.
     * The server applies `array` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Array(Client client, String key) {
      super(client, key, "array");
    }

    /** Native OP APPEND for the array wire type. */
    /**
     * send native OP APPEND to the existing `array` key.
     * The server applies `array` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object append(Map<String, Object> args) {
      return op("APPEND", args);
    }

    /**
     * send native OP GET to the existing `array` key.
     * The server applies `array` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `index`.
     * Reference argument example: {"index":0}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object append() {
      return append(Map.of());
    }

    /** Native OP GET for the array wire type. */
    /**
     * send native OP GET to the existing `array` key.
     * The server applies `array` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `index`.
     * Reference argument example: {"index":0}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get(Map<String, Object> args) {
      return op("GET", args);
    }

    /**
     * send native OP LEN to the existing `array` key.
     * The server applies `array` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get() {
      return get(Map.of());
    }

    /** Native OP LEN for the array wire type. */
    /**
     * send native OP LEN to the existing `array` key.
     * The server applies `array` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP SET to the existing `array` key.
     * The server applies `array` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `index`, `value`.
     * Reference argument example: {"index":0,"value":{"type":"utf8_string","value":"updated"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }

    /** Native OP SET for the array wire type. */
    /**
     * send native OP SET to the existing `array` key.
     * The server applies `array` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `index`, `value`.
     * Reference argument example: {"index":0,"value":{"type":"utf8_string","value":"updated"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set(Map<String, Object> args) {
      return op("SET", args);
    }

    /**
     * send native OP VALUES to the existing `array` key.
     * The server applies `array` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set() {
      return set(Map.of());
    }

    /** Native OP VALUES for the array wire type. */
    /**
     * send native OP VALUES to the existing `array` key.
     * The server applies `array` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object values(Map<String, Object> args) {
      return op("VALUES", args);
    }

    /**
     * send native OP VALUES to the existing `array` key.
     * The server applies `array` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object values() {
      return values(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public AtomicCounter atomicCounter(String key) {
    return new AtomicCounter(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class AtomicCounter extends TypeHandle {
    /**
     * send native OP ADD to the existing `atomic_counter` key.
     * The server applies `atomic_counter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `delta`.
     * Reference argument example: {"delta":5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public AtomicCounter(Client client, String key) {
      super(client, key, "atomic_counter");
    }

    /** Native OP ADD for the atomic_counter wire type. */
    /**
     * send native OP ADD to the existing `atomic_counter` key.
     * The server applies `atomic_counter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `delta`.
     * Reference argument example: {"delta":5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP CAS to the existing `atomic_counter` key.
     * The server applies `atomic_counter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `old`, `new`.
     * Reference argument example: {"old":15,"new":20}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP CAS for the atomic_counter wire type. */
    /**
     * send native OP CAS to the existing `atomic_counter` key.
     * The server applies `atomic_counter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `old`, `new`.
     * Reference argument example: {"old":15,"new":20}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object cas(Map<String, Object> args) {
      return op("CAS", args);
    }

    /**
     * send native OP LOAD to the existing `atomic_counter` key.
     * The server applies `atomic_counter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object cas() {
      return cas(Map.of());
    }

    /** Native OP LOAD for the atomic_counter wire type. */
    /**
     * send native OP LOAD to the existing `atomic_counter` key.
     * The server applies `atomic_counter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object load(Map<String, Object> args) {
      return op("LOAD", args);
    }

    /**
     * send native OP LOAD to the existing `atomic_counter` key.
     * The server applies `atomic_counter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object load() {
      return load(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Barrier barrier(String key) {
    return new Barrier(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Barrier extends TypeHandle {
    /**
     * send native OP WAIT to the existing `barrier` key.
     * The server applies `barrier` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Barrier(Client client, String key) {
      super(client, key, "barrier");
    }

    /** Native OP WAIT for the barrier wire type. */
    /**
     * send native OP WAIT to the existing `barrier` key.
     * The server applies `barrier` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object wait(Map<String, Object> args) {
      return op("WAIT", args);
    }

    /**
     * Performs the corresponding documented Amaquet operation.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object waitOperation() {
      return wait(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public BigDecimal bigDecimal(String key) {
    return new BigDecimal(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class BigDecimal extends TypeHandle {
    /**
     * send native OP ADD to the existing `big_decimal` key.
     * The server applies `big_decimal` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"0.3211"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public BigDecimal(Client client, String key) {
      super(client, key, "big_decimal");
    }

    /** Native OP ADD for the big_decimal wire type. */
    /**
     * send native OP ADD to the existing `big_decimal` key.
     * The server applies `big_decimal` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"0.3211"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP CMP to the existing `big_decimal` key.
     * The server applies `big_decimal` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"12345678901234567890.321100000000000001"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP CMP for the big_decimal wire type. */
    /**
     * send native OP CMP to the existing `big_decimal` key.
     * The server applies `big_decimal` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"12345678901234567890.321100000000000001"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object cmp(Map<String, Object> args) {
      return op("CMP", args);
    }

    /**
     * send native OP SUB to the existing `big_decimal` key.
     * The server applies `big_decimal` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"0.0011"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object cmp() {
      return cmp(Map.of());
    }

    /** Native OP SUB for the big_decimal wire type. */
    /**
     * send native OP SUB to the existing `big_decimal` key.
     * The server applies `big_decimal` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"0.0011"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object sub(Map<String, Object> args) {
      return op("SUB", args);
    }

    /**
     * send native OP SUB to the existing `big_decimal` key.
     * The server applies `big_decimal` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"0.0011"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object sub() {
      return sub(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public BigInteger bigInteger(String key) {
    return new BigInteger(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class BigInteger extends TypeHandle {
    /**
     * send native OP ADD to the existing `big_integer` key.
     * The server applies `big_integer` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"10"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public BigInteger(Client client, String key) {
      super(client, key, "big_integer");
    }

    /** Native OP ADD for the big_integer wire type. */
    /**
     * send native OP ADD to the existing `big_integer` key.
     * The server applies `big_integer` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"10"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP ADD to the existing `big_integer` key.
     * The server applies `big_integer` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"10"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public BinaryString binaryString(String key) {
    return new BinaryString(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class BinaryString extends TypeHandle {
    /**
     * Performs the corresponding documented Amaquet operation.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     */
    public BinaryString(Client client, String key) {
      super(client, key, "binary_string");
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public BitField bitField(String key) {
    return new BitField(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class BitField extends TypeHandle {
    /**
     * send native OP SET to the existing `bit_field` key.
     * The server applies `bit_field` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `offset`, `width`, `value`.
     * Reference argument example: {"offset":4,"width":8,"value":171}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public BitField(Client client, String key) {
      super(client, key, "bit_field");
    }

    /** Native OP SET for the bit_field wire type. */
    /**
     * send native OP SET to the existing `bit_field` key.
     * The server applies `bit_field` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `offset`, `width`, `value`.
     * Reference argument example: {"offset":4,"width":8,"value":171}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set(Map<String, Object> args) {
      return op("SET", args);
    }

    /**
     * send native OP GET to the existing `bit_field` key.
     * The server applies `bit_field` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `offset`, `width`.
     * Reference argument example: {"offset":4,"width":8}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set() {
      return set(Map.of());
    }

    /** Native OP GET for the bit_field wire type. */
    /**
     * send native OP GET to the existing `bit_field` key.
     * The server applies `bit_field` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `offset`, `width`.
     * Reference argument example: {"offset":4,"width":8}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get(Map<String, Object> args) {
      return op("GET", args);
    }

    /**
     * Read current data for a key or type-specific field, subject to server permissions and key existence.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get() {
      return get(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public BitSet bitSet(String key) {
    return new BitSet(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class BitSet extends TypeHandle {
    /**
     * send native OP SET to the existing `bit_set` key.
     * The server applies `bit_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `bit`, `value`.
     * Reference argument example: {"bit":7,"value":true}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public BitSet(Client client, String key) {
      super(client, key, "bit_set");
    }

    /** Native OP SET for the bit_set wire type. */
    /**
     * send native OP SET to the existing `bit_set` key.
     * The server applies `bit_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `bit`, `value`.
     * Reference argument example: {"bit":7,"value":true}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set(Map<String, Object> args) {
      return op("SET", args);
    }

    /**
     * send native OP GETBIT to the existing `bit_set` key.
     * The server applies `bit_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `bit`.
     * Reference argument example: {"bit":7}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set() {
      return set(Map.of());
    }

    /** Native OP GETBIT for the bit_set wire type. */
    /**
     * send native OP GETBIT to the existing `bit_set` key.
     * The server applies `bit_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `bit`.
     * Reference argument example: {"bit":7}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object getbit(Map<String, Object> args) {
      return op("GETBIT", args);
    }

    /**
     * send native OP COUNT to the existing `bit_set` key.
     * The server applies `bit_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object getbit() {
      return getbit(Map.of());
    }

    /** Native OP COUNT for the bit_set wire type. */
    /**
     * send native OP COUNT to the existing `bit_set` key.
     * The server applies `bit_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object count(Map<String, Object> args) {
      return op("COUNT", args);
    }

    /**
     * send native OP COUNT to the existing `bit_set` key.
     * The server applies `bit_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object count() {
      return count(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Bitmap bitmap(String key) {
    return new Bitmap(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Bitmap extends TypeHandle {
    /**
     * send native OP SET to the existing `bitmap` key.
     * The server applies `bitmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `bit`, `value`.
     * Reference argument example: {"bit":7,"value":true}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Bitmap(Client client, String key) {
      super(client, key, "bitmap");
    }

    /** Native OP SET for the bitmap wire type. */
    /**
     * send native OP SET to the existing `bitmap` key.
     * The server applies `bitmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `bit`, `value`.
     * Reference argument example: {"bit":7,"value":true}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set(Map<String, Object> args) {
      return op("SET", args);
    }

    /**
     * send native OP GETBIT to the existing `bitmap` key.
     * The server applies `bitmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `bit`.
     * Reference argument example: {"bit":7}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set() {
      return set(Map.of());
    }

    /** Native OP GETBIT for the bitmap wire type. */
    /**
     * send native OP GETBIT to the existing `bitmap` key.
     * The server applies `bitmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `bit`.
     * Reference argument example: {"bit":7}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object getbit(Map<String, Object> args) {
      return op("GETBIT", args);
    }

    /**
     * send native OP COUNT to the existing `bitmap` key.
     * The server applies `bitmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object getbit() {
      return getbit(Map.of());
    }

    /** Native OP COUNT for the bitmap wire type. */
    /**
     * send native OP COUNT to the existing `bitmap` key.
     * The server applies `bitmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object count(Map<String, Object> args) {
      return op("COUNT", args);
    }

    /**
     * send native OP COUNT to the existing `bitmap` key.
     * The server applies `bitmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object count() {
      return count(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public BlockingQueue blockingQueue(String key) {
    return new BlockingQueue(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class BlockingQueue extends TypeHandle {
    /**
     * send native OP ENQUEUE to the existing `blocking_queue` key.
     * The server applies `blocking_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public BlockingQueue(Client client, String key) {
      super(client, key, "blocking_queue");
    }

    /** Native OP ENQUEUE for the blocking_queue wire type. */
    /**
     * send native OP ENQUEUE to the existing `blocking_queue` key.
     * The server applies `blocking_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object enqueue(Map<String, Object> args) {
      return op("ENQUEUE", args);
    }

    /**
     * send native OP DEQUEUE to the existing `blocking_queue` key.
     * The server applies `blocking_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object enqueue() {
      return enqueue(Map.of());
    }

    /** Native OP DEQUEUE for the blocking_queue wire type. */
    /**
     * send native OP DEQUEUE to the existing `blocking_queue` key.
     * The server applies `blocking_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object dequeue(Map<String, Object> args) {
      return op("DEQUEUE", args);
    }

    /**
     * send native OP CLOSE to the existing `blocking_queue` key.
     * The server applies `blocking_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object dequeue() {
      return dequeue(Map.of());
    }

    /** Native OP CLOSE for the blocking_queue wire type. */
    /**
     * send native OP CLOSE to the existing `blocking_queue` key.
     * The server applies `blocking_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object close(Map<String, Object> args) {
      return op("CLOSE", args);
    }

    /**
     * send native OP LEN to the existing `blocking_queue` key.
     * The server applies `blocking_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object close() {
      return close(Map.of());
    }

    /** Native OP LEN for the blocking_queue wire type. */
    /**
     * send native OP LEN to the existing `blocking_queue` key.
     * The server applies `blocking_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP LEN to the existing `blocking_queue` key.
     * The server applies `blocking_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public BloomFilter bloomFilter(String key) {
    return new BloomFilter(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class BloomFilter extends TypeHandle {
    /**
     * send native OP ADD to the existing `bloom_filter` key.
     * The server applies `bloom_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public BloomFilter(Client client, String key) {
      super(client, key, "bloom_filter");
    }

    /** Native OP ADD for the bloom_filter wire type. */
    /**
     * send native OP ADD to the existing `bloom_filter` key.
     * The server applies `bloom_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP CONTAINS to the existing `bloom_filter` key.
     * The server applies `bloom_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP CONTAINS for the bloom_filter wire type. */
    /**
     * send native OP CONTAINS to the existing `bloom_filter` key.
     * The server applies `bloom_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object contains(Map<String, Object> args) {
      return op("CONTAINS", args);
    }

    /**
     * send native OP CONTAINS to the existing `bloom_filter` key.
     * The server applies `bloom_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object contains() {
      return contains(Map.of());
    }
  }

  /**
   * typed facade for the registered `boolean` value stored under an Amaquet key.
   * Construction command: `SET`. Documented construction option keys: (none).
   * Supported named operations: `TOGGLE`.
   * The facade forwards requests to the server and does not validate domain-specific arguments before transmission.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Boolean booleanType(String key) {
    return new Boolean(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Boolean extends TypeHandle {
    /**
     * send native OP TOGGLE to the existing `boolean` key.
     * The server applies `boolean` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Boolean(Client client, String key) {
      super(client, key, "boolean");
    }

    /** Native OP TOGGLE for the boolean wire type. */
    /**
     * send native OP TOGGLE to the existing `boolean` key.
     * The server applies `boolean` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object toggle(Map<String, Object> args) {
      return op("TOGGLE", args);
    }

    /**
     * send native OP TOGGLE to the existing `boolean` key.
     * The server applies `boolean` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object toggle() {
      return toggle(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public BoundingBox boundingBox(String key) {
    return new BoundingBox(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class BoundingBox extends TypeHandle {
    /**
     * send native OP CONTAINS to the existing `bounding_box` key.
     * The server applies `bounding_box` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `point`.
     * Reference argument example: {"point":{"Lat":14.5,"Lon":121.0}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public BoundingBox(Client client, String key) {
      super(client, key, "bounding_box");
    }

    /** Native OP CONTAINS for the bounding_box wire type. */
    /**
     * send native OP CONTAINS to the existing `bounding_box` key.
     * The server applies `bounding_box` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `point`.
     * Reference argument example: {"point":{"Lat":14.5,"Lon":121.0}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object contains(Map<String, Object> args) {
      return op("CONTAINS", args);
    }

    /**
     * send native OP CONTAINS to the existing `bounding_box` key.
     * The server applies `bounding_box` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `point`.
     * Reference argument example: {"point":{"Lat":14.5,"Lon":121.0}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object contains() {
      return contains(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public BtreeIndex btreeIndex(String key) {
    return new BtreeIndex(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class BtreeIndex extends TypeHandle {
    /**
     * send native OP INSERT to the existing `btree_index` key.
     * The server applies `btree_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `id`.
     * Reference argument example: {"value":"b","id":"2"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public BtreeIndex(Client client, String key) {
      super(client, key, "btree_index");
    }

    /** Native OP INSERT for the btree_index wire type. */
    /**
     * send native OP INSERT to the existing `btree_index` key.
     * The server applies `btree_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `id`.
     * Reference argument example: {"value":"b","id":"2"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object insert(Map<String, Object> args) {
      return op("INSERT", args);
    }

    /**
     * send native OP SEARCH to the existing `btree_index` key.
     * The server applies `btree_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"a"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object insert() {
      return insert(Map.of());
    }

    /** Native OP SEARCH for the btree_index wire type. */
    /**
     * send native OP SEARCH to the existing `btree_index` key.
     * The server applies `btree_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"a"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object search(Map<String, Object> args) {
      return op("SEARCH", args);
    }

    /**
     * send native OP DELETE to the existing `btree_index` key.
     * The server applies `btree_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `id`.
     * Reference argument example: {"value":"b","id":"2"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object search() {
      return search(Map.of());
    }

    /** Native OP DELETE for the btree_index wire type. */
    /**
     * send native OP DELETE to the existing `btree_index` key.
     * The server applies `btree_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `id`.
     * Reference argument example: {"value":"b","id":"2"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object delete(Map<String, Object> args) {
      return op("DELETE", args);
    }

    /**
     * send native OP RANGE to the existing `btree_index` key.
     * The server applies `btree_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `max`, `limit`.
     * Reference argument example: {"value":"a","max":"z","limit":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object delete() {
      return delete(Map.of());
    }

    /** Native OP RANGE for the btree_index wire type. */
    /**
     * send native OP RANGE to the existing `btree_index` key.
     * The server applies `btree_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `max`, `limit`.
     * Reference argument example: {"value":"a","max":"z","limit":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object range(Map<String, Object> args) {
      return op("RANGE", args);
    }

    /**
     * send native OP RANGE to the existing `btree_index` key.
     * The server applies `btree_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `max`, `limit`.
     * Reference argument example: {"value":"a","max":"z","limit":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object range() {
      return range(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Cbor cbor(String key) {
    return new Cbor(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Cbor extends TypeHandle {
    /**
     * send native OP GET to the existing `cbor` key.
     * The server applies `cbor` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Cbor(Client client, String key) {
      super(client, key, "cbor");
    }

    /** Native OP GET for the cbor wire type. */
    /**
     * send native OP GET to the existing `cbor` key.
     * The server applies `cbor` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get(Map<String, Object> args) {
      return op("GET", args);
    }

    /**
     * Read current data for a key or type-specific field, subject to server permissions and key existence.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get() {
      return get(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public CidrNetwork cidrNetwork(String key) {
    return new CidrNetwork(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class CidrNetwork extends TypeHandle {
    /**
     * send native OP CONTAINS to the existing `cidr_network` key.
     * The server applies `cidr_network` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `ip`.
     * Reference argument example: {"ip":"10.1.2.3"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public CidrNetwork(Client client, String key) {
      super(client, key, "cidr_network");
    }

    /** Native OP CONTAINS for the cidr_network wire type. */
    /**
     * send native OP CONTAINS to the existing `cidr_network` key.
     * The server applies `cidr_network` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `ip`.
     * Reference argument example: {"ip":"10.1.2.3"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object contains(Map<String, Object> args) {
      return op("CONTAINS", args);
    }

    /**
     * send native OP CONTAINS to the existing `cidr_network` key.
     * The server applies `cidr_network` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `ip`.
     * Reference argument example: {"ip":"10.1.2.3"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object contains() {
      return contains(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public ConsumerGroup consumerGroup(String key) {
    return new ConsumerGroup(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class ConsumerGroup extends TypeHandle {
    /**
     * send native OP SNAPSHOT to the existing `consumer_group` key.
     * The server applies `consumer_group` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public ConsumerGroup(Client client, String key) {
      super(client, key, "consumer_group");
    }

    /** Native OP SNAPSHOT for the consumer_group wire type. */
    /**
     * send native OP SNAPSHOT to the existing `consumer_group` key.
     * The server applies `consumer_group` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object snapshot(Map<String, Object> args) {
      return op("SNAPSHOT", args);
    }

    /**
     * Read the server snapshot of the current key; returned JSON shape depends on the stored wire type.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object snapshot() {
      return snapshot(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public CountMinSketch countMinSketch(String key) {
    return new CountMinSketch(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class CountMinSketch extends TypeHandle {
    /**
     * send native OP ADD to the existing `count_min_sketch` key.
     * The server applies `count_min_sketch` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `count`.
     * Reference argument example: {"value":"alice","count":5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public CountMinSketch(Client client, String key) {
      super(client, key, "count_min_sketch");
    }

    /** Native OP ADD for the count_min_sketch wire type. */
    /**
     * send native OP ADD to the existing `count_min_sketch` key.
     * The server applies `count_min_sketch` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `count`.
     * Reference argument example: {"value":"alice","count":5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP ESTIMATE to the existing `count_min_sketch` key.
     * The server applies `count_min_sketch` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP ESTIMATE for the count_min_sketch wire type. */
    /**
     * send native OP ESTIMATE to the existing `count_min_sketch` key.
     * The server applies `count_min_sketch` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object estimate(Map<String, Object> args) {
      return op("ESTIMATE", args);
    }

    /**
     * send native OP ESTIMATE to the existing `count_min_sketch` key.
     * The server applies `count_min_sketch` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object estimate() {
      return estimate(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public CounterSeries counterSeries(String key) {
    return new CounterSeries(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class CounterSeries extends TypeHandle {
    /**
     * send native OP ADD to the existing `counter_series` key.
     * The server applies `counter_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `timestamp`, `value`.
     * Reference argument example: {"timestamp":1000,"value":5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public CounterSeries(Client client, String key) {
      super(client, key, "counter_series");
    }

    /** Native OP ADD for the counter_series wire type. */
    /**
     * send native OP ADD to the existing `counter_series` key.
     * The server applies `counter_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `timestamp`, `value`.
     * Reference argument example: {"timestamp":1000,"value":5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP LAST to the existing `counter_series` key.
     * The server applies `counter_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP LAST for the counter_series wire type. */
    /**
     * send native OP LAST to the existing `counter_series` key.
     * The server applies `counter_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object last(Map<String, Object> args) {
      return op("LAST", args);
    }

    /**
     * send native OP AGGREGATE to the existing `counter_series` key.
     * The server applies `counter_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`, `bucket_ms`, `aggregation`.
     * Reference argument example: {"from":0,"to":3000,"bucket_ms":1000,"aggregation":"sum"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object last() {
      return last(Map.of());
    }

    /** Native OP AGGREGATE for the counter_series wire type. */
    /**
     * send native OP AGGREGATE to the existing `counter_series` key.
     * The server applies `counter_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`, `bucket_ms`, `aggregation`.
     * Reference argument example: {"from":0,"to":3000,"bucket_ms":1000,"aggregation":"sum"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object aggregate(Map<String, Object> args) {
      return op("AGGREGATE", args);
    }

    /**
     * send native OP LEN to the existing `counter_series` key.
     * The server applies `counter_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object aggregate() {
      return aggregate(Map.of());
    }

    /** Native OP LEN for the counter_series wire type. */
    /**
     * send native OP LEN to the existing `counter_series` key.
     * The server applies `counter_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP RANGE to the existing `counter_series` key.
     * The server applies `counter_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`.
     * Reference argument example: {"from":0,"to":3000}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }

    /** Native OP RANGE for the counter_series wire type. */
    /**
     * send native OP RANGE to the existing `counter_series` key.
     * The server applies `counter_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`.
     * Reference argument example: {"from":0,"to":3000}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object range(Map<String, Object> args) {
      return op("RANGE", args);
    }

    /**
     * send native OP RANGE to the existing `counter_series` key.
     * The server applies `counter_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`.
     * Reference argument example: {"from":0,"to":3000}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object range() {
      return range(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public CountingBloomFilter countingBloomFilter(String key) {
    return new CountingBloomFilter(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class CountingBloomFilter extends TypeHandle {
    /**
     * send native OP ADD to the existing `counting_bloom_filter` key.
     * The server applies `counting_bloom_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public CountingBloomFilter(Client client, String key) {
      super(client, key, "counting_bloom_filter");
    }

    /** Native OP ADD for the counting_bloom_filter wire type. */
    /**
     * send native OP ADD to the existing `counting_bloom_filter` key.
     * The server applies `counting_bloom_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP CONTAINS to the existing `counting_bloom_filter` key.
     * The server applies `counting_bloom_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP CONTAINS for the counting_bloom_filter wire type. */
    /**
     * send native OP CONTAINS to the existing `counting_bloom_filter` key.
     * The server applies `counting_bloom_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object contains(Map<String, Object> args) {
      return op("CONTAINS", args);
    }

    /**
     * send native OP REMOVE to the existing `counting_bloom_filter` key.
     * The server applies `counting_bloom_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object contains() {
      return contains(Map.of());
    }

    /** Native OP REMOVE for the counting_bloom_filter wire type. */
    /**
     * send native OP REMOVE to the existing `counting_bloom_filter` key.
     * The server applies `counting_bloom_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove(Map<String, Object> args) {
      return op("REMOVE", args);
    }

    /**
     * send native OP REMOVE to the existing `counting_bloom_filter` key.
     * The server applies `counting_bloom_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove() {
      return remove(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public CuckooFilter cuckooFilter(String key) {
    return new CuckooFilter(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class CuckooFilter extends TypeHandle {
    /**
     * send native OP ADD to the existing `cuckoo_filter` key.
     * The server applies `cuckoo_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public CuckooFilter(Client client, String key) {
      super(client, key, "cuckoo_filter");
    }

    /** Native OP ADD for the cuckoo_filter wire type. */
    /**
     * send native OP ADD to the existing `cuckoo_filter` key.
     * The server applies `cuckoo_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP CONTAINS to the existing `cuckoo_filter` key.
     * The server applies `cuckoo_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP CONTAINS for the cuckoo_filter wire type. */
    /**
     * send native OP CONTAINS to the existing `cuckoo_filter` key.
     * The server applies `cuckoo_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object contains(Map<String, Object> args) {
      return op("CONTAINS", args);
    }

    /**
     * send native OP REMOVE to the existing `cuckoo_filter` key.
     * The server applies `cuckoo_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object contains() {
      return contains(Map.of());
    }

    /** Native OP REMOVE for the cuckoo_filter wire type. */
    /**
     * send native OP REMOVE to the existing `cuckoo_filter` key.
     * The server applies `cuckoo_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove(Map<String, Object> args) {
      return op("REMOVE", args);
    }

    /**
     * send native OP REMOVE to the existing `cuckoo_filter` key.
     * The server applies `cuckoo_filter` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove() {
      return remove(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Decimal decimal(String key) {
    return new Decimal(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Decimal extends TypeHandle {
    /**
     * send native OP ADD to the existing `decimal` key.
     * The server applies `decimal` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"0.3211"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Decimal(Client client, String key) {
      super(client, key, "decimal");
    }

    /** Native OP ADD for the decimal wire type. */
    /**
     * send native OP ADD to the existing `decimal` key.
     * The server applies `decimal` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"0.3211"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP CMP to the existing `decimal` key.
     * The server applies `decimal` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"12346.0000"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP CMP for the decimal wire type. */
    /**
     * send native OP CMP to the existing `decimal` key.
     * The server applies `decimal` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"12346.0000"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object cmp(Map<String, Object> args) {
      return op("CMP", args);
    }

    /**
     * send native OP SUB to the existing `decimal` key.
     * The server applies `decimal` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"0.3211"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object cmp() {
      return cmp(Map.of());
    }

    /** Native OP SUB for the decimal wire type. */
    /**
     * send native OP SUB to the existing `decimal` key.
     * The server applies `decimal` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"0.3211"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object sub(Map<String, Object> args) {
      return op("SUB", args);
    }

    /**
     * send native OP SUB to the existing `decimal` key.
     * The server applies `decimal` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"0.3211"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object sub() {
      return sub(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public DelayedQueue delayedQueue(String key) {
    return new DelayedQueue(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class DelayedQueue extends TypeHandle {
    /**
     * send native OP ENQUEUE to the existing `delayed_queue` key.
     * The server applies `delayed_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`, `value`, `available_at_ms`.
     * Reference argument example: {"id":"job1","value":{"type":"utf8_string","value":"payload"},"available_at_ms":0}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public DelayedQueue(Client client, String key) {
      super(client, key, "delayed_queue");
    }

    /** Native OP ENQUEUE for the delayed_queue wire type. */
    /**
     * send native OP ENQUEUE to the existing `delayed_queue` key.
     * The server applies `delayed_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`, `value`, `available_at_ms`.
     * Reference argument example: {"id":"job1","value":{"type":"utf8_string","value":"payload"},"available_at_ms":0}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object enqueue(Map<String, Object> args) {
      return op("ENQUEUE", args);
    }

    /**
     * send native OP POP_READY to the existing `delayed_queue` key.
     * The server applies `delayed_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object enqueue() {
      return enqueue(Map.of());
    }

    /** Native OP POP_READY for the delayed_queue wire type. */
    /**
     * send native OP POP_READY to the existing `delayed_queue` key.
     * The server applies `delayed_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object popReady(Map<String, Object> args) {
      return op("POP_READY", args);
    }

    /**
     * send native OP LEN to the existing `delayed_queue` key.
     * The server applies `delayed_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object popReady() {
      return popReady(Map.of());
    }

    /**
     * send native OP LEN to the existing `delayed_queue` key.
     * The server applies `delayed_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object pop_ready(Map<String, Object> args) {
      return popReady(args);
    }

    /** Native OP LEN for the delayed_queue wire type. */
    /**
     * send native OP LEN to the existing `delayed_queue` key.
     * The server applies `delayed_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP LEN to the existing `delayed_queue` key.
     * The server applies `delayed_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public DenseVector denseVector(String key) {
    return new DenseVector(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class DenseVector extends TypeHandle {
    /**
     * send native OP DISTANCE to the existing `dense_vector` key.
     * The server applies `dense_vector` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `vector`, `metric`.
     * Reference argument example: {"vector":[1.0,0.0,0.5],"metric":"cosine"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public DenseVector(Client client, String key) {
      super(client, key, "dense_vector");
    }

    /** Native OP DISTANCE for the dense_vector wire type. */
    /**
     * send native OP DISTANCE to the existing `dense_vector` key.
     * The server applies `dense_vector` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `vector`, `metric`.
     * Reference argument example: {"vector":[1.0,0.0,0.5],"metric":"cosine"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object distance(Map<String, Object> args) {
      return op("DISTANCE", args);
    }

    /**
     * send native OP DISTANCE to the existing `dense_vector` key.
     * The server applies `dense_vector` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `vector`, `metric`.
     * Reference argument example: {"vector":[1.0,0.0,0.5],"metric":"cosine"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object distance() {
      return distance(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Deque deque(String key) {
    return new Deque(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Deque extends TypeHandle {
    /**
     * send native OP PUSH_BACK to the existing `deque` key.
     * The server applies `deque` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Deque(Client client, String key) {
      super(client, key, "deque");
    }

    /** Native OP PUSH_BACK for the deque wire type. */
    /**
     * send native OP PUSH_BACK to the existing `deque` key.
     * The server applies `deque` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object pushBack(Map<String, Object> args) {
      return op("PUSH_BACK", args);
    }

    /**
     * send native OP PUSH_FRONT to the existing `deque` key.
     * The server applies `deque` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"integer","value":7}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object pushBack() {
      return pushBack(Map.of());
    }

    /**
     * send native OP PUSH_FRONT to the existing `deque` key.
     * The server applies `deque` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"integer","value":7}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object push_back(Map<String, Object> args) {
      return pushBack(args);
    }

    /** Native OP PUSH_FRONT for the deque wire type. */
    /**
     * send native OP PUSH_FRONT to the existing `deque` key.
     * The server applies `deque` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"integer","value":7}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object pushFront(Map<String, Object> args) {
      return op("PUSH_FRONT", args);
    }

    /**
     * send native OP LEN to the existing `deque` key.
     * The server applies `deque` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object pushFront() {
      return pushFront(Map.of());
    }

    /**
     * send native OP LEN to the existing `deque` key.
     * The server applies `deque` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object push_front(Map<String, Object> args) {
      return pushFront(args);
    }

    /** Native OP LEN for the deque wire type. */
    /**
     * send native OP LEN to the existing `deque` key.
     * The server applies `deque` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP POP_FRONT to the existing `deque` key.
     * The server applies `deque` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }

    /** Native OP POP_FRONT for the deque wire type. */
    /**
     * send native OP POP_FRONT to the existing `deque` key.
     * The server applies `deque` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object popFront(Map<String, Object> args) {
      return op("POP_FRONT", args);
    }

    /**
     * send native OP POP_BACK to the existing `deque` key.
     * The server applies `deque` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object popFront() {
      return popFront(Map.of());
    }

    /**
     * send native OP POP_BACK to the existing `deque` key.
     * The server applies `deque` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object pop_front(Map<String, Object> args) {
      return popFront(args);
    }

    /** Native OP POP_BACK for the deque wire type. */
    /**
     * send native OP POP_BACK to the existing `deque` key.
     * The server applies `deque` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object popBack(Map<String, Object> args) {
      return op("POP_BACK", args);
    }

    /**
     * send native OP VALUES to the existing `deque` key.
     * The server applies `deque` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object popBack() {
      return popBack(Map.of());
    }

    /**
     * send native OP VALUES to the existing `deque` key.
     * The server applies `deque` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object pop_back(Map<String, Object> args) {
      return popBack(args);
    }

    /** Native OP VALUES for the deque wire type. */
    /**
     * send native OP VALUES to the existing `deque` key.
     * The server applies `deque` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object values(Map<String, Object> args) {
      return op("VALUES", args);
    }

    /**
     * send native OP VALUES to the existing `deque` key.
     * The server applies `deque` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object values() {
      return values(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Duration duration(String key) {
    return new Duration(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Duration extends TypeHandle {
    /**
     * send native OP ADD to the existing `duration` key.
     * The server applies `duration` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `delta_ms`.
     * Reference argument example: {"delta_ms":1000}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Duration(Client client, String key) {
      super(client, key, "duration");
    }

    /** Native OP ADD for the duration wire type. */
    /**
     * send native OP ADD to the existing `duration` key.
     * The server applies `duration` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `delta_ms`.
     * Reference argument example: {"delta_ms":1000}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP ADD to the existing `duration` key.
     * The server applies `duration` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `delta_ms`.
     * Reference argument example: {"delta_ms":1000}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public EventLog eventLog(String key) {
    return new EventLog(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class EventLog extends TypeHandle {
    /**
     * send native OP APPEND to the existing `event_log` key.
     * The server applies `event_log` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `event_type`, `aggregate_id`, `payload`.
     * Reference argument example: {"event_type":"created","aggregate_id":"agg1","payload":{"type":"utf8_string","value":"born"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public EventLog(Client client, String key) {
      super(client, key, "event_log");
    }

    /** Native OP APPEND for the event_log wire type. */
    /**
     * send native OP APPEND to the existing `event_log` key.
     * The server applies `event_log` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `event_type`, `aggregate_id`, `payload`.
     * Reference argument example: {"event_type":"created","aggregate_id":"agg1","payload":{"type":"utf8_string","value":"born"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object append(Map<String, Object> args) {
      return op("APPEND", args);
    }

    /**
     * send native OP LEN to the existing `event_log` key.
     * The server applies `event_log` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object append() {
      return append(Map.of());
    }

    /** Native OP LEN for the event_log wire type. */
    /**
     * send native OP LEN to the existing `event_log` key.
     * The server applies `event_log` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP READ to the existing `event_log` key.
     * The server applies `event_log` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `count`.
     * Reference argument example: {"from":1,"count":100}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }

    /** Native OP READ for the event_log wire type. */
    /**
     * send native OP READ to the existing `event_log` key.
     * The server applies `event_log` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `count`.
     * Reference argument example: {"from":1,"count":100}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object read(Map<String, Object> args) {
      return op("READ", args);
    }

    /**
     * send native OP READ to the existing `event_log` key.
     * The server applies `event_log` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `count`.
     * Reference argument example: {"from":1,"count":100}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object read() {
      return read(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public FifoQueue fifoQueue(String key) {
    return new FifoQueue(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class FifoQueue extends TypeHandle {
    /**
     * send native OP ENQUEUE to the existing `fifo_queue` key.
     * The server applies `fifo_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"first"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public FifoQueue(Client client, String key) {
      super(client, key, "fifo_queue");
    }

    /** Native OP ENQUEUE for the fifo_queue wire type. */
    /**
     * send native OP ENQUEUE to the existing `fifo_queue` key.
     * The server applies `fifo_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"first"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object enqueue(Map<String, Object> args) {
      return op("ENQUEUE", args);
    }

    /**
     * send native OP PEEK to the existing `fifo_queue` key.
     * The server applies `fifo_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object enqueue() {
      return enqueue(Map.of());
    }

    /** Native OP PEEK for the fifo_queue wire type. */
    /**
     * send native OP PEEK to the existing `fifo_queue` key.
     * The server applies `fifo_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object peek(Map<String, Object> args) {
      return op("PEEK", args);
    }

    /**
     * send native OP DEQUEUE to the existing `fifo_queue` key.
     * The server applies `fifo_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object peek() {
      return peek(Map.of());
    }

    /** Native OP DEQUEUE for the fifo_queue wire type. */
    /**
     * send native OP DEQUEUE to the existing `fifo_queue` key.
     * The server applies `fifo_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object dequeue(Map<String, Object> args) {
      return op("DEQUEUE", args);
    }

    /**
     * send native OP LEN to the existing `fifo_queue` key.
     * The server applies `fifo_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object dequeue() {
      return dequeue(Map.of());
    }

    /** Native OP LEN for the fifo_queue wire type. */
    /**
     * send native OP LEN to the existing `fifo_queue` key.
     * The server applies `fifo_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP LEN to the existing `fifo_queue` key.
     * The server applies `fifo_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public FlatVectorIndex flatVectorIndex(String key) {
    return new FlatVectorIndex(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class FlatVectorIndex extends TypeHandle {
    /**
     * send native OP ADD to the existing `flat_vector_index` key.
     * The server applies `flat_vector_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`, `vector`, `metadata`.
     * Reference argument example: {"id":"v1","vector":[1,0,0],"metadata":{}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public FlatVectorIndex(Client client, String key) {
      super(client, key, "flat_vector_index");
    }

    /** Native OP ADD for the flat_vector_index wire type. */
    /**
     * send native OP ADD to the existing `flat_vector_index` key.
     * The server applies `flat_vector_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`, `vector`, `metadata`.
     * Reference argument example: {"id":"v1","vector":[1,0,0],"metadata":{}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP SEARCH to the existing `flat_vector_index` key.
     * The server applies `flat_vector_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `vector`, `k`.
     * Reference argument example: {"vector":[1,0,0],"k":1}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP SEARCH for the flat_vector_index wire type. */
    /**
     * send native OP SEARCH to the existing `flat_vector_index` key.
     * The server applies `flat_vector_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `vector`, `k`.
     * Reference argument example: {"vector":[1,0,0],"k":1}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object search(Map<String, Object> args) {
      return op("SEARCH", args);
    }

    /**
     * send native OP REMOVE to the existing `flat_vector_index` key.
     * The server applies `flat_vector_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`.
     * Reference argument example: {"id":"v1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object search() {
      return search(Map.of());
    }

    /** Native OP REMOVE for the flat_vector_index wire type. */
    /**
     * send native OP REMOVE to the existing `flat_vector_index` key.
     * The server applies `flat_vector_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`.
     * Reference argument example: {"id":"v1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove(Map<String, Object> args) {
      return op("REMOVE", args);
    }

    /**
     * send native OP LEN to the existing `flat_vector_index` key.
     * The server applies `flat_vector_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove() {
      return remove(Map.of());
    }

    /** Native OP LEN for the flat_vector_index wire type. */
    /**
     * send native OP LEN to the existing `flat_vector_index` key.
     * The server applies `flat_vector_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP LEN to the existing `flat_vector_index` key.
     * The server applies `flat_vector_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }
  }

  /**
   * typed facade for the registered `float` value stored under an Amaquet key.
   * Construction command: `SET`. Documented construction option keys: (none).
   * Supported named operations: `ADD`.
   * The facade forwards requests to the server and does not validate domain-specific arguments before transmission.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Float floatType(String key) {
    return new Float(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Float extends TypeHandle {
    /**
     * send native OP ADD to the existing `float` key.
     * The server applies `float` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `delta`.
     * Reference argument example: {"delta":0.5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Float(Client client, String key) {
      super(client, key, "float");
    }

    /** Native OP ADD for the float wire type. */
    /**
     * send native OP ADD to the existing `float` key.
     * The server applies `float` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `delta`.
     * Reference argument example: {"delta":0.5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP ADD to the existing `float` key.
     * The server applies `float` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `delta`.
     * Reference argument example: {"delta":0.5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public FullTextDocument fullTextDocument(String key) {
    return new FullTextDocument(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class FullTextDocument extends TypeHandle {
    /**
     * send native OP ADD_TEXT to the existing `full_text_document` key.
     * The server applies `full_text_document` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `name`, `value`, `weight`.
     * Reference argument example: {"name":"body","value":"hello amaquet world","weight":2}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public FullTextDocument(Client client, String key) {
      super(client, key, "full_text_document");
    }

    /** Native OP ADD_TEXT for the full_text_document wire type. */
    /**
     * send native OP ADD_TEXT to the existing `full_text_document` key.
     * The server applies `full_text_document` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `name`, `value`, `weight`.
     * Reference argument example: {"name":"body","value":"hello amaquet world","weight":2}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object addText(Map<String, Object> args) {
      return op("ADD_TEXT", args);
    }

    /**
     * send native OP ADD_TAG to the existing `full_text_document` key.
     * The server applies `full_text_document` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `name`, `values`.
     * Reference argument example: {"name":"tags","values":["go","memory"]}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object addText() {
      return addText(Map.of());
    }

    /**
     * send native OP ADD_TAG to the existing `full_text_document` key.
     * The server applies `full_text_document` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `name`, `values`.
     * Reference argument example: {"name":"tags","values":["go","memory"]}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add_text(Map<String, Object> args) {
      return addText(args);
    }

    /** Native OP ADD_TAG for the full_text_document wire type. */
    /**
     * send native OP ADD_TAG to the existing `full_text_document` key.
     * The server applies `full_text_document` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `name`, `values`.
     * Reference argument example: {"name":"tags","values":["go","memory"]}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object addTag(Map<String, Object> args) {
      return op("ADD_TAG", args);
    }

    /**
     * send native OP ADD_NUMERIC to the existing `full_text_document` key.
     * The server applies `full_text_document` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `name`, `value`.
     * Reference argument example: {"name":"score","value":9.5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object addTag() {
      return addTag(Map.of());
    }

    /**
     * send native OP ADD_NUMERIC to the existing `full_text_document` key.
     * The server applies `full_text_document` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `name`, `value`.
     * Reference argument example: {"name":"score","value":9.5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add_tag(Map<String, Object> args) {
      return addTag(args);
    }

    /** Native OP ADD_NUMERIC for the full_text_document wire type. */
    /**
     * send native OP ADD_NUMERIC to the existing `full_text_document` key.
     * The server applies `full_text_document` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `name`, `value`.
     * Reference argument example: {"name":"score","value":9.5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object addNumeric(Map<String, Object> args) {
      return op("ADD_NUMERIC", args);
    }

    /**
     * send native OP SNAPSHOT to the existing `full_text_document` key.
     * The server applies `full_text_document` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object addNumeric() {
      return addNumeric(Map.of());
    }

    /**
     * send native OP SNAPSHOT to the existing `full_text_document` key.
     * The server applies `full_text_document` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add_numeric(Map<String, Object> args) {
      return addNumeric(args);
    }

    /** Native OP SNAPSHOT for the full_text_document wire type. */
    /**
     * send native OP SNAPSHOT to the existing `full_text_document` key.
     * The server applies `full_text_document` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object snapshot(Map<String, Object> args) {
      return op("SNAPSHOT", args);
    }

    /**
     * send native OP GET to the existing `full_text_document` key.
     * The server applies `full_text_document` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object snapshot() {
      return snapshot(Map.of());
    }

    /** Native OP GET for the full_text_document wire type. */
    /**
     * send native OP GET to the existing `full_text_document` key.
     * The server applies `full_text_document` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get(Map<String, Object> args) {
      return op("GET", args);
    }

    /**
     * Read current data for a key or type-specific field, subject to server permissions and key existence.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get() {
      return get(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public GaugeSeries gaugeSeries(String key) {
    return new GaugeSeries(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class GaugeSeries extends TypeHandle {
    /**
     * send native OP SET to the existing `gauge_series` key.
     * The server applies `gauge_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `timestamp`, `value`.
     * Reference argument example: {"timestamp":1000,"value":42.5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public GaugeSeries(Client client, String key) {
      super(client, key, "gauge_series");
    }

    /** Native OP SET for the gauge_series wire type. */
    /**
     * send native OP SET to the existing `gauge_series` key.
     * The server applies `gauge_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `timestamp`, `value`.
     * Reference argument example: {"timestamp":1000,"value":42.5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set(Map<String, Object> args) {
      return op("SET", args);
    }

    /**
     * send native OP LAST to the existing `gauge_series` key.
     * The server applies `gauge_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set() {
      return set(Map.of());
    }

    /** Native OP LAST for the gauge_series wire type. */
    /**
     * send native OP LAST to the existing `gauge_series` key.
     * The server applies `gauge_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object last(Map<String, Object> args) {
      return op("LAST", args);
    }

    /**
     * send native OP RANGE to the existing `gauge_series` key.
     * The server applies `gauge_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`.
     * Reference argument example: {"from":0,"to":3000}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object last() {
      return last(Map.of());
    }

    /** Native OP RANGE for the gauge_series wire type. */
    /**
     * send native OP RANGE to the existing `gauge_series` key.
     * The server applies `gauge_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`.
     * Reference argument example: {"from":0,"to":3000}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object range(Map<String, Object> args) {
      return op("RANGE", args);
    }

    /**
     * send native OP AGGREGATE to the existing `gauge_series` key.
     * The server applies `gauge_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`, `bucket_ms`, `aggregation`.
     * Reference argument example: {"from":0,"to":3000,"bucket_ms":1000,"aggregation":"avg"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object range() {
      return range(Map.of());
    }

    /** Native OP AGGREGATE for the gauge_series wire type. */
    /**
     * send native OP AGGREGATE to the existing `gauge_series` key.
     * The server applies `gauge_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`, `bucket_ms`, `aggregation`.
     * Reference argument example: {"from":0,"to":3000,"bucket_ms":1000,"aggregation":"avg"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object aggregate(Map<String, Object> args) {
      return op("AGGREGATE", args);
    }

    /**
     * send native OP LEN to the existing `gauge_series` key.
     * The server applies `gauge_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object aggregate() {
      return aggregate(Map.of());
    }

    /** Native OP LEN for the gauge_series wire type. */
    /**
     * send native OP LEN to the existing `gauge_series` key.
     * The server applies `gauge_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP LEN to the existing `gauge_series` key.
     * The server applies `gauge_series` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public GeoPoint geoPoint(String key) {
    return new GeoPoint(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class GeoPoint extends TypeHandle {
    /**
     * send native OP DISTANCE to the existing `geo_point` key.
     * The server applies `geo_point` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `point`.
     * Reference argument example: {"point":{"Lat":14.5995,"Lon":120.9842}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public GeoPoint(Client client, String key) {
      super(client, key, "geo_point");
    }

    /** Native OP DISTANCE for the geo_point wire type. */
    /**
     * send native OP DISTANCE to the existing `geo_point` key.
     * The server applies `geo_point` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `point`.
     * Reference argument example: {"point":{"Lat":14.5995,"Lon":120.9842}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object distance(Map<String, Object> args) {
      return op("DISTANCE", args);
    }

    /**
     * send native OP DISTANCE to the existing `geo_point` key.
     * The server applies `geo_point` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `point`.
     * Reference argument example: {"point":{"Lat":14.5995,"Lon":120.9842}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object distance() {
      return distance(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public GeoSpatialIndex geoSpatialIndex(String key) {
    return new GeoSpatialIndex(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class GeoSpatialIndex extends TypeHandle {
    /**
     * send native OP ADD to the existing `geo_spatial_index` key.
     * The server applies `geo_spatial_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`, `point`, `metadata`.
     * Reference argument example: {"id":"manila","point":{"Lat":14.5995,"Lon":120.9842},"metadata":{"country":"PH"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public GeoSpatialIndex(Client client, String key) {
      super(client, key, "geo_spatial_index");
    }

    /** Native OP ADD for the geo_spatial_index wire type. */
    /**
     * send native OP ADD to the existing `geo_spatial_index` key.
     * The server applies `geo_spatial_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`, `point`, `metadata`.
     * Reference argument example: {"id":"manila","point":{"Lat":14.5995,"Lon":120.9842},"metadata":{"country":"PH"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP RADIUS to the existing `geo_spatial_index` key.
     * The server applies `geo_spatial_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `center`, `radius_meters`, `limit`.
     * Reference argument example: {"center":{"Lat":14.5995,"Lon":120.9842},"radius_meters":1000,"limit":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP RADIUS for the geo_spatial_index wire type. */
    /**
     * send native OP RADIUS to the existing `geo_spatial_index` key.
     * The server applies `geo_spatial_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `center`, `radius_meters`, `limit`.
     * Reference argument example: {"center":{"Lat":14.5995,"Lon":120.9842},"radius_meters":1000,"limit":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object radius(Map<String, Object> args) {
      return op("RADIUS", args);
    }

    /**
     * send native OP REMOVE to the existing `geo_spatial_index` key.
     * The server applies `geo_spatial_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`.
     * Reference argument example: {"id":"manila"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object radius() {
      return radius(Map.of());
    }

    /** Native OP REMOVE for the geo_spatial_index wire type. */
    /**
     * send native OP REMOVE to the existing `geo_spatial_index` key.
     * The server applies `geo_spatial_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`.
     * Reference argument example: {"id":"manila"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove(Map<String, Object> args) {
      return op("REMOVE", args);
    }

    /**
     * send native OP BOX to the existing `geo_spatial_index` key.
     * The server applies `geo_spatial_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `box`.
     * Reference argument example: {"box":{"MinLat":14.0,"MinLon":120.0,"MaxLat":15.0,"MaxLon":122.0}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove() {
      return remove(Map.of());
    }

    /** Native OP BOX for the geo_spatial_index wire type. */
    /**
     * send native OP BOX to the existing `geo_spatial_index` key.
     * The server applies `geo_spatial_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `box`.
     * Reference argument example: {"box":{"MinLat":14.0,"MinLon":120.0,"MaxLat":15.0,"MaxLon":122.0}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object box(Map<String, Object> args) {
      return op("BOX", args);
    }

    /**
     * send native OP POLYGON to the existing `geo_spatial_index` key.
     * The server applies `geo_spatial_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `points`.
     * Reference argument example: {"points":[{"Lat":14.5,"Lon":120.9},{"Lat":14.7,"Lon":120.9},{"Lat":14.6,"Lon":121.1}]}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object box() {
      return box(Map.of());
    }

    /** Native OP POLYGON for the geo_spatial_index wire type. */
    /**
     * send native OP POLYGON to the existing `geo_spatial_index` key.
     * The server applies `geo_spatial_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `points`.
     * Reference argument example: {"points":[{"Lat":14.5,"Lon":120.9},{"Lat":14.7,"Lon":120.9},{"Lat":14.6,"Lon":121.1}]}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object polygon(Map<String, Object> args) {
      return op("POLYGON", args);
    }

    /**
     * send native OP POLYGON to the existing `geo_spatial_index` key.
     * The server applies `geo_spatial_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `points`.
     * Reference argument example: {"points":[{"Lat":14.5,"Lon":120.9},{"Lat":14.7,"Lon":120.9},{"Lat":14.6,"Lon":121.1}]}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object polygon() {
      return polygon(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public GeospatialSecondaryIndex geospatialSecondaryIndex(String key) {
    return new GeospatialSecondaryIndex(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class GeospatialSecondaryIndex extends TypeHandle {
    /**
     * send native OP ADD to the existing `geospatial_secondary_index` key.
     * The server applies `geospatial_secondary_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`, `point`, `metadata`.
     * Reference argument example: {"id":"manila","point":{"Lat":14.5995,"Lon":120.9842},"metadata":{"country":"PH"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public GeospatialSecondaryIndex(Client client, String key) {
      super(client, key, "geospatial_secondary_index");
    }

    /** Native OP ADD for the geospatial_secondary_index wire type. */
    /**
     * send native OP ADD to the existing `geospatial_secondary_index` key.
     * The server applies `geospatial_secondary_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`, `point`, `metadata`.
     * Reference argument example: {"id":"manila","point":{"Lat":14.5995,"Lon":120.9842},"metadata":{"country":"PH"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP RADIUS to the existing `geospatial_secondary_index` key.
     * The server applies `geospatial_secondary_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `center`, `radius_meters`, `limit`.
     * Reference argument example: {"center":{"Lat":14.5995,"Lon":120.9842},"radius_meters":100,"limit":5}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP RADIUS for the geospatial_secondary_index wire type. */
    /**
     * send native OP RADIUS to the existing `geospatial_secondary_index` key.
     * The server applies `geospatial_secondary_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `center`, `radius_meters`, `limit`.
     * Reference argument example: {"center":{"Lat":14.5995,"Lon":120.9842},"radius_meters":100,"limit":5}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object radius(Map<String, Object> args) {
      return op("RADIUS", args);
    }

    /**
     * send native OP REMOVE to the existing `geospatial_secondary_index` key.
     * The server applies `geospatial_secondary_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`.
     * Reference argument example: {"id":"manila"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object radius() {
      return radius(Map.of());
    }

    /** Native OP REMOVE for the geospatial_secondary_index wire type. */
    /**
     * send native OP REMOVE to the existing `geospatial_secondary_index` key.
     * The server applies `geospatial_secondary_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`.
     * Reference argument example: {"id":"manila"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove(Map<String, Object> args) {
      return op("REMOVE", args);
    }

    /**
     * send native OP BOX to the existing `geospatial_secondary_index` key.
     * The server applies `geospatial_secondary_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `box`.
     * Reference argument example: {"box":{"MinLat":14.0,"MinLon":120.0,"MaxLat":15.0,"MaxLon":122.0}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove() {
      return remove(Map.of());
    }

    /** Native OP BOX for the geospatial_secondary_index wire type. */
    /**
     * send native OP BOX to the existing `geospatial_secondary_index` key.
     * The server applies `geospatial_secondary_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `box`.
     * Reference argument example: {"box":{"MinLat":14.0,"MinLon":120.0,"MaxLat":15.0,"MaxLon":122.0}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object box(Map<String, Object> args) {
      return op("BOX", args);
    }

    /**
     * send native OP POLYGON to the existing `geospatial_secondary_index` key.
     * The server applies `geospatial_secondary_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `points`.
     * Reference argument example: {"points":[{"Lat":14.5,"Lon":120.9},{"Lat":14.7,"Lon":120.9},{"Lat":14.6,"Lon":121.1}]}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object box() {
      return box(Map.of());
    }

    /** Native OP POLYGON for the geospatial_secondary_index wire type. */
    /**
     * send native OP POLYGON to the existing `geospatial_secondary_index` key.
     * The server applies `geospatial_secondary_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `points`.
     * Reference argument example: {"points":[{"Lat":14.5,"Lon":120.9},{"Lat":14.7,"Lon":120.9},{"Lat":14.6,"Lon":121.1}]}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object polygon(Map<String, Object> args) {
      return op("POLYGON", args);
    }

    /**
     * send native OP POLYGON to the existing `geospatial_secondary_index` key.
     * The server applies `geospatial_secondary_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `points`.
     * Reference argument example: {"points":[{"Lat":14.5,"Lon":120.9},{"Lat":14.7,"Lon":120.9},{"Lat":14.6,"Lon":121.1}]}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object polygon() {
      return polygon(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public HashIndex hashIndex(String key) {
    return new HashIndex(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class HashIndex extends TypeHandle {
    /**
     * send native OP ADD to the existing `hash_index` key.
     * The server applies `hash_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `id`.
     * Reference argument example: {"value":"blue","id":"1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public HashIndex(Client client, String key) {
      super(client, key, "hash_index");
    }

    /** Native OP ADD for the hash_index wire type. */
    /**
     * send native OP ADD to the existing `hash_index` key.
     * The server applies `hash_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `id`.
     * Reference argument example: {"value":"blue","id":"1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP LOOKUP to the existing `hash_index` key.
     * The server applies `hash_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"blue"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP LOOKUP for the hash_index wire type. */
    /**
     * send native OP LOOKUP to the existing `hash_index` key.
     * The server applies `hash_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"blue"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object lookup(Map<String, Object> args) {
      return op("LOOKUP", args);
    }

    /**
     * send native OP REMOVE to the existing `hash_index` key.
     * The server applies `hash_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `id`.
     * Reference argument example: {"value":"blue","id":"1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object lookup() {
      return lookup(Map.of());
    }

    /** Native OP REMOVE for the hash_index wire type. */
    /**
     * send native OP REMOVE to the existing `hash_index` key.
     * The server applies `hash_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `id`.
     * Reference argument example: {"value":"blue","id":"1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove(Map<String, Object> args) {
      return op("REMOVE", args);
    }

    /**
     * send native OP REMOVE to the existing `hash_index` key.
     * The server applies `hash_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `id`.
     * Reference argument example: {"value":"blue","id":"1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove() {
      return remove(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Hashmap hashmap(String key) {
    return new Hashmap(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Hashmap extends TypeHandle {
    /**
     * send native OP SET to the existing `hashmap` key.
     * The server applies `hashmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`, `value`.
     * Reference argument example: {"field":"name","value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Hashmap(Client client, String key) {
      super(client, key, "hashmap");
    }

    /** Native OP SET for the hashmap wire type. */
    /**
     * send native OP SET to the existing `hashmap` key.
     * The server applies `hashmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`, `value`.
     * Reference argument example: {"field":"name","value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set(Map<String, Object> args) {
      return op("SET", args);
    }

    /**
     * send native OP GET to the existing `hashmap` key.
     * The server applies `hashmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`.
     * Reference argument example: {"field":"name"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set() {
      return set(Map.of());
    }

    /** Native OP GET for the hashmap wire type. */
    /**
     * send native OP GET to the existing `hashmap` key.
     * The server applies `hashmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`.
     * Reference argument example: {"field":"name"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get(Map<String, Object> args) {
      return op("GET", args);
    }

    /**
     * send native OP DELETE to the existing `hashmap` key.
     * The server applies `hashmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`.
     * Reference argument example: {"field":"name"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get() {
      return get(Map.of());
    }

    /** Native OP DELETE for the hashmap wire type. */
    /**
     * send native OP DELETE to the existing `hashmap` key.
     * The server applies `hashmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`.
     * Reference argument example: {"field":"name"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object delete(Map<String, Object> args) {
      return op("DELETE", args);
    }

    /**
     * send native OP KEYS to the existing `hashmap` key.
     * The server applies `hashmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object delete() {
      return delete(Map.of());
    }

    /** Native OP KEYS for the hashmap wire type. */
    /**
     * send native OP KEYS to the existing `hashmap` key.
     * The server applies `hashmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object keys(Map<String, Object> args) {
      return op("KEYS", args);
    }

    /**
     * send native OP KEYS to the existing `hashmap` key.
     * The server applies `hashmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object keys() {
      return keys(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Histogram histogram(String key) {
    return new Histogram(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Histogram extends TypeHandle {
    /**
     * send native OP OBSERVE to the existing `histogram` key.
     * The server applies `histogram` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":3}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Histogram(Client client, String key) {
      super(client, key, "histogram");
    }

    /** Native OP OBSERVE for the histogram wire type. */
    /**
     * send native OP OBSERVE to the existing `histogram` key.
     * The server applies `histogram` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":3}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object observe(Map<String, Object> args) {
      return op("OBSERVE", args);
    }

    /**
     * send native OP SNAPSHOT to the existing `histogram` key.
     * The server applies `histogram` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object observe() {
      return observe(Map.of());
    }

    /** Native OP SNAPSHOT for the histogram wire type. */
    /**
     * send native OP SNAPSHOT to the existing `histogram` key.
     * The server applies `histogram` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object snapshot(Map<String, Object> args) {
      return op("SNAPSHOT", args);
    }

    /**
     * Read the server snapshot of the current key; returned JSON shape depends on the stored wire type.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object snapshot() {
      return snapshot(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public HnswIndex hnswIndex(String key) {
    return new HnswIndex(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class HnswIndex extends TypeHandle {
    /**
     * send native OP ADD to the existing `hnsw_index` key.
     * The server applies `hnsw_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`, `vector`, `metadata`.
     * Reference argument example: {"id":"v1","vector":[1,0,0],"metadata":{}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public HnswIndex(Client client, String key) {
      super(client, key, "hnsw_index");
    }

    /** Native OP ADD for the hnsw_index wire type. */
    /**
     * send native OP ADD to the existing `hnsw_index` key.
     * The server applies `hnsw_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`, `vector`, `metadata`.
     * Reference argument example: {"id":"v1","vector":[1,0,0],"metadata":{}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP SEARCH to the existing `hnsw_index` key.
     * The server applies `hnsw_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `vector`, `k`.
     * Reference argument example: {"vector":[1,0,0],"k":1}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP SEARCH for the hnsw_index wire type. */
    /**
     * send native OP SEARCH to the existing `hnsw_index` key.
     * The server applies `hnsw_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `vector`, `k`.
     * Reference argument example: {"vector":[1,0,0],"k":1}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object search(Map<String, Object> args) {
      return op("SEARCH", args);
    }

    /**
     * send native OP REMOVE to the existing `hnsw_index` key.
     * The server applies `hnsw_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`.
     * Reference argument example: {"id":"v1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object search() {
      return search(Map.of());
    }

    /** Native OP REMOVE for the hnsw_index wire type. */
    /**
     * send native OP REMOVE to the existing `hnsw_index` key.
     * The server applies `hnsw_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`.
     * Reference argument example: {"id":"v1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove(Map<String, Object> args) {
      return op("REMOVE", args);
    }

    /**
     * send native OP REMOVE to the existing `hnsw_index` key.
     * The server applies `hnsw_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`.
     * Reference argument example: {"id":"v1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove() {
      return remove(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Hyperloglog hyperloglog(String key) {
    return new Hyperloglog(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Hyperloglog extends TypeHandle {
    /**
     * send native OP ADD to the existing `hyperloglog` key.
     * The server applies `hyperloglog` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Hyperloglog(Client client, String key) {
      super(client, key, "hyperloglog");
    }

    /** Native OP ADD for the hyperloglog wire type. */
    /**
     * send native OP ADD to the existing `hyperloglog` key.
     * The server applies `hyperloglog` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"alice"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP COUNT to the existing `hyperloglog` key.
     * The server applies `hyperloglog` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP COUNT for the hyperloglog wire type. */
    /**
     * send native OP COUNT to the existing `hyperloglog` key.
     * The server applies `hyperloglog` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object count(Map<String, Object> args) {
      return op("COUNT", args);
    }

    /**
     * send native OP COUNT to the existing `hyperloglog` key.
     * The server applies `hyperloglog` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object count() {
      return count(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Integer integer(String key) {
    return new Integer(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Integer extends TypeHandle {
    /**
     * send native OP ADD to the existing `integer` key.
     * The server applies `integer` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `delta`.
     * Reference argument example: {"delta":8}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Integer(Client client, String key) {
      super(client, key, "integer");
    }

    /** Native OP ADD for the integer wire type. */
    /**
     * send native OP ADD to the existing `integer` key.
     * The server applies `integer` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `delta`.
     * Reference argument example: {"delta":8}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP ADD to the existing `integer` key.
     * The server applies `integer` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `delta`.
     * Reference argument example: {"delta":8}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public InvertedIndex invertedIndex(String key) {
    return new InvertedIndex(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class InvertedIndex extends TypeHandle {
    /**
     * send native OP INDEX to the existing `inverted_index` key.
     * The server applies `inverted_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`, `text`.
     * Reference argument example: {"id":"d1","text":"amaquet in-memory database"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public InvertedIndex(Client client, String key) {
      super(client, key, "inverted_index");
    }

    /** Native OP INDEX for the inverted_index wire type. */
    /**
     * send native OP INDEX to the existing `inverted_index` key.
     * The server applies `inverted_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`, `text`.
     * Reference argument example: {"id":"d1","text":"amaquet in-memory database"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object index(Map<String, Object> args) {
      return op("INDEX", args);
    }

    /**
     * send native OP SEARCH to the existing `inverted_index` key.
     * The server applies `inverted_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `query`, `limit`.
     * Reference argument example: {"query":"memory","limit":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object index() {
      return index(Map.of());
    }

    /** Native OP SEARCH for the inverted_index wire type. */
    /**
     * send native OP SEARCH to the existing `inverted_index` key.
     * The server applies `inverted_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `query`, `limit`.
     * Reference argument example: {"query":"memory","limit":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object search(Map<String, Object> args) {
      return op("SEARCH", args);
    }

    /**
     * send native OP REMOVE to the existing `inverted_index` key.
     * The server applies `inverted_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`.
     * Reference argument example: {"id":"d1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object search() {
      return search(Map.of());
    }

    /** Native OP REMOVE for the inverted_index wire type. */
    /**
     * send native OP REMOVE to the existing `inverted_index` key.
     * The server applies `inverted_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`.
     * Reference argument example: {"id":"d1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove(Map<String, Object> args) {
      return op("REMOVE", args);
    }

    /**
     * send native OP REMOVE to the existing `inverted_index` key.
     * The server applies `inverted_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`.
     * Reference argument example: {"id":"d1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove() {
      return remove(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Json json(String key) {
    return new Json(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Json extends TypeHandle {
    /**
     * send native OP SET to the existing `json` key.
     * The server applies `json` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"hello":"world","n":1}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Json(Client client, String key) {
      super(client, key, "json");
    }

    /** Native OP SET for the json wire type. */
    /**
     * send native OP SET to the existing `json` key.
     * The server applies `json` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"hello":"world","n":1}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set(Map<String, Object> args) {
      return op("SET", args);
    }

    /**
     * send native OP GET to the existing `json` key.
     * The server applies `json` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set() {
      return set(Map.of());
    }

    /** Native OP GET for the json wire type. */
    /**
     * send native OP GET to the existing `json` key.
     * The server applies `json` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get(Map<String, Object> args) {
      return op("GET", args);
    }

    /**
     * Read current data for a key or type-specific field, subject to server permissions and key existence.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get() {
      return get(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Lease lease(String key) {
    return new Lease(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Lease extends TypeHandle {
    /**
     * send native OP ACQUIRE to the existing `lease` key.
     * The server applies `lease` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `owner`, `token`, `ttl_ms`.
     * Reference argument example: {"owner":"worker-a","token":"t1","ttl_ms":5000}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Lease(Client client, String key) {
      super(client, key, "lease");
    }

    /** Native OP ACQUIRE for the lease wire type. */
    /**
     * send native OP ACQUIRE to the existing `lease` key.
     * The server applies `lease` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `owner`, `token`, `ttl_ms`.
     * Reference argument example: {"owner":"worker-a","token":"t1","ttl_ms":5000}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object acquire(Map<String, Object> args) {
      return op("ACQUIRE", args);
    }

    /**
     * send native OP HELD to the existing `lease` key.
     * The server applies `lease` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object acquire() {
      return acquire(Map.of());
    }

    /** Native OP HELD for the lease wire type. */
    /**
     * send native OP HELD to the existing `lease` key.
     * The server applies `lease` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object held(Map<String, Object> args) {
      return op("HELD", args);
    }

    /**
     * send native OP RELEASE to the existing `lease` key.
     * The server applies `lease` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `token`.
     * Reference argument example: {"token":"t1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object held() {
      return held(Map.of());
    }

    /** Native OP RELEASE for the lease wire type. */
    /**
     * send native OP RELEASE to the existing `lease` key.
     * The server applies `lease` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `token`.
     * Reference argument example: {"token":"t1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object release(Map<String, Object> args) {
      return op("RELEASE", args);
    }

    /**
     * send native OP RENEW to the existing `lease` key.
     * The server applies `lease` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `token`, `ttl_ms`.
     * Reference argument example: {"token":"t1","ttl_ms":5000}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object release() {
      return release(Map.of());
    }

    /** Native OP RENEW for the lease wire type. */
    /**
     * send native OP RENEW to the existing `lease` key.
     * The server applies `lease` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `token`, `ttl_ms`.
     * Reference argument example: {"token":"t1","ttl_ms":5000}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object renew(Map<String, Object> args) {
      return op("RENEW", args);
    }

    /**
     * send native OP RENEW to the existing `lease` key.
     * The server applies `lease` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `token`, `ttl_ms`.
     * Reference argument example: {"token":"t1","ttl_ms":5000}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object renew() {
      return renew(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public LifoStack lifoStack(String key) {
    return new LifoStack(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class LifoStack extends TypeHandle {
    /**
     * send native OP PUSH to the existing `lifo_stack` key.
     * The server applies `lifo_stack` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"first"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public LifoStack(Client client, String key) {
      super(client, key, "lifo_stack");
    }

    /** Native OP PUSH for the lifo_stack wire type. */
    /**
     * send native OP PUSH to the existing `lifo_stack` key.
     * The server applies `lifo_stack` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"first"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object push(Map<String, Object> args) {
      return op("PUSH", args);
    }

    /**
     * send native OP PEEK to the existing `lifo_stack` key.
     * The server applies `lifo_stack` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object push() {
      return push(Map.of());
    }

    /** Native OP PEEK for the lifo_stack wire type. */
    /**
     * send native OP PEEK to the existing `lifo_stack` key.
     * The server applies `lifo_stack` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object peek(Map<String, Object> args) {
      return op("PEEK", args);
    }

    /**
     * send native OP POP to the existing `lifo_stack` key.
     * The server applies `lifo_stack` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object peek() {
      return peek(Map.of());
    }

    /** Native OP POP for the lifo_stack wire type. */
    /**
     * send native OP POP to the existing `lifo_stack` key.
     * The server applies `lifo_stack` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object pop(Map<String, Object> args) {
      return op("POP", args);
    }

    /**
     * send native OP LEN to the existing `lifo_stack` key.
     * The server applies `lifo_stack` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object pop() {
      return pop(Map.of());
    }

    /** Native OP LEN for the lifo_stack wire type. */
    /**
     * send native OP LEN to the existing `lifo_stack` key.
     * The server applies `lifo_stack` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP LEN to the existing `lifo_stack` key.
     * The server applies `lifo_stack` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public List list(String key) {
    return new List(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class List extends TypeHandle {
    /**
     * send native OP PUSH_BACK to the existing `list` key.
     * The server applies `list` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public List(Client client, String key) {
      super(client, key, "list");
    }

    /** Native OP PUSH_BACK for the list wire type. */
    /**
     * send native OP PUSH_BACK to the existing `list` key.
     * The server applies `list` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object pushBack(Map<String, Object> args) {
      return op("PUSH_BACK", args);
    }

    /**
     * send native OP PUSH_FRONT to the existing `list` key.
     * The server applies `list` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"integer","value":7}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object pushBack() {
      return pushBack(Map.of());
    }

    /**
     * send native OP PUSH_FRONT to the existing `list` key.
     * The server applies `list` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"integer","value":7}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object push_back(Map<String, Object> args) {
      return pushBack(args);
    }

    /** Native OP PUSH_FRONT for the list wire type. */
    /**
     * send native OP PUSH_FRONT to the existing `list` key.
     * The server applies `list` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"integer","value":7}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object pushFront(Map<String, Object> args) {
      return op("PUSH_FRONT", args);
    }

    /**
     * send native OP LEN to the existing `list` key.
     * The server applies `list` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object pushFront() {
      return pushFront(Map.of());
    }

    /**
     * send native OP LEN to the existing `list` key.
     * The server applies `list` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object push_front(Map<String, Object> args) {
      return pushFront(args);
    }

    /** Native OP LEN for the list wire type. */
    /**
     * send native OP LEN to the existing `list` key.
     * The server applies `list` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP RANGE to the existing `list` key.
     * The server applies `list` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `start`, `stop`.
     * Reference argument example: {"start":0,"stop":-1}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }

    /** Native OP RANGE for the list wire type. */
    /**
     * send native OP RANGE to the existing `list` key.
     * The server applies `list` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `start`, `stop`.
     * Reference argument example: {"start":0,"stop":-1}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object range(Map<String, Object> args) {
      return op("RANGE", args);
    }

    /**
     * send native OP POP_FRONT to the existing `list` key.
     * The server applies `list` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object range() {
      return range(Map.of());
    }

    /** Native OP POP_FRONT for the list wire type. */
    /**
     * send native OP POP_FRONT to the existing `list` key.
     * The server applies `list` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object popFront(Map<String, Object> args) {
      return op("POP_FRONT", args);
    }

    /**
     * send native OP POP_BACK to the existing `list` key.
     * The server applies `list` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object popFront() {
      return popFront(Map.of());
    }

    /**
     * send native OP POP_BACK to the existing `list` key.
     * The server applies `list` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object pop_front(Map<String, Object> args) {
      return popFront(args);
    }

    /** Native OP POP_BACK for the list wire type. */
    /**
     * send native OP POP_BACK to the existing `list` key.
     * The server applies `list` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object popBack(Map<String, Object> args) {
      return op("POP_BACK", args);
    }

    /**
     * Performs the corresponding documented Amaquet operation.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object popBack() {
      return popBack(Map.of());
    }

    /**
     * Performs the corresponding documented Amaquet operation.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object pop_back(Map<String, Object> args) {
      return popBack(args);
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Lock lock(String key) {
    return new Lock(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Lock extends TypeHandle {
    /**
     * send native OP TRY_LOCK to the existing `lock` key.
     * The server applies `lock` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `owner`, `token`, `ttl_ms`.
     * Reference argument example: {"owner":"worker-a","token":"t1","ttl_ms":5000}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Lock(Client client, String key) {
      super(client, key, "lock");
    }

    /** Native OP TRY_LOCK for the lock wire type. */
    /**
     * send native OP TRY_LOCK to the existing `lock` key.
     * The server applies `lock` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `owner`, `token`, `ttl_ms`.
     * Reference argument example: {"owner":"worker-a","token":"t1","ttl_ms":5000}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object tryLock(Map<String, Object> args) {
      return op("TRY_LOCK", args);
    }

    /**
     * send native OP UNLOCK to the existing `lock` key.
     * The server applies `lock` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `token`.
     * Reference argument example: {"token":"t1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object tryLock() {
      return tryLock(Map.of());
    }

    /**
     * send native OP UNLOCK to the existing `lock` key.
     * The server applies `lock` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `token`.
     * Reference argument example: {"token":"t1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object try_lock(Map<String, Object> args) {
      return tryLock(args);
    }

    /** Native OP UNLOCK for the lock wire type. */
    /**
     * send native OP UNLOCK to the existing `lock` key.
     * The server applies `lock` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `token`.
     * Reference argument example: {"token":"t1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object unlock(Map<String, Object> args) {
      return op("UNLOCK", args);
    }

    /**
     * send native OP RENEW to the existing `lock` key.
     * The server applies `lock` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `token`, `ttl_ms`.
     * Reference argument example: {"token":"t1","ttl_ms":5000}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object unlock() {
      return unlock(Map.of());
    }

    /** Native OP RENEW for the lock wire type. */
    /**
     * send native OP RENEW to the existing `lock` key.
     * The server applies `lock` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `token`, `ttl_ms`.
     * Reference argument example: {"token":"t1","ttl_ms":5000}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object renew(Map<String, Object> args) {
      return op("RENEW", args);
    }

    /**
     * send native OP RENEW to the existing `lock` key.
     * The server applies `lock` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `token`, `ttl_ms`.
     * Reference argument example: {"token":"t1","ttl_ms":5000}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object renew() {
      return renew(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Matrix matrix(String key) {
    return new Matrix(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Matrix extends TypeHandle {
    /**
     * send native OP GET to the existing `matrix` key.
     * The server applies `matrix` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `indices`.
     * Reference argument example: {"indices":[1,1]}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Matrix(Client client, String key) {
      super(client, key, "matrix");
    }

    /** Native OP GET for the matrix wire type. */
    /**
     * send native OP GET to the existing `matrix` key.
     * The server applies `matrix` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `indices`.
     * Reference argument example: {"indices":[1,1]}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get(Map<String, Object> args) {
      return op("GET", args);
    }

    /**
     * send native OP SET to the existing `matrix` key.
     * The server applies `matrix` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `indices`, `value`.
     * Reference argument example: {"indices":[0,1],"value":9}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get() {
      return get(Map.of());
    }

    /** Native OP SET for the matrix wire type. */
    /**
     * send native OP SET to the existing `matrix` key.
     * The server applies `matrix` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `indices`, `value`.
     * Reference argument example: {"indices":[0,1],"value":9}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set(Map<String, Object> args) {
      return op("SET", args);
    }

    /**
     * send native OP LEN to the existing `matrix` key.
     * The server applies `matrix` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set() {
      return set(Map.of());
    }

    /** Native OP LEN for the matrix wire type. */
    /**
     * send native OP LEN to the existing `matrix` key.
     * The server applies `matrix` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP LEN to the existing `matrix` key.
     * The server applies `matrix` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Messagepack messagepack(String key) {
    return new Messagepack(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Messagepack extends TypeHandle {
    /**
     * send native OP GET to the existing `messagepack` key.
     * The server applies `messagepack` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Messagepack(Client client, String key) {
      super(client, key, "messagepack");
    }

    /** Native OP GET for the messagepack wire type. */
    /**
     * send native OP GET to the existing `messagepack` key.
     * The server applies `messagepack` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get(Map<String, Object> args) {
      return op("GET", args);
    }

    /**
     * Read current data for a key or type-specific field, subject to server permissions and key existence.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get() {
      return get(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Multimap multimap(String key) {
    return new Multimap(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Multimap extends TypeHandle {
    /**
     * send native OP ADD to the existing `multimap` key.
     * The server applies `multimap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`, `value`.
     * Reference argument example: {"field":"tag","value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Multimap(Client client, String key) {
      super(client, key, "multimap");
    }

    /** Native OP ADD for the multimap wire type. */
    /**
     * send native OP ADD to the existing `multimap` key.
     * The server applies `multimap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`, `value`.
     * Reference argument example: {"field":"tag","value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP GET to the existing `multimap` key.
     * The server applies `multimap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`.
     * Reference argument example: {"field":"tag"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP GET for the multimap wire type. */
    /**
     * send native OP GET to the existing `multimap` key.
     * The server applies `multimap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`.
     * Reference argument example: {"field":"tag"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get(Map<String, Object> args) {
      return op("GET", args);
    }

    /**
     * send native OP REMOVE to the existing `multimap` key.
     * The server applies `multimap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`, `value`.
     * Reference argument example: {"field":"tag","value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get() {
      return get(Map.of());
    }

    /** Native OP REMOVE for the multimap wire type. */
    /**
     * send native OP REMOVE to the existing `multimap` key.
     * The server applies `multimap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`, `value`.
     * Reference argument example: {"field":"tag","value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove(Map<String, Object> args) {
      return op("REMOVE", args);
    }

    /**
     * send native OP REMOVE to the existing `multimap` key.
     * The server applies `multimap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`, `value`.
     * Reference argument example: {"field":"tag","value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove() {
      return remove(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Multiset multiset(String key) {
    return new Multiset(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Multiset extends TypeHandle {
    /**
     * send native OP ADD to the existing `multiset` key.
     * The server applies `multiset` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `count`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"},"count":3}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Multiset(Client client, String key) {
      super(client, key, "multiset");
    }

    /** Native OP ADD for the multiset wire type. */
    /**
     * send native OP ADD to the existing `multiset` key.
     * The server applies `multiset` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `count`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"},"count":3}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP COUNT to the existing `multiset` key.
     * The server applies `multiset` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP COUNT for the multiset wire type. */
    /**
     * send native OP COUNT to the existing `multiset` key.
     * The server applies `multiset` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object count(Map<String, Object> args) {
      return op("COUNT", args);
    }

    /**
     * send native OP REMOVE to the existing `multiset` key.
     * The server applies `multiset` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `count`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"},"count":1}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object count() {
      return count(Map.of());
    }

    /** Native OP REMOVE for the multiset wire type. */
    /**
     * send native OP REMOVE to the existing `multiset` key.
     * The server applies `multiset` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `count`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"},"count":1}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove(Map<String, Object> args) {
      return op("REMOVE", args);
    }

    /**
     * send native OP REMOVE to the existing `multiset` key.
     * The server applies `multiset` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `count`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"},"count":1}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove() {
      return remove(Map.of());
    }
  }

  /**
   * typed facade for the registered `null` value stored under an Amaquet key.
   * Construction command: `SET`. Documented construction option keys: (none).
   * Supported named operations: (none; use key commands).
   * The facade forwards requests to the server and does not validate domain-specific arguments before transmission.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Null nullType(String key) {
    return new Null(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Null extends TypeHandle {
    /**
     * Performs the corresponding documented Amaquet operation.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     */
    public Null(Client client, String key) {
      super(client, key, "null");
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public NumericField numericField(String key) {
    return new NumericField(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class NumericField extends TypeHandle {
    /**
     * send native OP SET to the existing `numeric_field` key.
     * The server applies `numeric_field` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `name`, `value`.
     * Reference argument example: {"name":"score","value":99.5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public NumericField(Client client, String key) {
      super(client, key, "numeric_field");
    }

    /** Native OP SET for the numeric_field wire type. */
    /**
     * send native OP SET to the existing `numeric_field` key.
     * The server applies `numeric_field` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `name`, `value`.
     * Reference argument example: {"name":"score","value":99.5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set(Map<String, Object> args) {
      return op("SET", args);
    }

    /**
     * send native OP GET to the existing `numeric_field` key.
     * The server applies `numeric_field` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set() {
      return set(Map.of());
    }

    /** Native OP GET for the numeric_field wire type. */
    /**
     * send native OP GET to the existing `numeric_field` key.
     * The server applies `numeric_field` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get(Map<String, Object> args) {
      return op("GET", args);
    }

    /**
     * Read current data for a key or type-specific field, subject to server permissions and key existence.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get() {
      return get(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public OrderedMap orderedMap(String key) {
    return new OrderedMap(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class OrderedMap extends TypeHandle {
    /**
     * send native OP SET to the existing `ordered_map` key.
     * The server applies `ordered_map` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`, `value`.
     * Reference argument example: {"field":"name","value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public OrderedMap(Client client, String key) {
      super(client, key, "ordered_map");
    }

    /** Native OP SET for the ordered_map wire type. */
    /**
     * send native OP SET to the existing `ordered_map` key.
     * The server applies `ordered_map` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`, `value`.
     * Reference argument example: {"field":"name","value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set(Map<String, Object> args) {
      return op("SET", args);
    }

    /**
     * send native OP GET to the existing `ordered_map` key.
     * The server applies `ordered_map` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`.
     * Reference argument example: {"field":"name"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set() {
      return set(Map.of());
    }

    /** Native OP GET for the ordered_map wire type. */
    /**
     * send native OP GET to the existing `ordered_map` key.
     * The server applies `ordered_map` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`.
     * Reference argument example: {"field":"name"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get(Map<String, Object> args) {
      return op("GET", args);
    }

    /**
     * send native OP DELETE to the existing `ordered_map` key.
     * The server applies `ordered_map` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`.
     * Reference argument example: {"field":"name"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get() {
      return get(Map.of());
    }

    /** Native OP DELETE for the ordered_map wire type. */
    /**
     * send native OP DELETE to the existing `ordered_map` key.
     * The server applies `ordered_map` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`.
     * Reference argument example: {"field":"name"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object delete(Map<String, Object> args) {
      return op("DELETE", args);
    }

    /**
     * send native OP ENTRIES to the existing `ordered_map` key.
     * The server applies `ordered_map` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object delete() {
      return delete(Map.of());
    }

    /** Native OP ENTRIES for the ordered_map wire type. */
    /**
     * send native OP ENTRIES to the existing `ordered_map` key.
     * The server applies `ordered_map` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object entries(Map<String, Object> args) {
      return op("ENTRIES", args);
    }

    /**
     * send native OP ENTRIES to the existing `ordered_map` key.
     * The server applies `ordered_map` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object entries() {
      return entries(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public OrderedSet orderedSet(String key) {
    return new OrderedSet(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class OrderedSet extends TypeHandle {
    /**
     * send native OP ADD to the existing `ordered_set` key.
     * The server applies `ordered_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public OrderedSet(Client client, String key) {
      super(client, key, "ordered_set");
    }

    /** Native OP ADD for the ordered_set wire type. */
    /**
     * send native OP ADD to the existing `ordered_set` key.
     * The server applies `ordered_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP VALUES to the existing `ordered_set` key.
     * The server applies `ordered_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP VALUES for the ordered_set wire type. */
    /**
     * send native OP VALUES to the existing `ordered_set` key.
     * The server applies `ordered_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object values(Map<String, Object> args) {
      return op("VALUES", args);
    }

    /**
     * send native OP REMOVE to the existing `ordered_set` key.
     * The server applies `ordered_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object values() {
      return values(Map.of());
    }

    /** Native OP REMOVE for the ordered_set wire type. */
    /**
     * send native OP REMOVE to the existing `ordered_set` key.
     * The server applies `ordered_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove(Map<String, Object> args) {
      return op("REMOVE", args);
    }

    /**
     * send native OP REMOVE to the existing `ordered_set` key.
     * The server applies `ordered_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove() {
      return remove(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public PersistentTopic persistentTopic(String key) {
    return new PersistentTopic(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class PersistentTopic extends TypeHandle {
    /**
     * send native OP PUBLISH to the existing `persistent_topic` key.
     * The server applies `persistent_topic` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `message_key`, `value`.
     * Reference argument example: {"message_key":"m1","value":{"type":"utf8_string","value":"hello"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public PersistentTopic(Client client, String key) {
      super(client, key, "persistent_topic");
    }

    /** Native OP PUBLISH for the persistent_topic wire type. */
    /**
     * send native OP PUBLISH to the existing `persistent_topic` key.
     * The server applies `persistent_topic` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `message_key`, `value`.
     * Reference argument example: {"message_key":"m1","value":{"type":"utf8_string","value":"hello"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object publish(Map<String, Object> args) {
      return op("PUBLISH", args);
    }

    /**
     * send native OP READ to the existing `persistent_topic` key.
     * The server applies `persistent_topic` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `count`.
     * Reference argument example: {"from":0,"count":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object publish() {
      return publish(Map.of());
    }

    /** Native OP READ for the persistent_topic wire type. */
    /**
     * send native OP READ to the existing `persistent_topic` key.
     * The server applies `persistent_topic` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `count`.
     * Reference argument example: {"from":0,"count":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object read(Map<String, Object> args) {
      return op("READ", args);
    }

    /**
     * send native OP READ to the existing `persistent_topic` key.
     * The server applies `persistent_topic` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `count`.
     * Reference argument example: {"from":0,"count":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object read() {
      return read(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Polygon polygon(String key) {
    return new Polygon(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Polygon extends TypeHandle {
    /**
     * send native OP CONTAINS to the existing `polygon` key.
     * The server applies `polygon` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `point`.
     * Reference argument example: {"point":{"Lat":14.6,"Lon":121.0}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Polygon(Client client, String key) {
      super(client, key, "polygon");
    }

    /** Native OP CONTAINS for the polygon wire type. */
    /**
     * send native OP CONTAINS to the existing `polygon` key.
     * The server applies `polygon` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `point`.
     * Reference argument example: {"point":{"Lat":14.6,"Lon":121.0}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object contains(Map<String, Object> args) {
      return op("CONTAINS", args);
    }

    /**
     * send native OP CONTAINS to the existing `polygon` key.
     * The server applies `polygon` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `point`.
     * Reference argument example: {"point":{"Lat":14.6,"Lon":121.0}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object contains() {
      return contains(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public PriorityQueue priorityQueue(String key) {
    return new PriorityQueue(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class PriorityQueue extends TypeHandle {
    /**
     * send native OP ENQUEUE to the existing `priority_queue` key.
     * The server applies `priority_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `priority`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"low"},"priority":1}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public PriorityQueue(Client client, String key) {
      super(client, key, "priority_queue");
    }

    /** Native OP ENQUEUE for the priority_queue wire type. */
    /**
     * send native OP ENQUEUE to the existing `priority_queue` key.
     * The server applies `priority_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `priority`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"low"},"priority":1}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object enqueue(Map<String, Object> args) {
      return op("ENQUEUE", args);
    }

    /**
     * send native OP LEN to the existing `priority_queue` key.
     * The server applies `priority_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object enqueue() {
      return enqueue(Map.of());
    }

    /** Native OP LEN for the priority_queue wire type. */
    /**
     * send native OP LEN to the existing `priority_queue` key.
     * The server applies `priority_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP DEQUEUE to the existing `priority_queue` key.
     * The server applies `priority_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }

    /** Native OP DEQUEUE for the priority_queue wire type. */
    /**
     * send native OP DEQUEUE to the existing `priority_queue` key.
     * The server applies `priority_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object dequeue(Map<String, Object> args) {
      return op("DEQUEUE", args);
    }

    /**
     * send native OP DEQUEUE to the existing `priority_queue` key.
     * The server applies `priority_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object dequeue() {
      return dequeue(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Pubsub pubsub(String key) {
    return new Pubsub(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Pubsub extends TypeHandle {
    /**
     * send native OP PUBLISH to the existing `pubsub` key.
     * The server applies `pubsub` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `channel`, `value`.
     * Reference argument example: {"channel":"events","value":{"type":"utf8_string","value":"ping"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Pubsub(Client client, String key) {
      super(client, key, "pubsub");
    }

    /** Native OP PUBLISH for the pubsub wire type. */
    /**
     * send native OP PUBLISH to the existing `pubsub` key.
     * The server applies `pubsub` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `channel`, `value`.
     * Reference argument example: {"channel":"events","value":{"type":"utf8_string","value":"ping"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object publish(Map<String, Object> args) {
      return op("PUBLISH", args);
    }

    /**
     * send native OP STATS to the existing `pubsub` key.
     * The server applies `pubsub` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object publish() {
      return publish(Map.of());
    }

    /** Native OP STATS for the pubsub wire type. */
    /**
     * send native OP STATS to the existing `pubsub` key.
     * The server applies `pubsub` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object stats(Map<String, Object> args) {
      return op("STATS", args);
    }

    /**
     * send native OP STATS to the existing `pubsub` key.
     * The server applies `pubsub` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object stats() {
      return stats(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public QuantizedVector quantizedVector(String key) {
    return new QuantizedVector(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class QuantizedVector extends TypeHandle {
    /**
     * send native OP DEQUANTIZE to the existing `quantized_vector` key.
     * The server applies `quantized_vector` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public QuantizedVector(Client client, String key) {
      super(client, key, "quantized_vector");
    }

    /** Native OP DEQUANTIZE for the quantized_vector wire type. */
    /**
     * send native OP DEQUANTIZE to the existing `quantized_vector` key.
     * The server applies `quantized_vector` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object dequantize(Map<String, Object> args) {
      return op("DEQUANTIZE", args);
    }

    /**
     * send native OP DEQUANTIZE to the existing `quantized_vector` key.
     * The server applies `quantized_vector` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object dequantize() {
      return dequantize(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public RadixTree radixTree(String key) {
    return new RadixTree(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class RadixTree extends TypeHandle {
    /**
     * send native OP INSERT to the existing `radix_tree` key.
     * The server applies `radix_tree` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `id`.
     * Reference argument example: {"value":"amaquet","id":"1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public RadixTree(Client client, String key) {
      super(client, key, "radix_tree");
    }

    /** Native OP INSERT for the radix_tree wire type. */
    /**
     * send native OP INSERT to the existing `radix_tree` key.
     * The server applies `radix_tree` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `id`.
     * Reference argument example: {"value":"amaquet","id":"1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object insert(Map<String, Object> args) {
      return op("INSERT", args);
    }

    /**
     * send native OP PREFIX to the existing `radix_tree` key.
     * The server applies `radix_tree` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `prefix`, `limit`.
     * Reference argument example: {"prefix":"ama","limit":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object insert() {
      return insert(Map.of());
    }

    /** Native OP PREFIX for the radix_tree wire type. */
    /**
     * send native OP PREFIX to the existing `radix_tree` key.
     * The server applies `radix_tree` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `prefix`, `limit`.
     * Reference argument example: {"prefix":"ama","limit":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object prefix(Map<String, Object> args) {
      return op("PREFIX", args);
    }

    /**
     * send native OP EXACT to the existing `radix_tree` key.
     * The server applies `radix_tree` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"amaquet"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object prefix() {
      return prefix(Map.of());
    }

    /** Native OP EXACT for the radix_tree wire type. */
    /**
     * send native OP EXACT to the existing `radix_tree` key.
     * The server applies `radix_tree` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"amaquet"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object exact(Map<String, Object> args) {
      return op("EXACT", args);
    }

    /**
     * send native OP EXACT to the existing `radix_tree` key.
     * The server applies `radix_tree` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"amaquet"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object exact() {
      return exact(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public RateLimitBucket rateLimitBucket(String key) {
    return new RateLimitBucket(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class RateLimitBucket extends TypeHandle {
    /**
     * send native OP ALLOW to the existing `rate_limit_bucket` key.
     * The server applies `rate_limit_bucket` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `cost`.
     * Reference argument example: {"cost":1}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public RateLimitBucket(Client client, String key) {
      super(client, key, "rate_limit_bucket");
    }

    /** Native OP ALLOW for the rate_limit_bucket wire type. */
    /**
     * send native OP ALLOW to the existing `rate_limit_bucket` key.
     * The server applies `rate_limit_bucket` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `cost`.
     * Reference argument example: {"cost":1}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object allow(Map<String, Object> args) {
      return op("ALLOW", args);
    }

    /**
     * send native OP REMAINING to the existing `rate_limit_bucket` key.
     * The server applies `rate_limit_bucket` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object allow() {
      return allow(Map.of());
    }

    /** Native OP REMAINING for the rate_limit_bucket wire type. */
    /**
     * send native OP REMAINING to the existing `rate_limit_bucket` key.
     * The server applies `rate_limit_bucket` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remaining(Map<String, Object> args) {
      return op("REMAINING", args);
    }

    /**
     * send native OP REMAINING to the existing `rate_limit_bucket` key.
     * The server applies `rate_limit_bucket` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remaining() {
      return remaining(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Record record(String key) {
    return new Record(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Record extends TypeHandle {
    /**
     * send native OP SET to the existing `record` key.
     * The server applies `record` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`, `value`.
     * Reference argument example: {"field":"name","value":{"type":"utf8_string","value":"alice"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Record(Client client, String key) {
      super(client, key, "record");
    }

    /** Native OP SET for the record wire type. */
    /**
     * send native OP SET to the existing `record` key.
     * The server applies `record` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`, `value`.
     * Reference argument example: {"field":"name","value":{"type":"utf8_string","value":"alice"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set(Map<String, Object> args) {
      return op("SET", args);
    }

    /**
     * send native OP GET to the existing `record` key.
     * The server applies `record` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`.
     * Reference argument example: {"field":"name"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set() {
      return set(Map.of());
    }

    /** Native OP GET for the record wire type. */
    /**
     * send native OP GET to the existing `record` key.
     * The server applies `record` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`.
     * Reference argument example: {"field":"name"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get(Map<String, Object> args) {
      return op("GET", args);
    }

    /**
     * Read current data for a key or type-specific field, subject to server permissions and key existence.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get() {
      return get(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public ReliableQueue reliableQueue(String key) {
    return new ReliableQueue(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class ReliableQueue extends TypeHandle {
    /**
     * send native OP ENQUEUE to the existing `reliable_queue` key.
     * The server applies `reliable_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`, `value`.
     * Reference argument example: {"id":"job1","value":{"type":"utf8_string","value":"work"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public ReliableQueue(Client client, String key) {
      super(client, key, "reliable_queue");
    }

    /** Native OP ENQUEUE for the reliable_queue wire type. */
    /**
     * send native OP ENQUEUE to the existing `reliable_queue` key.
     * The server applies `reliable_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`, `value`.
     * Reference argument example: {"id":"job1","value":{"type":"utf8_string","value":"work"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object enqueue(Map<String, Object> args) {
      return op("ENQUEUE", args);
    }

    /**
     * send native OP CLAIM to the existing `reliable_queue` key.
     * The server applies `reliable_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `lease_ms`.
     * Reference argument example: {"lease_ms":5000}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object enqueue() {
      return enqueue(Map.of());
    }

    /** Native OP CLAIM for the reliable_queue wire type. */
    /**
     * send native OP CLAIM to the existing `reliable_queue` key.
     * The server applies `reliable_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `lease_ms`.
     * Reference argument example: {"lease_ms":5000}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object claim(Map<String, Object> args) {
      return op("CLAIM", args);
    }

    /**
     * send native OP ACK to the existing `reliable_queue` key.
     * The server applies `reliable_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`.
     * Reference argument example: {"id":"job1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object claim() {
      return claim(Map.of());
    }

    /** Native OP ACK for the reliable_queue wire type. */
    /**
     * send native OP ACK to the existing `reliable_queue` key.
     * The server applies `reliable_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`.
     * Reference argument example: {"id":"job1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object ack(Map<String, Object> args) {
      return op("ACK", args);
    }

    /**
     * send native OP NACK to the existing `reliable_queue` key.
     * The server applies `reliable_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`.
     * Reference argument example: {"id":"job1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object ack() {
      return ack(Map.of());
    }

    /** Native OP NACK for the reliable_queue wire type. */
    /**
     * send native OP NACK to the existing `reliable_queue` key.
     * The server applies `reliable_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`.
     * Reference argument example: {"id":"job1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object nack(Map<String, Object> args) {
      return op("NACK", args);
    }

    /**
     * send native OP STATS to the existing `reliable_queue` key.
     * The server applies `reliable_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object nack() {
      return nack(Map.of());
    }

    /** Native OP STATS for the reliable_queue wire type. */
    /**
     * send native OP STATS to the existing `reliable_queue` key.
     * The server applies `reliable_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object stats(Map<String, Object> args) {
      return op("STATS", args);
    }

    /**
     * send native OP STATS to the existing `reliable_queue` key.
     * The server applies `reliable_queue` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object stats() {
      return stats(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public RingBuffer ringBuffer(String key) {
    return new RingBuffer(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class RingBuffer extends TypeHandle {
    /**
     * send native OP PUSH to the existing `ring_buffer` key.
     * The server applies `ring_buffer` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"a"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public RingBuffer(Client client, String key) {
      super(client, key, "ring_buffer");
    }

    /** Native OP PUSH for the ring_buffer wire type. */
    /**
     * send native OP PUSH to the existing `ring_buffer` key.
     * The server applies `ring_buffer` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"a"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object push(Map<String, Object> args) {
      return op("PUSH", args);
    }

    /**
     * send native OP LEN to the existing `ring_buffer` key.
     * The server applies `ring_buffer` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object push() {
      return push(Map.of());
    }

    /** Native OP LEN for the ring_buffer wire type. */
    /**
     * send native OP LEN to the existing `ring_buffer` key.
     * The server applies `ring_buffer` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP VALUES to the existing `ring_buffer` key.
     * The server applies `ring_buffer` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }

    /** Native OP VALUES for the ring_buffer wire type. */
    /**
     * send native OP VALUES to the existing `ring_buffer` key.
     * The server applies `ring_buffer` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object values(Map<String, Object> args) {
      return op("VALUES", args);
    }

    /**
     * send native OP VALUES to the existing `ring_buffer` key.
     * The server applies `ring_buffer` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object values() {
      return values(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public RoaringBitmap roaringBitmap(String key) {
    return new RoaringBitmap(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class RoaringBitmap extends TypeHandle {
    /**
     * send native OP ADD to the existing `roaring_bitmap` key.
     * The server applies `roaring_bitmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":42}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public RoaringBitmap(Client client, String key) {
      super(client, key, "roaring_bitmap");
    }

    /** Native OP ADD for the roaring_bitmap wire type. */
    /**
     * send native OP ADD to the existing `roaring_bitmap` key.
     * The server applies `roaring_bitmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":42}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP CONTAINS to the existing `roaring_bitmap` key.
     * The server applies `roaring_bitmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":42}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP CONTAINS for the roaring_bitmap wire type. */
    /**
     * send native OP CONTAINS to the existing `roaring_bitmap` key.
     * The server applies `roaring_bitmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":42}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object contains(Map<String, Object> args) {
      return op("CONTAINS", args);
    }

    /**
     * send native OP CARDINALITY to the existing `roaring_bitmap` key.
     * The server applies `roaring_bitmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object contains() {
      return contains(Map.of());
    }

    /** Native OP CARDINALITY for the roaring_bitmap wire type. */
    /**
     * send native OP CARDINALITY to the existing `roaring_bitmap` key.
     * The server applies `roaring_bitmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object cardinality(Map<String, Object> args) {
      return op("CARDINALITY", args);
    }

    /**
     * send native OP REMOVE to the existing `roaring_bitmap` key.
     * The server applies `roaring_bitmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":42}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object cardinality() {
      return cardinality(Map.of());
    }

    /** Native OP REMOVE for the roaring_bitmap wire type. */
    /**
     * send native OP REMOVE to the existing `roaring_bitmap` key.
     * The server applies `roaring_bitmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":42}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove(Map<String, Object> args) {
      return op("REMOVE", args);
    }

    /**
     * send native OP VALUES to the existing `roaring_bitmap` key.
     * The server applies `roaring_bitmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove() {
      return remove(Map.of());
    }

    /** Native OP VALUES for the roaring_bitmap wire type. */
    /**
     * send native OP VALUES to the existing `roaring_bitmap` key.
     * The server applies `roaring_bitmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object values(Map<String, Object> args) {
      return op("VALUES", args);
    }

    /**
     * send native OP VALUES to the existing `roaring_bitmap` key.
     * The server applies `roaring_bitmap` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object values() {
      return values(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public SecondaryIndex secondaryIndex(String key) {
    return new SecondaryIndex(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class SecondaryIndex extends TypeHandle {
    /**
     * send native OP ADD to the existing `secondary_index` key.
     * The server applies `secondary_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`, `value`, `id`.
     * Reference argument example: {"field":"status","value":"active","id":"u1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public SecondaryIndex(Client client, String key) {
      super(client, key, "secondary_index");
    }

    /** Native OP ADD for the secondary_index wire type. */
    /**
     * send native OP ADD to the existing `secondary_index` key.
     * The server applies `secondary_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`, `value`, `id`.
     * Reference argument example: {"field":"status","value":"active","id":"u1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP LOOKUP to the existing `secondary_index` key.
     * The server applies `secondary_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`, `value`.
     * Reference argument example: {"field":"status","value":"active"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP LOOKUP for the secondary_index wire type. */
    /**
     * send native OP LOOKUP to the existing `secondary_index` key.
     * The server applies `secondary_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`, `value`.
     * Reference argument example: {"field":"status","value":"active"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object lookup(Map<String, Object> args) {
      return op("LOOKUP", args);
    }

    /**
     * send native OP LOOKUP to the existing `secondary_index` key.
     * The server applies `secondary_index` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`, `value`.
     * Reference argument example: {"field":"status","value":"active"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object lookup() {
      return lookup(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Semaphore semaphore(String key) {
    return new Semaphore(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Semaphore extends TypeHandle {
    /**
     * send native OP TRY_ACQUIRE to the existing `semaphore` key.
     * The server applies `semaphore` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Semaphore(Client client, String key) {
      super(client, key, "semaphore");
    }

    /** Native OP TRY_ACQUIRE for the semaphore wire type. */
    /**
     * send native OP TRY_ACQUIRE to the existing `semaphore` key.
     * The server applies `semaphore` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object tryAcquire(Map<String, Object> args) {
      return op("TRY_ACQUIRE", args);
    }

    /**
     * send native OP STATS to the existing `semaphore` key.
     * The server applies `semaphore` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object tryAcquire() {
      return tryAcquire(Map.of());
    }

    /**
     * send native OP STATS to the existing `semaphore` key.
     * The server applies `semaphore` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object try_acquire(Map<String, Object> args) {
      return tryAcquire(args);
    }

    /** Native OP STATS for the semaphore wire type. */
    /**
     * send native OP STATS to the existing `semaphore` key.
     * The server applies `semaphore` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object stats(Map<String, Object> args) {
      return op("STATS", args);
    }

    /**
     * send native OP RELEASE to the existing `semaphore` key.
     * The server applies `semaphore` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object stats() {
      return stats(Map.of());
    }

    /** Native OP RELEASE for the semaphore wire type. */
    /**
     * send native OP RELEASE to the existing `semaphore` key.
     * The server applies `semaphore` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object release(Map<String, Object> args) {
      return op("RELEASE", args);
    }

    /**
     * send native OP ACQUIRE to the existing `semaphore` key.
     * The server applies `semaphore` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object release() {
      return release(Map.of());
    }

    /** Native OP ACQUIRE for the semaphore wire type. */
    /**
     * send native OP ACQUIRE to the existing `semaphore` key.
     * The server applies `semaphore` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object acquire(Map<String, Object> args) {
      return op("ACQUIRE", args);
    }

    /**
     * send native OP ACQUIRE to the existing `semaphore` key.
     * The server applies `semaphore` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object acquire() {
      return acquire(Map.of());
    }
  }

  /**
   * Write a value using the server documented type and options; this may replace existing state.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Set set(String key) {
    return new Set(client, key);
  }

  /**
   * Write a value using the server documented type and options; this may replace existing state.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Set extends TypeHandle {
    /**
     * send native OP ADD to the existing `set` key.
     * The server applies `set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Set(Client client, String key) {
      super(client, key, "set");
    }

    /** Native OP ADD for the set wire type. */
    /**
     * send native OP ADD to the existing `set` key.
     * The server applies `set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP CONTAINS to the existing `set` key.
     * The server applies `set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP CONTAINS for the set wire type. */
    /**
     * send native OP CONTAINS to the existing `set` key.
     * The server applies `set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object contains(Map<String, Object> args) {
      return op("CONTAINS", args);
    }

    /**
     * send native OP REMOVE to the existing `set` key.
     * The server applies `set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object contains() {
      return contains(Map.of());
    }

    /** Native OP REMOVE for the set wire type. */
    /**
     * send native OP REMOVE to the existing `set` key.
     * The server applies `set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove(Map<String, Object> args) {
      return op("REMOVE", args);
    }

    /**
     * send native OP MEMBERS to the existing `set` key.
     * The server applies `set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove() {
      return remove(Map.of());
    }

    /** Native OP MEMBERS for the set wire type. */
    /**
     * send native OP MEMBERS to the existing `set` key.
     * The server applies `set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object members(Map<String, Object> args) {
      return op("MEMBERS", args);
    }

    /**
     * send native OP LEN to the existing `set` key.
     * The server applies `set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object members() {
      return members(Map.of());
    }

    /** Native OP LEN for the set wire type. */
    /**
     * send native OP LEN to the existing `set` key.
     * The server applies `set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP LEN to the existing `set` key.
     * The server applies `set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public SortedSet sortedSet(String key) {
    return new SortedSet(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class SortedSet extends TypeHandle {
    /**
     * send native OP ADD to the existing `sorted_set` key.
     * The server applies `sorted_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `member`, `score`.
     * Reference argument example: {"member":{"type":"utf8_string","value":"alpha"},"score":10.5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public SortedSet(Client client, String key) {
      super(client, key, "sorted_set");
    }

    /** Native OP ADD for the sorted_set wire type. */
    /**
     * send native OP ADD to the existing `sorted_set` key.
     * The server applies `sorted_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `member`, `score`.
     * Reference argument example: {"member":{"type":"utf8_string","value":"alpha"},"score":10.5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP SCORE to the existing `sorted_set` key.
     * The server applies `sorted_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `member`.
     * Reference argument example: {"member":{"type":"utf8_string","value":"alpha"}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP SCORE for the sorted_set wire type. */
    /**
     * send native OP SCORE to the existing `sorted_set` key.
     * The server applies `sorted_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `member`.
     * Reference argument example: {"member":{"type":"utf8_string","value":"alpha"}}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object score(Map<String, Object> args) {
      return op("SCORE", args);
    }

    /**
     * send native OP REMOVE to the existing `sorted_set` key.
     * The server applies `sorted_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `member`.
     * Reference argument example: {"member":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object score() {
      return score(Map.of());
    }

    /** Native OP REMOVE for the sorted_set wire type. */
    /**
     * send native OP REMOVE to the existing `sorted_set` key.
     * The server applies `sorted_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `member`.
     * Reference argument example: {"member":{"type":"utf8_string","value":"alpha"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove(Map<String, Object> args) {
      return op("REMOVE", args);
    }

    /**
     * send native OP RANGE to the existing `sorted_set` key.
     * The server applies `sorted_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `start`, `stop`, `reverse`.
     * Reference argument example: {"start":0,"stop":-1,"reverse":false}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove() {
      return remove(Map.of());
    }

    /** Native OP RANGE for the sorted_set wire type. */
    /**
     * send native OP RANGE to the existing `sorted_set` key.
     * The server applies `sorted_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `start`, `stop`, `reverse`.
     * Reference argument example: {"start":0,"stop":-1,"reverse":false}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object range(Map<String, Object> args) {
      return op("RANGE", args);
    }

    /**
     * send native OP RANGE to the existing `sorted_set` key.
     * The server applies `sorted_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `start`, `stop`, `reverse`.
     * Reference argument example: {"start":0,"stop":-1,"reverse":false}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object range() {
      return range(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public SparseVector sparseVector(String key) {
    return new SparseVector(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class SparseVector extends TypeHandle {
    /**
     * Performs the corresponding documented Amaquet operation.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     */
    public SparseVector(Client client, String key) {
      super(client, key, "sparse_vector");
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Stream stream(String key) {
    return new Stream(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Stream extends TypeHandle {
    /**
     * send native OP ADD to the existing `stream` key.
     * The server applies `stream` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `fields`.
     * Reference argument example: {"fields":{"msg":{"type":"utf8_string","value":"hello"}}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Stream(Client client, String key) {
      super(client, key, "stream");
    }

    /** Native OP ADD for the stream wire type. */
    /**
     * send native OP ADD to the existing `stream` key.
     * The server applies `stream` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `fields`.
     * Reference argument example: {"fields":{"msg":{"type":"utf8_string","value":"hello"}}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP RANGE to the existing `stream` key.
     * The server applies `stream` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `count`.
     * Reference argument example: {"count":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP RANGE for the stream wire type. */
    /**
     * send native OP RANGE to the existing `stream` key.
     * The server applies `stream` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `count`.
     * Reference argument example: {"count":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object range(Map<String, Object> args) {
      return op("RANGE", args);
    }

    /**
     * send native OP GROUP_CREATE to the existing `stream` key.
     * The server applies `stream` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `group`.
     * Reference argument example: {"group":"g1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object range() {
      return range(Map.of());
    }

    /** Native OP GROUP_CREATE for the stream wire type. */
    /**
     * send native OP GROUP_CREATE to the existing `stream` key.
     * The server applies `stream` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `group`.
     * Reference argument example: {"group":"g1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object groupCreate(Map<String, Object> args) {
      return op("GROUP_CREATE", args);
    }

    /**
     * send native OP GROUP_READ to the existing `stream` key.
     * The server applies `stream` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `group`, `consumer`, `count`.
     * Reference argument example: {"group":"workers","consumer":"c1","count":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object groupCreate() {
      return groupCreate(Map.of());
    }

    /**
     * send native OP GROUP_READ to the existing `stream` key.
     * The server applies `stream` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `group`, `consumer`, `count`.
     * Reference argument example: {"group":"workers","consumer":"c1","count":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object group_create(Map<String, Object> args) {
      return groupCreate(args);
    }

    /** Native OP GROUP_READ for the stream wire type. */
    /**
     * send native OP GROUP_READ to the existing `stream` key.
     * The server applies `stream` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `group`, `consumer`, `count`.
     * Reference argument example: {"group":"workers","consumer":"c1","count":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Timing: Can depend on available data or synchronization state; configure an appropriate request timeout.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object groupRead(Map<String, Object> args) {
      return op("GROUP_READ", args);
    }

    /**
     * send native OP ACK to the existing `stream` key.
     * The server applies `stream` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `group`, `ids`.
     * Reference argument example: {"group":"workers","ids":["0-1"]}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object groupRead() {
      return groupRead(Map.of());
    }

    /**
     * send native OP ACK to the existing `stream` key.
     * The server applies `stream` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `group`, `ids`.
     * Reference argument example: {"group":"workers","ids":["0-1"]}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object group_read(Map<String, Object> args) {
      return groupRead(args);
    }

    /** Native OP ACK for the stream wire type. */
    /**
     * send native OP ACK to the existing `stream` key.
     * The server applies `stream` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `group`, `ids`.
     * Reference argument example: {"group":"workers","ids":["0-1"]}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object ack(Map<String, Object> args) {
      return op("ACK", args);
    }

    /**
     * send native OP PENDING to the existing `stream` key.
     * The server applies `stream` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `group`.
     * Reference argument example: {"group":"workers"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object ack() {
      return ack(Map.of());
    }

    /** Native OP PENDING for the stream wire type. */
    /**
     * send native OP PENDING to the existing `stream` key.
     * The server applies `stream` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `group`.
     * Reference argument example: {"group":"workers"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object pending(Map<String, Object> args) {
      return op("PENDING", args);
    }

    /**
     * send native OP LEN to the existing `stream` key.
     * The server applies `stream` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object pending() {
      return pending(Map.of());
    }

    /** Native OP LEN for the stream wire type. */
    /**
     * send native OP LEN to the existing `stream` key.
     * The server applies `stream` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP LEN to the existing `stream` key.
     * The server applies `stream` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Symbol symbol(String key) {
    return new Symbol(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Symbol extends TypeHandle {
    /**
     * Performs the corresponding documented Amaquet operation.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     */
    public Symbol(Client client, String key) {
      super(client, key, "symbol");
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public TDigest tDigest(String key) {
    return new TDigest(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class TDigest extends TypeHandle {
    /**
     * send native OP ADD to the existing `t_digest` key.
     * The server applies `t_digest` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public TDigest(Client client, String key) {
      super(client, key, "t_digest");
    }

    /** Native OP ADD for the t_digest wire type. */
    /**
     * send native OP ADD to the existing `t_digest` key.
     * The server applies `t_digest` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":5}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP QUANTILE to the existing `t_digest` key.
     * The server applies `t_digest` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `q`.
     * Reference argument example: {"q":0.5}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP QUANTILE for the t_digest wire type. */
    /**
     * send native OP QUANTILE to the existing `t_digest` key.
     * The server applies `t_digest` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `q`.
     * Reference argument example: {"q":0.5}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object quantile(Map<String, Object> args) {
      return op("QUANTILE", args);
    }

    /**
     * send native OP QUANTILE to the existing `t_digest` key.
     * The server applies `t_digest` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `q`.
     * Reference argument example: {"q":0.5}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object quantile() {
      return quantile(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public TagField tagField(String key) {
    return new TagField(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class TagField extends TypeHandle {
    /**
     * send native OP SET to the existing `tag_field` key.
     * The server applies `tag_field` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `name`, `values`.
     * Reference argument example: {"name":"tags","values":["a","b","c"]}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public TagField(Client client, String key) {
      super(client, key, "tag_field");
    }

    /** Native OP SET for the tag_field wire type. */
    /**
     * send native OP SET to the existing `tag_field` key.
     * The server applies `tag_field` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `name`, `values`.
     * Reference argument example: {"name":"tags","values":["a","b","c"]}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set(Map<String, Object> args) {
      return op("SET", args);
    }

    /**
     * send native OP GET to the existing `tag_field` key.
     * The server applies `tag_field` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set() {
      return set(Map.of());
    }

    /** Native OP GET for the tag_field wire type. */
    /**
     * send native OP GET to the existing `tag_field` key.
     * The server applies `tag_field` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get(Map<String, Object> args) {
      return op("GET", args);
    }

    /**
     * Read current data for a key or type-specific field, subject to server permissions and key existence.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get() {
      return get(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Tensor tensor(String key) {
    return new Tensor(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Tensor extends TypeHandle {
    /**
     * send native OP GET to the existing `tensor` key.
     * The server applies `tensor` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `indices`.
     * Reference argument example: {"indices":[1,1,1]}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Tensor(Client client, String key) {
      super(client, key, "tensor");
    }

    /** Native OP GET for the tensor wire type. */
    /**
     * send native OP GET to the existing `tensor` key.
     * The server applies `tensor` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `indices`.
     * Reference argument example: {"indices":[1,1,1]}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get(Map<String, Object> args) {
      return op("GET", args);
    }

    /**
     * send native OP SET to the existing `tensor` key.
     * The server applies `tensor` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `indices`, `value`.
     * Reference argument example: {"indices":[0,0,0],"value":99}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get() {
      return get(Map.of());
    }

    /** Native OP SET for the tensor wire type. */
    /**
     * send native OP SET to the existing `tensor` key.
     * The server applies `tensor` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `indices`, `value`.
     * Reference argument example: {"indices":[0,0,0],"value":99}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set(Map<String, Object> args) {
      return op("SET", args);
    }

    /**
     * send native OP LEN to the existing `tensor` key.
     * The server applies `tensor` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set() {
      return set(Map.of());
    }

    /** Native OP LEN for the tensor wire type. */
    /**
     * send native OP LEN to the existing `tensor` key.
     * The server applies `tensor` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP LEN to the existing `tensor` key.
     * The server applies `tensor` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public TextField textField(String key) {
    return new TextField(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class TextField extends TypeHandle {
    /**
     * send native OP SET to the existing `text_field` key.
     * The server applies `text_field` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `name`, `value`, `weight`.
     * Reference argument example: {"name":"body","value":"updated text","weight":2}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public TextField(Client client, String key) {
      super(client, key, "text_field");
    }

    /** Native OP SET for the text_field wire type. */
    /**
     * send native OP SET to the existing `text_field` key.
     * The server applies `text_field` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `name`, `value`, `weight`.
     * Reference argument example: {"name":"body","value":"updated text","weight":2}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set(Map<String, Object> args) {
      return op("SET", args);
    }

    /**
     * send native OP GET to the existing `text_field` key.
     * The server applies `text_field` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set() {
      return set(Map.of());
    }

    /** Native OP GET for the text_field wire type. */
    /**
     * send native OP GET to the existing `text_field` key.
     * The server applies `text_field` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get(Map<String, Object> args) {
      return op("GET", args);
    }

    /**
     * Read current data for a key or type-specific field, subject to server permissions and key existence.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get() {
      return get(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Timeseries timeseries(String key) {
    return new Timeseries(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Timeseries extends TypeHandle {
    /**
     * send native OP ADD to the existing `timeseries` key.
     * The server applies `timeseries` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `timestamp`, `value`.
     * Reference argument example: {"timestamp":1730000000000,"value":10}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Timeseries(Client client, String key) {
      super(client, key, "timeseries");
    }

    /** Native OP ADD for the timeseries wire type. */
    /**
     * send native OP ADD to the existing `timeseries` key.
     * The server applies `timeseries` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `timestamp`, `value`.
     * Reference argument example: {"timestamp":1730000000000,"value":10}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP LEN to the existing `timeseries` key.
     * The server applies `timeseries` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP LEN for the timeseries wire type. */
    /**
     * send native OP LEN to the existing `timeseries` key.
     * The server applies `timeseries` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP RANGE to the existing `timeseries` key.
     * The server applies `timeseries` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`.
     * Reference argument example: {"from":0,"to":3000}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }

    /** Native OP RANGE for the timeseries wire type. */
    /**
     * send native OP RANGE to the existing `timeseries` key.
     * The server applies `timeseries` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`.
     * Reference argument example: {"from":0,"to":3000}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object range(Map<String, Object> args) {
      return op("RANGE", args);
    }

    /**
     * send native OP LAST to the existing `timeseries` key.
     * The server applies `timeseries` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object range() {
      return range(Map.of());
    }

    /** Native OP LAST for the timeseries wire type. */
    /**
     * send native OP LAST to the existing `timeseries` key.
     * The server applies `timeseries` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object last(Map<String, Object> args) {
      return op("LAST", args);
    }

    /**
     * send native OP AGGREGATE to the existing `timeseries` key.
     * The server applies `timeseries` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`, `bucket_ms`, `aggregation`.
     * Reference argument example: {"from":0,"to":3000,"bucket_ms":1000,"aggregation":"avg"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object last() {
      return last(Map.of());
    }

    /** Native OP AGGREGATE for the timeseries wire type. */
    /**
     * send native OP AGGREGATE to the existing `timeseries` key.
     * The server applies `timeseries` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`, `bucket_ms`, `aggregation`.
     * Reference argument example: {"from":0,"to":3000,"bucket_ms":1000,"aggregation":"avg"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object aggregate(Map<String, Object> args) {
      return op("AGGREGATE", args);
    }

    /**
     * send native OP AGGREGATE to the existing `timeseries` key.
     * The server applies `timeseries` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `from`, `to`, `bucket_ms`, `aggregation`.
     * Reference argument example: {"from":0,"to":3000,"bucket_ms":1000,"aggregation":"avg"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object aggregate() {
      return aggregate(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public TimeseriesLabels timeseriesLabels(String key) {
    return new TimeseriesLabels(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class TimeseriesLabels extends TypeHandle {
    /**
     * send native OP SET to the existing `timeseries_labels` key.
     * The server applies `timeseries_labels` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `label`, `value`.
     * Reference argument example: {"label":"region","value":"ph"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public TimeseriesLabels(Client client, String key) {
      super(client, key, "timeseries_labels");
    }

    /** Native OP SET for the timeseries_labels wire type. */
    /**
     * send native OP SET to the existing `timeseries_labels` key.
     * The server applies `timeseries_labels` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `label`, `value`.
     * Reference argument example: {"label":"region","value":"ph"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set(Map<String, Object> args) {
      return op("SET", args);
    }

    /**
     * send native OP GET to the existing `timeseries_labels` key.
     * The server applies `timeseries_labels` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set() {
      return set(Map.of());
    }

    /** Native OP GET for the timeseries_labels wire type. */
    /**
     * send native OP GET to the existing `timeseries_labels` key.
     * The server applies `timeseries_labels` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get(Map<String, Object> args) {
      return op("GET", args);
    }

    /**
     * Read current data for a key or type-specific field, subject to server permissions and key existence.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get() {
      return get(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Timestamp timestamp(String key) {
    return new Timestamp(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Timestamp extends TypeHandle {
    /**
     * Performs the corresponding documented Amaquet operation.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     */
    public Timestamp(Client client, String key) {
      super(client, key, "timestamp");
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public TopK topK(String key) {
    return new TopK(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class TopK extends TypeHandle {
    /**
     * send native OP ADD to the existing `top_k` key.
     * The server applies `top_k` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `count`.
     * Reference argument example: {"value":"alice","count":10}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public TopK(Client client, String key) {
      super(client, key, "top_k");
    }

    /** Native OP ADD for the top_k wire type. */
    /**
     * send native OP ADD to the existing `top_k` key.
     * The server applies `top_k` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `count`.
     * Reference argument example: {"value":"alice","count":10}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP ITEMS to the existing `top_k` key.
     * The server applies `top_k` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP ITEMS for the top_k wire type. */
    /**
     * send native OP ITEMS to the existing `top_k` key.
     * The server applies `top_k` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object items(Map<String, Object> args) {
      return op("ITEMS", args);
    }

    /**
     * send native OP ITEMS to the existing `top_k` key.
     * The server applies `top_k` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object items() {
      return items(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Trie trie(String key) {
    return new Trie(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Trie extends TypeHandle {
    /**
     * send native OP INSERT to the existing `trie` key.
     * The server applies `trie` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `id`.
     * Reference argument example: {"value":"amaquet","id":"1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Trie(Client client, String key) {
      super(client, key, "trie");
    }

    /** Native OP INSERT for the trie wire type. */
    /**
     * send native OP INSERT to the existing `trie` key.
     * The server applies `trie` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`, `id`.
     * Reference argument example: {"value":"amaquet","id":"1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object insert(Map<String, Object> args) {
      return op("INSERT", args);
    }

    /**
     * send native OP PREFIX to the existing `trie` key.
     * The server applies `trie` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `prefix`, `limit`.
     * Reference argument example: {"prefix":"ama","limit":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object insert() {
      return insert(Map.of());
    }

    /** Native OP PREFIX for the trie wire type. */
    /**
     * send native OP PREFIX to the existing `trie` key.
     * The server applies `trie` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `prefix`, `limit`.
     * Reference argument example: {"prefix":"ama","limit":10}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object prefix(Map<String, Object> args) {
      return op("PREFIX", args);
    }

    /**
     * send native OP EXACT to the existing `trie` key.
     * The server applies `trie` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"amaquet"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object prefix() {
      return prefix(Map.of());
    }

    /** Native OP EXACT for the trie wire type. */
    /**
     * send native OP EXACT to the existing `trie` key.
     * The server applies `trie` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"amaquet"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object exact(Map<String, Object> args) {
      return op("EXACT", args);
    }

    /**
     * send native OP EXACT to the existing `trie` key.
     * The server applies `trie` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `value`.
     * Reference argument example: {"value":"amaquet"}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object exact() {
      return exact(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Tuple tuple(String key) {
    return new Tuple(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Tuple extends TypeHandle {
    /**
     * send native OP LEN to the existing `tuple` key.
     * The server applies `tuple` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Tuple(Client client, String key) {
      super(client, key, "tuple");
    }

    /** Native OP LEN for the tuple wire type. */
    /**
     * send native OP LEN to the existing `tuple` key.
     * The server applies `tuple` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP GET to the existing `tuple` key.
     * The server applies `tuple` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `index`.
     * Reference argument example: {"index":0}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }

    /** Native OP GET for the tuple wire type. */
    /**
     * send native OP GET to the existing `tuple` key.
     * The server applies `tuple` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `index`.
     * Reference argument example: {"index":0}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get(Map<String, Object> args) {
      return op("GET", args);
    }

    /**
     * send native OP VALUES to the existing `tuple` key.
     * The server applies `tuple` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get() {
      return get(Map.of());
    }

    /** Native OP VALUES for the tuple wire type. */
    /**
     * send native OP VALUES to the existing `tuple` key.
     * The server applies `tuple` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object values(Map<String, Object> args) {
      return op("VALUES", args);
    }

    /**
     * send native OP VALUES to the existing `tuple` key.
     * The server applies `tuple` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object values() {
      return values(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public UnsignedInteger unsignedInteger(String key) {
    return new UnsignedInteger(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class UnsignedInteger extends TypeHandle {
    /**
     * send native OP ADD to the existing `unsigned_integer` key.
     * The server applies `unsigned_integer` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `delta`.
     * Reference argument example: {"delta":8}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public UnsignedInteger(Client client, String key) {
      super(client, key, "unsigned_integer");
    }

    /** Native OP ADD for the unsigned_integer wire type. */
    /**
     * send native OP ADD to the existing `unsigned_integer` key.
     * The server applies `unsigned_integer` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `delta`.
     * Reference argument example: {"delta":8}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP ADD to the existing `unsigned_integer` key.
     * The server applies `unsigned_integer` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `delta`.
     * Reference argument example: {"delta":8}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Url url(String key) {
    return new Url(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Url extends TypeHandle {
    /**
     * send native OP HOST to the existing `url` key.
     * The server applies `url` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public Url(Client client, String key) {
      super(client, key, "url");
    }

    /** Native OP HOST for the url wire type. */
    /**
     * send native OP HOST to the existing `url` key.
     * The server applies `url` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object host(Map<String, Object> args) {
      return op("HOST", args);
    }

    /**
     * send native OP SCHEME to the existing `url` key.
     * The server applies `url` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object host() {
      return host(Map.of());
    }

    /** Native OP SCHEME for the url wire type. */
    /**
     * send native OP SCHEME to the existing `url` key.
     * The server applies `url` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object scheme(Map<String, Object> args) {
      return op("SCHEME", args);
    }

    /**
     * send native OP SCHEME to the existing `url` key.
     * The server applies `url` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object scheme() {
      return scheme(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Utf8String utf8String(String key) {
    return new Utf8String(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Utf8String extends TypeHandle {
    /**
     * Performs the corresponding documented Amaquet operation.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     */
    public Utf8String(Client client, String key) {
      super(client, key, "utf8_string");
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public Uuid uuid(String key) {
    return new Uuid(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class Uuid extends TypeHandle {
    /**
     * Performs the corresponding documented Amaquet operation.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
     */
    public Uuid(Client client, String key) {
      super(client, key, "uuid");
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public VectorMetadata vectorMetadata(String key) {
    return new VectorMetadata(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class VectorMetadata extends TypeHandle {
    /**
     * send native OP SET to the existing `vector_metadata` key.
     * The server applies `vector_metadata` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`, `value`.
     * Reference argument example: {"field":"lang","value":"en"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public VectorMetadata(Client client, String key) {
      super(client, key, "vector_metadata");
    }

    /** Native OP SET for the vector_metadata wire type. */
    /**
     * send native OP SET to the existing `vector_metadata` key.
     * The server applies `vector_metadata` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`, `value`.
     * Reference argument example: {"field":"lang","value":"en"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set(Map<String, Object> args) {
      return op("SET", args);
    }

    /**
     * send native OP GET to the existing `vector_metadata` key.
     * The server applies `vector_metadata` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object set() {
      return set(Map.of());
    }

    /** Native OP GET for the vector_metadata wire type. */
    /**
     * send native OP GET to the existing `vector_metadata` key.
     * The server applies `vector_metadata` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get(Map<String, Object> args) {
      return op("GET", args);
    }

    /**
     * send native OP DELETE to the existing `vector_metadata` key.
     * The server applies `vector_metadata` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`.
     * Reference argument example: {"field":"lang"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object get() {
      return get(Map.of());
    }

    /** Native OP DELETE for the vector_metadata wire type. */
    /**
     * send native OP DELETE to the existing `vector_metadata` key.
     * The server applies `vector_metadata` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`.
     * Reference argument example: {"field":"lang"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object delete(Map<String, Object> args) {
      return op("DELETE", args);
    }

    /**
     * send native OP DELETE to the existing `vector_metadata` key.
     * The server applies `vector_metadata` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `field`.
     * Reference argument example: {"field":"lang"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object delete() {
      return delete(Map.of());
    }
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * @param key Target Amaquet key; use unique keys for examples and tests.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   * @return The server response or computed SDK result; exact shape depends on the operation.
   */
  public VectorSet vectorSet(String key) {
    return new VectorSet(client, key);
  }

  /**
   * Performs the corresponding documented Amaquet operation.
   * Failures: Invalid arguments, protocol errors, remote errors, timeout, or connection loss may be reported as appropriate to this API.
   */
  public static final class VectorSet extends TypeHandle {
    /**
     * send native OP ADD to the existing `vector_set` key.
     * The server applies `vector_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`, `vector`, `metadata`.
     * Reference argument example: {"id":"v1","vector":[1,0,0],"metadata":{"class":"a"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param key Target Amaquet key; use unique keys for examples and tests.
     * @param client Existing client connected to the Amaquet endpoint.
     */
    public VectorSet(Client client, String key) {
      super(client, key, "vector_set");
    }

    /** Native OP ADD for the vector_set wire type. */
    /**
     * send native OP ADD to the existing `vector_set` key.
     * The server applies `vector_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`, `vector`, `metadata`.
     * Reference argument example: {"id":"v1","vector":[1,0,0],"metadata":{"class":"a"}}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add(Map<String, Object> args) {
      return op("ADD", args);
    }

    /**
     * send native OP SEARCH to the existing `vector_set` key.
     * The server applies `vector_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `vector`, `k`.
     * Reference argument example: {"vector":[1,0,0],"k":1}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object add() {
      return add(Map.of());
    }

    /** Native OP SEARCH for the vector_set wire type. */
    /**
     * send native OP SEARCH to the existing `vector_set` key.
     * The server applies `vector_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `vector`, `k`.
     * Reference argument example: {"vector":[1,0,0],"k":1}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object search(Map<String, Object> args) {
      return op("SEARCH", args);
    }

    /**
     * send native OP REMOVE to the existing `vector_set` key.
     * The server applies `vector_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`.
     * Reference argument example: {"id":"v1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object search() {
      return search(Map.of());
    }

    /** Native OP REMOVE for the vector_set wire type. */
    /**
     * send native OP REMOVE to the existing `vector_set` key.
     * The server applies `vector_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: `id`.
     * Reference argument example: {"id":"v1"}.
     * Effects: May change server-side state; do not run as a read-only health check.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove(Map<String, Object> args) {
      return op("REMOVE", args);
    }

    /**
     * send native OP LEN to the existing `vector_set` key.
     * The server applies `vector_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object remove() {
      return remove(Map.of());
    }

    /** Native OP LEN for the vector_set wire type. */
    /**
     * send native OP LEN to the existing `vector_set` key.
     * The server applies `vector_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @param args Exact JSON argument properties supported by the server; names are not translated.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len(Map<String, Object> args) {
      return op("LEN", args);
    }

    /**
     * send native OP LEN to the existing `vector_set` key.
     * The server applies `vector_set` semantics and validates the operation; this wrapper does not emulate the operation locally.
     * Documented JSON argument keys: (none for the reference scenario).
     * Reference argument example: {}.
     * Effects: No mutation is implied by the operation name; verify the server contract for stateful cases.
     * Result: Returns or decodes the server response; the result structure is specific to this operation and data type.
     * @return The server response or computed SDK result; exact shape depends on the operation.
     */
    public Object len() {
      return len(Map.of());
    }
  }
}
