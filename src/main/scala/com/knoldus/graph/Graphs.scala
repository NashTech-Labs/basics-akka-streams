package com.knoldus.graph

import akka.NotUsed
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, Merge, RunnableGraph, Sink, Source, Unzip, Zip}
import akka.stream.{ClosedShape, FlowShape, SinkShape, SourceShape}
import com.knoldus.basics.Sources.numbers
import com.knoldus.basics.Sinks.{sideEffectSink,ignoreSink}

object Graphs {
  val graphAddProd: RunnableGraph[NotUsed] =
    RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
      import GraphDSL.Implicits._

      val broadcast = builder.add(Broadcast[Int](2))
      val merge = builder.add(Merge[Int](2))

      val addTwo: Flow[Int, Int, NotUsed] = Flow[Int].map(_ + 2)
      val prodTwo: Flow[Int, Int, NotUsed] = Flow[Int].map(_ * 2)
      numbers ~> broadcast ~> addTwo ~> merge ~> sideEffectSink
      broadcast ~> prodTwo ~> merge
      ClosedShape
    })

  val pairs: Source[(Int, Int), NotUsed] = Source.fromGraph(GraphDSL.create() { implicit builder =>
    import GraphDSL.Implicits._

    val zip = builder.add(Zip[Int, Int]())

    numbers.filter(_ % 2 != 0) ~> zip.in0
    numbers.filter(_ % 2 == 0) ~> zip.in1
    SourceShape(zip.out)
  })
  val extractOne: Sink[(Int, Int), NotUsed] = Sink.fromGraph(GraphDSL.create() { implicit builder =>
    import GraphDSL.Implicits._

    val unzip = builder.add(Unzip[Int, Int])
    unzip.out1 ~> ignoreSink
    unzip.out0 ~> sideEffectSink

    SinkShape(unzip.in)
  })

  val pairUpWithToString: Flow[Int, (Int, String), NotUsed] =
    Flow.fromGraph(GraphDSL.create() { implicit b =>
      import GraphDSL.Implicits._

      val broadcast = b.add(Broadcast[Int](2))
      val zip = b.add(Zip[Int, String]())

      broadcast.out(0).map(identity) ~> zip.in0
      broadcast.out(1).map(_.toString) ~> zip.in1

      FlowShape(broadcast.in, zip.out)
    })

}
