# Blog-SpringBoot
It consists of a blog application as if it were a social network, which through a rest API allows POST, PUT, GET, DELETE requests to manage 
the publications and comments associated with these publications, we can have as many publications and comments as we want.

In addition, the publications are paginated and sorted and through parameters we can indicate how we want to obtain the publications.

On the other hand, it also has exception handling and validation for posts and comments.

Finally, the API is protected with Spring security, managing roles and users with encrypted passwords and then using Json Web Token to handle 
user validation for the operations we perform. This means that we have user authentication and validation with registration and login.

The tecnologies that i used are: Springboot with JPA, JWT, ModelMapper, MySQL, Spring security, etc.
