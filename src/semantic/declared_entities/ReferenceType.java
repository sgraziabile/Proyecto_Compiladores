package semantic.declared_entities;

import static main.MainModule.symbolTable;

public class ReferenceType extends Type {

    public ReferenceType(String name) {
        super(name);
    }
    public boolean conformsTo(Type other) {
        boolean conforms = false;
        if(this.name.equals("null")) {
            if(other instanceof ReferenceType) {
                conforms = true;
            }
        } else {

            if (other.getName().equals(this.name)) {
                conforms = true;
            } else {
                conforms = isSubtype(other);
            }
        }
        return conforms;
    }
    private boolean isSubtype(Type other) {
        boolean isSubtype = false;
        for(Class c: symbolTable.getClasses().values()) {
            if(c.getName().equals(this.name)) {
                if(c.getSuperclass() != null && !c.getSuperclass().getLexeme().equals("Object")) {
                    if(c.getSuperclass().getLexeme().equals(other.getName())) {
                        isSubtype = true;
                    } else {
                        String parent = c.getSuperclass().getLexeme();
                        ReferenceType parentType = new ReferenceType(parent);
                        isSubtype = parentType.isSubtype(other);
                    }
                }
            }
        }
        return isSubtype;
    }
}

