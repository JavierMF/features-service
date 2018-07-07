package org.javiermf.features.exceptions

class ObjectNotFoundException(productName: String) : RuntimeException(String.format("Object with id %s has not been found", productName))
