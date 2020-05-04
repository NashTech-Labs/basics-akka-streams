package com.knoldus

import akka.NotUsed
import akka.actor.Cancellable
import akka.stream.scaladsl.Source

import scala.concurrent.duration._

object Sources {
  //different sources
  val numbers: Source[Int, NotUsed] = Source(1 to 10)
  val single: Source[String, NotUsed] = Source.single("no repetition")
  val delayBased: Source[String, Cancellable] = Source.tick(initialDelay = 1.second,
    interval = 5.seconds,
    tick = "I'll be sent after the delay")
  val emptySource: Source[Int, NotUsed] = Source.empty[Int]

}
