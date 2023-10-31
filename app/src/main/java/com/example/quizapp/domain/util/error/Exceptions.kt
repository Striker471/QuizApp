package com.example.quizapp.domain.util.error

class EmptyUserException : Exception("Cannot find the user")
class NullSnapshotException : Exception("User document does not exist")
class UserDeserializationException : Exception("Unable to deserialize user object from Firestore document")
class UserNotLoggedInException : Exception("User not logged in")
