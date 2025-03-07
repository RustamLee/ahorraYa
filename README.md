# AhorraYa*
AhorraYa is a web application designed to help users save money by providing up-to-date information on discounts and cashback offers available at various supermarkets.
*The name "AhorraYa" has Spanish roots and means "Save now"

## About the Project
The idea behind AhorraYa emerged from the need to track current promotional offers in one place. The primary goal is to create a convenient and functional solution that allows easy management of discounts.

### Features
The platform provides users with the ability to:
- **Add, view, edit, and delete promotional offers.**
- **Filter discounts by day of the week, store, bank, and card type.**
- **View existing offers without authorization.**

## Filters and Authorization
- Unauthorized users can see available filters but cannot edit them.
- Upon first launch, a list of banks and stores available in the database is loaded.
- After authorization, users can edit filters and manage their discounts.

## Creating and Managing Discounts
- Each user can only see discounts they created.
- Editing and deletion are available only for their own records.
- To create a new discount, all fields must be filled in, except for the optional details.
- When entering the name of a bank or store, a debounced search (0.5 sec) is performed after two characters, suggesting options from the database.
- If a bank or store is not in the database, a new record is created.
- When saving a discount, the user ID, bank ID, and store ID are saved in the database.

## Data Storage
- The user ID is taken from localStorage, where the JWT token is also stored.

## Technologies
### Backend
- **Spring Boot** — primary framework.
- **Spring Security** — JWT-based authentication.
- **PostgreSQL** — database.

### Frontend
- **Vue.js** — user interface.
- **Nginx** — server for frontend deployment.

## Deployment
All services (backend, database, and frontend) are deployed on Digital Ocean and are accessible via the public link:  
[http://159.65.178.178/](http://159.65.178.178/)
