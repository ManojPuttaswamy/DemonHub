/**
 * This package contains classes and services for managing Orders in the application.
 *
 * <p>Dependencies used in this package:</p>
 *
 * <ul>
 *   <li><b>Spring Data JPA:</b>
 *       <p>Provides repository interfaces for interacting with relational databases. 
 *       The {@link org.springframework.data.jpa.repository.JpaRepository} is extended 
 *       by {@code OrderRepository} to enable CRUD operations and query methods for 
 *       the {@code Order} entity.</p>
 *   </li>
 *   <li><b>Spring Web:</b>
 *       <p>Used for creating RESTful endpoints in the controllers, such as 
 *       {@link edu.depaul.cdm.demonhub.order.AdminOrderController} and 
 *       {@link edu.depaul.cdm.demonhub.order.CustomerOrderController}.</p>
 *   </li>
 *   <li><b>Spring Boot:</b>
 *       <p>Facilitates the creation and management of the application context, 
 *       dependency injection, and autoconfiguration for this package.</p>
 *   </li>
 *   <li><b>Swagger (Springdoc OpenAPI):</b>
 *       <p>Enables API documentation and visualization for endpoints using 
 *       annotations like {@link io.swagger.v3.oas.annotations.Operation} and 
 *       {@link io.swagger.v3.oas.annotations.tags.Tag}.</p>
 *   </li>
 *   <li><b>Lombok:</b>
 *       <p>Reduces boilerplate code by automatically generating constructors, 
 *       getters, setters, and other methods. For example, 
 *       {@link edu.depaul.cdm.demonhub.order.OrderRequest} uses 
 *       {@code @Data} and {@code @NoArgsConstructor} annotations.</p>
 *   </li>
 *   <li><b>Mockito:</b>
 *       <p>Used in unit tests for mocking dependencies and verifying behaviors. 
 *       For example, {@link edu.depaul.cdm.demonhub.order.AdminOrderServiceTest} 
 *       uses Mockito to mock the {@code OrderRepository} and test the 
 *       {@code AdminOrderService}.</p>
 *   </li>
 *   <li><b>JUnit 5:</b>
 *       <p>The testing framework used for writing unit tests in this package. 
 *       Includes annotations like {@code @Test} for defining test cases.</p>
 *   </li>
 * </ul>
 *
 * <p>This package supports functionalities such as creating, updating, retrieving, 
 * and deleting orders for both administrative and customer-facing operations.</p>
 * 
 * @author Meghashree Badri Lakshmi Narasimhan
 */
package edu.depaul.cdm.demonhub.order;