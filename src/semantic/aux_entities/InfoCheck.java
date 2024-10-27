package semantic.aux_entities;

import semantic.declared_entities.Type;

public class InfoCheck {
    private Type resultType;
    private boolean isAssignable;

    public InfoCheck(Type resultType, boolean isAssignable) {
        this.resultType = resultType;
        this.isAssignable = isAssignable;
    }
    public Type getResultType() {
        return resultType;
    }
    public boolean isAssignable() {
        return isAssignable;
    }
    public void setResultType(Type resultType) {
        this.resultType = resultType;
    }
    public void setAssignable(boolean isAssignable) {
        this.isAssignable = isAssignable;
    }
}
