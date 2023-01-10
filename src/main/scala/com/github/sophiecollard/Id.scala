package com.github.sophiecollard

import java.util.UUID

final case class Id[A](value: UUID)

object Id {

  def random[A](): Id[A] =
    Id[A](UUID.randomUUID())

}
