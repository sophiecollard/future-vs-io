package com.github.sophiecollard

import cats.data.OptionT

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._

object FutureExample {

  implicit val ec: ExecutionContext = ExecutionContext.global

  def main(args: Array[String]): Unit =
    Await.result(pushRedButton(UsersRepo.donald.id), 10.seconds)

  def pushRedButton(userId: Id[User]): Future[Unit] = {
    val optionT = for {
      user <- OptionT(UsersRepo.UsingFuture.getById(userId))
      _ <- OptionT.when[Future, Unit](user.isThePresident)(())
      _ <- OptionT.liftF(sealTheBunker())
      _ <- OptionT.liftF(launchTheMissiles())
    } yield ()
    optionT.value.map(_ => ())
  }

//  def pushRedButton(userId: Id[User]): Future[Unit] = {
//    val optionT = for {
//      user <- OptionT(UsersRepo.UsingFuture.getById(userId))
//      _ <- OptionT.when[Future, Unit](user.isThePresident)(())
//      _ <- OptionT.liftF(launchTheMissiles())
//    } yield ()
//    optionT.value.map(_ => ())
//  }

  private def launchTheMissiles(): Future[Unit] =
    Future {
      Thread.sleep(5000)
      println(Boom)
    }

  private def sealTheBunker(): Future[Unit] =
    Future(println("Phew!"))

}
