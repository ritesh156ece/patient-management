
Patient service
1. Build image :
   docker build -t patient-service .
2. Create and start container :
   docker run -p 4000:4000 --network internal --env-file app.env --name patient-service patient-service

app.env content
SPRING_DATASOURCE_PASSWORD=password
SPRING_DATASOURCE_URL=jdbc:postgresql://patient-service-db:5432/db
SPRING_DATASOURCE_USERNAME=admin_user
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_SQL_INIT_MODE=always


Billing service
1. Build image :
   docker build -t billing-service .
2. Create and start container :
   docker run -p 4001:4001 -p 9001:9001 --network internal --name billing-service billing-service