package com.timjstewart.akkafun

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext

import akka.util.Timeout
import akka.actor.{Actor, ActorSystem, Props}
import akka.dispatch.ExecutionContexts
import akka.pattern.ask

case class Print(s: String)

case class PrintAck(val response: String)

class Printer extends Actor {
  def receive = {
    case Print(s) => {
      println(s)
      sender ! PrintAck("done!")
    }
  }
}

object HelloWorld {

  implicit val timeout : Timeout = Timeout(5 seconds) 

  implicit val execContext : ExecutionContext = ExecutionContexts.global()

  def main(args: Array[String]) {

    val system  = ActorSystem("HelloWorld")

    val printer = system.actorOf(Props[Printer])

    printer ? Print("Hi") onComplete { x =>
      println(x)
      system.shutdown()
    }
  }

}
