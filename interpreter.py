from nodes import *
from values import Result
from derivative_table import derivative_table


class Interpreter:
    def visit(self, node):
        method_name = f"visit_{type(node).__name__}"
        return getattr(self, method_name)(node)

    def visit_NumberNode(self, node):
        return Result('0')

    def visit_ConstantNode(self, node):
        return Result('0')

    def visit_VarNode(self, node):
        return Result('1')

    def visit_AddNode(self, node):
        return Result(f"{self.visit(node.node_a).value} + {self.visit(node.node_b).value}")

    def visit_SubtractNode(self, node):
        return Result(f"{self.visit(node.node_a).value} - {self.visit(node.node_b).value}")

    def visit_MultiplyNode(self, node):
        f = self.visit(node.node_a).value
        g = self.visit(node.node_b).value
        return Result(f"(({f} * {node.node_b.__repr__()}) + ({node.node_a.__repr__()} * {g}))")

    def visit_PowerNode(self, node: PowerNode):
        f_prime = self.visit(node.node_a).value
        g_prime = self.visit(node.node_b).value
        f = node.node_a.__repr__()
        g = node.node_b.__repr__()
        return Result(f"{node.__repr__()} * (({g} / {f}) * {f_prime} + ({g_prime} * log({f})))")

    def visit_MathNode(self, node: MathNode):
        f_prime = self.visit(node.value).value
        f = node.value.__repr__()
        operation_prime = derivative_table[node.operation]
        return Result(f"{operation_prime}({f}) * ({f_prime})")

    def visit_PlusNode(self, node):
        return self.visit(node.node)

    def visit_MinusNode(self, node):
        return Result(f"-{self.visit(node.node)}")

    def visit_DivideNode(self, node):
        f_prime = self.visit(node.node_a).value
        g_prime = self.visit(node.node_b).value
        f = node.node_a.__repr__()
        g = node.node_b.__repr__()
        return Result(f"(({g} * {f_prime}) - ({f} * {g_prime})) / ({g} ^ 2)")
