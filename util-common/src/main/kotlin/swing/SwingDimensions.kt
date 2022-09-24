package swing

import java.awt.Dimension

infix fun Int.x(other: Int): Dimension = Dimension(this, other)

fun SquareDimension(size: Int): Dimension = Dimension(size, size)