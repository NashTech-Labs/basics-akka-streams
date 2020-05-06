package com.knoldus.graph

import akka.NotUsed
import akka.stream.scaladsl.{Merge, Source}

object SimplifiedApiExample {
  val sourceOne: Source[String, NotUsed] = Source(List("hi", "hello"))
  val sourceTwo: Source[Int, NotUsed] = Source(List(1, 2))
  val source: Source[Any, NotUsed] = Source.combine(sourceOne, sourceTwo)(Merge(_))
}
