package fr.acle.notello.common.util

import org.springframework.core.ParameterizedTypeReference


inline fun <reified T> typeReference() = object : ParameterizedTypeReference<T>() {}

