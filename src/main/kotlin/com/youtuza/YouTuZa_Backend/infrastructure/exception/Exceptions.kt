package com.youtuza.YouTuZa_Backend.infrastructure.exception

import xquare.app.xquareinfra.infrastructure.exception.DomainNames

open class YouTuZaException(
    val status: Int,
    message: String
) : RuntimeException(message) {

    companion object {

        // Default
        val BAD_REQUEST = YouTuZaException(400, "Bad Request")
        val UNAUTHORIZED = YouTuZaException(401, "Unauthorized")
        val FORBIDDEN = YouTuZaException(403, "Forbidden")
        val NOT_FOUND = YouTuZaException(404, "Not Found")
        val CONFLICT = YouTuZaException(409, "Conflict")
        protected val I_M_A_TEAPOT = YouTuZaException(418, "I'm a Teapot")
        val INTERNAL_SERVER_ERROR = YouTuZaException(500, "Internal Server Error")
    }
}

class BusinessLogicException(
    status: Int,
    message: String
) : YouTuZaException(status, message) {
    constructor(errorCodePrefixSuffix: ErrorCodePrefixSuffix, domainName: DomainNames) :
        this(
            status = errorCodePrefixSuffix.status,
            message = if (errorCodePrefixSuffix.isPrefix) {
                errorCodePrefixSuffix.message + domainName.value
            } else {
                domainName.value + errorCodePrefixSuffix.message
            }
        )

    companion object {
        val USER_NOT_FOUND = BusinessLogicException(ErrorCodePrefixSuffix.XXX_NOT_FOUND, DomainNames.USER)
        val ALREADY_EXISTS_TEAM = BusinessLogicException(ErrorCodePrefixSuffix.ALREADY_EXISTS_XXX, DomainNames.TEAM)
        val ALREADY_EXISTS_USER_TEAM = BusinessLogicException(ErrorCodePrefixSuffix.ALREADY_EXISTS_XXX, DomainNames.USER_TEAM)
        val TEAM_NOT_FOUND = BusinessLogicException(ErrorCodePrefixSuffix.XXX_NOT_FOUND, DomainNames.TEAM)
        val ADD_TEAM_PERMISSION_DENIED = BusinessLogicException(ErrorCodePrefixSuffix.XXX_PERMISSION_DENIED, DomainNames.TEAM)
        val USER_TEAM_NOT_FOUND = BusinessLogicException(ErrorCodePrefixSuffix.XXX_NOT_FOUND, DomainNames.USER_TEAM)
        val USER_TEAM_BAD_REQUEST = BusinessLogicException(ErrorCodePrefixSuffix.XXX_BAD_REQUEST, DomainNames.USER_TEAM)
        val ALREADY_EXISTS_DEPLOY = BusinessLogicException(ErrorCodePrefixSuffix.ALREADY_EXISTS_XXX, DomainNames.DEPLOY)
        val DEPLOY_NOT_FOUND = BusinessLogicException(ErrorCodePrefixSuffix.XXX_NOT_FOUND, DomainNames.DEPLOY)
        val CREATE_DEPLOY_BAD_REQUEST = BusinessLogicException(ErrorCodePrefixSuffix.XXX_BAD_REQUEST, DomainNames.DEPLOY)
        val USER_ALREADY_EXISTS = BusinessLogicException(ErrorCodePrefixSuffix.ALREADY_EXISTS_XXX, DomainNames.USER)
        val CONTAINER_NOT_FOUND = BusinessLogicException(ErrorCodePrefixSuffix.XXX_NOT_FOUND, DomainNames.CONTAINER)
    }
}

class SecurityException(
    status: Int,
    message: String
) : YouTuZaException(status, message) {

    constructor(errorCodePrefixSuffix: ErrorCodePrefixSuffix, domainName: DomainNames) :
        this(
            status = errorCodePrefixSuffix.status,
            message = if (errorCodePrefixSuffix.isPrefix) {
                errorCodePrefixSuffix.message + domainName.value
            } else {
                domainName.value + errorCodePrefixSuffix.message
            }
        )

    companion object {

        val INVALID_TOKEN = SecurityException(401, "Invalid Token")

        val PERMISSION_DENIED = SecurityException(403, "Permission Denied")
    }
}

enum class ErrorCodePrefixSuffix(
    val status: Int,
    val message: String,
    val isPrefix: Boolean
) {
    XXX_NOT_FOUND(404, " Not Found", false),
    XXX_PERMISSION_DENIED(403, " Permission Denied", false),
    XXX_BAD_REQUEST(400, " Bad Request", false),
    ALREADY_EXISTS_XXX(409, "Already Exists ", true)
}

class FilterException(
    status: Int,
    message: String
) : YouTuZaException(status, message)

class InterceptorException(
    status: Int,
    message: String
) : YouTuZaException(status, message)

class PresentationValidationException(
    status: Int,
    message: String,
    val fields: Map<String, String>
) : YouTuZaException(status, message)

class AuthenticationException(
    status: Int,
    message: String
) : YouTuZaException(status, message) {

    companion object {

        // UnAuthorized
        val INVALID_TOKEN = AuthenticationException(401, "Invalid Token")
        val EXPIRED_TOKEN = AuthenticationException(401, "Expired Token")
        val UNAUTHORIZED = AuthenticationException(401, "Unauthorized")
    }
}

class FeignException(
    status: Int,
    message: String
) : YouTuZaException(status, message) {

    companion object {

        val FEIGN_BAD_REQUEST = FeignException(400, "Feign Bad Request")
        val FEIGN_UNAUTHORIZED = FeignException(401, "Feign UnAuthorized")
        val FEIGN_FORBIDDEN = FeignException(403, "Feign Forbidden")
        val FEIGN_SERVER_ERROR = FeignException(500, "Feign Server Error")
        val FEIGN_UNKNOWN_CLIENT_ERROR = FeignException(500, "Feign Unknown Error")
    }
}

class CriticalException(
    status: Int,
    message: String
) : YouTuZaException(status, message)
