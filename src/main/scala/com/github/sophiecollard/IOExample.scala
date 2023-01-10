package com.github.sophiecollard

import cats.effect.{ExitCode, IO, IOApp}

object IOExample extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    pushRedButton(Users.donald.id).as(ExitCode.Success)

  def pushRedButton(userId: Id[User]): IO[Unit] =
    for {
      maybeUser <- Users.UsingIO.getById(userId)
      _ <- if (maybeUser.exists(_.isThePresident))
        launchTheMissiles()
      else
        IO.delay(println("Unauthorized user: launch aborted"))
    } yield ()

  private def launchTheMissiles(): IO[Unit] =
    IO.delay(println("The End"))

}
