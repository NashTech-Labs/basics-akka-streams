package com.knoldus.basics

import akka.NotUsed
import akka.stream.scaladsl.Flow

object Flows {
  //different flow
  val flowToDouble: Flow[Int, Int, NotUsed] = Flow[Int].map(_ * 2)
  val groupTwo: Flow[Int, Seq[Int], NotUsed] = Flow[Int].grouped(2)
  val streamToUpper: Flow[String, String, NotUsed] = Flow[String].map(_.toUpperCase)
  val streamSum: Flow[Int, Int, NotUsed] = Flow[Int].reduce(_ + _)
}
