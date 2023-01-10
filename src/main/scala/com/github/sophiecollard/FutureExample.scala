package com.github.sophiecollard

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._

object FutureExample {

  implicit val ec: ExecutionContext = ExecutionContext.global

  def main(args: Array[String]): Unit =
    Await.result(pushRedButton(Users.donald.id), 10.seconds)

  def pushRedButton(userId: Id[User]): Future[Unit] =
    for {
      maybeUser <- Users.UsingFuture.getById(userId)
      _ <- if (maybeUser.exists(_.isThePresident))
        launchTheMissiles()
      else
        Future(println("Unauthorized user: launch aborted"))
    } yield ()

  private def launchTheMissiles(): Future[Unit] =
    Future(println("The End"))

}
