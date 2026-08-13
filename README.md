# Insurance Management System - MyBatis Maven

Pure **Core Java + Maven + MyBatis XML + MySQL** console application.

## No Spring
This project does **not** use Spring, Spring Boot, Spring MVC, Spring Data, or MyBatis-Spring.

## Package
`com.crimsonlogic.insurancemanagementsystem`

## Main requirements
- `Main.java` contains only `main()` and starts `Application`.
- Abstract `User` extended by `Admin`, `Employee`, `Agent`, and `Customer`.
- Role-based login with separate menus.
- Admin registers employees and agents and manages user status.
- Employee registers customers and manages customer status.
- Customer can view and take policies, pay premiums, view payment history, and submit/view claims.
- Agent can view assigned claims and approve/reject only claims assigned to that agent.
- Five sample agents are included.
- Agent assignment is automatic: active agent with the lowest active-policy count is selected; agent name breaks ties.
- Premium is calculated at purchase time from customer age and policy coverage.
- Payment method/status enums are used in the payment flow.
- Claim status enum is used in submit/approve/reject flow.
- Premium status enum is used for due/paid validation.
- Delete is replaced by ACTIVE/INACTIVE status.
- UUIDs are generated in Java for IDs.
- Separate validators for employee, agent, customer, policy, payment, and claim.
- User-defined exceptions with try/catch handling.
- Interfaces + service implementations for reusable business logic.
- `HashMap`, `ArrayList`, `List`, `Optional`, and streams are used where appropriate.
- TableFormatter prints data as console tables.
- Invalid input is caught and the user is asked to enter the value again instead of terminating the menu.
- MyBatis mapper interfaces and XML mapper files are used for database operations.

## Database setup
1. Create/open MySQL.
2. Run `database/insurance_management.sql`.
3. Update `src/main/resources/db.properties` with your MySQL username/password.
4. Reload Maven in IntelliJ.
5. Run `com.crimsonlogic.insurancemanagementsystem.Main`.

## Sample logins
- Admin: `admin` / `admin123`
- Employee: `employee` / `emp123`
- Agent: `rahul` / `agent123`
- Agent: `priya` / `agent123`
- Agent: `kiran` / `agent123`
- Agent: `sneha` / `agent123`
- Agent: `vikram` / `agent123`
- Customer: `amit` / `cust123`

## Important MyBatis fix
The configuration does not register `EnumTypeHandler` globally. MyBatis automatically selects the correct enum handler for enum-valued properties. Registering `EnumTypeHandler` without its required enum-class constructor caused the `Unable to find a usable constructor for class org.apache.ibatis.type.EnumTypeHandler` error.


## MySQL Public Key Retrieval Fix

The JDBC URL includes `allowPublicKeyRetrieval=true` so MySQL 8/9 authentication does not fail with:
`Public Key Retrieval is not allowed`.

If your MySQL server uses a different host, port, database name, username, or password, update `src/main/resources/db.properties`.


## Login Flow

At the start of login, the application asks the user to select:
1. Admin
2. Employee
3. Agent
4. Customer

Only after selecting the role does it ask for username and password.
The selected role is compared with the role stored in the database. If the
role does not match, login is rejected and the user can try again.
