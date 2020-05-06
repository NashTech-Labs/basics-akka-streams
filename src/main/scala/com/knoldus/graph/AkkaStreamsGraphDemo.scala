package com.knoldus.graph

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.knoldus.basics.Sinks.sideEffectSink
import com.knoldus.basics.Sources.numbers
import com.knoldus.graph.Graphs.{extractOne, graphAddProd, pairUpWithToString, pairs}
import com.knoldus.graph.SimplifiedApiExample.source

object AkkaStreamsGraphDemo extends App{
  implicit val system: ActorSystem = ActorSystem("Demo-Graph")
  implicit val materializer: Materializer.type = Materializer

  graphAddProd.run()
  pairs.runWith(extractOne)
  numbers.via(pairUpWithToString).runWith(sideEffectSink)
  source.runWith(sideEffectSink)
}
