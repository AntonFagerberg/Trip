import java.nio.charset.StandardCharsets
import java.nio.file.{Paths, Files}

import com.antonfagerberg.{TripGenerator, TripSolver}
import org.scalatest._

class TripSpec extends FlatSpec with Matchers {
  "The file addresses " should "contain 10,000 unique addresses" in {
    val addressList =
      scala.io.Source.fromURL(
        getClass.getClassLoader.getResource("addresses")
      ).getLines().toList

    addressList.distinct.length shouldBe 10000
  }

  "Solver" should "merge transitive sets correctly" in {
    val input =
      List(
        Set("a", "b"),
        Set("b", "c"),
        Set("d", "e"),
        Set("g", "f"),
        Set("i", "h"),
        Set("j", "e")
      )

    val result = TripSolver.merge(input)

    result should contain theSameElementsAs List(Set("a", "b", "c"), Set("d", "e", "j"), Set("g", "f"), Set("i", "h"))
  }

  "Solver" should "find the max value in a simple case" in {
    val input =
      "a <-> b\n" +
      "c <-> d\n" +
      "e <-> f\n"

    TripSolver.solve(input) shouldEqual 2
  }

  "Solver" should "find the max value in transitive groups" in {
    val input =
      "a <-> b\n" +
      "b <-> c\n" +
      "c <-> d\n" +
      "e <-> f\n"

    TripSolver.solve(input) shouldEqual 4
  }

  "Solver" should "should solve the example in the readme" in {
    val input =
      "2698 Oriole Park <-> 8654 Ridgeview Plaza\n" +
      "6767 Upham Trail <-> 80 Kenwood Drive\n" +
      "8222 Lillian Park <-> 2698 Oriole Park\n" +
      "19 Bluestem Junction <-> 8654 Ridgeview Plaza\n" +
      "74835 Ronald Regan Alley <-> 2849 Grim Place\n" +
      "7816 Stang Drive <-> 6767 Upham Trail\n" +
      "6483 Declaration Terrace <-> 19 Bluestem Junction"

    TripSolver.solve(input) shouldEqual 5
  }

  "Solver" should "work on generated input" in {
    val input =
      scala.io.Source.fromURL(
        getClass.getClassLoader.getResource("input")
      ).mkString("")

    val solution =
      scala.io.Source.fromURL(
        getClass.getClassLoader.getResource("solution")
      ).mkString("").toInt

    TripSolver.solve(input) * 10 should equal(solution)
  }
}
