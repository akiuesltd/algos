package com.akieus.fn

import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object SomethingScala extends App {
//    printBF(testGraph, 1, 3)

  val list = new ListBuffer[Node]
//  listBreadthFirst(testGraph, list)
  listDepthFirst(testGraph, list)
  list.foreach(x => println(x.id))

  def listBreadthFirst(node: Node, list: ListBuffer[Node]) {
    list += node
    node.children.foreach(listBreadthFirst(_, list))
  }

  def listDepthFirst(node: Node, list: ListBuffer[Node]) {
    list += node
    if (node.children.nonEmpty) {
      val stack = new mutable.Stack[Node]()
      node.children.foreach(stack.push(_))
      stack.foreach(listBreadthFirst(_, list))
    }
  }

  def printBF(node: Node, currentDepth: Int, maxDepth: Int) {
    if (currentDepth <= maxDepth) {
      println(node.id)
      node.children.foreach(printBF(_, currentDepth + 1, maxDepth))
    }
  }

  def testGraph: Node = {
    new Node(1, List(new Node(2, List(new Node(4), new Node(5))), new Node(3, List(new Node(6), new Node(7)))))
  }

  class Node(val id: Int, val children: List[Node]) {
    def this(id: Int) = this(id, List.empty)

    override def toString = id + ": " + children
  }

}
