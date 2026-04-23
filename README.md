GraphQL is a query language and runtime for APIs that lets clients request exactly the data they need—nothing more, nothing less.
🔹 Key Idea
Instead of multiple REST endpoints, GraphQL uses a single endpoint where the client specifies what data it wants.

🔹 How it works
Client sends a query (or mutation)
Server processes it using a defined schema
Returns only the requested fields in JSON

🔹 Example
Query:
{
 user(id: "1") {
   name
   email
 }
}
Response:
{
 "data": {
   "user": {
     "name": "Shiva",
     "email": "shiva@example.com"
   }
 }
}

🔹 Main Components
Schema → defines data types and structure
Query → fetch data
Mutation → create/update/delete data
Resolver → handles how data is fetched

🔹 Advantages
Fetch only required data (no over-fetching)
Single endpoint (simpler API management)
Strongly typed schema
Good for microservices & frontend flexibility

🔹 GraphQL vs REST (quick)
Feature
GraphQL
REST
Endpoints
Single
Multiple
Data Fetch
Flexible
Fixed
Over-fetching
No
Possible


🔹 When to use
Complex frontend (React apps)
Multiple microservices 
Need flexible data queries
Steps to Integrate Graphql with Springboot

1. add graphql dependency
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-graphql</artifactId>
		</dependency>


2. inside your controller:
@Controller
public class ProductController {
	
	@Autowired
	private ProductRepo productRepo;
	
	
	@QueryMapping
	public List<Product> getProducts(){
		return productRepo.findAll();
	}
	
	@QueryMapping
	public List<Product> getProductsBYCategory(@Argument String category){
		return productRepo.findByCategory(category);
	}

	@MutationMapping
	public Product updateStockById(@Argument int id, @Argument int stock) {
Product product = productRepo.findById(id).orElseThrow(()-> new RuntimeException("product not found with this id"));
		product.setStock(stock);
		return productRepo.save(product);
	}
}

3. create a schema.graphqls inside src/main/resources/graphql folder

type Product{
	id: ID
	name: String
	cost: Float
	category: String
	stock: Int
}

type Query{
	getProducts: [Product]
	getProductsBYCategory(category:String): [Product]
}

type Mutation {
	updateStockById(id:ID, stock:Int): Product
}
	
	
4. simply run spring boot app and test in postman like below
	4.1 create a new graphql query
	4.2 type url http://localhost:8081/graphql and enter
	4.3 you will see all the schemas under query
	4.4 test query by selecting required fields
5. to test in web same like swagger, just need to add 2 lines in yml file
	5.1
	spring.graphql.graphiql.enabled = true
	spring.graphql.graphiql.path = /graphiql
	5.2 open browser and hit localhost:8081/graphiql , then see graphql dashboard to explore, test and run by required fields

