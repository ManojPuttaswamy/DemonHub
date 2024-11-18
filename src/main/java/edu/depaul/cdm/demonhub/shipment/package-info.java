/**
 * This package manages the Shipment-related functionality in the application.
 *
 * <p>Dependencies used in this package:</p>
 *
 * <ul>
 *   <li><b>Spring Data MongoDB:</b>
 *       <p>Provides repository interfaces for interacting with MongoDB. The 
 *       {@link org.springframework.data.mongodb.repository.MongoRepository} is 
 *       extended by {@code ShipmentRepository} to enable CRUD operations and query 
 *       methods for the {@code Shipment} entity.</p>
 *   </li>
 *   <li><b>Spring Web:</b>
 *       <p>Used for defining RESTful endpoints in the {@link edu.depaul.cdm.demonhub.shipment.ShipmentController}. 
 *       It includes annotations such as {@code @RestController}, {@code @RequestMapping}, 
 *       {@code @GetMapping}, {@code @PostMapping}, and {@code @DeleteMapping}.</p>
 *   </li>
 *   <li><b>Spring Boot:</b>
 *       <p>Facilitates application context management, dependency injection, 
 *       and MongoDB integration through autoconfiguration.</p>
 *   </li>
 *   <li><b>Swagger (Springdoc OpenAPI):</b>
 *       <p>Enables API documentation and visualization for endpoints using 
 *       annotations like {@link io.swagger.v3.oas.annotations.Operation} and 
 *       {@link io.swagger.v3.oas.annotations.tags.Tag} in the 
 *       {@link edu.depaul.cdm.demonhub.shipment.ShipmentController}.</p>
 *   </li>
 *   <li><b>Lombok:</b>
 *       <p>Reduces boilerplate code by automatically generating constructors, 
 *       getters, setters, and other methods. For example, 
 *       {@link edu.depaul.cdm.demonhub.shipment.Shipment} uses annotations like 
 *       {@code @Data} and {@code @NoArgsConstructor}.</p>
 *   </li>
 *   <li><b>Mockito:</b>
 *       <p>Used in unit tests for mocking dependencies and verifying behaviors. 
 *       For example, {@link edu.depaul.cdm.demonhub.shipment.ShipmentServiceTest} 
 *       uses Mockito to mock the {@code ShipmentRepository} and test the 
 *       {@code ShipmentService}.</p>
 *   </li>
 *   <li><b>JUnit 5:</b>
 *       <p>The testing framework used for writing unit tests in this package. 
 *       Includes annotations like {@code @Test} for defining test cases.</p>
 *   </li>
 * </ul>
 *
 * <p>This package supports functionalities such as creating, updating, retrieving, 
 * and deleting shipment records. It allows managing shipment tracking, 
 * statuses, and delivery dates.</p>
 * 
 * @author Meghashree Badri Lakshmi Narasimhan
 */
package edu.depaul.cdm.demonhub.shipment;