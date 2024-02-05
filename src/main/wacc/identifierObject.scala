package wacc

// We can just use the AST Type Nodes I believe
// sealed trait TypeNode

sealed trait TYPE extends TypeNode

case class VARIABLE(varType: TYPE) extends TypeNode

case class PARAMETER(paramType: TYPE) extends TypeNode


// Basic WACC types
case class INT(value: Int) extends TYPE {
  require(
    value >= -math.pow(2, 31).toInt && value <= math.pow(2, 31).toInt - 1, 
    "Value must be within the valid range for INT."
  )
}

case class BOOL(value: Boolean) extends TYPE {
  require(
    value == true || value == false, 
    "Value must be a valid Boolean (true or false)."
  )
}

case class CHAR(value: Char) extends TYPE {
  require(
    value.toString.forall(c => c >= 0 && c <= 127), 
    "Value must be a valid 7-bit ASCII string."
  )
}

case class STRING(
  // SHOULD share a common representation (to allow for easy conversion).
  // MUST be represented as pointers and track their number of characters/elements 
  // in their allocated data.
  // SHOULD be stored within the data-segment of the executable to avoid memory leaks. 
  // Empty string is valid, there is no "null" string
) extends TYPE


// Arrays
case class ARRAY(
  elemType: TYPE
) extends PAIRELEM 

// Pairs
sealed trait PAIRELEM extends TYPE

case class PAIR(
  fst: PAIRELEM,
  snd: PAIRELEM
) extends TYPE 
// with LValue? (Ask Aaron)
