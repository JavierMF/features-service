package org.javiermf.features.exceptions

class DuplicatedObjectException(name: String) : RuntimeException(String.format("Object with id %s already exists", name))
