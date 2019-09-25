package com.github.monkeyj.ast;

import com.github.monkeyj.Token;
import com.github.monkeyj.value.IObject;

public class IfExpression extends Expression {
    private Expression condition;
    private BlockStatement consequence;
    private BlockStatement alternative;

    public IfExpression(Token token) {
        super(token);
    }

    public void expressionNode() {

    }

    public Expression getCondition() {
        return condition;
    }

    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    public BlockStatement getConsequence() {
        return consequence;
    }

    public void setConsequence(BlockStatement consequence) {
        this.consequence = consequence;
    }

    public BlockStatement getAlternative() {
        return alternative;
    }

    public void setAlternative(BlockStatement alternative) {
        this.alternative = alternative;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("if");
        buf.append(condition.toString());
        buf.append(" ");
        if (alternative != null) {
            buf.append("else ");
            buf.append(alternative.toString());
        }
        return buf.toString();
    }

    public IObject accept(NodeVisitor visitor) {
        return visitor.visit(this);
    }
}
