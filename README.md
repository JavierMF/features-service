
[![Build Status](https://travis-ci.org/JavierMF/features-service.svg?branch=develop)](https://travis-ci.org/JavierMF/features-service)

# Features Model MicroService

REST MicroService for managing products Feature Models (https://en.wikipedia.org/wiki/Feature_model)

### TODO
- [x] Implement JPA Integration
- [x] Refactor to use Services
- [x] Implement REST tests
- [x] Refactor to use Jersey Subresources
- [x] Implement editing REST resources
- [x] Add Swagger support
- [ ] Implement features constraints (dependencies, exclusions, etc.)
- [ ] Document API
- [ ] Add CORS support
- [ ] Implement value features

### Resources

 - [x]   GET /products/
 - [x]   GET /products/NAME
 - [x]   POST /products/NAME
 - [x]   DELETE /products/NAME
 - [x]   GET /products/NAME/features
 - [x]   POST /products/NAME/features/NAME
 - [x]   PUT /products/NAME/features/NAME
 - [x]   DELETE /products/NAME/features/NAME

 - [x]   GET /products/NAME/configurations
 - [x]   GET /products/NAME/configurations/NAME
 - [x]   POST /products/NAME/configurations/NAME
 - [x]   DELETE /products/NAME/configurations/NAME
 - [x]   GET /products/NAME/configurations/NAME/features
 - [x]   POST /products/NAME/configurations/NAME/features/NAME
 - [x]   DELETE /products/NAME/configurations/NAME/features/NAME
