
[![Build Status](https://travis-ci.org/JavierMF/features-service.svg?branch=develop)](https://travis-ci.org/JavierMF/features-service)

# Features Model MicroService

REST MicroService for managing products Feature Models (https://en.wikipedia.org/wiki/Feature_model)

### TODO
- [x] Implement JPA Integration
- [x] Refactor to use Services
- [x] Implement REST tests
- [x] Refactor to use Jersey Subresources
- [ ] Implement editing REST resources
- [ ] Add Swagger support
- [ ] Implement features constraints (dependencies, exclusions, etc.)
- [ ] Implement value features

### Resources

 - [x]   GET /products/
 - [x]   GET /products/NAME
 - [x]   POST /products/NAME
 - [x]   DELETE /products/NAME
 - [x]   GET /products/NAME/features
 - [x]   POST /products/NAME/features/NAME
 - [x]   PUT /products/NAME/features/NAME
 - [ ]   DELETE /products/NAME/features/NAME

 - [x]   GET /products/NAME/configurations
 - [x]   GET /products/NAME/configurations/NAME
 - [ ]   POST /products/NAME/configurations/NAME
 - [ ]   UPDATE /products/NAME/configurations/NAME
 - [ ]   DELETE /products/NAME/configurations/NAME
 - [ ]   POST /products/NAME/configurations/NAME/features/NAME
 - [ ]   DELETE /products/NAME/configurations/NAME/features/NAME
