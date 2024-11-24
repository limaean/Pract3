
import org.scalatest.funsuite.AnyFunSuite
import org.scalactic.Tolerance.convertNumericToPlusOrMinusWrapper

val tolerance = 0.00001

class GridSuite extends AnyFunSuite:
  test("Part1: Correct for regular Grid") {
    var myGrid = Grid(20)
    myGrid.add(BaseStation("Alpha", Cell(2, 7)))
    myGrid.add(BaseStation("Gamma", Cell(5, 20))) // outside grid
    myGrid.add(BaseStation("Delta", Cell(20, 2))) // outside grid
    myGrid.add(BaseStation("Epsilon", Cell(2, 7))) // same cell as Alpha
    myGrid.add(BaseStation("Beta", Cell(5, 3)))
    assert(myGrid.toString === "Alpha(2, 7), Beta(5, 3)")
  }

  test("Part1: correct for 1x1 Grid") {
    var myGrid = Grid(1)
    myGrid.add(BaseStation("Alpha", Cell(0, 0)))
    myGrid.add(BaseStation("Gamma", Cell(5, 20)))
    myGrid.add(BaseStation("Delta", Cell(20, 2)))
    myGrid.add(BaseStation("Zeta", Cell(-1, -1)))
    myGrid.add(BaseStation("Beta", Cell(5, 3)))
    assert(myGrid.toString === "Alpha(0, 0)")
  }

  test("Part1: correct for 0x0 Grid") {
    try
      var myGrid = Grid(0)
    catch
      case e:Exception => assert(true)
  }

  test("Part2: Coverage") {
    var myGrid = Grid(4)
    val alpha = BaseStation("Alpha", Cell(1, 1))
    myGrid.add(alpha)
    val beta = BaseStation("Beta", Cell(3, 3))
    myGrid.add(beta)
    assert(myGrid.coverage(Cell(1, 1)) === 1.0 +- tolerance)
    assert(myGrid.coverage(Cell(3, 3)) === 1.0 +- tolerance)
    assert(myGrid.coverage(Cell(0, 0)) === 0.41421 +- tolerance)
    assert(myGrid.coverage(Cell(2, 2)) === 0.41421 +- tolerance)
    assert(myGrid.coverage(Cell(0, 2)) === 0.41421 +- tolerance)
    assert(myGrid.coverage(Cell(2, 0)) === 0.41421 +- tolerance)
    assert(myGrid.coverage(Cell(0, 1)) === 0.5 +- tolerance)
    assert(myGrid.coverage(Cell(1, 0)) === 0.5 +- tolerance)
    assert(myGrid.coverage(Cell(2, 3)) === 0.5 +- tolerance)
    assert(myGrid.coverage(Cell(3, 2)) === 0.5 +- tolerance)
    assert(myGrid.coverage(Cell(3, 0)) === 0.30902 +- tolerance)
    assert(myGrid.coverage(Cell(0, 3)) === 0.30902 +- tolerance)
  }

  test("Part2: All Coverage") {
    var myGrid = Grid(4)
    val alpha = BaseStation("Alpha", Cell(1, 1))
    myGrid.add(alpha)
    val beta = BaseStation("Beta", Cell(0, 3))
    myGrid.add(beta)
    val expected = List(0.41421, 0.5, 0.41421, 0.30902, 0.5, 1.0, 0.5, 0.33333, 0.5, 0.5, 0.41421, 0.30902, 1.0, 0.5, 0.33333, 0.26120)
    val iterExpected = expected.iterator
    val iterActual = myGrid.allCoverage.iterator
    while iterExpected.hasNext do
      assert(iterExpected.next() === iterActual.next +- tolerance)
  }

  test("Part3: Grid Quality") {
    var myGrid = Grid(4)
    val alpha = BaseStation("Alpha", Cell(1, 1))
    myGrid.add(alpha)
    val beta = BaseStation("Beta", Cell(3, 3))
    myGrid.add(beta)
    assert(myGrid.gridQuality === 0.49635 +- tolerance)
  }

  test("Part3: Neighbours mid grid") {
    val neighbours = Cell(2, 3).neighbours
    assert(neighbours.length === 8)
    assert(neighbours.contains(Cell(1, 2)))
    assert(neighbours.contains(Cell(1, 3)))
    assert(neighbours.contains(Cell(1, 4)))
    assert(neighbours.contains(Cell(2, 2)))
    assert(neighbours.contains(Cell(2, 4)))
    assert(neighbours.contains(Cell(3, 2)))
    assert(neighbours.contains(Cell(3, 3)))
    assert(neighbours.contains(Cell(3, 4)))
  }

  test("Part3: Neighbours in corner") {
    var myGrid = Grid(4)
    val neighbours = myGrid.neighbours(Cell(0, 0))
    assert(neighbours.length === 3)
    assert(neighbours.contains(Cell(1, 0)))
    assert(neighbours.contains(Cell(0, 1)))
    assert(neighbours.contains(Cell(1, 1)))
  }
  
  // test that neighbours in correct order is missing

  test("Part3: bestNeighbour 1") {
    var myGrid = Grid(4)
    val alpha = BaseStation("Alpha", Cell(0, 0))
    myGrid.add(alpha)
    val (cell, quality) = myGrid.bestNeighbour(alpha)
    assert(Cell(1, 1) === cell )
    assert(quality === 0.42629 +- tolerance )
  }

  test("Part3: bestNeighbour 2") {
    var myGrid = Grid(4)
    val alpha = BaseStation("Alpha", Cell(3, 3))
    myGrid.add(alpha)
    val (cell, quality) = myGrid.bestNeighbour(alpha)
    assert(Cell(2, 2) === cell)
    assert(quality === 0.42629 +- tolerance )
  }

  test("Part3: bestNeighbour 3") {
    var myGrid = Grid(7)
    val alpha = BaseStation("Alpha", Cell(3, 3))
    myGrid.add(alpha)
    val (cell, quality) = myGrid.bestNeighbour(alpha)
    assert(Cell(3, 3) === cell)
    assert(quality === 0.30467 +- tolerance )
  }

  // more robust tests required for Part 4
  
  test("Part4: Improve Grid 1") {
    var myGrid = Grid(20)
    val alpha = BaseStation("Alpha", Cell(0, 0))
    val beta = BaseStation("Beta", Cell(19, 19))
    myGrid.add(alpha)
    myGrid.add(beta)
    myGrid.improveGrid
    assert("Alpha(6, 6), Beta(13, 14)" === myGrid.toString)
  }

  test("Part4: Improve Grid 2") {
    var myGrid = Grid(20)
    val alpha = BaseStation("Alpha", Cell(0, 0))
    val beta = BaseStation("Beta", Cell(19, 19))
    val gamma = BaseStation("Gamma", Cell(0, 19))
    val delta = BaseStation("Delta", Cell(19, 0))
    myGrid.add(alpha)
    myGrid.add(beta)
    myGrid.add(gamma)
    myGrid.add(delta)
    myGrid.improveGrid
    assert("Alpha(5, 5), Beta(14, 14), Gamma(4, 15), Delta(15, 4)" === myGrid.toString)
  }
