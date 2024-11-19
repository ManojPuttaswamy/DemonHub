/************************************************************************************************************
 * This package handles Cart functionality for the e-commerce application.
 *
 * <p>Key Features:</p>
 * <ul>
 *   <li>Manages user carts, including adding, updating, and removing items.</li>
 *   <li>Calculates total price for all items in the cart.</li>
 *   <li>Supports persistence using Spring Data JPA and relational databases.</li>
 * </ul>
 *
 * <p>Primary Classes:</p>
 * <ul>
 *   <li>{@link edu.depaul.cdm.demonhub.cart.Cart} - Represents the Cart entity, storing a list of items.</li>
 *   <li>{@link edu.depaul.cdm.demonhub.cart.CartItem} - Represents an item in the cart, including details like product name, price, and quantity.</li>
 *   <li>{@link edu.depaul.cdm.demonhub.cart.CartService} - Contains the business logic for cart operations.</li>
 *   <li>{@link edu.depaul.cdm.demonhub.cart.CartController} - Exposes RESTful APIs for cart operations.</li>
 *   <li>{@link edu.depaul.cdm.demonhub.cart.CartRepository} - Repository interface for database interactions.</li>
 * </ul>
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>Spring Boot</li>
 *   <li>Spring Data JPA</li>
 *   <li>Spring Web</li>
 * </ul>
 *
 * <p>Notes:</p>
 * <ul>
 *   <li>Ensure database connection configurations are properly set in the application properties file.</li>
 *   <li>Unit and integration tests cover all critical functionalities for this package.</li>
 * </ul>
 *
 * @author Mahadev Reddy Kasireddy
 ************************************************************************************************************/
package edu.depaul.cdm.demonhub.cart;