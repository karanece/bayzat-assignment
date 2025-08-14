## Changes
1. Changed the datatype of tables and added few fields and constraints
2. Assumption - I believe the `end_time` will be `null`, when the delivery agent first picks the order, only then, we know
if the order took more time to deliver. If we receive only the data after the delivery is done, with the `end_time`, then
we can't identify if it took long time to deliver.
3. Added `user_role` field to clearly identify the `person` role.

## To run existing project with Docker
1. Same instruction as in README
2. Application is already dockerized. You can run following command in root folder for setting up Postgres database.
- ```docker compose up```
3. Run application from Gradle or IDE.

## Test Data
1. I tested the application from my local with some sample test data.
2. To add a person
- ```curl -X POST http://localhost:8081/api/person -H 'Content-Type: application/json' -d '{"name":"Prabakaran", "email":"abc@xyz.com", "role":"customer", "registrationNumber":10001}'```
- ```curl -X POST http://localhost:8081/api/person -H 'Content-Type: application/json' -d '{"name":"Subramanian", "email":"xyz@abc.com", "role":"delivery_agent", "registrationNumber":10002}'```
- ```curl -X POST http://localhost:8081/api/person -H 'Content-Type: application/json' -d '{"name":"Rasha", "email":"xyz@abcd.com", "role":"delivery_agent", "registrationNumber":10003}'```
3. To add a delivery
- ```curl -X POST http://localhost:8081/api/delivery -H 'Content-Type: application/json;charset=UTF-8' -d '{"deliveryMan":{"id":2}, "customer":{"id":1}, "startTime":"2025-08-14T10:34:33.685426", "endTime":"2025-08-14T11:15:33.685426", "distance":10, "price":35.78}```
- The below should trigger `DelayedDeliveryNotifier` notifications, if the time is set less than 45 minutes from current time
- ```curl -X POST http://localhost:8081/api/delivery -H 'Content-Type: application/json;charset=UTF-8' -d '{"deliveryMan":{"id":3}, "customer":{"id":1}, "startTime":"2025-08-14T18:30:33.685426", "endTime":null, "distance":25, "price":295.48}```
- ```curl -X POST http://localhost:8081/api/delivery -H 'Content-Type: application/json;charset=UTF-8' -d '{"deliveryMan":{"id":2}, "customer":{"id":1}, "startTime":"2025-08-13T14:10:33.685426", "endTime":"2025-08-13T03:10:33.685426", "distance":35, "price":595.48}```
- ```curl -X POST http://localhost:8081/api/delivery -H 'Content-Type: application/json;charset=UTF-8' -d '{"deliveryMan":{"id":2}, "customer":{"id":1}, "startTime":"2025-08-13T00:10:33.685426", "endTime":"2025-08-13T01:10:33.685426", "distance":5, "price":95.48}```
- Updating the `end_time` for the second delivery
- ```curl -X POST http://localhost:8081/api/delivery -H 'Content-Type: application/json;charset=UTF-8' -d '{"deliveryMan":{"id":2}, "customer":{"id":1}, "startTime":"2025-08-11T19:34:33.685426", "endTime":"2025-08-11T19:59:33.685426", "distance":25, "price":295.48, "id":2}'```
4. To fetch top K Agents with Max Commission
- ```curl -v http://localhost:8081/api/delivery/topKAgents\?startTime\=2025-08-12T01%3A01%3A00.000000Z\&endTime\=2025-08-15T01%3A00%3A00.000000Z```
