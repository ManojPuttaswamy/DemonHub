/**
 * This package contains classes and services for managing Wishlists in the application.
 *
 * <p>Dependencies used in this package:</p>
 *
 * <ul>
 *   <li><b>Spring Data MongoDB:</b>
 *       <p>Provides repository interfaces for interacting with MongoDB.
 *       The {@link org.springframework.data.mongodb.repository.MongoRepository} is extended
 *       by {@code WishlistRepository} to enable CRUD operations and query methods for
 *       the {@code Wishlist} and {@code WishlistItem} entities.</p>
 *   </li>
 *   <li><b>Spring Web:</b>
 *       <p>Used for creating RESTful endpoints in the controllers, such as
 *       {@link edu.depaul.cdm.demonhub.wishlist.WishlistController}. These endpoints
 *       allow users to manage their wishlists, including adding, removing, and
 *       clearing items.</p>
 *   </li>
 *   <li><b>Spring Boot:</b>
 *       <p>Facilitates the creation and management of the application context,
 *       dependency injection, and autoconfiguration for this package.</p>
 *   </li>
 *   <li><b>Swagger (Springdoc OpenAPI):</b>
 *       <p>Enables API documentation and visualization for endpoints using
 *       annotations like {@link io.swagger.v3.oas.annotations.Operation} and
 *       {@link io.swagger.v3.oas.annotations.tags.Tag}. This documentation
 *       ensures developers can easily explore the wishlist API.</p>
 *   </li>
 *   <li><b>Lombok:</b>
 *       <p>Reduces boilerplate code by automatically generating constructors,
 *       getters, setters, and other methods. For example,
 *       {@link edu.depaul.cdm.demonhub.wishlist.WishlistItem} uses
 *       {@code @Data} and {@code @NoArgsConstructor} annotations.</p>
 *   </li>
 *   <li><b>Mockito:</b>
 *       <p>Used in unit tests for mocking dependencies and verifying behaviors.
 *       For example, {@link edu.depaul.cdm.demonhub.wishlist.WishlistServiceTest}
 *       uses Mockito to mock the {@code WishlistRepository} and test the
 *       {@code WishlistService} logic.</p>
 *   </li>
 *   <li><b>JUnit 5:</b>
 *       <p>The testing framework used for writing unit tests in this package.
 *       Includes annotations like {@code @Test} for defining test cases.</p>
 *   </li>
 *   <li><b>Jackson:</b>
 *       <p>Facilitates JSON serialization and deserialization for API requests
 *       and responses. For example, {@link edu.depaul.cdm.demonhub.wishlist.WishlistController}
 *       accepts JSON input for adding items to a wishlist.</p>
 *   </li>
 * </ul>
 *
 * <p>This package supports functionalities such as:</p>
 *
 * <ul>
 *   <li>Creating a wishlist for a user if one does not already exist.</li>
 *   <li>Adding items to a user's wishlist.</li>
 *   <li>Removing specific items from a wishlist.</li>
 *   <li>Clearing all items from a wishlist.</li>
 *   <li>Retrieving the user's wishlist and its contents.</li>
 * </ul>
 *
 * <p>These functionalities aim to improve user engagement and provide a seamless
 * shopping experience by allowing users to save and manage their favorite products
 * for future purchases.</p>
 *
 * @author Mahadev Reddy Kasireddy
 */
package edu.depaul.cdm.demonhub.wishlist;