package com.intioz.flow

import java.io.File
import java.io.StringReader

import scala.xml.Elem
import scala.xml.XML


import akka.actor.ActorSystem
import akka.event.Logging
import akka.event.LoggingAdapter
import akka.stream.Attributes
import akka.stream.FlowShape
import akka.stream.Inlet
import akka.stream.Outlet
import akka.stream.stage.GraphStage
import akka.stream.stage.GraphStageLogic
import akka.stream.stage.InHandler
import akka.stream.stage.OutHandler
import akka.stream.stage.StageLogging
import akka.util.ByteString
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource


class IntiozXsltProcessorStage(xsltDir: String,xsltFileName:String,outDir:String,xmlFileName:String = "NONE",deleteXMLFlag:Boolean) extends GraphStage[FlowShape[ByteString, String]] {
  private val in = Inlet[ByteString]("IntiozXsltProcessor.in")
  private val out = Outlet[String]("IntiozXsltProcessor.out")
  override val shape = FlowShape.of(in, out)

  
  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) {
      setHandler(in, new InHandler {
        override def onPush(): Unit = {
          val fileName = xmlFileName match {
            case "NONE" => java.util.UUID.randomUUID().toString()+".xml"
            case _ => xmlFileName
          }
          
          val xmlL = scala.xml.XML.loadString(grab(in).utf8String)
          try {
            val tFactory: TransformerFactory = TransformerFactory.newInstance
            val transformer: Transformer = tFactory.newTransformer(new StreamSource("xsltDir"+"/"+xsltFileName))
            transformer.transform(new StreamSource(new StringReader(xmlL.toString)), new StreamResult(outDir+"/"+xmlFileName))        
            } catch {
            case e: Exception => e.printStackTrace
          }
          val xmlO = XML.loadFile(outDir+"/"+xmlFileName)
          if(deleteXMLFlag){
            new File(outDir+"/"+xmlFileName).delete()
          }
          push(out, xmlO.toString)
        }
      })
      setHandler(out, new OutHandler {
        override def onPull(): Unit = {
          pull(in)
        }
      })
    }
}