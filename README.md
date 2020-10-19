## Internet shop
Internet shop with basic operations. 

The main goal of this project was to create model of online shop built on SOLID principles and RBAC filter
strategy.
This project is based on N-tier architecture with DB, DAO, service and controllers layers.

Depending on the role the user will have access to different functions:<br>
##### No role:<br>
- register
- log in
- inject mock data to DB<br>
##### User role:
- view products
- buy/remove products from shopping cart
- complete the order
- view list of own orders<br>
##### Admin role:
- view/delete users
- view/add/delete products
- view/delete orders

### Used technologies
- Apache Tomcat 
- Maven
- MySQL 
- JDBC
- Servlet
- JSTL
- JSP
- HTML, CSS

### Launch guide
1) Download and configure Apache Tomcat
2) Download and install MySQl and MySql workbench
3) Use script in \main\resources\init_db.sql as a query in MySQL workbench to create schema and all needed tables.
4) Change user and password in \main\java\com\internet\shop\util\ConnectionUtil.java to login/password of your MySQL.
5) After starting project click on "Inject test data". After this next data will be inserted to DB:
- user: login "bob", password "1", role "USER"
- admin: login "admin", password "1", role "ADMIN"
- three products for testing

#### Authors
[Oleksii Tromsa](https://github.com/OleksiiTromsa)