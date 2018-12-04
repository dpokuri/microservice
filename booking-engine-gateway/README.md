# TIX API Gateway

This project was created for connecting the ReactJS Front-end to Microservices CRUD operation, only applies to the new Admin Dashboard.

Will be used for :
- Handle login session from PHP Monolith
- Authenticate access and privilege for every menu in New Admin Dashboard 

How it works :
- Each time user login in PHP Monolith, it will store the userdata session into redis by using its cookie as redis key
- Frontend getting the current cookie, sending to the TIX-GATEWAY while requesting the data
- TIX-GATEWAY then will receive the cookie, and read the REDIS by using the received cookie
- Via userdata which got from the redis, TIX-GATEWAY will authenticate user if it's allowed to access or not
- If it's allowed then user can access the microservices, if not then the user will notified if he/she not authenticated
..test Line

