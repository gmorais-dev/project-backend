package com.example.kotlindemo.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank

/**
 * Created by rajeevkumarsingh on 05/10/17.
 */
@Entity
data class User (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @get: NotBlank
    val name: String = "",

    @get: NotBlank
    val email: String = "",

    @get: NotBlank
    val password: String = "",

    @get: NotBlank
    val description: String? = "",

    @get: NotBlank
    val is_company: String = ""
)