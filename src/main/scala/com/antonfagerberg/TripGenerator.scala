package com.antonfagerberg

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import scala.util.Random


object TripGenerator extends App {
  def createInput: String = {
    val addresses = io.Source.fromURL(getClass.getClassLoader.getResource("addresses")).getLines().toList
    val shuffledAddresses = Random.shuffle(addresses)

    shuffledAddresses
      .sliding(2, 1)
      .flatMap { case List(address1, address2) =>
        if (Random.nextInt(1000) < 1) None
        else Some(s"$address1 <-> $address2")
      }
      .mkString("\n")
  }

  0 until 1000 map { index =>
    val input = createInput
    val solution = TripSolver.solve(input) * 10

    Files.write(Paths.get(s"input/$index"), input.getBytes(StandardCharsets.UTF_8))
    Files.write(Paths.get(s"solution/$index"), solution.toString.getBytes(StandardCharsets.UTF_8))
  }
}
