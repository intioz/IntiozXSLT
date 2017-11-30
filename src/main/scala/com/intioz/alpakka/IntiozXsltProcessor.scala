package com.intioz.alpakka

import scala.xml.Elem

import akka.NotUsed
import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream.FlowShape
import akka.stream.Graph
import akka.stream.scaladsl.Flow
import akka.util.ByteString

object IntiozXsltProcessor {
 
  

  def applyXSLT(xsltDir: String,xsltFileName:String,outDir:String,xmlFileName:String = "NONE",deleteXMLFlag:Boolean): Flow[ByteString, String, NotUsed] ={
    val intiozXSLTGraph: Graph[FlowShape[ByteString, String],NotUsed] = new IntiozXsltProcessorStage(xsltDir,xsltFileName,outDir,xmlFileName:String,deleteXMLFlag)
    Flow.fromGraph(intiozXSLTGraph)
  }

}