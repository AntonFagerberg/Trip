package com.antonfagerberg

import scala.annotation.tailrec

object TripSolver {
  def solve(input: String): Int = {
    val sets =
      input
        .split("\n")
        .map(_.split(" <-> ").toSet)
        .toList

    merge(sets).map(_.size).max
  }

  @tailrec
  def merge(sets: List[Set[String]]): List[Set[String]] = {
    val mutation =
      sets.foldLeft(List.empty[Set[String]]) { (acc, currentSet) =>
        if (acc.exists(_.exists(currentSet.contains))) {
          acc
            .map { set =>
              if (set.exists(currentSet.contains)) set ++ currentSet
              else set
            }
        } else {
          currentSet :: acc
        }
      }

    if (!mutation.exists(sets.contains)) merge(mutation)
    else mutation
  }
}
