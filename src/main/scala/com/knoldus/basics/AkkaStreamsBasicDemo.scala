package com.knoldus.basics

import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.stream.scaladsl.Keep
import com.knoldus.basics.Flows.{flowToDouble, streamSum, streamToUpper}
import com.knoldus.basics.Sinks.sideEffectSink
import com.knoldus.basics.Sources.{delayBased, numbers, single}

object AkkaStreamsBasicDemo extends App {

  implicit val system: ActorSystem = ActorSystem("Demo-Basics")
  implicit val materializer: Materializer.type = Materializer


  single.viaMat(streamToUpper)(Keep.left).toMat(sideEffectSink)(Keep.left).run()
  numbers.via(flowToDouble).to(sideEffectSink).run()
  numbers.via(streamSum).async.runWith(sideEffectSink)
  delayBased.runWith(sideEffectSink)

}
