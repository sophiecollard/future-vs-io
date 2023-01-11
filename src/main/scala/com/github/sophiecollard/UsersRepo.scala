package com.github.sophiecollard

import cats.effect.IO

import scala.collection.concurrent.TrieMap
import scala.concurrent.{ExecutionContext, Future}

trait UsersRepo[F[_]] {

  def getById(id: Id[User]): F[Option[User]]

}

object UsersRepo {

  implicit val ec: ExecutionContext = ExecutionContext.global

  val donald: User = User(id = Id.random[User](), name = "Donald", isThePresident = false)
  val kim: User = User(id = Id.random[User](), name = "Kim", isThePresident = true)

  private val data: TrieMap[Id[User], User] =
    TrieMap.from(
      List(
        donald.id -> donald,
        kim.id -> kim
      )
    )

  val UsingFuture: UsersRepo[Future] = new UsersRepo[Future] {
    override def getById(id: Id[User]): Future[Option[User]] =
      Future.successful(data.get(id))
  }

  val UsingIO: UsersRepo[IO] = new UsersRepo[IO] {
    override def getById(id: Id[User]): IO[Option[User]] =
      IO.pure(data.get(id))
  }

}
