package com.github.sophiecollard

import cats.data.OptionT
import cats.effect.{ExitCode, IO, IOApp}

object IOExample extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    pushRedButton(UsersRepo.donald.id).as(ExitCode.Success)

  def pushRedButton(userId: Id[User]): IO[Unit] = {
    val optionT = for {
      user <- OptionT(UsersRepo.UsingIO.getById(userId))
      _ <- OptionT.when[IO, Unit](user.isThePresident)(())
      _ <- OptionT.liftF(launchTheMissiles())
    } yield ()
    optionT.value.void
  }

//  def pushRedButton(userId: Id[User]): IO[Unit] = {
//    val launchF = OptionT.liftF(launchTheMissiles())
//    val optionT = for {
//      user <- OptionT(UsersRepo.UsingIO.getById(userId))
//      _ <- OptionT.when[IO, Unit](user.isThePresident)(())
//      _ <- launchF
//    } yield ()
//    optionT.value.void
//  }

  private def launchTheMissiles(): IO[Unit] =
    IO.delay(println(Boom))

}
