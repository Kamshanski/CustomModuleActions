package dev.itssho.module.component.idea.action.idea

import dev.itssho.module.component.idea.common.IdeaException

class AlreadyExistsException(message: String? = null, cause: Throwable? = null) : IdeaException(message, cause)
class NotValidDirectory(message: String? = null, cause: Throwable? = null) : IdeaException(message, cause)
class NotValidDirectoryName(message: String? = null, cause: Throwable? = null) : IdeaException(message, cause)
class UserHomeDirectoryNotFound(message: String? = null, cause: Throwable? = null) : IdeaException(message, cause)
class PsiCreationException(message: String? = null, cause: Throwable? = null) : IdeaException(message, cause)