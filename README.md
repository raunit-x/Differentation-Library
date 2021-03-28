# Differentation-Library
<h3> Grammar Rules</h3>
<code><pre>
      EXPR         --> TERM | TERM + EXPR | TERM - EXPR
      TERM         --> FACTOR | FACTOR * TERM | FACTOR / TERM
      FACTOR       --> FACTOR_PRIME | FACTOR ^ FACTOR_PRIME
      FACTOR_PRIME --> VAR: X | CONSTANT | NUMBER | MATH(EXPR) | + FACTOR_PRIME | - FACTOR_PRIME | (EXPR)</pre></code>
<h3> Type of Parser </h3>
<code>Recursive Descent Parser with Backtracking</code>
<h3> How to use </h3>
<ol>
<li> Clone the repo </li>
      <li><code>cd</code> to the repo dir</li>
<li>Run: <code>python3 main.py</code></li>
</ol>
