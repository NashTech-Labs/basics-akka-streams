package com.knoldus

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import akka.stream.scaladsl.Flow
import com.knoldus.Sinks.sideEffectSink
import com.knoldus.Sources.{delayBased, numbers}

object AkkaDemo extends App{

  implicit val system: ActorSystem = ActorSystem("Demo")
  implicit val materializer = Materializer

  //different flow
  val flowToDouble: Flow[Int, Int, NotUsed] = Flow[Int].map(_ * 2)
  val groupTwo: Flow[Int, Seq[Int], NotUsed] = Flow[Int].grouped(2)

 numbers.via(flowToDouble).to(sideEffectSink).run()
  delayBased.runWith(sideEffectSink)
}
