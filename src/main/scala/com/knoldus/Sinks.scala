package com.knoldus

import akka.Done
import akka.stream.scaladsl.Sink

import scala.concurrent.Future

object Sinks {
  //different sinks
  val ignoreSink: Sink[Any, Future[Done]] = Sink.ignore
  val sideEffectSink: Sink[Any, Future[Done]] = Sink.foreach(element => println(element))
  val sum: Sink[Int, Future[Int]] = Sink.fold(0) { case (sum, ele) => sum + ele }

}
