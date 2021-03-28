# Differentation-Library
<h3> Grammar For the language and the parser </h3>
<code><pre>
        EXPR --> TERM | TERM + EXPR | TERM - EXPR
        TERM --> FACTOR | FACTOR * TERM | FACTOR / TERM
      FACTOR --> FACTOR_PRIME | FACTOR ^ FACTOR_PRIME
FACTOR_PRIME --> <ID, X> | CONSTANT | NUMBER | MATH(EXPR)
</pre>
</code>
<h3> Type of Parser </h3>
<h5><code>Recursive Descent Parser with Backtracking</code></h5>
