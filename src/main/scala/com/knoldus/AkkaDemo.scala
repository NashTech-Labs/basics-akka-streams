package com.knoldus

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Keep}
import com.knoldus.Sinks.sideEffectSink
import com.knoldus.Sources.{delayBased, numbers, single}

object AkkaDemo extends App {

  implicit val system: ActorSystem = ActorSystem("Demo")
  implicit val materializer: Materializer.type = Materializer

  //different flow
  val flowToDouble: Flow[Int, Int, NotUsed] = Flow[Int].map(_ * 2)
  val groupTwo: Flow[Int, Seq[Int], NotUsed] = Flow[Int].grouped(2)
  val streamToUpper: Flow[String, String, NotUsed] = Flow[String].map(_.toUpperCase)
  val streamSum: Flow[Int, Int, NotUsed] = Flow[Int].reduce(_+_)
  single.viaMat(streamToUpper)(Keep.left).toMat(sideEffectSink)(Keep.left).run()
  numbers.via(flowToDouble).to(sideEffectSink).run()
  numbers.via(streamSum).async.runWith(sideEffectSink)
  delayBased.runWith(sideEffectSink)
}
