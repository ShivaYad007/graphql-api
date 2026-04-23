# 🚀 GraphQL — Complete Guide with Spring Boot Integration

> **GraphQL** is a query language and runtime for APIs that lets clients request **exactly the data they need** — nothing more, nothing less.

---

## 📌 Table of Contents

- [Key Idea](#-key-idea)
- [How It Works](#-how-it-works)
- [Example](#-example)
- [Main Components](#-main-components)
- [Advantages](#-advantages)
- [GraphQL vs REST](#-graphql-vs-rest)
- [When to Use](#-when-to-use)
- [Spring Boot Integration](#-spring-boot-integration)

---

## 💡 Key Idea

Instead of multiple REST endpoints, GraphQL uses a **single endpoint** where the client specifies exactly what data it wants.

```
POST /graphql   ← one endpoint to rule them all
```

---

## ⚙️ How It Works

```
Client sends Query / Mutation
        ↓
Server processes it using a defined Schema
        ↓
Returns only the requested fields in JSON
```

---

## 📦 Example

**Query:**
```graphql
{
  user(id: "1") {
    name
    email
  }
}
```

**Response:**
```json
{
  "data": {
    "user": {
      "name": "Shiva",
      "email": "shiva@example.com"
    }
  }
}
```

---

## 🧩 Main Components

| Component | Description |
|-----------|-------------|
| **Schema** | Defines data types and structure |
| **Query** | Fetch / read data |
| **Mutation** | Create, update, or delete data |
| **Resolver** | Handles how data is actually fetched |

---

## ✅ Advantages

- 🎯 **Fetch only required data** — no over-fetching or under-fetching
- 🔗 **Single endpoint** — simpler API management
- 🔒 **Strongly typed schema** — predictable and self-documenting
- 🔧 **Great for microservices** — flexible per-service data contracts
- 💻 **Frontend flexibility** — clients control the shape of data

---

## ⚖️ GraphQL vs REST

| Feature | GraphQL | REST |
|---------|---------|------|
| Endpoints | Single (`/graphql`) | Multiple (`/users`, `/orders`, etc.) |
| Data Fetch | Flexible — client decides | Fixed — server decides |
| Over-fetching | ❌ Not possible | ✅ Common |
| Under-fetching | ❌ Not possible | ✅ Common |
| Type System | Strongly typed schema | No enforced standard |
| Versioning | Not needed | `/v1/`, `/v2/` etc. |

---

## 🤔 When to Use

- ✅ Complex frontend apps (React, Angular, Flutter)
- ✅ Multiple microservices aggregation
- ✅ Need for flexible / dynamic data queries
- ✅ Mobile apps where bandwidth matters
- ❌ Simple CRUD with no flexibility needs → REST is fine

---

## 🛠️ Spring Boot Integration

### Step 1 — Add Dependency

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-graphql</artifactId>
</dependency>
```

---

### Step 2 — Create the Controller

```java
@Controller
public class ProductController {

    @Autowired
    private ProductRepo productRepo;

    @QueryMapping
    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    @QueryMapping
    public List<Product> getProductsByCategory(@Argument String category) {
        return productRepo.findByCategory(category);
    }

    @MutationMapping
    public Product updateStockById(@Argument int id, @Argument int stock) {
        Product product = productRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setStock(stock);
        return productRepo.save(product);
    }
}
```

> **Note:** Use `@QueryMapping` for queries and `@MutationMapping` for mutations. `@Argument` binds input parameters.

---

### Step 3 — Define the Schema

Create `src/main/resources/graphql/schema.graphqls`:

```graphql
type Product {
    id: ID
    name: String
    cost: Float
    category: String
    stock: Int
}

type Query {
    getProducts: [Product]
    getProductsByCategory(category: String): [Product]
}

type Mutation {
    updateStockById(id: ID, stock: Int): Product
}
```

---

### Step 4 — Test with Postman

1. Open Postman → **New** → **GraphQL Query**
2. Set URL: `http://localhost:8081/graphql`
3. All available schemas will appear under the query explorer
4. Select the required fields and run the query

---

### Step 5 — Enable GraphiQL (Web UI like Swagger)

Add the following to your `application.yml` or `application.properties`:

**`application.yml`:**
```yaml
spring:
  graphql:
    graphiql:
      enabled: true
      path: /graphiql
```

**`application.properties`:**
```properties
spring.graphql.graphiql.enabled=true
spring.graphql.graphiql.path=/graphiql
```

Then open your browser and navigate to:

```
http://localhost:8081/graphiql
```

You'll see a full GraphQL dashboard to **explore, test, and run queries** by selecting only the required fields — similar to Swagger UI.

---

## 📁 Project Structure (Relevant Files)

```
src/
└── main/
    ├── java/
    │   └── com/example/
    │       ├── controller/
    │       │   └── ProductController.java
    │       ├── model/
    │       │   └── Product.java
    │       └── repository/
    │           └── ProductRepo.java
    └── resources/
        ├── graphql/
        │   └── schema.graphqls        ← GraphQL schema here
        └── application.yml
```

---

## 🔗 References

- [GraphQL Official Docs](https://graphql.org/learn/)
- [Spring for GraphQL Docs](https://docs.spring.io/spring-graphql/docs/current/reference/html/)
- [GraphiQL Explorer](https://github.com/graphql/graphiql)

---

<p align="center">Made with ☕ and curiosity</p>
