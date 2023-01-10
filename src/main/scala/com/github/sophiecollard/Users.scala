package com.github.sophiecollard

import cats.effect.IO

import scala.collection.concurrent.TrieMap
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

trait Users[F[_]] {

  def getById(id: Id[User]): F[Option[User]]

}

object Users {

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

  val UsingFuture: Users[Future] = new Users[Future] {
    override def getById(id: Id[User]): Future[Option[User]] =
      Future {
        Thread.sleep(5000)
        data.get(id)
      }
  }

  val UsingIO: Users[IO] = new Users[IO] {
    override def getById(id: Id[User]): IO[Option[User]] =
      IO.sleep(5.seconds).as(data.get(id))
  }

}
