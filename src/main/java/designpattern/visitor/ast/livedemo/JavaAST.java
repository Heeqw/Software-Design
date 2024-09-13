package designpattern.visitor.ast.livedemo;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

class SimpleClass {
    public int testMethod(int param) {
        if (param < 0)
            return -1;
        else
            return 1;

    }
}

public class JavaAST {
    public static void main(String[] args) {
        String simpleClassSrc = "class SimpleClass{\n" + //
                "    public int testMethod(int param){\n" + //
                "        if(param < 0 ) return -1;\n" + //
                "        else return 1;\n" + //
                "\n" + //
                "    }\n" + //
                "}";
        CompilationUnit parsed = StaticJavaParser.parse(simpleClassSrc);

        for (TypeDeclaration<?> t : parsed.getTypes()) {
            for (Object m : t.getMembers()) {
                if (m instanceof MethodDeclaration) {
                    System.out.println(((MethodDeclaration) m).getName());
                }
            }
        }

        parsed.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodDeclaration n, Void arg) {
                System.out.println(n.getName());
            }
        }, null);

    }
}
