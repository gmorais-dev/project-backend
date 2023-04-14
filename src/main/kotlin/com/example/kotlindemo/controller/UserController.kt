package com.example.kotlindemo.controller

import com.example.kotlindemo.model.User
import com.example.kotlindemo.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController(private val userRepository: UserRepository) {

    @GetMapping("/users")
    fun getAllUsers(): ResponseEntity<List<User>>  {
        val users: MutableList<User>

        try {
            users = userRepository.findAll()
        } catch (ex: Exception) {
            return ResponseEntity.internalServerError().build()
        }

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        return ResponseEntity.ok(users)
    }

    @PostMapping
    fun create(@Valid @RequestBody user: User): ResponseEntity<User> {
        try {
            userRepository.save(user)
            return ResponseEntity.status(HttpStatus.CREATED).build()
        } catch (ex: Exception) {
            return ResponseEntity.internalServerError().build()
        }
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable(value = "id") id_user: Long): ResponseEntity<User> {
        return userRepository.findById(id_user).map { user ->

            try {
                ResponseEntity.ok(user)
            } catch (ex: Exception) {
                ResponseEntity.internalServerError().build<User>()
            }

        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/{id}")
    fun update(@PathVariable(value = "id") userId: Long,
               @Valid @RequestBody newContent: User): ResponseEntity<User> {

        return userRepository.findById(userId).map { existingUser ->
            val updatedArticle: User = existingUser
                .copy(name = newContent.name, email = newContent.email,  description = newContent.description, is_company = newContent.is_company)

            try {
                ResponseEntity.ok().body(userRepository.save(updatedArticle))
            } catch (ex: Exception) {
                ResponseEntity.internalServerError().build<User>()
            }

        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun destroy(@PathVariable(value = "id") userId: Long): ResponseEntity<User> {

        return userRepository.findById(userId).map { user  ->

            try {
                userRepository.delete(user)
            } catch (ex: Exception) {
                ResponseEntity.internalServerError()
            }

            ResponseEntity.ok().build<User>()
        }.orElse(ResponseEntity.notFound().build())

    }
}